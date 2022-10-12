package jarvis.testutil;

import java.time.LocalDate;

import jarvis.model.Task;
import jarvis.model.TaskDeadline;
import jarvis.model.TaskDesc;

public class TaskBuilder {

    public static final String DEFAULT_TASK_DESC = "Mark quest 1";
    public static final LocalDate DEFAULT_TASK_DEADLINE = null;

    private TaskDesc taskDesc;
    private TaskDeadline taskDeadline;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        taskDesc = new TaskDesc(DEFAULT_TASK_DESC);
        taskDeadline = new TaskDeadline(DEFAULT_TASK_DEADLINE);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        taskDesc = taskToCopy.getDesc();
        taskDeadline = taskToCopy.getDeadline();
    }

    /**
     * Sets the {@code Desc} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        this.taskDesc = new TaskDesc(desc);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(LocalDate deadline) {
        this.taskDeadline = new TaskDeadline(deadline);
        return this;
    }

    public Task build() {
        return new Task(taskDesc, taskDeadline);
    }
}
