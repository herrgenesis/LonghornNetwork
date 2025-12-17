import java.util.*;

/**
 * Abstract base class for a student.
 * Defines common attributes and requires subclasses to implement
 * connection strength calculation.
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;

    /**
     * Calculates a connection strength between this student and another.
     *
     * @param other another Student
     * @return integer strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
