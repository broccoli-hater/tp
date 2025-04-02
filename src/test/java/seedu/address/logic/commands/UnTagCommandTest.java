package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnTagCommand.checkForTagInExistingTags;
import static seedu.address.logic.commands.UnTagCommand.unTagProjectFromPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnTagCommand.
 */
public class UnTagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag sampleTag;
    private Set<Tag> tagsToRemove;
    @BeforeEach
    public void setUp() {
        sampleTag = new Tag("friends");
        tagsToRemove = new LinkedHashSet<>();
        tagsToRemove.add(sampleTag);
    }

    @Test
    public void execute_unTag_success() {
        Person personToTag = getTypicalPersons().get(1);
        Phone phone = personToTag.getPhone();
        UnTagCommand untagComd = new UnTagCommand(phone, tagsToRemove);
        Person unTaggedPerson = unTagProjectFromPerson(personToTag, tagsToRemove);
        String check = checkForTagInExistingTags(personToTag.getTags(), tagsToRemove);

        String expectedMessage = String.format(UnTagCommand.MESSAGE_SUCCESS, unTaggedPerson.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, unTaggedPerson);
        assertCommandSuccess(untagComd, model, expectedMessage, expectedModel);
        assertEquals("", check);
    }

    @Test
    public void execute_unTag_failure() {
        Person personToTag = getTypicalPersons().get(2);
        Phone phone = personToTag.getPhone();
        UnTagCommand untagComd = new UnTagCommand(phone, tagsToRemove);
        Person unTaggedPerson = unTagProjectFromPerson(personToTag, tagsToRemove);
        String check = checkForTagInExistingTags(personToTag.getTags(), tagsToRemove);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, unTaggedPerson);
        CommandException thrown = assertThrows(CommandException.class, () -> untagComd.execute(model));
        assertEquals(String.format(Messages.MESSAGE_ABSENT_TAG_PROJECT, check, personToTag.getName(),
                personToTag.getPhone()), thrown.getMessage());
    }

    @Test
    public void checkForTag_success() {
        Person person = getTypicalPersons().get(1);
        Set<Tag> personTags = person.getTags();
        String check = checkForTagInExistingTags(personTags, tagsToRemove);
        assertEquals("", check);
    }

    @Test
    public void checkForTag_failure() {
        Person person = getTypicalPersons().get(2);
        Set<Tag> personTags = person.getTags();
        String check = checkForTagInExistingTags(personTags, tagsToRemove);
        assertEquals("friends", check);
    }

    @Test
    public void equals() {
        Person p = new PersonBuilder().withTags("T_3st-x").build();
        Phone phone = p.getPhone();
        UnTagCommand removeTagCommand = new UnTagCommand(phone, tagsToRemove);
        UnTagCommand otherRemoveTagCommand = new UnTagCommand(phone, new LinkedHashSet<>());

        // same object -> returns true
        assertTrue(removeTagCommand.equals(removeTagCommand));

        // same values -> returns true
        UnTagCommand unTagCommandCopy = new UnTagCommand(phone, tagsToRemove);
        assertTrue(removeTagCommand.equals(unTagCommandCopy));

        // different types -> returns false
        assertFalse(removeTagCommand.equals(1));

        // null -> returns false
        assertFalse(removeTagCommand.equals(null));

        // different person -> returns false
        assertFalse(removeTagCommand.equals(otherRemoveTagCommand));
    }
}
