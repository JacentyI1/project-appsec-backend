package akkoma

import "testing"

func TestUserParsinbg(t *testing.T) {
    wanted := User {
        Username: "wilmhit",
        Instance: "awawa.cat",
        Handle: "@wilmhit@awawa.cat",
    }
    actual := ParseUserFromHandle("@wilmhit@awawa.cat")
    if actual != wanted {
        t.Fatal("Actual is not wanted")
    }
}

func TestUserValid(t *testing.T) {
    user := ParseUserFromHandle("@wilmhit@awawa.cat")
    if !user.CheckIfExists() {
        t.Fatal("Cannot validate a valid user")
    }
}

func TestUserTracking(t *testing.T) {
    user := ParseUserFromHandle("@wilmhit@awawa.cat")
    s := Session {}
    sub := user.Subscribe(&s)
    _ = sub
}


