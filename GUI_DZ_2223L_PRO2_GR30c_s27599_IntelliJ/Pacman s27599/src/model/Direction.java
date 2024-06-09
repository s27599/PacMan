package model;

public enum Direction {
    UP(3),
    DOWN(1),
    LEFT(2),
    RIGHT(0),
    NA(0);
    private final int value;


    private Direction(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }



}
