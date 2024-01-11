package raw

import (
    "net/http"
    "net/url"
)

func RequestInstanceInfo(protocol, instance string) (InstanceInfo, error) {
    var info InstanceInfo
    url, err := buildURL(
        protocol,
        instance,
        []string { "/api/v1/instance" },
        map[string]string {},
    )
    if err != nil {
        return info, err
    }
    err = getBody(url, &info)
    return info, err
}

func RequestNodeInfo21(protocol, instance string) (NodeInfo21, error) {
    var info NodeInfo21
    url, err := buildURL(
        protocol,
        instance,
        []string { "/nodeinfo/2.1.json" },
        map[string]string {},
    )
    if err != nil {
        return info, err
    }
    err = getBody(url, &info)
    return info, err
}

func SendStatus(protocol, instance, token string, status OutgoingStatus) error {
    url, err := buildURL(
        protocol,
        instance,
        []string {
            "/api/v1/statuses/",
        },
        map[string]string {},
    )
    if err != nil {
        return err
    }
    var ignore Ignore
    err = postBody(url, token, status, &ignore)
    return err
}

// Newest returned status will have index 0.
func AccountStatuses(protocol, instance, username string, numOfStatuses, offset int) ([]Status, error) {
    // TODO
    // - parameterize limit
    // - parameterize offset
    var posts []Status
    url, err := buildURL(
        protocol,
        instance,
        []string {
            "/api/v1/accounts/",
            username,
            "/statuses",
        },
        map[string]string {
            "with_muted": "false",
            "exclude_reblogs": "true",
            "exclude_replies": "true",
            "limit": "10",
            "offset": "0",
        },
    )
    if err != nil {
        return posts, err
    }
    err = getBody(url, &posts)
    return posts, err
}

func RequestAccount(protocol, instance, username string) (Account, error) {
    // username can be either just username for local accounts
    // or full handle for both local and remote accounts
    var account Account
    url, err := buildURL(
        protocol,
        instance,
        []string {
            "/api/v1/accounts/",
            username,
        },
        map[string]string {},
    )
    if err != nil {
        return account, err
    }
    err = getBody(url, &account)
    return account, err
}

func RequestOauthToken(protocol, instance, clientId, clientSecret, clientToken string) (OauthToken, error) {
    var oauthToken OauthToken
    urlStr, err := buildURL(
        protocol,
        instance,
        []string {
            "/oauth/token",
        },
        map[string]string {},
    )

    res, err := http.PostForm(urlStr, url.Values {
        "scope": {"read write"},
        "client_id": {clientId},
        "client_secret": {clientSecret},
        "redirect_uri": {"urn:ietf:wg:oauth:2.0:oob"},
        "code": {clientToken},
        "grant_type": {"authorization_code"},
    })
    if err != nil {
        return oauthToken, err
    }

    err = parseJsonBodyFromResponse(res, &oauthToken)
    return oauthToken, err
}
