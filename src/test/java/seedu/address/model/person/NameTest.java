package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void nameContainsPrefix() {
        assertFalse(Name.isValidName("abcn/991")); // with known delimiter
        assertFalse(Name.isValidName("abcp/991")); // with known delimiter
        assertFalse(Name.isValidName("abce/991")); // with known delimiter
        assertFalse(Name.isValidName("abct/991")); // with known delimiter
        assertFalse(Name.isValidName("abcproj/991")); // with known delimiter
        assertFalse(Name.isValidName("abcby/991")); // with known delimiter
        assertFalse(Name.isValidName("abcpay/991")); // with known delimiter
        assertFalse(Name.isValidName("abcpaprog/991")); // with known delimiter
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // more than 40 chars
        assertFalse(Name.isValidName("John@Doe")); // with invalid special characters @
        assertFalse(Name.isValidName("John Doe!")); // with invalid special characters !
        assertFalse(Name.isValidName("John Doe\\")); // with invalid special characters \\

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("John Doe.")); // with doe
        assertTrue(Name.isValidName("Joh/Doe")); // with slash
        assertTrue(Name.isValidName("John_Doe")); // with underscore
        assertTrue(Name.isValidName("Dr. /Prof John")); // with slash and dot
        assertTrue(Name.isValidName("Oneida / ka")); // with slash and dot
        assertTrue(Name.isValidName("Tom s/o Tommy")); // with slash and dot
        assertTrue(Name.isValidName("Tom, Tea Tommy")); // with comma
        assertTrue(Name.isValidName("O'neil Tommy")); // with apostrophe
        assertTrue(Name.isValidName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // 40 characters

    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));

        // extra space will be different from each other
        assertFalse(name.equals(new Name("ValidName")));

        // upper/lower case are not the same
        assertFalse(name.equals(new Name("valid name")));
    }

    @Test
    public void invalidName_characters_invalid() {
        String invalidName = "John@Doe"; // Invalid because of '@'
        Optional<String> errorMessage = Name.invaildNameCheck(invalidName);
        assertTrue(errorMessage.isPresent());
        assertEquals(Messages.INVALID_NAME_CHARACTERS_MESSAGE, errorMessage.get());
    }

    @Test
    public void nameTooLong_invalid() {
        String nameTooLong = "a".repeat(41); // 41 characters, should be invalid
        Optional<String> errorMessage = Name.invaildNameCheck(nameTooLong);
        assertTrue(errorMessage.isPresent());
        assertEquals(Messages.MESSAGE_NAME_LENGTH_ERROR, errorMessage.get());
    }

    @Test
    public void emptyName_invalid() {
        String emptyName = ""; // Empty name
        Optional<String> errorMessage = Name.invaildNameCheck(emptyName);
        assertTrue(errorMessage.isPresent());
        assertEquals(Messages.MESSAGE_EMPTY_NAME_MSG, errorMessage.get());
    }

    @Test
    public void spaceName_invalid() {
        String emptyName = " "; // Empty name
        Optional<String> errorMessage = Name.invaildNameCheck(emptyName);
        assertTrue(errorMessage.isPresent());
        assertEquals(Messages.MESSAGE_EMPTY_NAME_MSG, errorMessage.get());
    }
}
