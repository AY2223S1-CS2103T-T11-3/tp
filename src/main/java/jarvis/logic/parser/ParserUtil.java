package jarvis.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jarvis.commons.core.Messages;
import jarvis.commons.core.index.Index;
import jarvis.commons.util.StringUtil;
import jarvis.logic.parser.exceptions.ParseException;
import jarvis.model.LessonDesc;
import jarvis.model.MasteryCheckStatus;
import jarvis.model.MatricNum;
import jarvis.model.StudentName;
import jarvis.model.TaskDeadline;
import jarvis.model.TaskDesc;
import jarvis.model.TimePeriod;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static StudentName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!StudentName.isValidName(trimmedName)) {
            throw new ParseException(StudentName.MESSAGE_CONSTRAINTS);
        }
        return new StudentName(trimmedName);
    }

    /**
     * Parses a {@code String matricNum} into a {@code MatricNum}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matricNum} is invalid.
     */
    public static MatricNum parseMatricNum(String matricNum) throws ParseException {
        requireNonNull(matricNum);
        String trimmedMatricNum = matricNum.trim();
        if (!MatricNum.isValidMatricNum(trimmedMatricNum)) {
            throw new ParseException(MatricNum.MESSAGE_CONSTRAINTS);
        }
        return new MatricNum(trimmedMatricNum);
    }

    /**
     * Parses a {@code String taskDesc} into a {@code TaskDesc}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskDesc parseTaskDesc(String taskDesc) throws ParseException {
        requireNonNull(taskDesc);
        String trimmedTaskDesc = taskDesc.trim();
        if (!TaskDesc.isValidTaskDesc(trimmedTaskDesc)) {
            throw new ParseException(TaskDesc.MESSAGE_CONSTRAINTS);
        }
        return new TaskDesc(trimmedTaskDesc);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskDeadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        if (deadline.isEmpty()) {
            return new TaskDeadline(null);
        }
        try {
            LocalDate trimmedDeadline = LocalDate.parse(deadline.trim());
            return new TaskDeadline(trimmedDeadline);
        } catch (DateTimeParseException e) {
            throw new ParseException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String mcNum} into an {@code int} indicating the mastery check number.
     *
     * @throws ParseException if the given {@code mcNum} is invalid.
     */
    public static int parseMcNum(String mcNum) throws ParseException {
        String trimmedMcNum = mcNum.trim();
        int value;
        try {
            value = Integer.parseInt(trimmedMcNum);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MasteryCheckStatus.MESSAGE_INVALID_MCNUM);
        }

        if (value != 1 && value != 2) {
            throw new ParseException(MasteryCheckStatus.MESSAGE_INVALID_MCNUM);
        }
        return value;
    }

    /**
     * Parses a {@code String mcResult} into a {@code boolean}.
     *
     * @return True if the mastery check result is a pass
     * @throws ParseException if the given {@code mcResult} is invalid.
     */
    public static boolean parseMcResult(String mcResult) throws ParseException {
        requireNonNull(mcResult);
        String trimmedMcResult = mcResult.trim().toLowerCase();
        if (trimmedMcResult.equals("pass")) {
            return true;
        } else if (trimmedMcResult.equals("fail")) {
            return false;
        } else {
            throw new ParseException(MasteryCheckStatus.MESSAGE_INVALID_MCRESULT);
        }
    }

    /**
     * Parses a {@code String lessonDesc} into a {@code LessonDesc}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lessonDesc} is invalid.
     */
    public static LessonDesc parseLessonDesc(String lessonDesc) throws ParseException {
        requireNonNull(lessonDesc);
        String trimmedLessonDesc = lessonDesc.trim().toLowerCase();
        if (!LessonDesc.isValidLessonDesc(trimmedLessonDesc)) {
            throw new ParseException(LessonDesc.MESSAGE_CONSTRAINTS);
        }
        return new LessonDesc(trimmedLessonDesc);
    }

    /**
     * Parses a {@code String DateTime} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);

        try {
            LocalDateTime actualDateTime = LocalDateTime.parse(dateTime);
            return actualDateTime;
        } catch (DateTimeParseException e) {
            throw new ParseException(TimePeriod.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Collection<String> studentIndexes} into a {@code Set<Student>}.
     *
     * @throws ParseException if the given {@code studentIndexes} is invalid.
     */
    public static Set<Index> parseStudentIndexes(Collection<String> studentIndexes) throws ParseException {
        requireNonNull(studentIndexes);

        final Set<Index> studentIndexSet = new HashSet<>();
        try {
            for (String studentIndex : studentIndexes) {
                requireNonNull(studentIndex);
                Index trimmedStudentIndex = ParserUtil.parseIndex(studentIndex.trim());
                studentIndexSet.add(trimmedStudentIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        return studentIndexSet;
    }

}
