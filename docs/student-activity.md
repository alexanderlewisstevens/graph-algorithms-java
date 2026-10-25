# Student Activity: One Graph, Three Questions

## Learning goals

By the end of this activity, you should be able to:

1. distinguish graph exploration from shortest-path and spanning-tree problems;
2. predict how a representation and a tie-breaking rule affect observable output;
3. identify the preconditions that make an algorithm's answer meaningful; and
4. support a claim with a trace, a test, or a counterexample.

## Scenario

A town is comparing routes among six community sites. Vertices represent sites
and edge weights represent travel time in minutes.

```text
Library --4-- Station --3-- Clinic
   |              |             |
   2              1             5
   |              |             |
School  --2--  Market  --1--  Shelter
```

Create this graph in a small test or scratch program. Preserve the vertex and
edge insertion order shown by your code so another group can reproduce your
traversal output.

```java
Graph<String> town = Graph.undirected();
town.addEdge("Library", "Station", 4.0);
town.addEdge("Station", "Clinic", 3.0);
town.addEdge("Library", "School", 2.0);
town.addEdge("Station", "Market", 1.0);
town.addEdge("Clinic", "Shelter", 5.0);
town.addEdge("School", "Market", 2.0);
town.addEdge("Market", "Shelter", 1.0);
```

Before continuing, compare the code with the diagram and verify that each edge
appears exactly once in the construction sequence.

## Part 1: Explore

Before running the code, predict the breadth-first and depth-first traversal
orders starting at `Library`.

- Which edges does each method ignore?
- If two groups obtain different valid DFS orders, what implementation choice
  might explain the difference?
- What does a traversal order establish, and what does it *not* establish about
  shortest travel time?

Use a short trace table to compare your prediction with the program's output.

## Part 2: Route

Use Dijkstra's algorithm to find a shortest path from `Library` to `Shelter`.

- Record the tentative distances after each vertex is removed from the priority
  queue.
- Explain why the first discovered route to a vertex need not be its shortest
  route.
- Change one edge to a negative weight. Do not run the method yet: first explain
  which assumption would be violated and what behavior a well-designed library
  should have.

## Part 3: Connect

Use Kruskal's algorithm to select a minimum-cost set of connections that keeps
all six sites connected.

- List the edges in the order considered.
- For every rejected edge, identify the cycle it would create.
- Compare the spanning tree with the shortest path from Part 2. Why are these
  answers to different questions?

## Part 4: Test the boundaries

With a partner, choose two of the following cases and write a JUnit test for
each:

- an isolated vertex;
- a disconnected graph;
- two equal-weight choices;
- a self-loop;
- a parallel request that replaces an existing edge's weight;
- a negative edge supplied to Dijkstra's algorithm.

Name each test as a claim about expected behavior. Trade tests with another
pair and determine whether the test makes its assumptions clear.

## Collaboration and submission

Work in pairs. Rotate the roles of **driver** (edits or runs code) and
**reasoner** (maintains the trace and challenges assumptions) after each part.
Both partners should be prepared to explain any line of submitted work.

Submit the traversal trace, shortest-path trace, spanning-tree justification,
and two tests. Text, a clearly structured table, or an accessible digital
diagram are all acceptable ways to present a trace.
