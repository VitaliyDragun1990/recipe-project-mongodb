package guru.springframework.domain;

public enum Difficulty {
    EASY("Easy"), MODERATE("Moderate"), KIND_OF_HARD("Kind of hard"), HARD("Hard");

    private final String textValue;

    Difficulty(String textValue) {
        this.textValue = textValue;
    }

    public static Difficulty[] getValues() {
        return Difficulty.values();
    }

    @Override
    public String toString() {
        return  textValue;
    }
}
