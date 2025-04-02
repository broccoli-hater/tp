package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.UnTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UnTagCommand object
 */
public class UnTagCommandParser implements Parser<UnTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnTagCommand
     * and returns an UnTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_TAG, PREFIX_PROJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE)
                || (argMultimap.getValue(PREFIX_TAG).isEmpty() && argMultimap.getValue(PREFIX_PROJECT).isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnTagCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE);
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        tagList.addAll(ParserUtil.parseProjects(argMultimap.getAllValues(PREFIX_PROJECT)));

        return new UnTagCommand(phone, tagList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
