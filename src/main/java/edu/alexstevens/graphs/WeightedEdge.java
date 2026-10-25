package edu.alexstevens.graphs;

import java.util.Objects;

/**
 * An edge between two vertices with a finite numeric weight.
 *
 * @param from the edge's first endpoint
 * @param to the edge's second endpoint
 * @param weight the edge weight
 * @param <V> the vertex type
 */
public record WeightedEdge<V>(V from, V to, double weight) {

    public WeightedEdge {
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        if (!Double.isFinite(weight)) {
            throw new IllegalArgumentException("weight must be finite");
        }
    }
}
