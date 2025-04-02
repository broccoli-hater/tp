package seedu.address.storage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PreferredContactMethod;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String preferredContact;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("projects") List<JsonAdaptedProject> projects,
                             @JsonProperty("preferredContacts") String preferredContact) {
        this.name = name;
        this.phone = phone;
        this.email = email;

        if (tags != null) {
            this.tags.addAll(tags);
        }

        if (projects != null) {
            this.projects.addAll(projects);
        }

        this.preferredContact = preferredContact;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().map(email -> email.value).orElse("");

        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());

        projects.addAll(source.getProjects().stream()
                .map(JsonAdaptedProject::new)
                .toList());

        preferredContact = source.getPreferredContactMethod().getPreferredContactMethod();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Project> personProjectTags = new ArrayList<>();

        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        for (JsonAdaptedProject projectTag : projects) {
            personProjectTags.add(projectTag.toModelType());
        }

        final Set<Tag> modelTags = new LinkedHashSet<>();

        modelTags.addAll(personTags);
        modelTags.addAll(personProjectTags);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email) && !email.isEmpty()) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        final Optional<Email> modelEmail;

        if (email.isEmpty()) {
            modelEmail = Optional.empty();
        } else {
            modelEmail = Optional.of(new Email(email));
        }

        final PreferredContactMethod preferredContactMethod = new PreferredContactMethod(preferredContact);

        return new Person(modelName, modelPhone, modelEmail, modelTags, preferredContactMethod);
    }

}
