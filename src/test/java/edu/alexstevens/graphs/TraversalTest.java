package edu.alexstevens.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class TraversalTest {

    @Test
    void traversalsFollowInsertionOrderWithinTheirOwnStrategies() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("A", "C", 1.0);
        graph.addEdge("B", "D", 1.0);
        graph.addEdge("C", "E", 1.0);

        assertEquals(List.of("A", "B", "C", "D", "E"),
                GraphAlgorithms.breadthFirstOrder(graph, "A"));
        assertEquals(List.of("A", "B", "D", "C", "E"),
                GraphAlgorithms.depthFirstOrder(graph, "A"));
    }

    @Test
    void traversalDoesNotInventAPathToDisconnectedVertices() {
        Graph<Integer> graph = Graph.undirected();
        graph.addEdge(1, 2, 1.0);
        graph.addVertex(3);

        assertEquals(List.of(1, 2), GraphAlgorithms.breadthFirstOrder(graph, 1));
        assertEquals(List.of(3), GraphAlgorithms.depthFirstOrder(graph, 3));
    }

    @Test
    void traversalRequiresAKnownStartVertex() {
        Graph<String> graph = Graph.undirected();
        graph.addVertex("A");

        assertThrows(IllegalArgumentException.class,
                () -> GraphAlgorithms.breadthFirstOrder(graph, "missing"));
    }
}
