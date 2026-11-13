public enum CommandType {
    HELP("help"),
    ADD_JOURNEY("addjourney"),
    FIND_JOURNEY("findjourney"),
    FIND_JOURNEY_MIN_STOPS("findjourneyminstops"),
    REMOVE_JOURNEY("removejourney"),
    QUIT("quit"),
    UNKNOWN("");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType fromString(String command) {
        for (CommandType cmd : CommandType.values()) {
            if (cmd.command.equals(command)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }

}
