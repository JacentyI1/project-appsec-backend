package util

import (
    "testing" 
)

func TestLinkFiltering(t *testing.T) {
    tc := TextChecker {
        FilterQuotedPosts: false,
        FilterLinks: true,
    }

    noLink := "http should be banned"
    link := "http should be banned https://motherfuckingwebsite.com"

    if !tc.IsSuitable(noLink) {
        t.Fatal("Post without link was detected as containing link")
    }
    if tc.IsSuitable(link) {
        t.Fatal("Post with link was not detected")
    }
}

func TestQuotedPosts(t *testing.T) {
    tc := TextChecker {
        FilterQuotedPosts: true,
        FilterLinks: false,
    }

    noQuote := "http should be banned"
    quote := `
http should be banned"

RE:
https://motherfuckingwebsite.com`

    if !tc.IsSuitable(noQuote) {
        t.Fatal("Post without quote was detected as containing quote")
    }
    if tc.IsSuitable(quote) {
        t.Fatal("Post with quote was not detected")
    }
}
