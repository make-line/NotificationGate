package com.example.notificationgate.model.enums;

public enum Stage {
    CREATE(0), FIRST(1), SECOND(2), FINAL(3);

    private final int value;
    Stage(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
    public static Stage getByValue(int value){
        return switch (value) {
            case 0 -> CREATE;
            case 1 -> FIRST;
            case 2 -> SECOND;
            default -> FINAL;
        };
    }
}
