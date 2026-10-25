# Instructor Note

## Intended setting

This 60- to 90-minute activity fits after students have seen adjacency lists,
queues, stacks, and asymptotic notation. It can introduce the distinction among
traversal, single-source shortest paths, and minimum spanning trees, or serve as
an integration exercise before assessment.

## Learning evidence

Look for students who can:

- trace state rather than report only a final order or weight;
- connect algorithm preconditions to correctness;
- distinguish "reaches every vertex," "finds a shortest route," and "connects
  all vertices at minimum total cost";
- design a boundary test that expresses a meaningful behavioral claim; and
- explain how representation and insertion order affect a deterministic example
  without confusing those choices with mathematical correctness.

## Likely misconceptions

1. **BFS always finds a minimum-weight path.** It does so only when edge costs
   are uniform (or when path length means number of edges).
2. **The first path discovered by Dijkstra is final.** A distance becomes final
   when the corresponding minimum-distance frontier entry is processed.
3. **A shortest-path tree is a minimum spanning tree.** Their objectives differ.
4. **Disconnected input makes Kruskal fail.** The natural result is a minimum
   spanning forest, provided the API says so.
5. **A different traversal order is automatically incorrect.** Neighbor-order
   conventions can yield multiple valid traversals.
6. **Every listed edge must appear in a spanning tree.** Self-loops and edges
   that close a cycle cannot improve connectivity, even when a self-loop has a
   very small or negative weight.

## Facilitation choices

- Ask for predictions before execution so the tool provides feedback rather
  than replacing reasoning.
- Rotate driver/reasoner roles and pause after each algorithm so one student
  does not become the permanent operator.
- Invite groups with different valid DFS orders to compare adjacency order and
  stack behavior.
- Use the negative-edge prompt to discuss defensive API design. The supplied
  implementation rejects the input rather than returning a plausible but
  unjustified result.
- Contrast Dijkstra's rejection of negative edges with Kruskal's ability to use
  negative edges safely; this makes algorithm-specific preconditions concrete.

## Accessibility and participation

- Provide the graph both as text and as a visual diagram.
- Allow traces as tables, structured prose, or accessible digital diagrams.
- Keep the sample small enough to trace without relying on color or fine motor
  precision.
- Give students private prediction time before group discussion.
- Treat requests for clarification as contributions that expose hidden
  assumptions in the model or instructions.

## Compact rubric (12 points)

| Criterion | 3 points | 2 points | 1 point | 0 points |
|---|---|---|---|---|
| Traces | Complete and internally consistent | Minor error, sound method | Partial state shown | No usable trace |
| Algorithm distinctions | Clearly explains all three objectives | One distinction unclear | Relies mostly on labels | Treats algorithms as interchangeable |
| Preconditions | Connects assumptions to correctness | Names assumptions | Notices an edge case | Does not address assumptions |
| Tests and explanation | Two meaningful boundary tests with clear claims | Two tests with limited explanation | One meaningful test | No meaningful test |

## Extension options

- Ask students to implement path reconstruction from predecessor links.
- Compare a binary heap implementation with a deliberately simple linear scan.
- Investigate stable tie-breaking without asserting that one traversal or
  spanning tree is uniquely correct.
- Use a local or civic network dataset only after discussing what the weights
  represent and whose goals the optimization objective captures.
