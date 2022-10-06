package jarvis.logic.commands;

import static jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static jarvis.testutil.TypicalStudents.getTypicalStudentBook;

import jarvis.logic.commands.student.ListCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jarvis.model.Model;
import jarvis.model.ModelManager;
import jarvis.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getStudentBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
