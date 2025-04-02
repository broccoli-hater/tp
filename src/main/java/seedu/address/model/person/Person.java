package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Optional<Email> optionalEmail;
    private final PreferredContactMethod preferredContactMethod;

    // Data fields
    private final Set<Tag> tags = new LinkedHashSet<>();
    private final Set<Project> projects = new LinkedHashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Optional<Email> optionalEmail, Set<Tag> tags) {
        requireAllNonNull(name, phone, optionalEmail, tags);
        this.name = name;
        this.phone = phone;
        this.optionalEmail = optionalEmail;
        tagOrProject(tags);
        this.preferredContactMethod = new PreferredContactMethod("Phone");
    }

    /**
     * Constructs a {@code Person} with a specified preferred contact method.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @param optionalEmail The email address of the person.
     * @param tags The set of tags associated with the person.
     * @param preferredContactMethod The preferred method of contact (Phone or Email).
     */
    public Person(Name name, Phone phone, Optional<Email> optionalEmail, Set<Tag> tags, PreferredContactMethod
            preferredContactMethod) {
        requireAllNonNull(name, phone, optionalEmail, tags, preferredContactMethod);
        this.name = name;
        this.phone = phone;
        this.optionalEmail = optionalEmail;
        tagOrProject(tags);
        this.preferredContactMethod = preferredContactMethod;

    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }
    public Optional<Email> getEmail() {
        return optionalEmail;
    }

    public PreferredContactMethod getPreferredContactMethod() {
        return preferredContactMethod;
    }

    /**
     * Creates and returns a new {@code Person} with updated details.
     */
    public Person createEditedPerson(EditPersonDescriptor epd) {
        Name updatedName = epd.name().orElse(this.name);
        Phone updatedPhone = epd.phone().orElse(this.phone);
        Optional<Email> updatedEmail = epd.email().or(() -> this.optionalEmail);

        Set<Project> currentProjects = this.projects;
        Set<Tag> updatedTags = new LinkedHashSet<>(this.tags);
        updatedTags.addAll(currentProjects);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedTags);
    }

    /**
     * Separates tags from projects and place them in separate LinkedHashSets
     * @param tags set of tags
     */
    public void tagOrProject(Set<Tag> tags) {
        for (Tag t : tags) {
            if (t instanceof Project project) {
                this.projects.add(project);
            } else {
                this.tags.add(t);
            }
        }
    }

    /**
     * Replaces a project in a person.
     * @param project to replace with.
     */
    public Person replaceProject(Project project) {
        if (this.projects.remove(project)) {
            projects.add(project);
        }
        return this;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public record EditPersonDescriptor(Optional<Name> name, Optional<Phone> phone, Optional<Email> email) {

        // No-arg constructor, defaults all fields to Optional.empty.
        public EditPersonDescriptor() {
            this(Optional.empty(), Optional.empty(), Optional.empty());
        }

        // Copy constructor.
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this(toCopy.name, toCopy.phone, toCopy.email);
        }

        public EditPersonDescriptor setName(Name newName) {
            return new EditPersonDescriptor(Optional.ofNullable(newName), this.phone, this.email);
        }

        public EditPersonDescriptor setPhone(Phone newPhone) {
            return new EditPersonDescriptor(this.name, Optional.ofNullable(newPhone), this.email);
        }

        public EditPersonDescriptor setEmail(Email newEmail) {
            return new EditPersonDescriptor(this.name, this.phone, Optional.ofNullable(newEmail));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .toString();
        }
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Person otherPerson) {
            return name.equals(otherPerson.name)
                    && phone.equals(otherPerson.phone)
                    && optionalEmail.equals(otherPerson.optionalEmail)
                    && tags.equals(otherPerson.tags);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, optionalEmail, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", optionalEmail)
                .add("tags", tags)
                .toString();
    }
}
