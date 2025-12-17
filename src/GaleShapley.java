import java.util.*;

/**
 * Implements Gale-Shapley algorithm for stable roommate matching.
 * Students propose to their preferences in order until stable matches are found.
 */
public class GaleShapley {

    /**
     * Assigns roommates using stable matching algorithm.
     * Updates each student's assignedRoommate field.
     * 
     * @param students list of students to match
     */
    public static void setAssignedRoommate(List<UniversityStudent> students) {
        // Map student name -> student object for quick lookup
        Map<String, UniversityStudent> nameMap = new HashMap<>();
        for (UniversityStudent s : students) {
            s.setAssignedRoommate(null); // Clear previous assignments
            nameMap.put(s.getName(), s);
        }

        // Queue of free students who still want a roommate
        Queue<UniversityStudent> freeStudents = new LinkedList<>();
        for (UniversityStudent s : students) {
            if (!s.getRoommatePreferences().isEmpty()) {
                freeStudents.add(s);
            }
        }

        // Track the index of next roommate to propose to for each student
        Map<UniversityStudent, Integer> nextProposalIndex = new HashMap<>();
        for (UniversityStudent s : students) {
            nextProposalIndex.put(s, 0);
        }

        while (!freeStudents.isEmpty()) {
            UniversityStudent proposer = freeStudents.poll();
            
            // Skip if proposer is already paired (important for reciprocal matching)
            if (proposer.getRoommate() != null) {
                continue;
            }
            
            List<String> prefs = proposer.getRoommatePreferences();
            int idx = nextProposalIndex.get(proposer);

            if (idx >= prefs.size()) {
                // No one left to propose to
                continue;
            }

            String prefName = prefs.get(idx);
            nextProposalIndex.put(proposer, idx + 1); // Move to next preference next time
            UniversityStudent proposee = nameMap.get(prefName);

            if (proposee.getRoommate() == null) {
                // Proposee is free — accept proposal
                proposer.setAssignedRoommate(proposee);
                proposee.setAssignedRoommate(proposer);
            } else {
                UniversityStudent current = proposee.getRoommate();

                // Decide if proposee prefers new proposer over current
                if (prefers(proposee, proposer, current)) {
                    // Swap: new proposer gets accepted, current goes back to free
                    current.setAssignedRoommate(null);
                    proposer.setAssignedRoommate(proposee);
                    proposee.setAssignedRoommate(proposer);
                    freeStudents.add(current);
                } else {
                    // Proposer remains free, will try next preference later
                    freeStudents.add(proposer);
                }
            }
        }
    }

    /**
     * Checks if proposee prefers newProposer over current roommate.
     * 
     * @return true if newProposer appears earlier in preference list
     */
    private static boolean prefers(UniversityStudent proposee, UniversityStudent newProposer, UniversityStudent current) {
        List<String> prefs = proposee.getRoommatePreferences();
        int newIndex = prefs.indexOf(newProposer.getName());
        int currentIndex = prefs.indexOf(current.getName());
        return newIndex != -1 && (currentIndex == -1 || newIndex < currentIndex);
    }
}