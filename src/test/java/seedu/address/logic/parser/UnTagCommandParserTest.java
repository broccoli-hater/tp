package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_X;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnTagCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class UnTagCommandParserTest {

    public static final String PHONE_DESC_ALICE = " " + PREFIX_PHONE + VALID_PHONE_ALICE;
    public static final String PHONE_DESC_BENSON = " " + PREFIX_PHONE + VALID_PHONE_BENSON;
    private UnTagCommandParser parser = new UnTagCommandParser();
    private Project sampleProject;
    private Tag sampleTag;
    private Set<Tag> projectsToRemove;
    private Set<Tag> tagsToRemove;
    @BeforeEach
    public void setUp() {
        sampleTag = new Tag(VALID_TAG_FRIENDS);
        tagsToRemove = new LinkedHashSet<>();
        tagsToRemove.add(sampleTag);

        sampleProject = new Project(VALID_PROJECT_X);
        projectsToRemove = new LinkedHashSet<>();
        projectsToRemove.add(sampleProject);
    }

    @Test
    public void parse_tagPresent_success() {
        Person person = new PersonBuilder(ALICE).build();
        Phone personPhone = person.getPhone();
        assertParseSuccess(parser, PHONE_DESC_ALICE
                + TAG_DESC_FRIENDS, new UnTagCommand(personPhone, tagsToRemove));
    }

    @Test
    public void parse_projectPresent_success() {
        Person person = new PersonBuilder(BENSON).build();
        Phone personPhone = person.getPhone();
        assertParseSuccess(parser, PHONE_DESC_BENSON
                + PROJECT_DESC_X, new UnTagCommand(personPhone, projectsToRemove));
    }

    @Test
    public void parse_invalidField_failure() {
        // Invalid Phone Number (not starting with 6, 8 or 9)
        assertParseFailure(parser, INVALID_PHONE_DESC
                + TAG_DESC_FRIENDS, Phone.MESSAGE_CONSTRAINTS);

        // Invalid Tag (Contains non-alphanumerical characters, beyond hyphens/underscore)
        assertParseFailure(parser, PHONE_DESC_BENSON
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // Invalid Project Name (Contains non-alphanumerical characters, beyond hyphens/underscore)
        assertParseFailure(parser, PHONE_DESC_BENSON
                + INVALID_PROJECT_DESC, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnTagCommand.MESSAGE_USAGE);

        // Missing All
        assertParseFailure(parser, "", expectedMessage);

        // Missing Phone Number
        assertParseFailure(parser, TAG_DESC_FRIENDS, expectedMessage);

        // Missing Tag/Project (Only has Phone Number)
        assertParseFailure(parser, PHONE_DESC_ALICE, expectedMessage);

    }
}
