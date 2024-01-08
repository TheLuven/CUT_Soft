package dataTypes.classMap;

import dataTypes.classMap.object.BoardOrientation;
import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.DeskOrientation;
import dataTypes.classMap.object.Room;

import java.util.ArrayList;

public class ClassMapLayer {
    private String name;
    private ArrayList<Desk> desks;
    private Room room;
    public ClassMapLayer(String name, double width, double height, BoardOrientation boardOrientation){
        this.name = name;
        this.desks = new ArrayList<>();
        this.room = new Room(width,height,boardOrientation);
    }
    public String getName(){
        return this.name;
    }

    public ArrayList<Desk> getDesks(){
        return this.desks;
    }
    public void addDesk(Desk desk){
        this.desks.add(desk);
    }
    public void removeDesk(Desk desk){
        this.desks.remove(desk);
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public Room getRoom(){
        return this.room;
    }

    @Override
    public String toString() {
        return "ClassMapLayer{" +
                "name='" + name + '\'' +
                ", room=" + room +
                '}';
    }
}
