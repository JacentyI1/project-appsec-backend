package util

import (
	"fmt"
	"log/slog"
	"os"
        "errors"
)

type State struct {
    // Filepath is ok for now. That means we're not keeping file open.
    // Whenever new file needs to be saved, we will open the file again.
    // If this causes too many delays we will need to keep open file descriptior here.
    filepath string
}

func GetState(filepath string) State {
    if _, err := os.Stat(filepath); errors.Is(err, os.ErrNotExist) {
        f, err := os.Create(filepath)
        if err != nil {
            panic(err)
        }
        f.Close()
        slog.Info(fmt.Sprintf(
            "Creted new EbooksState object with filepath %v",
            filepath,
        ))
    }
    return State { filepath }
}

func (s *State) SavePost(post string) {
    f, err := os.OpenFile(s.filepath, os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    defer f.Close()
    if err != nil {
        panic(err)
    }
    if _, err := f.WriteString(post); err != nil { 
        panic(err)
    }
    if _, err := f.Write([]byte { 0 }); err != nil {
        panic(err)
    }
}

func (s *State) Close() {}

func (s *State) IsEmpty() bool {
    // TODO
    return false
}

func (s *State) AllPosts() []string {
    contents, err := os.ReadFile(s.filepath)

    if err != nil {
        slog.Warn(
            "Cannot open state file for reading. If this is first run then it's normal." +
            " New file will be created when state needs to save.",
        )
        return []string {}
    }

    posts := make([]string, 0)
    previousPostEnd := 0
    for i, byte := range contents {
        if byte != 0 { continue } // We separeate posts with null character

        post := string(contents[previousPostEnd:i])
        previousPostEnd = i+1 // +1 to skip null
        posts = append(posts, post)
    }

    return posts
}
