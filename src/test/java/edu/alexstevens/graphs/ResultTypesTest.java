package edu.alexstevens.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ResultTypesTest {

    @Test
    void shortestPathResultDefensivelyCopiesAndPreservesMapOrder() {
        Map<String, Double> distances = new LinkedHashMap<>();
        distances.put("B", 2.0);
        distances.put("A", 0.0);
        Map<String, String> predecessors = new LinkedHashMap<>();
        predecessors.put("B", "A");

        ShortestPathResult<String> result = new ShortestPathResult<>(
                "A", distances, predecessors);
        distances.put("C", 3.0);
        predecessors.clear();

        assertEquals(List.of("B", "A"), new ArrayList<>(result.distances().keySet()));
        assertEquals(List.of("A", "B"), result.pathTo("B").orElseThrow());
        assertThrows(UnsupportedOperationException.class,
                () -> result.distances().put("C", 3.0));
        assertThrows(UnsupportedOperationException.class,
                () -> result.predecessors().clear());
    }

    @Test
    void spanningForestDefensivelyCopiesItsEdgeList() {
        List<WeightedEdge<String>> edges = new ArrayList<>();
        edges.add(new WeightedEdge<>("A", "B", 1.0));

        SpanningForest<String> forest = new SpanningForest<>(edges, 1.0, 1);
        edges.clear();

        assertEquals(1, forest.edges().size());
        assertThrows(UnsupportedOperationException.class, () -> forest.edges().clear());
    }
}
