package edu.alexstevens.graphs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable distances and predecessor links produced by a single-source
 * shortest-path algorithm.
 */
public record ShortestPathResult<V>(
        V source,
        Map<V, Double> distances,
        Map<V, V> predecessors) {

    public ShortestPathResult {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(distances, "distances must not be null");
        Objects.requireNonNull(predecessors, "predecessors must not be null");
        distances = Collections.unmodifiableMap(new LinkedHashMap<>(distances));
        predecessors = Collections.unmodifiableMap(new LinkedHashMap<>(predecessors));
        if (!distances.containsKey(source)) {
            throw new IllegalArgumentException("distances must contain the source");
        }
    }

    public double distanceTo(V target) {
        Double distance = distances.get(target);
        if (distance == null) {
            throw new IllegalArgumentException("unknown vertex: " + target);
        }
        return distance;
    }

    /**
     * Returns an empty optional when the target exists but is unreachable.
     */
    public Optional<List<V>> pathTo(V target) {
        double distance = distanceTo(target);
        if (Double.isInfinite(distance)) {
            return Optional.empty();
        }

        LinkedList<V> path = new LinkedList<>();
        V current = target;
        path.addFirst(current);
        while (!Objects.equals(current, source)) {
            current = predecessors.get(current);
            if (current == null) {
                throw new IllegalStateException("reachable vertex has no predecessor chain");
            }
            path.addFirst(current);
        }
        return Optional.of(List.copyOf(path));
    }
}
