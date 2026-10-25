# Graph Algorithms in Java

A small Java 21 graph library with tests and accompanying course materials.

## Scope

The project covers several topics from a standard data-structures and
algorithms sequence:

- a generic weighted adjacency-list graph;
- directed and undirected semantics;
- deterministic breadth-first and depth-first traversal;
- Dijkstra's single-source shortest paths with path reconstruction;
- Kruskal's minimum spanning forest with union-find;
- explicit input validation and immutable result views; and
- tests for disconnected graphs, isolated vertices, invalid weights, and
  algorithm preconditions.

## Requirements and quick start

- A JDK (not only a JRE), Java 21 or newer
- No global Maven installation is required after the Maven wrapper is present

```bash
./mvnw test
```

The default `java` on some Macs may still point to an older browser runtime.
When using a Homebrew JDK, select it for the command without changing the
system-wide Java configuration:

```bash
JAVA_HOME="$(brew --prefix openjdk)/libexec/openjdk.jdk/Contents/Home" ./mvnw test
```

The continuous-integration workflow runs the same tests with Java 21.

For a full clean verification, use `./mvnw --batch-mode clean verify` (with the
same `JAVA_HOME` prefix on Macs that need it). The compiler runs with all lint
warnings enabled.

## Small example

```java
Graph<String> graph = Graph.undirected();
graph.addEdge("Library", "School", 2.0);
graph.addEdge("School", "Market", 2.0);
graph.addEdge("Library", "Station", 4.0);
graph.addEdge("Station", "Market", 1.0);

List<String> bfs = GraphAlgorithms.breadthFirstOrder(graph, "Library");
ShortestPathResult<String> paths = GraphAlgorithms.dijkstra(graph, "Library");
SpanningForest<String> forest = GraphAlgorithms.kruskal(graph);
```

## Design decisions

### One weighted representation

Every edge has a `double` weight. Traversal algorithms ignore weights, while
shortest-path and spanning-tree algorithms interpret them. This keeps the model
small and makes the contrast among algorithmic questions visible.

### Explicit direction

`Graph.directed()` and `Graph.undirected()` make edge semantics a construction
decision. Kruskal rejects directed input. Dijkstra accepts either direction but
rejects negative weights.

### Reproducible examples

The graph uses insertion-ordered maps. BFS and DFS therefore produce stable
orders for a fixed construction sequence. The documentation makes clear that
other neighbor orders may also be mathematically valid.

### A forest instead of a hidden failure

Kruskal returns a `SpanningForest` and reports its component count. Disconnected
input is part of the public contract rather than an unhandled special case.

## Complexity

Let `V` be the number of vertices and `E` the number of stored edges.

| Operation | Time | Additional space | Preconditions |
|---|---:|---:|---|
| Add vertex | amortized `O(1)` | `O(1)` | non-null vertex |
| Add or replace edge | amortized `O(1)` | `O(1)` | finite weight |
| BFS | `O(V + E)` | `O(V)` | known start vertex |
| DFS | `O(V + E)` | `O(V)` | known start vertex |
| Dijkstra | `O((V + E) log V)` | `O(V + E)` | nonnegative weights |
| Kruskal | `O(E log E)` | `O(V + E)` | undirected graph |

## Teaching materials

- [Student activity](docs/student-activity.md): prediction, tracing, comparison,
  and boundary-test design.
- [Instructor note](docs/instructor-note.md): learning goals, likely
  misconceptions, facilitation notes, rubric, and extensions.

## Verification coverage

The JUnit suite covers directed and undirected representation, replacement and
self-loop behavior, immutable views, deterministic traversal, disconnected and
isolated vertices, zero and negative weights, unreachable paths, path
reconstruction, empty graphs, and minimum spanning forests.

## Future work

1. Add a small command-line demonstration only if it improves inspectability.
2. Ask a Java-experienced colleague to review naming and API choices.
3. Collect feedback from instructors or students who use the activity.

## License

The code and instructional materials are available under the [MIT License](LICENSE).
