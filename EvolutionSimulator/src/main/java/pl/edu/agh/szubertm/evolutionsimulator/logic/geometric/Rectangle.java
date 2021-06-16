package pl.edu.agh.szubertm.evolutionsimulator.logic.geometric;

public class Rectangle {
    protected final Vector2d botLeft;
    protected final Vector2d topRight;

    public Rectangle(Vector2d botLeft, Vector2d topRight) {
        this.botLeft = botLeft;
        this.topRight = topRight;
    }

    public Vector2d getBotLeft(){
        return botLeft;
    }

    public Vector2d getTopRight(){
        return topRight;
    }

    public int getXsize(){return topRight.x - botLeft.x;}

    public int getYsize(){return topRight.y - botLeft.y;}

    public boolean isInside(Vector2d point){
        return point.follows(botLeft) && point.precedes(topRight);
    }
}