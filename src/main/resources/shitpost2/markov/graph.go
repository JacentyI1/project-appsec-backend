package markov

import (
    "math/rand"
    "slices"
    "strings"
)

///////////////
// Graph
///////////////

type Graph struct {
    words []*graphNode
}

func NewGraph() Graph {
    return Graph {
        make([]*graphNode, 0, 100),
    }
}

func (g *Graph) AddChain(chain string) {
    words := splitWords(chain)
    for i, word := range words {
        if i+1 != len(words) {
            nextWord := words[i+1]
            g.UpdateNodes(word, nextWord)
        }
    }
}

// Helper to check if graph has been filled
func (g *Graph) IsEmpty() bool {
    return len(g.words) == 0
}

// Use this to randomly choose first word
func (g *Graph) GetFirstWord() *graphNode {
    for i := 0; i < 1000; i++ {
        max := len(g.words)
        gn := g.words[rand.Intn(max)]
        if !gn.isSpecial {
            return gn
        }
    } 
    panic("Could not begin!")
}

// Upate graph stat by one word pair
func (g *Graph) UpdateNodes(word, nextWord string) {
    var wordNode *graphNode
    var nextWordNode *graphNode

    wordNodeIndex := g.findNodeIndex(word)
    nextWordNodeIndex := g.findNodeIndex(nextWord)

    if wordNodeIndex == -1 {
        wordNode = g.createNode(word, isSpecialWord(word))
    } else {
        wordNode = g.words[wordNodeIndex]
    }

    if nextWordNodeIndex == -1 {
        nextWordNode = g.createNode(nextWord, isSpecialWord(nextWord))
    } else {
        nextWordNode = g.words[nextWordNodeIndex]
    }
    wordNode.addCount(nextWordNode)
}

// Find index in graph of a given word
func (g *Graph) findNodeIndex(word string) int {
    return slices.IndexFunc(g.words, func(node *graphNode) bool {
        return node.word == word
    })
}

// Create a node, with no stats and add it to the graph
func (g *Graph) createNode(word string, isSpecial bool) (*graphNode) {
    node := graphNode {
        word: word,
        isSpecial: isSpecial,
        nextWords: make(map[*graphNode] int),
    }
    g.words = append(g.words, &node)
    return &node
}


///////////////
// graphNode
///////////////

type graphNode struct {
    word string
    nextWords map[*graphNode] int
    isSpecial bool
}

// Get semi-randomly chosen next word
// accounting for words stats
func (gn *graphNode) GetNextWord() *graphNode {
    type rankedNode struct {
        pointer *graphNode
        stat int
    }

    nodes := []rankedNode {}
    for k,v := range gn.nextWords {
        nodes = append(nodes, rankedNode{k, v})
    }

    slices.SortFunc(nodes, func(a, b rankedNode) int {
        if a.stat == b.stat {
            return 0
        } else if a.stat < b.stat {
            return 1
        } else  {
            return -1
        }
    })

    seed := rand.Int() 
    for _, node := range nodes {
        if (seed & 1) == 1 {
            return node.pointer
        } else {
            seed = seed >> 1
        }
    }

    if gn.isEndWord() {
        panic("You're looking for a word after end word")
    } else {
        return nodes[0].pointer
    }
}

// Exaclty the same as GetNextWord except you can use this
// when you want to end chain
func (gn *graphNode) NextEndIfProbable() *graphNode {
    for k := range gn.nextWords {
        if k.isEndWord() {
            return k
        }
    }
    return gn.GetNextWord()
}

// Add +1 to stat for given next word
func (gn *graphNode) addCount(n *graphNode) {
    for node, stat := range gn.nextWords {
        if node == n {
            gn.nextWords[node] = stat + 1
            return
        }
    }
    gn.nextWords[n] = 1
}

// Helper function to check if we end with that word
func (gn *graphNode) isEndWord() bool {
    return gn.word == ""
}

// Helper function to check word length
func (gn *graphNode) len() int {
    return len(gn.word)
}


///////////////
// Other
///////////////

// Check if word is a special word to prevent
// beginning sentences with that word
func isSpecialWord(w string) bool {
    runes := []rune(w)
    isSpecialRune := func(r rune) bool {
        return (r < 'A' || r > 'z') && (r < '0' || r > '9')
    }

    if len(w) == 1 && isSpecialRune(runes[0]) {
        return true
    }

    if len(w) == len(runes) {
        // Case for "!!!" but excludes longer UTF-8 runes
        // Like single japanese symbol
        for _, rune := range runes {
            if !isSpecialRune(rune) {
                return false
            }
        }
        return true
    }

    return false
}

func splitWords(post string) []string {
    separated := make([]string, 0, 10)
    spaceSeparated := strings.Split(post, " ") 

    for _, word := range spaceSeparated {
        separated = append(separated, separateEnding(word)...)
    }
    separated = append(separated, "")
    return separated
}

func separateEnding(word string) []string {
    // Note:
    // Generally words separated with this method (and not just by spaces)
    // should be marked as "Special words" (see graph.go file). However 
    // it is easier to just make a separate detection logic since I didn't 
    // think of it early enough.

    l := len(word)
    runseReversed := []rune(word)
    slices.Reverse(runseReversed)
    isSpecialRune := func(r rune) bool {
        return (r < 'A' || r > 'z') && (r < '0' || r > '9')
    }

    suffixLength := 0
    for i, rune := range runseReversed {
        if isSpecialRune(rune) {
            suffixLength = i+1
        } else {
            break
        }
    }

    if suffixLength == 0 {
        return []string {word}
    } else {
        return []string {
            word[0 : l-suffixLength],
            word[l-suffixLength : l],
        }
    }
}
