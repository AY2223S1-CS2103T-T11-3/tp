package jarvis.logic.commands;

import static jarvis.logic.commands.StudentCommandTestUtil.assertCommandSuccess;
import static jarvis.logic.commands.StudentCommandTestUtil.showPersonAtIndex;
import static jarvis.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static jarvis.testutil.TypicalStudents.getTypicalStudentBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jarvis.model.Model;
import jarvis.model.ModelManager;
import jarvis.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListStudentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getStudentBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListStudentCommand(), model, ListStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_OBJECT);
        assertCommandSuccess(new ListStudentCommand(), model, ListStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
