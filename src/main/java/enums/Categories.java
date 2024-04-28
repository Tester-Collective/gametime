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
    SIMULATION("SIMULATION"),
    SPORTS("SPORTS"),
    RACING("RACING"),
    HORROR("HORROR"),
    SURVIVAL("SURVIVAL"),
    MMORPG("MMORPG"),
    MOBA("MOBA"),
    BATTLEROYALE("BATTLE_ROYALE"),
    FPS("FPS"),
    TPS("TPS"),
    TOWERDEFENSE("TOWER_DEFENSE"),
    CARD("CARD"),
    BOARD("BOARD"),
    PUZZLE("PUZZLE"),
    EDUCATIONAL("EDUCATIONAL"),
    MUSIC("MUSIC"),
    PARTY("PARTY"),
    CASUAL("CASUAL"),
    ARCADE("ARCADE"),
    FIGHTING("FIGHTING"),
    SHOOTER("SHOOTER"),
    OPEN_WORLD("OPEN_WORLD"),
    SANDBOX("SANDBOX");

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