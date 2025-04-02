package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Project.SetStatusDescriptor;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SetStatusCommandParser implements Parser<SetStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetStatusCommand
     * and returns an SetStatusCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SetStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        Index index;
        String projectName;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            projectName = argMultimap.getValue(PREFIX_PROJECT).get();
        } catch (NoSuchElementException | ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE), e);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        Optional<String> optProgressString = argMultimap.getValue(PREFIX_PROGRESS);
        Optional<String> optPaymentString = argMultimap.getValue(PREFIX_PAYMENT);
        Optional<String> optDeadlineString = argMultimap.getValue(PREFIX_DEADLINE);

        boolean isAnyFieldEdited = optProgressString.isPresent() || optPaymentString.isPresent()
                || optDeadlineString.isPresent();

        if (!isAnyFieldEdited) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        SetStatusDescriptor ssd = new SetStatusDescriptor();

        if (optProgressString.isPresent()) {
            ssd = ssd.setIsComplete(ParserUtil.parseProgress(optProgressString.get()));
        }
        if (optPaymentString.isPresent()) {
            ssd = ssd.setIsPaid(ParserUtil.parsePayment(optPaymentString.get()));
        }
        if (optDeadlineString.isPresent()) {
            ssd = ssd.setDeadline(ParserUtil.parseDeadline(optDeadlineString.get()));
        }

        return new SetStatusCommand(index, projectName, ssd);
    }
}
