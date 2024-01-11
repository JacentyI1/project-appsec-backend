package raw

type Ignore struct {}

type InstanceInfo struct {
    Version string `json:"version"`
    MaxTootChars int `json:"max_toot_chars"`
    Title string `json:"title"`
    Thumbnail string `json:"thumbnail"`
    Uri string `json:"uri"`
    Description string `json:"description"`
    Email string `json:"email"`
}

type nodeInfo21Metadata struct {
    AccoutActivationRequired bool `json:"accoutActivationRequired"`
    NodeName string `json:"nodeName"`
    LocalBubbleInstances []string `json:"localBubbleInstances"`
    NodeDescription string `json:"NodeDescription"`
    Features []string `json:"features"`
}

type NodeInfo21 struct {
    Metadata nodeInfo21Metadata `json:"metadata"`
    Protocols []string `json:"protocols"`
    Software map[string]string `json:"software"`
    Version string `json:"version"`
}

type Status struct {
    Reblogged bool `json:"reblogged"`
    Muted bool `json:"muted"`
    In_reply_to_account_id string `json:"in_reply_to_account_id"`
    Uri string `json:"uri"`
    Replies_count int `json:"replies_count"`
    Pinned bool `json:"pinned"`
    Id string `json:"id"`
    Visibility string `json:"visibility"`
    In_reply_to_id string `json:"in_reply_to_id"`
    Content string `json:"content"`
    Created_at string `json:"created_at"`
    Edited_at string `json:"edited_at"`
    Bookmarked bool `json:"bookmarked"`
    Url string `json:"url"`
    Spoiler_text string `json:"spoiler_text"`
    Sensitive bool `json:"sensitive"`
    Reblogs_count int `json:"reblogs_count"`
    Language string `json:"language"`
    Favourites_count int `json:"favourites_count"`
    Favourited bool `json:"favourited"`
}

type OutgoingStatus struct {
    Status string `json:"status"`
    SpoilerText string `json:"spoiler_text"`
    Sensitive bool `json:"sensitive"`
    ExpiresIn int `json:"expires_in"`
    ContentType string `json:"content_type"`
    Language string `json:"language"`
}

type Account struct {
    AcceptsDirectMessagesFrom string `json:"acceptsDirectMessagesFrom"`
    Acct string `json:"acct"`
    Avatar string `json:"avatar"`
    AvatarStatic string `json:"avatar_static"`
    Bot bool `json:"bot"`
    CreatedAt string `json:"created_at"`
    DisplayName string `json:"display_name"`
    FollowRequestCount int `json:"follow_request_count"`
    FollowersCount int `json:"followers_count"`
    FollowingCount int `json:"following_count"`
    Fqn string `json:"fqn"`
    Id string `json:"id"`
    LastStatusAt string `json:"last_status_at"`
    Locked bool `json:"locked"`
    Note string `json:"note"`
    Username string `json:"username"`
    Url string `json:"url"`
}

type OauthToken struct {
    AccessToken string `json:"access_token"`
    CreatedAt int `json:"created_at"`
    ExpiresIn int `json:"expires_in"`
    Id int `json:"id"`
    Scope string `json:"scope"`
    RefreshToken string `json:"refresh_token"`
    TokenType string `json:"token_type"`
}

