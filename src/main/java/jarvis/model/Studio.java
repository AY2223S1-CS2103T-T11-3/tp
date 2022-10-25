package jarvis.model;

import java.util.Collection;
import java.util.Objects;

import jarvis.storage.JsonAdaptedStudio;
import jarvis.storage.JsonAdaptedTimePeriod;

/**
 * Represents a Studio in JARVIS.
 * Guarantees: details are present and not null.
 */
public class Studio extends Lesson {
    private final StudioParticipation participation;

    /**
     * Every field must be present and not null.
     */
    public Studio(LessonDesc lessonDesc, TimePeriod timePeriod, Collection<Student> students) {
        super(lessonDesc, timePeriod, students);
        this.participation = new StudioParticipation(students);
    }

    public void setParticipationForStudent(Student student, int i) {
        participation.setParticipationForStudent(student, i);
    }

    public int getParticipationForStudent(Student student) {
        return participation.getParticipationForStudent(student);
    }

    public StudioParticipation getParticipation() {
        return participation;
    }

    @Override
    public void setStudent(Student targetStudent, Student editedStudent) {
        super.setStudent(targetStudent, editedStudent);
        participation.setStudent(targetStudent, editedStudent);
    }
    /**
     * Returns true if both Studios have the same description,
     * occur at the same time, are attended by the same students,
     * and all students have the same participation scores.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Studio)) {
            return false;
        }

        Studio otherStudio = (Studio) other;

        boolean studioDescEquality;
        if (hasDesc()) {
            studioDescEquality = getDesc().equals(otherStudio.getDesc());
        } else {
            studioDescEquality = otherStudio.getDesc() == null;
        }

        return studioDescEquality
                && otherStudio.startDateTime().equals(startDateTime())
                && otherStudio.endDateTime().equals(endDateTime())
                && otherStudio.getAttendance().equals(getAttendance())
                && otherStudio.getParticipation().equals(getParticipation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDesc(), getTimePeriod(), getAttendance(), getParticipation());
    }

    @Override
    public String toString() {
        return "Studio at " + getTimePeriod();
    }

    @Override
    public LessonType getLessonType() {
        return LessonType.STUDIO;
    }
}
