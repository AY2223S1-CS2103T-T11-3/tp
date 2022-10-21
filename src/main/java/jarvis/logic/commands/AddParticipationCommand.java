package jarvis.logic.commands;

import static jarvis.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static jarvis.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static jarvis.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static jarvis.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static java.util.Objects.requireNonNull;

import java.util.List;

import jarvis.commons.core.Messages;
import jarvis.commons.core.index.Index;
import jarvis.logic.commands.exceptions.CommandException;
import jarvis.model.Lesson;
import jarvis.model.Model;
import jarvis.model.Student;
import jarvis.model.Studio;

/**
 * Adds participation for a student for a given studio.
 * The student is identified using its displayed index from the student book.
 * The studio is identified using its displayed index from the lesson book.
 */
public class AddParticipationCommand extends Command {

    public static final String COMMAND_WORD = "addpart";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds participation for a student for a studio. The student and studio are identified by their "
            + "respective index number used in the displayed student list and in the displayed lesson list.\n"
            + "Parameters: "
            + PREFIX_PARTICIPATION + "PARTICIPATION "
            + PREFIX_LESSON_INDEX + "LESSON INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PARTICIPATION + "100 " + PREFIX_LESSON_INDEX + "1 "
            + PREFIX_STUDENT_INDEX + "2";

    public static final String MESSAGE_ADD_PARTICIPATION_SUCCESS = "Added participation for %1$s in %2$s: %3$d";

    private final int participation;
    private final Index lessonIndex;
    private final Index studentIndex;

    /**
     * Creates a AddParticipationCommand to add participation for the specified student at student index for a given
     * studio at lesson index.
     *
     * @param participation
     * @param lessonIndex
     * @param studentIndex
     */
    public AddParticipationCommand(int participation, Index lessonIndex, Index studentIndex) {
        this.participation = participation;
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();
        if (lessonIndex.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        List<Student> lastShownStudentList = model.getFilteredStudentList();
        if (studentIndex.getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Lesson lessonToMark = lastShownLessonList.get(lessonIndex.getZeroBased());
        if (!(lessonToMark instanceof Studio)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDIO_DISPLAYED_INDEX);
        }
        Studio studioToMark = (Studio) lessonToMark;
        Student studentToMark = lastShownStudentList.get(studentIndex.getZeroBased());
        studioToMark.setParticipationForStudent(studentToMark, participation);
        model.setLesson(studioToMark, studioToMark);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(String.format(MESSAGE_ADD_PARTICIPATION_SUCCESS, studentToMark, studioToMark,
                participation));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddParticipationCommand // instanceof handles nulls
                && lessonIndex.equals(((AddParticipationCommand) other).lessonIndex)
                && studentIndex.equals(((AddParticipationCommand) other).studentIndex)
                && participation == ((AddParticipationCommand) other).participation);
        // state check
    }
}
