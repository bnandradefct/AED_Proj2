/**
 * Enumeration of supported user commands in the Journey Planner application.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(1)
 */
public enum CommandType {
    HELP("help"),
    ADD_JOURNEY("addjourney"),
    FIND_JOURNEY("findjourney"),
    FIND_JOURNEY_MIN_STOPS("findjourneyminstops"),
    REMOVE_JOURNEY("removejourney"),
    QUIT("quit"),
    UNKNOWN("");

    private final String command;

    /**
     * Constructs a CommandType constant associated with its normalized command string.
     *
     * @param command the normalized command name
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    CommandType(String command) {
        this.command = command;
    }

    /**
     * Resolves a normalized command string into its corresponding CommandType constant.
     *
     * @param command the command string to match
     * @return the matching CommandType, or UNKNOWN if not recognized
     *
     * Time complexity:
     * - Best case: O(1) when the command matches the first enum constant
     * - Worst case: O(1) (iterates over a fixed constant number of enum values)
     */
    public static CommandType fromString(String command) {
        for (CommandType cmd : CommandType.values()) {
            if (cmd.command.equals(command)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }

}
