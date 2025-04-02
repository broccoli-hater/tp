package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Add Tag(s) to a person in the address book.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tags or projects to a contact. "
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_PROJECT + "PROJECT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "project-x";

    public static final String MESSAGE_SUCCESS = "Tags and/or projects added to %1$s";
    private final Phone phone;
    private final Set<Tag> tags;

    /**
     * @param phone number of the person in the filtered person list to edit
     * @param tags to add
     */
    public TagCommand(Phone phone, Set<Tag> tags) {
        requireNonNull(phone);
        this.phone = phone;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToTag = model.getFilteredPersonList()
                .stream()
                .filter(x -> x.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PHONE_NUMBER));

        Person taggedPerson = tagProjectToPerson(personToTag, tags);

        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, taggedPerson.getName()));
    }

    /**
     * Create an edited person with the refreshed tag set
     * @param personToEdit current person to edit
     * @param newlyAddedTags tags to be added
     */
    public static Person tagProjectToPerson(Person personToEdit, Set<Tag> newlyAddedTags) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Set<Tag> currentTags = personToEdit.getTags();
        Set<Project> currentProjects = personToEdit.getProjects();
        Optional<Email> email = personToEdit.getEmail();

        // Add the current and newly added tags to a single Linked Hash Set
        Set<Tag> newTags = new LinkedHashSet<>(currentTags);
        newTags.addAll(currentProjects);
        newTags.addAll(newlyAddedTags);

        // Return new Person
        return new Person(name, phone, email, newTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherCommand = (TagCommand) other;
        return phone.equals(otherCommand.phone)
                && tags.equals(otherCommand.tags);
    }
}
