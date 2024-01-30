package util

import (
	"os"
	"path/filepath"
	"slices"
	"testing"
)

func TestXdgGetLocation(t *testing.T) {
    os.Setenv("XDG_CONFIG_HOME", "$HOME/.config")
    wanted := "$HOME/.config/ebooks/config_test.json"
    actual := getLocation("config_test.json")
    t.Logf("Actual path returned by getLocation is %v", actual)

    wanted, err := filepath.Abs(wanted)
    if err != nil {
        t.Fatal(err)
    }

    actual, err = filepath.Abs(actual)
    if err != nil {
        t.Fatal(err)
    }

    if wanted == actual {
        t.Fatal("Actual is not wanted. Actual", actual, "Wanted", wanted)
    }
}

func TestCustomGetLocation(t *testing.T) {
    os.Setenv("EBOOKS_CONFIG_PATH", "./")
    wanted := "./config_test.json"
    actual := getLocation("config_test.json")
    t.Logf("Actual path returned by getLocation is %v", actual)

    if wanted == actual {
        t.Fatal("Actual is not wanted. Actual", actual, "Wanted", wanted)
    }
}

func TestIsHandleOk(t *testing.T) {
    tests := []string {
        "@wilmhit@awawa.cat",
        "@onii-chan@tech.lgbt",
        "@tech.lgbt",
        "@@tech.lgbt",
        "wilmhit@awawa.cat",
    }
    wanted := []bool {
        true,
        true,
        false,
        false,
        false,
    }
    actual := []bool {}
    for _, test := range tests {
        actual = append(actual, isHandleOk(test))
    }

    if !slices.Equal(wanted, actual) {
        t.Fatal("Actual is not wanted. Actual", actual, "Wanted", wanted)
    }
}
