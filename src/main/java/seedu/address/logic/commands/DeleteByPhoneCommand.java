package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes a person by their phone number in the addressbook.
 */
public class DeleteByPhoneCommand extends DeleteCommand {
    private final Phone phone;

    public DeleteByPhoneCommand(Phone phone) {
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = model.getFilteredPersonList()
            .stream()
            .filter(x -> x.getPhone().equals(phone))
            .findFirst()
            .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PHONE_NUMBER));

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof DeleteByPhoneCommand dbpc) {
            return phone.equals(dbpc.phone);
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
