package akkoma

// This package is in esence rewrite of <below> but in Golang
// https://github.com/robertoszek/pleroma-bot/blob/master/pleroma_bot/_pleroma.py

import (
	"errors"
        "time"
        "fmt"
        "math/rand"
        "awawa.cat/ebooks/akkoma/raw"
	"log/slog"
	"strings"
)

type OutgoingPost = raw.OutgoingStatus
type IncomingPost = raw.Status

//////////////
// Session
//////////////

type Session struct {
    BotToken string
    BotClientId string
    BotClientSecret string
    Instance string
    Protocol string
    DryRun bool
    isOpen bool
    token string
    refreshToken string
}

func (s *Session) verifyInstance() error {
    _, err := raw.RequestNodeInfo21(s.Protocol, s.Instance)
    if err != nil {
        return err
    }
    instanceInfo, err := raw.RequestInstanceInfo(s.Protocol, s.Instance)
    if err != nil {
        return err
    }

    if !strings.Contains(instanceInfo.Version, "Akkoma") &&  
        !strings.Contains(instanceInfo.Version, "Pleroma") {
        return errors.New(
            "Your software is not compatible. Bot requires Akkoma or Pleroma.",
        )
    }
    return nil
}

func (s *Session) refreshSession() (string, error) {
    // TODO check expiration
    // This is low prio because akkoma gives tokens valid for 100y
    slog.Info("Refreshing oauth token")
    return "", nil
}

func (s *Session) Open() error {
    if s.Protocol != "http" && s.Protocol != "https" {
        return errors.New("Protocol is not http or https")
    }

    if s.DryRun {
        slog.Info("Instance verification and oauth athorization is skipped")
    } else {
        err := s.verifyInstance()
        if err != nil {
            return err
        }
        slog.Info("Instance is up. Trying to request oauth token.")

        tokenResponse, err := raw.RequestOauthToken(
            s.Protocol,
            s.Instance,
            s.BotClientId,
            s.BotClientSecret,
            s.BotToken,
        )
        if err != nil {
            return err
        }
        if tokenResponse.TokenType != "Bearer" {
            return errors.New(
                "Instance granted me token but it wasn't Bearer token.",
            )
        }
        slog.Info("Token was granted.")
        s.token = tokenResponse.AccessToken
        s.refreshToken = tokenResponse.RefreshToken
    }

    s.isOpen = true
    return nil
}

func (s *Session) Close() error {
    if !s.isOpen {
        return errors.New("Session not open")
    }
    s.isOpen = false
    s.token = ""
    s.refreshToken = ""
    return nil
}

func (s *Session) SendPost(post OutgoingPost) error {
    if !s.isOpen {
        return errors.New("Session not open")
    }

    if (s.DryRun) {
        return nil
    } else {
        return raw.SendStatus(s.Protocol, s.Instance, s.token, post)
    }
}

func UploadMedia(media []byte) (string, error) {
    panic("Not supported!") 
}

//////////////
// User
//////////////

type User struct {
    Username string
    Instance string
    Handle string
}

func ParseUserFromHandle(handle string) User {
    parts := strings.Split(handle, "@")
    return User {
        Username: parts[0],
        Instance: parts[1],
        Handle: handle,
    }
}

func (u *User) ScrapMostPosts(s *Session) []IncomingPost {
    if s.DryRun {
        return []raw.Status {
            { Content: "This is a DryRun init scrap", Id: fakeId(), Language: "en" },
        }
    }

    // TODO
    time.Sleep(3000 * time.Millisecond)

    return make([]raw.Status, 0)
}

func (u *User) CheckIfExists(s *Session) bool {
    _, err := raw.RequestAccount("https", u.Instance, u.Username)
    if err != nil {
        slog.Warn("Could not validate user:")
        slog.Warn(err.Error())
        return false
    }
    return true
}

func (u *User) Subscribe(s *Session) UserSubscribe {
    return UserSubscribe {
        user: u,
        latestReadId: "",
        latestsPosts: make([]raw.Status, 0),
        session: s,
    }
}

//////////////
// UserSubscibe
//////////////

type UserSubscribe struct {
    user *User
    latestReadId string
    session *Session

    // The newest post will have index 0
    latestsPosts []raw.Status
}

func (us *UserSubscribe) HasUnread() bool {
    if len(us.latestsPosts) == 0 {
        return false
    }
    return us.latestsPosts[0].Id != us.latestReadId
}

func (self *UserSubscribe) Update() error {
    if self.session.DryRun {
        slog.Info("Adding fake posts for dryrun")
        self.addStatuses([]IncomingPost {
            { Content: "Dryrun#1", Id: fakeId(), Language: "en" },
            { Content: "Dryrun#2", Id: fakeId(), Language: "en" },
        })
        self.cleanOldStatuses()
        return nil
    }

    statuses, err := raw.AccountStatuses(
        self.session.Protocol,
        self.user.Instance,
        self.user.Username,
        10,
        0,
    )
    if err != nil {
        return err
    }
    self.addStatuses(statuses)
    self.cleanOldStatuses()
    return nil
}

func (self *UserSubscribe) addStatuses(statuses []raw.Status) {
    isAlreadyInSelf := func(id string) bool {
        for _, status := range self.latestsPosts {
            if status.Id == id { return true }
        }
        return false
    }

    toInsert := []raw.Status {}
    for _, status := range statuses {
        if !isAlreadyInSelf(status.Id) {
            toInsert = append(toInsert, status)
        }
    }
    self.latestsPosts = append(toInsert, self.latestsPosts...)
}

func (self *UserSubscribe) getLatestReadIndex() int {
    for index, status := range self.latestsPosts {
        if status.Id == self.latestReadId {
            return index
        }
    }
    return -1
}

func (self *UserSubscribe) cleanOldStatuses() {
    i := self.getLatestReadIndex()
    if i == 0 {
        self.latestsPosts = self.latestsPosts[:0]
    } else if i != -1 { 
        self.latestsPosts = self.latestsPosts[:len(self.latestsPosts) + 1 - i]
    }
}

func (self *UserSubscribe) GetNext() (raw.Status, error) {
    if len(self.latestsPosts) == 0 || self.latestsPosts[0].Id == self.latestReadId {
        return raw.Status {}, errors.New("Not updated but tried to read!")
    }
    i := self.getLatestReadIndex()
    oldestUnread := self.latestsPosts[i-1]
    self.latestReadId = oldestUnread.Id
    return oldestUnread, nil
}

func (self *UserSubscribe) GetAllUnread() ([]raw.Status, error) {
    if len(self.latestsPosts) == 0 || self.latestsPosts[0].Id == self.latestReadId {
        return []raw.Status {}, errors.New("Not updated but tried to read!")
    }

    if (self.getLatestReadIndex() == -1) {
        self.latestReadId = self.latestsPosts[0].Id
        return self.latestsPosts, nil
    } else {
        posts := self.latestsPosts[:self.getLatestReadIndex()]
        self.latestReadId = self.latestsPosts[0].Id
        return posts, nil
    }
}

func fakeId() string {
    return fmt.Sprintf("post%v",rand.Intn(100000))
}
