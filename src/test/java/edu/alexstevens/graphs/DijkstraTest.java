package edu.alexstevens.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class DijkstraTest {

    @Test
    void findsShortestDistancesAndReconstructsPaths() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 4.0);
        graph.addEdge("A", "C", 1.0);
        graph.addEdge("C", "B", 2.0);
        graph.addEdge("B", "D", 1.0);
        graph.addEdge("C", "D", 5.0);
        graph.addEdge("D", "E", 3.0);
        graph.addVertex("Z");

        ShortestPathResult<String> result = GraphAlgorithms.dijkstra(graph, "A");

        assertEquals(0.0, result.distanceTo("A"));
        assertEquals(4.0, result.distanceTo("D"));
        assertEquals(7.0, result.distanceTo("E"));
        assertEquals(List.of("A", "C", "B", "D", "E"), result.pathTo("E").orElseThrow());
        assertTrue(Double.isInfinite(result.distanceTo("Z")));
        assertTrue(result.pathTo("Z").isEmpty());
    }

    @Test
    void rejectsNegativeWeightsInsteadOfReturningAConvincingWrongAnswer() {
        Graph<String> graph = Graph.directed();
        graph.addEdge("A", "B", -1.0);

        assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.dijkstra(graph, "A"));
    }

    @Test
    void respectsDirectionAndAcceptsZeroWeightEdges() {
        Graph<String> graph = Graph.directed();
        graph.addEdge("A", "B", 0.0);
        graph.addEdge("B", "C", 2.0);

        ShortestPathResult<String> fromA = GraphAlgorithms.dijkstra(graph, "A");
        ShortestPathResult<String> fromC = GraphAlgorithms.dijkstra(graph, "C");

        assertEquals(2.0, fromA.distanceTo("C"));
        assertEquals(List.of("A", "B", "C"), fromA.pathTo("C").orElseThrow());
        assertTrue(fromC.pathTo("A").isEmpty());
    }

    @Test
    void reportsTheSourceAsItsOwnZeroLengthPath() {
        Graph<String> graph = Graph.undirected();
        graph.addVertex("A");

        ShortestPathResult<String> result = GraphAlgorithms.dijkstra(graph, "A");

        assertEquals(0.0, result.distanceTo("A"));
        assertEquals(List.of("A"), result.pathTo("A").orElseThrow());
    }
}
