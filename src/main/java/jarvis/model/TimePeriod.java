package jarvis.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a time period in JARVIS.
 */
public class TimePeriod {

    public static final String MESSAGE_CONSTRAINTS_DATE = "Date should be in yyyy-MM-dd format";
    public static final String MESSAGE_CONSTRAINTS_TIME = "Time should be in HH:mm format";

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Start time must be before end time.
     * @param start Starting time.
     * @param end Ending time.
     */
    public TimePeriod(LocalDateTime start, LocalDateTime end) {
        assert start.isBefore(end) : "Start time must be before end time";
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean hasOverlap(TimePeriod other) {
        return end.isAfter(other.start) && start.isBefore(other.end);
    }

    /**
     * Returns true if start date and end date falls on the same day.
     */
    public boolean isOnSameDay() {
        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        return startDate.isEqual(endDate);
    }

    @Override
    public String toString() {
        if (isOnSameDay()) {
            return start.format(DATE_TIME_FORMAT) + " - " + end.format(TIME_FORMAT);
        }
        return start.format(DATE_TIME_FORMAT) + " - " + end.format(DATE_TIME_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimePeriod)) {
            return false;
        }

        TimePeriod otherTimePeriod = (TimePeriod) other;
        return otherTimePeriod.getStart().equals(getStart())
                && otherTimePeriod.getEnd().equals(getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
