package edu.alexstevens.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void undirectedEdgesAppearInBothAdjacencyListsButAreCountedOnce() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 2.0);
        graph.addEdge("A", "B", 1.5);

        assertEquals(2, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
        assertEquals(Map.of("B", 1.5), graph.neighborsOf("A"));
        assertEquals(Map.of("A", 1.5), graph.neighborsOf("B"));
    }

    @Test
    void directedEdgesAreNotMirrored() {
        Graph<String> graph = Graph.directed();
        graph.addEdge("A", "B", 3.0);

        assertEquals(Map.of("B", 3.0), graph.neighborsOf("A"));
        assertEquals(Map.of(), graph.neighborsOf("B"));
    }

    @Test
    void reversingAnUndirectedEdgeRequestUpdatesTheExistingEdge() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 3.0);
        graph.addEdge("B", "A", 1.0);

        assertEquals(1, graph.edgeCount());
        assertEquals(1.0, graph.neighborsOf("A").get("B"));
        assertEquals(1.0, graph.neighborsOf("B").get("A"));
    }

    @Test
    void anUndirectedSelfLoopIsCountedOnce() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "A", 2.0);

        assertEquals(1, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
        assertEquals(Map.of("A", 2.0), graph.neighborsOf("A"));
    }

    @Test
    void publicCollectionsCannotModifyTheGraph() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 1.0);

        assertThrows(UnsupportedOperationException.class, () -> graph.vertices().add("C"));
        assertThrows(UnsupportedOperationException.class, () -> graph.neighborsOf("A").put("C", 2.0));
    }

    @Test
    void rejectsInvalidVerticesAndWeights() {
        Graph<String> graph = Graph.undirected();

        assertThrows(NullPointerException.class, () -> graph.addVertex(null));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B", Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> graph.neighborsOf("missing"));
    }
}
