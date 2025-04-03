package seedu.address.logic.commands.enumeration;

/**
 * Enumeration representing different prefixes used in command parsing.
 */
public enum PrefixEnum {
    PREFIX_NAME("n/"),
    PREFIX_PHONE("p/"),
    PREFIX_EMAIL("e/"),
    PREFIX_TAG("t/"),
    PREFIX_PROJECT("proj/"),
    PREFIX_DEADLINE("by/"),
    PREFIX_PAYMENT("pay/"),
    PREFIX_PROGRESS("prog/");
    private final String prefix;
    PrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
    /**
     * Checks if a given string contains any of the defined prefixes.
     *
     * @param name The string to check for the presence of any prefix.
     * @return {@code true} if the string contains a prefix, {@code false} otherwise.
     */
    public static boolean containsPrefix(String name) {
        for (PrefixEnum preix : PrefixEnum.values()) {
            if (name.contains(preix.getPrefix())) {
                return true;
            }
        }
        return false;
    }

}
