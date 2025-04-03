package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_ABSENT_PHONE_NUMBER = "No person found with that phone number";
    public static final String MESSAGE_ABSENT_TAG_PROJECT = "Tag/Project \"%1$s\" is not found with %2$s (%3$s)";
    public static final String MESSAGE_ABSENT_PROJECT = "No project found with that name";

    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String INVALID_NAME_CHARACTERS_MESSAGE = "Name contains invalid characters. Only letters,"
            + " numbers, spaces, '-', '_', '.', ',', apostrophe (') and '/' are allowed.";
    public static final String MESSAGE_NAME_LENGTH_ERROR = "Name must not exceed 40 characters.";
    public static final String MESSAGE_EMPTY_NAME_MSG = "Name field cannot be empty.";
    public static final String MESSAGE_NAME_CONTAINS_PREFIX = "Name contains command prefix.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
