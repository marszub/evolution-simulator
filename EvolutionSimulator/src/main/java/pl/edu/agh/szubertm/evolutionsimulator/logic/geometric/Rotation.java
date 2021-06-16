package pl.edu.agh.szubertm.evolutionsimulator.logic.geometric;

public enum Rotation {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    BACK_RIGHT,
    BACK,
    BACK_LEFT,
    LEFT,
    FORWARD_LEFT;

    public int toInt(){
        return switch (this){
            case FORWARD        -> 0;
            case FORWARD_RIGHT  -> 1;
            case RIGHT          -> 2;
            case BACK_RIGHT     -> 3;
            case BACK           -> 4;
            case BACK_LEFT      -> 5;
            case LEFT           -> 6;
            case FORWARD_LEFT   -> 7;
        };
    }

    public static Rotation valueOf(int number){
        return switch (number){
            case 0 -> FORWARD;
            case 1 -> FORWARD_RIGHT;
            case 2 -> RIGHT;
            case 3 -> BACK_RIGHT;
            case 4 -> BACK;
            case 5 -> BACK_LEFT;
            case 6 -> LEFT;
            case 7 -> FORWARD_LEFT;
            default -> throw new IllegalArgumentException("Value: " + number + "does not suit to Rotation enum. (0-7)");
        };
    }
}
