package markov

///////////////
// Exported
///////////////

func GetGraph() Graph {
    return *new(Graph)
}

func ProduceText(graph *Graph, estimatedCharLength int) string {
    // TODO maybe this should be graph.ProduceText(10) ?
    chosen := unparsedChain {}
    chosen.Add(graph.GetFirstWord())

    for chosen.ApproxLen() < estimatedCharLength && !chosen.Last().isEndWord() {
        chosen.Add(chosen.Last().GetNextWord())
    }
    for !chosen.Last().isEndWord() {
        chosen.Add(chosen.Last().NextEndIfProbable())
    }
    
    return chosen.Parse()
}

///////////////
// Unexported
///////////////

type unparsedChain struct {
    nodes []*graphNode
}

func (uc *unparsedChain) ApproxLen() int {
    counter := 0
    for _, node := range uc.nodes {
        counter += node.len()
    }
    // we add len to account for spaces
    return counter + len(uc.nodes)
}

func (uc *unparsedChain) Add(gn *graphNode) {
    uc.nodes = append(uc.nodes, gn) 
}

func (uc *unparsedChain) Last() *graphNode {
    return uc.nodes[len(uc.nodes) - 1]
}

func (uc *unparsedChain) Parse() string {
    parsed := ""
    for _, gn := range uc.nodes {
        if gn.isEndWord() {
            return parsed
        }
        if !gn.isSpecial {
            parsed += " "
        }
        parsed += gn.word
    }
    panic("String did not contain end word")
}

