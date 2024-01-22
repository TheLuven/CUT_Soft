package datatypes.classMap.object;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 22/12/2023
 * @brief Class used to describe the room of the classMap
 */
public class Room {
    private double width;
    private double height;
    private BoardOrientation boardOrientation;
    public Room(){
        this.width = 6;
        this.height = 10;
        this.boardOrientation = BoardOrientation.north;
    }
    public Room(double width, double height, BoardOrientation boardOrientation){
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
    public BoardOrientation getBoardOrientation(){
        return this.boardOrientation;
    }
}
