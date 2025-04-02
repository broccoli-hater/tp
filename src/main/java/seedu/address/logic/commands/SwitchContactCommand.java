package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PreferredContactMethod;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Switches the preferred contact method of a person between phone and email.
 */
public class SwitchContactCommand extends Command {

    public static final String COMMAND_WORD = "switchcontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the preferred contact method between phone "
            + "and email. If the current preferred contact is set to phone, this command will change it to email, "
            + "and vice versa. "
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "98765432 ";

    public static final String MESSAGE_SUCCESS = "Preferred contact method has switched successfully!";
    public static final String NO_EMAIL_FAILURE = "Cannot switch contact method because email is not provided.";
    private final Phone phone;

    /**
     * Constructs a {@code SwitchContactCommand} with the specified phone number.
     *
     * @param phone The phone number of the person whose preferred contact method is to be switched.
     */
    public SwitchContactCommand(Phone phone) {
        requireNonNull(phone);
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToSwitchPrefContact = model.getFilteredPersonList()
                .stream()
                .filter(x -> x.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PHONE_NUMBER));

        // Check if the email is present
        if (personToSwitchPrefContact.getEmail().isEmpty()) {
            throw new CommandException(NO_EMAIL_FAILURE);
        }

        Person newPrefContactPerson = switchPreferredContactFromPerson(personToSwitchPrefContact);

        model.setPerson(personToSwitchPrefContact, newPrefContactPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Creates a new {@code Person} object with the preferred contact method switched.
     *
     * @param personToEdit The person whose preferred contact method is to be switched.
     * @return A new {@code Person} object with the updated preferred contact method.
     */
    public static Person switchPreferredContactFromPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();

        Set<Tag> currentTags = personToEdit.getTags();
        Set<Project> currentProjects = personToEdit.getProjects();
        Optional<Email> email = personToEdit.getEmail();

        // Add the current and newly added tags to a single Linked Hash Set
        Set<Tag> newTags = new LinkedHashSet<>(currentTags);
        newTags.addAll(currentProjects);

        PreferredContactMethod preferredContactMethod = personToEdit.getPreferredContactMethod();

        //Switch the contact method
        PreferredContactMethod newPreferredContactMethod = preferredContactMethod.switchPreferredContactMethod();

        // Return new Person
        return new Person(name, phone, email, newTags, newPreferredContactMethod);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SwitchContactCommand swcc) {
            return this.phone.equals(swcc.phone);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("phone", phone)
            .toString();
    }
}
