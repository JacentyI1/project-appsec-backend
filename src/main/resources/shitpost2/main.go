package main

import (
    //"time"
    "awawa.cat/ebooks/markov"
    //"awawa.cat/ebooks/util"
    //"awawa.cat/ebooks/akkoma"
    //"log/slog"
    "encoding/csv"
    "io" 
    "fmt"
    "os"
)

func main() {
  // write a script that will initiate markov graph and display it
  // then, it will ask for a sentence and generate it
  // use only functions provided in markov.go  and graph.go
  
  graph := markov.GetGraph()
    file, err := os.Open("orange.csv")
    if err != nil {
    fmt.Println("Error:", err)
    return
  }
  defer file.Close()
  reader := csv.NewReader(file)
  for {
    record, err := reader.Read()
    if err == io.EOF {
      break
    } else if err != nil {
      fmt.Println("Error:", err)
      return
    }
    graph.AddChain(record[0])
  }

  graph.AddChain("I am a cat")
  graph.AddChain("I am a dog")
  graph.AddChain("never gonna give you up")
  graph.AddChain("never gonna let you down")
  graph.AddChain("never gonna run around and desert you")
  graph.AddChain("never gonna make you cry")
  graph.AddChain("never gonna say goodbye")
  graph.AddChain("never gonna tell a lie and hurt you")
  text := markov.ProduceText(&graph,10)
  //fmt.Print(text)
  // write the text to a file
  f, err := os.Create("out.txt")
  if err != nil {
    fmt.Println(err)
    return
  }
  l, err := f.WriteString(text)
  if err != nil {

  fmt.Println(err)
  f.Close()
  return

  }
  fmt.Println(l, "bytes written successfully")
  err = f.Close()
}
