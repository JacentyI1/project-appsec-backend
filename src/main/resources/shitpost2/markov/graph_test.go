package markov

import (
    "testing"
    "fmt"
)

func getGraphNode() graphNode {
    return graphNode {
        isSpecial: false,
        nextWords: make(map[*graphNode]int),
        word : "UwU",
    }
}

func TestAddCount(t *testing.T) {
    mainNode := getGraphNode()
    toAdd1 := getGraphNode()
    toAdd2 := getGraphNode()

    mainNode.addCount(&toAdd1)
    mainNode.addCount(&toAdd1)
    mainNode.addCount(&toAdd1)
    mainNode.addCount(&toAdd2) // 2
    mainNode.addCount(&toAdd1)

    expected := map[*graphNode]int {
        &toAdd1: 4,
        &toAdd2: 1,
    }

    if fmt.Sprint(expected) != fmt.Sprint(mainNode.nextWords) {
        t.Fatalf("(expected) != (actual)\n%v != %v", expected, mainNode.nextWords)
    }
}

func TestRandomWord(t *testing.T) {
    otherNodes := [3]graphNode {
        getGraphNode(),
        getGraphNode(),
        getGraphNode(),
    }

    mainNode := graphNode {
        word : "OwO",
        isSpecial: false,
        nextWords: map[*graphNode]int {
            &otherNodes[0]: 3,
            &otherNodes[1]: 2,
            &otherNodes[2]: 1,
        },
    }

    results := make([]*graphNode, 0, 10_000)
    for i := 0; i < 10_000; i++ {
        results = append(results, mainNode.GetNextWord())
    }

    // Im mad at go lang for not having shorcuts for this
    counts := [3]int {0, 0, 0}
    for _, v := range results {
        switch v {
        case &otherNodes[0]:
            counts[0] += 1
        case &otherNodes[1]:
            counts[1] += 1
        case &otherNodes[2]:
            counts[2] += 1
        }
    }

    for i, count := range counts {
        counts[i] = count / 100
    }

    bounds0 := [2]int { 70, 60 }
    bounds1 := [2]int { 30, 10 }
    bounds2 := [2]int { 20, 01 }

    checkIfWithinBounds := func(num int, bounds [2]int) {
        if bounds[0] < num {
            t.Fatal("Expected num to be less % than", bounds[0], "but is", num)
        }
        if bounds[1] > num {
            t.Fatal("Expected num to be more % than", bounds[0], "but is", num)
        }
    }

    checkIfWithinBounds(counts[0], bounds0)
    checkIfWithinBounds(counts[1], bounds1)
    checkIfWithinBounds(counts[2], bounds2)
}

func TestGetFirstWordAndUpdateNodes(t *testing.T) {
    g := NewGraph()
    g.UpdateNodes("Hello", "World")
    g.UpdateNodes("World", "!")

    for i := 0; i < 100; i++ {
        if g.GetFirstWord().word == "!" {
            t.Fatal("GetFirstWord did not work correctly")
        }
    }
}

func TestIsSpecialWord(t *testing.T) {
    type test struct {
        input string
        want  bool
    }

    tests := []test {
        {input: "Hello", want: false},
        {input: "World", want: false},
        {input: "P@ssword", want: false},
        {input: "Admin01", want: false},
        {input: "0040", want: false},
        {input: ":blobcat:", want: false},
        {input: "C++", want: false},
        {input: "...really+", want: false},
        {input: "名", want: false},
        {input: "平仮名", want: false},
        {input: "!", want: true},
        {input: ".", want: true},
        {input: "!!!", want: true},
    }

    for _, test := range tests {
        actual := isSpecialWord(test.input) 
        if  actual != test.want {
            t.Fatalf("%v was detected as: %v", test.input, actual)
        }
    }
}

func TestIsEndWord(t *testing.T) {
    is := graphNode {
        word: "",
    }
    isnt := graphNode {
        word: "isnot",
    }

    if !is.isEndWord() {
            t.Fatal()
    }
    if isnt.isEndWord() {
            t.Fatal()
    }
}

