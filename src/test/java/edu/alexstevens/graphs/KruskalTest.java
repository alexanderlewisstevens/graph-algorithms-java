package edu.alexstevens.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class KruskalTest {

    @Test
    void findsAMinimumSpanningTreeForAConnectedGraph() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("B", "C", 2.0);
        graph.addEdge("A", "C", 4.0);
        graph.addEdge("C", "D", 1.0);
        graph.addEdge("B", "D", 5.0);

        SpanningForest<String> forest = GraphAlgorithms.kruskal(graph);

        assertEquals(3, forest.edges().size());
        assertEquals(4.0, forest.totalWeight());
        assertEquals(1, forest.componentCount());
    }

    @Test
    void returnsAForestForDisconnectedGraphsAndKeepsIsolatedVertices() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("B", "C", 2.0);
        graph.addEdge("D", "E", 3.0);
        graph.addVertex("F");

        SpanningForest<String> forest = GraphAlgorithms.kruskal(graph);

        assertEquals(3, forest.edges().size());
        assertEquals(6.0, forest.totalWeight());
        assertEquals(3, forest.componentCount());
    }

    @Test
    void rejectsDirectedGraphs() {
        Graph<String> graph = Graph.directed();
        graph.addEdge("A", "B", 1.0);

        assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.kruskal(graph));
    }

    @Test
    void handlesAnEmptyGraph() {
        SpanningForest<String> forest = GraphAlgorithms.kruskal(Graph.undirected());

        assertEquals(0, forest.edges().size());
        assertEquals(0.0, forest.totalWeight());
        assertEquals(0, forest.componentCount());
    }

    @Test
    void ignoresSelfLoopsAndCanSelectNegativeEdges() {
        Graph<String> graph = Graph.undirected();
        graph.addEdge("A", "A", -10.0);
        graph.addEdge("A", "B", -2.0);
        graph.addEdge("B", "C", 3.0);
        graph.addEdge("A", "C", 5.0);

        SpanningForest<String> forest = GraphAlgorithms.kruskal(graph);

        assertEquals(2, forest.edges().size());
        assertEquals(1.0, forest.totalWeight());
        assertEquals(1, forest.componentCount());
    }
}
