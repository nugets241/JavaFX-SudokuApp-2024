package constants;

public enum Difficulty {
    EASY("Easy",30),
    MEDIUM("Medium",40),
    HARD("Hard",50),
    EXPERT("Expert",60);

    private final int value;
    private final String name;

    Difficulty(String name, int value) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
