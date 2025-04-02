package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Project.SetStatusDescriptor;

/**
 * Adds a person to the address book.
 */
public class SetStatusCommand extends Command {

    public static final String COMMAND_WORD = "setstatus";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of specified project of the "
            + "person identified by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PROJECT + "PROJECT "
            + "[" + PREFIX_PAYMENT + "PAYMENT] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_PROGRESS + "PROGRESS] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_PAYMENT + "paid "
            + PREFIX_DEADLINE + "07 Mar 2025 1800 "
            + PREFIX_PROGRESS + "complete ";

    public static final String MESSAGE_SUCCESS = "Project status is updated: %1$s";

    private final Index index;
    private final String projectName;
    private final SetStatusDescriptor setStatusDescriptor;

    /**
     * Creates an SetStatusCommand to update the specified {@code Project}
     */
    public SetStatusCommand(Index index, String projectName, SetStatusDescriptor setStatusDescriptor) {
        requireNonNull(index);
        requireNonNull(projectName);
        requireNonNull(setStatusDescriptor);

        this.index = index;
        this.projectName = projectName;
        this.setStatusDescriptor = new SetStatusDescriptor(setStatusDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Project projectToEdit = personToEdit.getProjects()
                .stream()
                .filter(x -> x.getTagName().equals(projectName))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PROJECT));

        Project editedProject = projectToEdit.createEditedProject(setStatusDescriptor);

        // Create new person with updated project, to replace old person with old project
        // This will ensure a new objectID and allow JavaFX to detect the change and update UI
        Person editedPerson = personToEdit.replaceProject(editedProject);
        model.setPerson(personToEdit, editedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedProject));
    }
}
