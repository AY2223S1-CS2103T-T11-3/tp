package jarvis.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jarvis.model.exceptions.StudentNotFoundException;

/**
 * Represents the attendance for a lesson in JARVIS.
 */
public class LessonAttendance {

    private final TreeMap<Student, Boolean> attendance;
    /**
     * Creates the attendance list for a lesson.
     * @param students Students who are involved in the lesson.
     */
    public LessonAttendance(Collection<Student> students) {
        attendance = new TreeMap<>(Comparator.comparing(s -> s.getName().toString()));
        for (Student stu : students) {
            attendance.put(stu, false);
        }
    }

    /**
     * Creates the attendance list with the given attendance data for a lesson.
     * @param attendance The given attendance data.
     */
    public LessonAttendance(TreeMap<Student, Boolean> attendance) {
        this.attendance = attendance;
    }

    /**
     * Marks a student as present for that lesson.
     * @param student Student to mark as present.
     */
    public void markAsPresent(Student student) {
        if (!attendance.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        attendance.put(student, true);
    }

    /**
     * Marks a student as absent for that lesson.
     * @param student Student to mark as absent.
     */
    public void markAsAbsent(Student student) {
        if (!attendance.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        attendance.put(student, false);
    }

    public boolean isPresent(Student student) {
        return attendance.getOrDefault(student, false);
    }

    public Set<Student> getAllStudents() {
        return attendance.keySet();
    }

    public String getAllStudentsName() {
        StringBuilder studentsNameBuilder = new StringBuilder();
        studentsNameBuilder.append("Students: ");
        Set<Student> students = getAllStudents();
        assert !students.isEmpty();

        for (Student student : students) {
            studentsNameBuilder.append(student.getName() + ", ");
        }
        studentsNameBuilder.deleteCharAt(studentsNameBuilder.length() - 2); //remove the last ','
        return studentsNameBuilder.toString();
    }

    public Map<Student, Boolean> getAttendance() {
        return attendance;
    }

    public void setStudent(Student targetStudent, Student editedStudent) {
        boolean b = attendance.get(targetStudent);
        attendance.remove(targetStudent);
        attendance.put(editedStudent, b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Attendance:\n");
        for (Map.Entry<Student, Boolean> entry : attendance.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue() ? "PRESENT" : "ABSENT");
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonAttendance)) {
            return false;
        }

        LessonAttendance otherLessonAttendance = (LessonAttendance) other;
        return otherLessonAttendance.attendance.equals(attendance);
    }

    @Override
    public int hashCode() {
        return attendance.hashCode();
    }
}
