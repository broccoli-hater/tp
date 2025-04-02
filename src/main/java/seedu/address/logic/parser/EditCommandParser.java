package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person.EditPersonDescriptor;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        Optional<String> optionalNameString = argMultimap.getValue(PREFIX_NAME);
        Optional<String> optionalPhoneString = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> optionalEmailString = argMultimap.getValue(PREFIX_EMAIL);

        Optional<Name> optionalName = Optional.empty();
        Optional<Phone> optionalPhone = Optional.empty();
        Optional<Email> optionalEmail = Optional.empty();

        if (optionalNameString.isPresent()) {
            optionalName = Optional.of(ParserUtil.parseName(optionalNameString.get()));
        }
        if (optionalPhoneString.isPresent()) {
            optionalPhone = Optional.of(ParserUtil.parsePhone(optionalPhoneString.get()));
        }
        if (optionalEmailString.isPresent()) {
            optionalEmail = Optional.of(ParserUtil.parseEmail(optionalEmailString.get()));
        }

        boolean isAnyFieldEdited = optionalName.isPresent() || optionalPhone.isPresent() || optionalEmail.isPresent();

        if (!isAnyFieldEdited) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(
                optionalName,
                optionalPhone,
                optionalEmail);

        return new EditCommand(index, editPersonDescriptor);
    }
}
