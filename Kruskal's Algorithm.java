import java.util.*;

// Class to represent an Edge
class Edge implements Comparable<Edge> {
    int source, destination, weight;

    // Constructor for Edge
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Compare edges based on their weight
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Class to represent a Graph
class Graph {
    int vertices;
    LinkedList<Edge> allEdges = new LinkedList<>();

    // Constructor for Graph
    Graph(int vertices) {
        this.vertices = vertices;
    }

    // Add edges to the graph
    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        allEdges.add(edge);
    }

    // Find set of an element (uses path compression)
    public int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex)
            parent[vertex] = find(parent, parent[vertex]);
        return parent[vertex];
    }

    // Union of two sets (uses union by rank)
    public void union(int[] parent, int[] rank, int root1, int root2) {
        if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else {
            parent[root2] = root1;
            rank[root1]++;
        }
    }

    // Kruskal's Algorithm to find the Minimum Spanning Tree
    public void kruskalMST() {
        // Sort all edges based on weight
        Collections.sort(allEdges);

        int[] parent = new int[vertices];
        int[] rank = new int[vertices];

        // Initialize parent and rank arrays
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        LinkedList<Edge> mst = new LinkedList<>();
        int index = 0; // Index to iterate through sorted edges

        // Process each edge and add to MST if it doesn't form a cycle
        while (mst.size() < vertices - 1) {
            Edge edge = allEdges.get(index++);
            int root1 = find(parent, edge.source);
            int root2 = find(parent, edge.destination);

            // If including this edge doesn't form a cycle, add it to the MST
            if (root1 != root2) {
                mst.add(edge);
                union(parent, rank, root1, root2);
            }
        }

        // Print the MST
        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println("Edge: " + edge.source + " - " + edge.destination + " weight: " + edge.weight);
        }
    }
}

public class KruskalAlgorithm {
    public static void main(String[] args) {
        int vertices = 6;  // Number of vertices in the graph
        Graph graph = new Graph(vertices);

        // Add edges to the graph (source, destination, weight)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 5);
        graph.addEdge(2, 4, 6);
        graph.addEdge(3, 4, 3);
        graph.addEdge(3, 5, 7);
        graph.addEdge(4, 5, 1);

        // Find and print the Minimum Spanning Tree using Kruskal's algorithm
        graph.kruskalMST();
    }
}
