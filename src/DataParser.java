import java.util.*;
import java.io.*;

/**
 * Parses student data from input files.
 * Handles pipe-delimited format and validates roommate preferences.
 */
public class DataParser {

    /**
     * Reads students from a file and creates UniversityStudent objects.
     * Filters roommate preferences to only include valid student names.
     * 
     * @param filePath path to the input file
     * @return list of parsed students
     * @throws IOException if file is unreadable or malformed
     */
    public static List<UniversityStudent> parseStudentsFromFile(String filePath) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length < 8) throw new IOException("Malformed line: " + line);

            String name = parts[0].trim();
            int age = Integer.parseInt(parts[1].trim());
            String gender = parts[2].trim();
            int year = Integer.parseInt(parts[3].trim());
            String major = parts[4].trim();
            double gpa = Double.parseDouble(parts[5].trim());

            List<String> roommatePrefs = parseList(parts[6].trim());
            List<String> internships = parseList(parts[7].trim());

            students.add(new UniversityStudent(name, age, gender, year, major, gpa, roommatePrefs, internships));
        }

        reader.close();

        // Remove invalid roommate preferences
        Set<String> validNames = new HashSet<>();
        for (UniversityStudent s : students) validNames.add(s.getName());

        for (UniversityStudent s : students) {
            List<String> filtered = new ArrayList<>();
            for (String pref : s.getRoommatePreferences()) {
                if (validNames.contains(pref)) filtered.add(pref);
            }
            s.getRoommatePreferences().clear();
            s.getRoommatePreferences().addAll(filtered);
        }

        return students;
    }

    /**
     * Parses a comma-separated list. Returns empty list for "None" or empty string.
     */
    private static List<String> parseList(String input) {
        if (input.isEmpty() || input.equalsIgnoreCase("None")) return new ArrayList<>();
        String[] items = input.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) list.add(item.trim());
        return list;
    }
}
