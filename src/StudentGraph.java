import java.util.*;

/**
 * Graph representation of students and their connections.
 * Uses adjacency list with weighted edges. Only creates edges
 * when connection strength is greater than 0.
 */
public class StudentGraph {

    /**
     * Represents an edge to a neighbor with a connection weight.
     */
    public static class Edge {
        public UniversityStudent neighbor;
        public int weight;

        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }

    private Map<UniversityStudent, List<Edge>> adjList = new HashMap<>();

    /**
     * Builds a graph from the list of students.
     * Calculates connection strengths and creates edges between students.
     * 
     * @param students list of students to include in graph
     */
    public StudentGraph(List<UniversityStudent> students) {
        // Initialize adjacency list for all students
        for (UniversityStudent s : students) {
            adjList.put(s, new ArrayList<>());
        }
        
        // Build edges between students
        for (int i = 0; i < students.size(); i++) {
            UniversityStudent s1 = students.get(i);
            for (int j = i + 1; j < students.size(); j++) {
                UniversityStudent s2 = students.get(j);
                
                int weight = s1.calculateConnectionStrength(s2);
                
                // Only add edge if connection strength > 0
                if (weight > 0) {
                    adjList.get(s1).add(new Edge(s2, weight));
                    adjList.get(s2).add(new Edge(s1, weight));
                }
            }
        }
    }

    /**
     * @return all students in the graph
     */
    public List<UniversityStudent> getAllNodes() {
        return new ArrayList<>(adjList.keySet());
    }

    /**
     * Gets edges connected to a student.
     * 
     * @param student the student to get neighbors for
     * @return list of edges
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjList.getOrDefault(student, new ArrayList<>());
    }

    /**
     * Prints the graph structure to console.
     */
    public void displayGraph() {
        for (UniversityStudent s : adjList.keySet()) {
            List<Edge> edges = adjList.get(s);
            if (edges.isEmpty()) {
                System.out.println(s.name + " has no connections (isolated node)");
            } else {
                System.out.println(s.name + " connected to:");
                for (Edge e : edges) {
                    System.out.println("   → " + e.neighbor.name + " (strength " + e.weight + ")");
                }
            }
        }
    }
}