package edu.alexstevens.graphs;

import java.util.List;

/**
 * The result of applying a minimum-spanning-tree algorithm to a graph that may
 * be disconnected.
 */
public record SpanningForest<V>(
        List<WeightedEdge<V>> edges,
        double totalWeight,
        int componentCount) {

    public SpanningForest {
        edges = List.copyOf(edges);
        if (!Double.isFinite(totalWeight)) {
            throw new IllegalArgumentException("totalWeight must be finite");
        }
        if (componentCount < 0) {
            throw new IllegalArgumentException("componentCount must not be negative");
        }
    }
}
