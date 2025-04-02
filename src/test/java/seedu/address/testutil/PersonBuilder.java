package seedu.address.testutil;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";

    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private Phone phone;
    private Optional<Email> optionalEmail;
    private Set<Tag> tags;
    private Set<Project> projects;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        optionalEmail = Optional.of(new Email(DEFAULT_EMAIL));
        tags = new LinkedHashSet<>();
        projects = new LinkedHashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        optionalEmail = personToCopy.getEmail();
        tags = new LinkedHashSet<>(personToCopy.getTags());
        projects = new LinkedHashSet<>(personToCopy.getProjects());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withProjects(String ... projects) {
        this.projects = SampleDataUtil.getProjectSet(projects);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building with email.
     */
    public PersonBuilder withEmail(String input) {
        this.optionalEmail = Optional.ofNullable(input).map(x -> new Email(x));
        return this;
    }

    public Person build() {
        return new Person(name, phone, optionalEmail, tags);
    }

}
