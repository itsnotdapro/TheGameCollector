public enum Platform {
    PC,
    PS ("PlayStation"),
    PS2 ("PlayStation 2"),
    PS3,
    PS4,
    PS5,
    PSP,
    VITA,
    XBOX,
    XBOX360,
    XBOXONE,
    XBOXSERIESX,
    N64,
    GC,
    WII,
    WIIU,
    SWITCH,
    GBA,
    DS,
    THREEDS;

    private String name;

    Platform(String name) {
        this.name = name;
    }

    Platform() {}

    @Override
    public String toString() {
        if (name != null) { return name; }
        else { return super.toString(); }
    }
}
