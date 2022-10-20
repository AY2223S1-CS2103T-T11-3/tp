package jarvis.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jarvis.commons.core.index.Index;
import jarvis.commons.util.StringUtil;
import jarvis.logic.parser.exceptions.ParseException;
import jarvis.model.Assessment;
import jarvis.model.MatricNum;
import jarvis.model.StudentName;
import jarvis.model.TaskDeadline;
import jarvis.model.TaskDesc;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_MARK = "Mark has to be a non-negative number.";
    public static final String MESSAGE_INVALID_MCNUM = "Mastery check number has to be 1 or 2.";
    public static final String MESSAGE_INVALID_MCRESULT = "Mastery check result has to be \"PASS\" or \"FAIL\"";

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
     * Parses a {@code String mcNum} into a {@code Assessment mc} corresponding to the specified mastery check.
     *
     * @throws ParseException if the given {@code mcNum} is invalid.
     */
    public static Assessment parseMcNum(String mcNum) throws ParseException {
        String trimmedMcNum = mcNum.trim();
        int value;
        try {
            value = Integer.parseInt(trimmedMcNum);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_MCNUM);
        }

        if (value != 1 && value != 2) {
            throw new ParseException(MESSAGE_INVALID_MCNUM);
        }
        return value == 1 ? Assessment.MC1 : Assessment.MC2;
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
            throw new ParseException(MESSAGE_INVALID_MCRESULT);
        }
    }

    public static double parseMarks(String marks) throws ParseException {
        requireNonNull(marks);
        String trimmedMarks = marks.trim();
        double value;
        try {
            value = Double.parseDouble(trimmedMarks);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_MARK);
        }

        if (value < 0) {
            throw new ParseException(MESSAGE_INVALID_MARK);
        }
        return value;
    }
}
