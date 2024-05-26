package enums;

import lombok.Getter;

@Getter
public enum Categories {
    ACTION("ACTION"),
    RPG("RPG"),
    RTS("RTS"),
    ADVENTURE("ADVENTURE"),
    SINGLE_PLAYER("SINGLE_PLAYER"),
    MULTIPLAYER("MULTIPLAYER"),
    STRATEGY("STRATEGY"),
    SPORTS("SPORTS"),
    HORROR("HORROR"),
    SURVIVAL("SURVIVAL"),
    MMORPG("MMORPG"),
    MOBA("MOBA"),
    BATTLEROYALE("BATTLE_ROYALE"),
    FPS("FPS"),
    TPS("TPS"),
    BOARD("BOARD"),
    PUZZLE("PUZZLE"),
    EDUCATIONAL("EDUCATIONAL"),
    MUSIC("MUSIC"),
    PARTY("PARTY"),
    CASUAL("CASUAL"),
    ARCADE("ARCADE"),
    FIGHTING("FIGHTING"),
    OPEN_WORLD("OPEN_WORLD");

    private final String value;
    private Categories(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (Categories category : Categories.values()) {
            if (category.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}