package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TagCommand.tagProjectToPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagCommand.
 */
public class TagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Tag sampleTag;
    private Set<Tag> tagsToAdd;
    @BeforeEach
    public void setUp() {
        sampleTag = new Tag("T_3st-x");
        tagsToAdd = new LinkedHashSet<>();
        tagsToAdd.add(sampleTag);
    }

    @Test
    public void execute_tag_success() {
        Person personToTag = getTypicalPersons().get(1);
        Phone phone = personToTag.getPhone();
        TagCommand tagComd = new TagCommand(phone, tagsToAdd);
        Person taggedPerson = tagProjectToPerson(personToTag, tagsToAdd);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, taggedPerson.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, taggedPerson);

        assertCommandSuccess(tagComd, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person p = new PersonBuilder().withTags("T_3st-x").build();
        Phone phone = p.getPhone();
        TagCommand addTagCommand = new TagCommand(phone, tagsToAdd);
        TagCommand otherAddTagCommand = new TagCommand(phone, new LinkedHashSet<>());

        // same object -> returns true
        assertTrue(addTagCommand.equals(addTagCommand));

        // same values -> returns true
        TagCommand tagCommandCopy = new TagCommand(phone, tagsToAdd);
        assertTrue(addTagCommand.equals(tagCommandCopy));

        // different types -> returns false
        assertFalse(addTagCommand.equals(1));

        // null -> returns false
        assertFalse(addTagCommand.equals(null));

        // different person -> returns false
        assertFalse(addTagCommand.equals(otherAddTagCommand));
    }
}
