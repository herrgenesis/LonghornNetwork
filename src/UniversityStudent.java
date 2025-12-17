import java.util.*;

/**
 * Represents a university student with roommate preferences and internship history.
 * Connection strength is calculated based on shared attributes:
 * roommate (+4), shared internships (+3 each), same major (+2), same age (+1).
 */
public class UniversityStudent extends Student {

    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    /**
     * Creates a new university student.
     */
    private List<String> messageHistory = new ArrayList<>();
    private UniversityStudent assignedRoommate;

    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = new ArrayList<>(roommatePreferences);
        this.previousInternships = new ArrayList<>(previousInternships);
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public int getYear() { return year; }
    public String getMajor() { return major; }
    public double getGpa() { return gpa; }
    public List<String> getRoommatePreferences() { return roommatePreferences; }
    public List<String> getPreviousInternships() { return previousInternships; }
    public UniversityStudent getRoommate() { return assignedRoommate; }
    public void setAssignedRoommate(UniversityStudent roommate) { this.assignedRoommate = roommate; }

    public void addMessageToHistory(String message) { messageHistory.add(message); }
    public List<String> getMessageHistory() { return new ArrayList<>(messageHistory); }

     /**
     * Calculates connection strength with another student.
     * Checks for roommate status, shared internships, same major, and same age.
     * 
     * @param other the other student
     * @return total connection strength
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        if (!(other instanceof UniversityStudent)) return 0;
        UniversityStudent o = (UniversityStudent) other;
        int score = 0;
        
        // Check if they are roommates (+4)
        if (this.assignedRoommate != null && this.assignedRoommate.equals(o)) {
            score += 4;
        }
        
        // Count shared internships (+3 each)
        for (String internship : this.previousInternships) {
            if (o.previousInternships.contains(internship)) {
                score += 3;
            }
        }
        
        // Same major (+2)
        if (this.major.equals(o.major)) {
            score += 2;
        }
        
        // Same age (+1)
        if (this.age == o.age) {
            score += 1;
        }
        
        return score;
    }

    @Override
    public String toString() {
        return "UniversityStudent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", GPA=" + gpa +
                ", roommatePreferences=" + roommatePreferences +
                ", previousInternships=" + previousInternships +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UniversityStudent)) return false;
        return this.name.equals(((UniversityStudent) obj).name);
    }

    @Override
    public int hashCode() { return name.hashCode(); }
}