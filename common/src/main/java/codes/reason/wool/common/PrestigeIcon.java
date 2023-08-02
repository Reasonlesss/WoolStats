package codes.reason.wool.common;

/*
    This enum is incomplete, any enum with the name PRESTIGE_X is missing the value.
 */
public enum PrestigeIcon {

    NONE(""),
    HEART("❤"),
    PLUS("✙"),
    STAR("✫"),
    PLANE("✈"),
    CROSS("✠"),
    CROWN("♛"),
    PRESTIGE_7("⚡"),
    NUKE("☢"),
    PRESTIGE_9("✏"),
    PRESTIGE_10("☯")

    ;

    private final String icon;

    PrestigeIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
