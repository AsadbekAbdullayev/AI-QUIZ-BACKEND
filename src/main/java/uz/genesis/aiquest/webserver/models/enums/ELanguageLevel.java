package uz.genesis.aiquest.webserver.models.enums;

public enum ELanguageLevel {
    A1_BASIC("A1 - Basic"),
    A2_ELEMENTARY("A2 - Elementary"),
    B1_INTERMEDIATE("B1 - Intermediate"),
    B2_UPPER_INTERMEDIATE("B2 - Upper Intermediate"),
    C1_ADVANCED("C1 - Advanced"),
    C2_PROFICIENT("C2-Proficient"),
    NATIVE("Native");

    private final String title;

    ELanguageLevel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
