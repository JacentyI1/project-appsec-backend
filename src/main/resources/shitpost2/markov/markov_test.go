package markov

import (
	"slices"
	"testing"
)

func TestWordSplitting(t *testing.T) {
    type test struct {
        input string
        want  []string
    }

    tests := [5]test {
        {input: "Hello World", want: []string {"Hello", "World", ""}},
        {input: "Hello World!", want: []string {"Hello", "World", "!", ""}},
        {input: "OwO!", want: []string {"OwO", "!", ""}},
        {input: "p@ssword", want: []string {"p@ssword", ""}},
        {input: "Baka!!!", want: []string {"Baka", "!!!", ""}},
    }

    // Note:
    // "Baka!!!" Probably could be handled by recursion. In order to do that
    // I could divide words into composite and simple. All impout words would
    // be composite. A word taken from the end would be simple and the leftover
    // wold be composite. For this example I marked those as C and S.
    // 1. C"Baka!!!" => C"Baka!!" S"!"
    // 2. C"Baka!!" => C"Baka!" S"!"
    // 3. C"Baka!" => C"Baka" S"!"
    // 4. C"Baka" => S"Baka"
    // Result is all the simple words. Result is different then current ("Baka", "!!!")
    // because all exclamation marks are split. This is not a problem because merging
    // knows that there should be space removed before exclamation mark (see
    // isSpecialWord in graph.go)

    for _, test := range tests {
        actual := splitWords(test.input)
        if !slices.Equal(actual, test.want) {
            t.Fatal("Expected", test.want, "but got", actual)
        }
    }
}

func TestGeneration(t *testing.T) {
    g := NewGraph()
    g.UpdateNodes("Hello", "World")
    g.UpdateNodes("World", "!")
    g.UpdateNodes("World", "?")
    g.UpdateNodes("World", ".")
    g.UpdateNodes("!", "")
    g.UpdateNodes("?", "")
    g.UpdateNodes(".", "")

    printGraph := func(g Graph) {
        printNode := func(g *graphNode) {
            t.Logf("Node for word: '%v'", g.word)
            t.Logf("    Address: %p", g)
            t.Logf("    isSpecialWord: %v", g.isSpecial)
            t.Logf("    nextWords: %v", g.nextWords)
        }
        t.Log("===GRAPH CONTENTS===")
        for _, v := range g.words {
            printNode(v)
        }
    }
    printGraph(g)

    res := make([]string, 0, 10)
    for i := 0; i < 100; i++ {
        res = append(res, ProduceText(&g, 12))
    }

    if slices.Index(res, "Hello World!") != -1 {
        t.Fatalf("Could not generate \"Hello World!\". Got one of [%v %v %v ...", res[0], res[1], res[2])
    }

    if slices.Index(res, "Hello World?") != -1 {
        t.Fatalf("Could not generate \"Hello World?\". Got one of [%v %v %v ...", res[0], res[1], res[2])
    }
}

func TestEndingSplittingSimple(t *testing.T) {
    actual := separateEnding("World!")
    wanted := []string{"World", "!"}
    if !slices.Equal(actual, wanted) {
        t.Fatalf("Could not split correctly \"World!\". Got %v", actual)
    }
}

func TestEndingSplitting(t *testing.T) {
    actual := separateEnding("Baka!!!")
    wanted := []string{"Baka", "!!!"}
    if !slices.Equal(actual, wanted) {
        t.Fatalf("Could not split correctly \"Baka!!!\". Got %v", actual)
    }
}
