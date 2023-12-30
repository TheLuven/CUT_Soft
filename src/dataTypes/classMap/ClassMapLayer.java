package dataTypes.classMap;

import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.Room;
import dataTypes.classMap.object.boardOrientation;

import java.util.ArrayList;

public class ClassMapLayer {
    private String name;
    private ArrayList<Desk> desks;
    private Room room;
    public ClassMapLayer(String name){
        this.name = name;
        this.desks = new ArrayList<>();
        this.room = new Room();
        this.desks.add(new Desk(0,2,"duo",this.room));
        this.desks.add(new Desk(0,4,"duo",this.room));
        this.desks.add(new Desk(0,6,"duo",this.room));
        this.desks.add(new Desk(0,8,"duo",this.room));
        this.desks.add(new Desk(4,2,"duo",this.room));
        this.desks.add(new Desk(4,4,"duo",this.room));
        this.desks.add(new Desk(4,6,"duo",this.room));
        this.desks.add(new Desk(4,8,"duo",this.room));
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
}
