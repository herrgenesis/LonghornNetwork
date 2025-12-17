import java.util.*;

/**
 * Groups students into pods based on connection strengths.
 * Uses greedy algorithm to form groups with strong connections.
 */
public class PodFormation {

    private StudentGraph graph;

    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Forms pods of the specified size.
     * Starts with the most connected student, then adds their strongest connections.
     * 
     * @param podSize target size for each pod
     */
    public void formPods(int podSize) {
        List<UniversityStudent> all = graph.getAllNodes();
        Set<UniversityStudent> unassigned = new HashSet<>(all);
        List<List<UniversityStudent>> allPods = new ArrayList<>();

        while (!unassigned.isEmpty()) {
            List<UniversityStudent> pod = new ArrayList<>();
            UniversityStudent start = pickStrongestStarter(unassigned);
            pod.add(start);
            unassigned.remove(start);

            while (pod.size() < podSize) {
                UniversityStudent bestCandidate = null;
                int bestScore = -1;
                for (UniversityStudent s : pod) {
                    for (StudentGraph.Edge e : graph.getNeighbors(s)) {
                        if (unassigned.contains(e.neighbor) && e.weight > bestScore) {
                            bestScore = e.weight;
                            bestCandidate = e.neighbor;
                        }
                    }
                }
                if (bestCandidate == null) break;
                pod.add(bestCandidate);
                unassigned.remove(bestCandidate);
            }

            allPods.add(pod);
        }

        int num = 1;
        for (List<UniversityStudent> pod : allPods) {
            System.out.println("Pod " + num + ":");
            for (UniversityStudent s : pod) System.out.println("   " + s.getName());
            System.out.println();
            num++;
        }
    }

    /**
     * Picks the student with highest total connection strength as pod starter.
     */
    private UniversityStudent pickStrongestStarter(Set<UniversityStudent> unassigned) {
        UniversityStudent best = null;
        int bestSum = -1;
        for (UniversityStudent s : unassigned) {
            int sum = 0;
            for (StudentGraph.Edge e : graph.getNeighbors(s)) sum += e.weight;
            if (sum > bestSum) {
                bestSum = sum;
                best = s;
            }
        }
        return best;
    }
}
