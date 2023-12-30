package dataTypes.classMap;

import java.util.ArrayList;

public class ClassMapLayer {
    private String name;
    private ArrayList<Desk> desks;

    public ClassMapLayer(String name){
        this.name = name;
        this.desks = new ArrayList<>();
        this.desks.add(new Desk(0,0,"test"));
        this.desks.add(new Desk(0,0,"test"));
        this.desks.add(new Desk(0,0,"test"));
        this.desks.add(new Desk(0,0,"test"));
    }
    public String getName(){
        return this.name;
    }
}
