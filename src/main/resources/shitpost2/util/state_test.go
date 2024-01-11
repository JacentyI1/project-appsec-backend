package util

import (
    "testing" 
    "slices"
    "os"
)

func __cleanup_after_tests() {
    err := os.RemoveAll("./.tmp_ebooks_tests")
    if err != nil {
        panic(err)
    }
}

func TestSaving(t *testing.T) {
    defer __cleanup_after_tests()
    __cleanup_after_tests()

    if err := os.Mkdir(".tmp_ebooks_tests", 0777); err != nil {
        t.Fatal(err)
    }
    file := ".tmp_ebooks_tests/state.test"

    s := GetState(file) 
    s.SavePost("A")
    s.SavePost("B")
    s.SavePost("ABC")

    wanted := []byte {'A', 0, 'B', 0, 'A', 'B', 'C', 0}
    actual, err := os.ReadFile(file)

    if err != nil {
        t.Fatal(err)
    }

    if ! slices.Equal(wanted, actual) {
        t.Fatal("Actual is not wanted. Actual", actual, "Wanted", wanted)
    }
}

func TestGetAll(t *testing.T) {
    defer __cleanup_after_tests()
    __cleanup_after_tests()

    if err := os.Mkdir(".tmp_ebooks_tests", 0777); err != nil {
        t.Fatal(err)
    }
    file := ".tmp_ebooks_tests/state.test"

    s := GetState(file) 
    s.SavePost("A")
    s.SavePost("B")
    s.SavePost("ABC")

    actual := s.AllPosts() 
    wanted := []string { 
        "A",
        "B",
        "ABC",
    }

    for i := range actual {
        if actual[i] != wanted[i] {
            t.Fatal("Actual[i] is not wanted[i]. Actual", []byte(actual[i]), "Wanted", []byte(wanted[i]), "for string", actual[i])
        }
    }
}
