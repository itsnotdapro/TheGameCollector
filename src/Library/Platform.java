package Library;

public enum Platform {
    PC,
    PS ("PlayStation"),
    PS2 ("PlayStation 2"),
    PS3 ("PlayStation 3"),
    PS4 ("PlayStation 4"),
    PS5 ("PlayStation 5"),
    PSP ("PlayStation Portable"),
    VITA ("PlayStation Vita"),
    XBOX ("XBOX"),
    XBOX360 ("XBOX 360"),
    XBOXONE ("XBOX One"),
    XBOXSERIESX ("XBOX Series X"),
    N64 ("Nintendo 64"),
    GC ("Nintendo Gamecube"),
    WII ("Nintendo Wii"),
    WIIU ("Wii U"),
    SWITCH ("Nintendo Switch"),
    GBA ("GameBoy Advance"),
    DS ("Nintendo DS"),
    THREEDS ("Nintendo 3DS");

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
