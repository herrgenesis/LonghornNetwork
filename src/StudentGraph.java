import java.util.*;

/**
 * Represents an undirected weighted graph where each node
 * is a UniversityStudent and each edge weight reflects
 * connection strength.
 */
public class StudentGraph {

    /**
     * Inner class representing a weighted edge to another student.
     */
    public static class Edge {
        public UniversityStudent neighbor;
        public int weight;

        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }

    /**
     * Builds a graph based on the list of students.
     *
     * @param students list of students to include in the graph
     */
    public StudentGraph(List<UniversityStudent> students) {
        // To be implemented
    }

    /**
     * Returns all student nodes that appear in the graph.
     *
     * @return list of student nodes
     */
    public List<UniversityStudent> getAllNodes() {
        return new ArrayList<>();
    }

    /**
     * Returns all edges connected to a given student.
     *
     * @param student node to check
     * @return list of edges
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return new ArrayList<>();
    }

    /**
     * Prints the graph in a readable format for debugging.
     */
    public void displayGraph() {
    }
}
