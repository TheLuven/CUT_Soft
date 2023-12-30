package dataTypes.classMap.object;

public class Room {
    private double width;
    private double height;
    private dataTypes.classMap.object.boardOrientation boardOrientation;
    public Room(){
        this.width = 6;
        this.height = 10;
        this.boardOrientation = dataTypes.classMap.object.boardOrientation.north;
    }
    public Room(double width, double height, dataTypes.classMap.object.boardOrientation boardOrientation){
        this.width = width;
        this.height = height;
        this.boardOrientation = boardOrientation;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    public boardOrientation getBoardOrientation(){
        return this.boardOrientation;
    }
}
