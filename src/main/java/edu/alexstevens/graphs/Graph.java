package edu.alexstevens.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A mutable weighted graph backed by insertion-ordered adjacency maps.
 *
 * <p>Insertion order makes examples and traversal tests reproducible. Algorithms
 * must still be correct for any vertex insertion order.</p>
 *
 * @param <V> the vertex type
 */
public final class Graph<V> {

    private final boolean directed;
    private final Map<V, LinkedHashMap<V, Double>> adjacency = new LinkedHashMap<>();

    private Graph(boolean directed) {
        this.directed = directed;
    }

    public static <V> Graph<V> directed() {
        return new Graph<>(true);
    }

    public static <V> Graph<V> undirected() {
        return new Graph<>(false);
    }

    public boolean isDirected() {
        return directed;
    }

    public void addVertex(V vertex) {
        Objects.requireNonNull(vertex, "vertex must not be null");
        adjacency.computeIfAbsent(vertex, ignored -> new LinkedHashMap<>());
    }

    /**
     * Adds an edge, creating missing endpoints. Adding an existing edge replaces
     * its weight without changing its insertion position.
     */
    public void addEdge(V from, V to, double weight) {
        WeightedEdge<V> edge = new WeightedEdge<>(from, to, weight);
        addVertex(edge.from());
        addVertex(edge.to());
        adjacency.get(edge.from()).put(edge.to(), edge.weight());
        if (!directed) {
            adjacency.get(edge.to()).put(edge.from(), edge.weight());
        }
    }

    public boolean containsVertex(V vertex) {
        return adjacency.containsKey(vertex);
    }

    public int vertexCount() {
        return adjacency.size();
    }

    public int edgeCount() {
        return edges().size();
    }

    public Set<V> vertices() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(adjacency.keySet()));
    }

    public Map<V, Double> neighborsOf(V vertex) {
        LinkedHashMap<V, Double> neighbors = adjacency.get(vertex);
        if (neighbors == null) {
            throw new IllegalArgumentException("unknown vertex: " + vertex);
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(neighbors));
    }

    /**
     * Returns each directed edge once, or each undirected edge once regardless
     * of which endpoint was visited first.
     */
    public List<WeightedEdge<V>> edges() {
        List<WeightedEdge<V>> result = new ArrayList<>();
        Set<UnorderedPair<V>> seen = new HashSet<>();

        for (Map.Entry<V, LinkedHashMap<V, Double>> row : adjacency.entrySet()) {
            for (Map.Entry<V, Double> neighbor : row.getValue().entrySet()) {
                if (directed || seen.add(new UnorderedPair<>(row.getKey(), neighbor.getKey()))) {
                    result.add(new WeightedEdge<>(row.getKey(), neighbor.getKey(), neighbor.getValue()));
                }
            }
        }
        return List.copyOf(result);
    }

    private record UnorderedPair<V>(V first, V second) {

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof UnorderedPair<?> pair)) {
                return false;
            }
            return (Objects.equals(first, pair.first) && Objects.equals(second, pair.second))
                    || (Objects.equals(first, pair.second) && Objects.equals(second, pair.first));
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(first) ^ Objects.hashCode(second);
        }
    }
}
