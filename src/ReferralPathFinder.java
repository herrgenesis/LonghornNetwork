import java.util.*;

/**
 * Finds referral paths to students with specific internships using Dijkstra's algorithm.
 * Uses inverted weights so stronger connections = shorter paths.
 */
public class ReferralPathFinder {

    private StudentGraph graph;

    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds the path with strongest connections to someone with the target internship.
     * 
     * @param start starting student
     * @param internship company to find
     * @return path to a student with that internship, or empty list if none found
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String internship) {
        // Priority queue: (distance, student)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Map<UniversityStudent, Integer> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();
        
        // Initialize distances
        for (UniversityStudent s : graph.getAllNodes()) {
            distances.put(s, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new Node(start, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            UniversityStudent currentStudent = current.student;
            
            if (visited.contains(currentStudent)) continue;
            visited.add(currentStudent);
            
            // Check if this student has the target internship
            if (currentStudent.getPreviousInternships().contains(internship)) {
                return reconstructPath(previous, start, currentStudent);
            }
            
            // Explore neighbors
            for (StudentGraph.Edge edge : graph.getNeighbors(currentStudent)) {
                UniversityStudent neighbor = edge.neighbor;
                
                if (visited.contains(neighbor)) continue;
                
                // Invert weight: higher connection = lower cost
                int invertedWeight = 100 - edge.weight;
                int newDistance = distances.get(currentStudent) + invertedWeight;
                
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentStudent);
                    pq.add(new Node(neighbor, newDistance));
                }
            }
        }
        
        return Collections.emptyList();
    }
    
    /**
     * Reconstruct the path from start to target using the previous map.
     */
    private List<UniversityStudent> reconstructPath(Map<UniversityStudent, UniversityStudent> previous, 
                                                     UniversityStudent start, 
                                                     UniversityStudent target) {
        LinkedList<UniversityStudent> path = new LinkedList<>();
        UniversityStudent current = target;
        
        while (current != null) {
            path.addFirst(current);
            current = previous.get(current);
        }
        
        return path;
    }
    
    /**
     * Node for Dijkstra's priority queue.
     */
    private static class Node {
        UniversityStudent student;
        int distance;
        
        Node(UniversityStudent student, int distance) {
            this.student = student;
            this.distance = distance;
        }
    }
}