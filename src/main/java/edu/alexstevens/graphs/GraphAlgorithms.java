package edu.alexstevens.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/** Standard graph algorithms with explicit preconditions and deterministic examples. */
public final class GraphAlgorithms {

    private GraphAlgorithms() {
    }

    /** Runs breadth-first search from {@code start}. */
    public static <V> List<V> breadthFirstOrder(Graph<V> graph, V start) {
        requireStart(graph, start);
        List<V> order = new ArrayList<>();
        Set<V> visited = new LinkedHashSet<>();
        Deque<V> queue = new ArrayDeque<>();
        visited.add(start);
        queue.addLast(start);

        while (!queue.isEmpty()) {
            V current = queue.removeFirst();
            order.add(current);
            for (V neighbor : graph.neighborsOf(current).keySet()) {
                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }
        return List.copyOf(order);
    }

    /** Runs iterative depth-first search from {@code start}. */
    public static <V> List<V> depthFirstOrder(Graph<V> graph, V start) {
        requireStart(graph, start);
        List<V> order = new ArrayList<>();
        Set<V> visited = new LinkedHashSet<>();
        Deque<V> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            V current = stack.pop();
            if (!visited.add(current)) {
                continue;
            }
            order.add(current);

            List<V> neighbors = new ArrayList<>(graph.neighborsOf(current).keySet());
            for (int index = neighbors.size() - 1; index >= 0; index--) {
                V neighbor = neighbors.get(index);
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        return List.copyOf(order);
    }

    /**
     * Computes shortest paths from {@code source} with Dijkstra's algorithm.
     * All edge weights must be nonnegative.
     */
    public static <V> ShortestPathResult<V> dijkstra(Graph<V> graph, V source) {
        requireStart(graph, source);
        for (WeightedEdge<V> edge : graph.edges()) {
            if (edge.weight() < 0.0) {
                throw new IllegalArgumentException("Dijkstra's algorithm requires nonnegative weights");
            }
        }

        Map<V, Double> distances = new LinkedHashMap<>();
        Map<V, V> predecessors = new LinkedHashMap<>();
        for (V vertex : graph.vertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);

        PriorityQueue<QueueEntry<V>> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(QueueEntry<V>::distance));
        frontier.add(new QueueEntry<>(source, 0.0));

        while (!frontier.isEmpty()) {
            QueueEntry<V> entry = frontier.remove();
            if (entry.distance() > distances.get(entry.vertex())) {
                continue;
            }

            for (Map.Entry<V, Double> neighbor : graph.neighborsOf(entry.vertex()).entrySet()) {
                double candidate = entry.distance() + neighbor.getValue();
                if (candidate < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), candidate);
                    predecessors.put(neighbor.getKey(), entry.vertex());
                    frontier.add(new QueueEntry<>(neighbor.getKey(), candidate));
                }
            }
        }
        return new ShortestPathResult<>(source, distances, predecessors);
    }

    /**
     * Computes a minimum spanning forest with Kruskal's algorithm.
     *
     * @throws IllegalArgumentException if the graph is directed
     */
    public static <V> SpanningForest<V> kruskal(Graph<V> graph) {
        Objects.requireNonNull(graph, "graph must not be null");
        if (graph.isDirected()) {
            throw new IllegalArgumentException("Kruskal's algorithm requires an undirected graph");
        }

        List<WeightedEdge<V>> candidates = new ArrayList<>(graph.edges());
        candidates.sort(Comparator.comparingDouble(WeightedEdge<V>::weight));
        UnionFind<V> components = new UnionFind<>(graph.vertices());
        List<WeightedEdge<V>> selected = new ArrayList<>();
        double totalWeight = 0.0;

        for (WeightedEdge<V> edge : candidates) {
            if (components.union(edge.from(), edge.to())) {
                selected.add(edge);
                totalWeight += edge.weight();
            }
        }
        return new SpanningForest<>(selected, totalWeight, components.componentCount());
    }

    private static <V> void requireStart(Graph<V> graph, V start) {
        Objects.requireNonNull(graph, "graph must not be null");
        Objects.requireNonNull(start, "start must not be null");
        if (!graph.containsVertex(start)) {
            throw new IllegalArgumentException("unknown start vertex: " + start);
        }
    }

    private record QueueEntry<V>(V vertex, double distance) {
    }

    private static final class UnionFind<V> {
        private final Map<V, V> parent = new HashMap<>();
        private final Map<V, Integer> rank = new HashMap<>();

        private UnionFind(Set<V> vertices) {
            for (V vertex : vertices) {
                parent.put(vertex, vertex);
                rank.put(vertex, 0);
            }
        }

        private V find(V vertex) {
            V parentVertex = parent.get(vertex);
            if (!Objects.equals(parentVertex, vertex)) {
                parentVertex = find(parentVertex);
                parent.put(vertex, parentVertex);
            }
            return parentVertex;
        }

        private boolean union(V first, V second) {
            V firstRoot = find(first);
            V secondRoot = find(second);
            if (Objects.equals(firstRoot, secondRoot)) {
                return false;
            }

            int firstRank = rank.get(firstRoot);
            int secondRank = rank.get(secondRoot);
            if (firstRank < secondRank) {
                parent.put(firstRoot, secondRoot);
            } else if (firstRank > secondRank) {
                parent.put(secondRoot, firstRoot);
            } else {
                parent.put(secondRoot, firstRoot);
                rank.put(firstRoot, firstRank + 1);
            }
            return true;
        }

        private int componentCount() {
            Set<V> roots = new LinkedHashSet<>();
            for (V vertex : parent.keySet()) {
                roots.add(find(vertex));
            }
            return roots.size();
        }
    }
}
