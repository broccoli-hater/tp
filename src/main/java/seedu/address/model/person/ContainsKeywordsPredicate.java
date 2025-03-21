package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * An abstract class used to check if a specific field in a {@code Person} object contains a given keyword.
 */
public abstract class ContainsKeywordsPredicate implements Predicate<Person> {

    /**
     * Checks if a field in Person matches the keyword.
     *
     * @param person {@code Person} which the check is based on.
     * @return true if the field in Person contains the keyword.
     */
    public abstract boolean test(Person person);
}
