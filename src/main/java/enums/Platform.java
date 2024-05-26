package enums;

import lombok.Getter;

@Getter
public enum Platform {
    PC("PC"),
    PS5("PS5"),
    XBOX("XBOX"),
    SWITCH("SWITCH"),
    MOBILE("MOBILE");

    private final String value;
    private Platform(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (Platform platform : Platform.values()) {
            if (platform.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}