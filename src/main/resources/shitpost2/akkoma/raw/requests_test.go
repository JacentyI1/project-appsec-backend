package raw

import "testing"

func TestBuildURL(t *testing.T) {
    wanted := "https://awawa.cat/api/v1/accounts/wilmhit/statuses?limit=10&with_muted=false"
    actual, err := buildURL(
        "https",
        "awawa.cat",
        []string {
            "/api/v1/accounts/",
            "wilmhit",
            "/statuses",
        },
        map[string]string {
            "with_muted": "false",
            "limit": "10",
        },
    )

    t.Log("Actual URL=", actual)

    if err != nil {
        t.Fatal(err)
    }
    if actual != wanted {
        t.Fatal("Actual is not wanted.")
    }
}

func TestAccountStatuses(t *testing.T) {
    _, err := AccountStatuses("https", "awawa.cat", "wilmhit", 10, 0)
    if err != nil {
        t.Fatal(err)
    }
}

func TestRequestInstanceInfo(t *testing.T) {
    _, err := RequestInstanceInfo("https", "awawa.cat")
    if err != nil {
        t.Fatal(err)
    }
}
