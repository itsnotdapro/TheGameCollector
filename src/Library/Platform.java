package Library;

import Exceptions.InvalidPlatformException;

public enum Platform {
    PC,
    PS ("PlayStation", "playstation"),
    PS2 ("PlayStation 2", "playstation-2"),
    PS3 ("PlayStation 3", "playstation-3"),
    PS4 ("PlayStation 4", "playstation-4"),
    PS5 ("PlayStation 5", "playstation-5"),
    PSP ("PlayStation Portable", "PSP"),
    VITA ("PlayStation Vita", "VITA"),
    XBOX ("XBOX", "XBOX"),
    XBOX360 ("XBOX 360", "xbox-360"),
    XBOXONE ("XBOX One", "xbox-one"),
    XBOXSERIESX ("XBOX Series X", "xbox-series-x"),
    N64 ("Nintendo 64", "nintendo-64"),
    GC ("Nintendo Gamecube", "gamecube"),
    WII ("Nintendo Wii", "wii"),
    WIIU ("Wii U", "wii-u"),
    SWITCH ("Nintendo Switch", "switch"),
    GBA ("GameBoy Advance", "GBA"),
    DS ("Nintendo DS", "ds"),
    THREEDS ("Nintendo 3DS", "3ds");

    private String name;
    private String apiName;


    private Platform(String name, String apiName) {
        this.name = name;
        this.apiName = apiName;
    }

    private Platform() { this.apiName = "PC"; }
    
    public String getApiName() { return apiName; }
	public String value() { return super.toString(); }
	
	public static Platform getPlatformFromString(String name) throws InvalidPlatformException {
		for (Platform platform : values()) {
			if (name.toLowerCase().equals(platform.value().toLowerCase()) || name.toLowerCase().equals(platform.toString().toLowerCase())) {
				return platform;
			}
		}
		throw new InvalidPlatformException();
	}

    @Override
    public String toString() {
        if (name != null) { return name; }
        else { return super.toString(); }
    }
}
