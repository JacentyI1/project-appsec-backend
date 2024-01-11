package util

import (
    "strings"
)

type bracketType [2]string

// Enum
type bracketTypes struct {
    Square bracketType
    Curly bracketType
    Parenthesis bracketType
    Quotes bracketType
}
var BracketTypes bracketTypes = bracketTypes {
    Square: bracketType {"[", "]"},
    Curly: bracketType {"{", "}"},
    Parenthesis: bracketType {"(", ")"},
    Quotes: bracketType {"\"", "\""},
}


type BracketConfiguration struct {
    BracketType bracketType
    CompleteLeft bool
    CompleteRight bool
}

type TextChecker struct {
    FilterQuotedPosts bool
    FilterLinks bool
    BracketConfigurations []BracketConfiguration
}

func (self *TextChecker) IsSuitable(text string) bool {
    isBaseValid :=
        isAtLeastThreeWords(text) &&
        (!self.FilterQuotedPosts || !isQutedPost(text)) &&
        (!self.FilterLinks || !containsLink(text))

    return isBaseValid
}

func (self *TextChecker) Fixup (text string) string {
    return text
}

func isAtLeastThreeWords(text string) bool {
    return len(strings.Split(text, " ")) > 3
}

func isQutedPost(text string) bool {
    // TODO
    return false
}

func containsLink(text string) bool {
    // TODO
    return false
}
