package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("12345678")); //starting with 1
        assertFalse(Phone.isValidPhone("22345678")); //starting with 2
        assertFalse(Phone.isValidPhone("32345678")); //starting with 3
        assertFalse(Phone.isValidPhone("42345678")); //starting with 4
        assertFalse(Phone.isValidPhone("52345678")); //starting with 5
        assertFalse(Phone.isValidPhone("72345678")); //starting with 7
        assertFalse(Phone.isValidPhone("02345678")); //starting with 0
        assertFalse(Phone.isValidPhone("922345678")); //more than 8
        assertFalse(Phone.isValidPhone("9223456")); //less than 8

        //Valid phone number
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("63121534"));
        assertTrue(Phone.isValidPhone("83121534"));

    }

    @Test
    public void equals() {
        Phone phone = new Phone("91234567");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("91234567")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("98765432")));
    }
}
