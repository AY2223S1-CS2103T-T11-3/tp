package jarvis.logic.commands;

import jarvis.commons.core.Messages;
import jarvis.commons.core.index.Index;
import jarvis.logic.commands.exceptions.CommandException;
import jarvis.model.Assessment;
import jarvis.model.GradeProfile;
import jarvis.model.Model;
import jarvis.model.Student;

import java.util.List;

import static jarvis.logic.parser.CliSyntax.*;
import static jarvis.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static java.util.Objects.requireNonNull;

public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Record a grade for the student identified "
            + "by the index number.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_RA1 + "MARKS] [" + PREFIX_RA2 + "MARKS] "
            + "[" + PREFIX_MIDTERM + "MARKS] [" + PREFIX_PRACTICAL_ASST + "MARKS] "
            + "[" + PREFIX_FINAL_ASST + "MARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RA1 + "17 " + PREFIX_MIDTERM + "55";

    public static final String MESSAGE_SUCCESS = "Marks recorded for student: %1$s";

    private final Index index;
    private final GradeProfile gradeProfile;

    public GradeCommand(Index index, GradeProfile gradeProfile) {
        requireNonNull(index);
        requireNonNull(gradeProfile);
        this.index = index;
        this.gradeProfile = gradeProfile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        studentToEdit.updateGrades(gradeProfile);
        model.setStudent(studentToEdit, studentToEdit);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format("Updated grades for " + studentToEdit));
    }

}
