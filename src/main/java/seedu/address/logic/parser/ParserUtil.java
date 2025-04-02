package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        String sanitizedInput = trimmedPhone.replaceAll("\\s+", "");
        if (!Phone.isValidPhone(sanitizedInput)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(sanitizedInput);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        //requireNonNull(email);

        if (email != null) {
            String trimmedEmail = email.trim();
            if (!Email.isValidEmail(trimmedEmail)) {
                throw new ParseException(Email.MESSAGE_CONSTRAINTS);
            }

            return new Email(trimmedEmail);
        } else {
            return new Email(null);
        }
    }

    /**
     * Parses a {@code String payment} into a boolean value.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String payment} is invalid.
     */
    public static boolean parsePayment(String payment) throws ParseException {
        requireNonNull(payment);
        String formattedPayment = payment.trim().toLowerCase();

        if (formattedPayment.equals("paid")) {
            return true;
        } else if (formattedPayment.equals("unpaid")) {
            return false;
        }
        throw new ParseException(Project.MESSAGE_PAYMENT_CONSTRAINTS);
    }

    /**
     * Parses a {@code String progress} into a boolean value.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String progress} is invalid.
     */
    public static boolean parseProgress(String progress) throws ParseException {
        requireNonNull(progress);
        String formattedProgress = progress.trim().toLowerCase();

        if (formattedProgress.equals("complete")) {
            return true;
        } else if (formattedProgress.equals("incomplete")) {
            return false;
        }
        throw new ParseException(Project.MESSAGE_PROGRESS_CONSTRAINTS);
    }

    /**
     * Parses a {@code String deadline} into a LocalDateTime object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String deadline} is invalid.
     */
    public static LocalDateTime parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();

        try {
            return Project.dateTimeStringToLocalDateTime(trimmedDeadline);
        } catch (DateTimeParseException e) {
            throw new ParseException(Project.MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }


    /**
     * Parses a {@code String tag} into a {@code Project}.
     * Leading and trailing whitespaces will be trimmed.
     * Formats the String datetime to LocalDateTime
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Project parseProject(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Project(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new LinkedHashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName.toLowerCase()));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseProjects(Collection<String> projects) throws ParseException {
        requireNonNull(projects);
        final Set<Tag> tagSet = new LinkedHashSet<>();
        for (String tagName : projects) {
            tagSet.add(parseProject(tagName.toLowerCase()));
        }
        return tagSet;
    }
}
