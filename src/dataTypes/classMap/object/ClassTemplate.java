/************************************************************
 * @author Victor VENULETH
 * @version 1.0.0
 * @date 2024/01/15
 ***********************************************************/
package dataTypes.classMap.object;

import dataTypes.classMap.ClassMapLayer;

import java.util.ArrayList;

/**
 * @author Victor VENULETH
 * @brief This class is used to create templates of classes
 */
public class ClassTemplate {

    private ArrayList<ClassMapLayer> classMapLayers = new ArrayList<>();
    public ClassTemplate(){
        this.classMapLayers.add(classMapLayer1());
        this.classMapLayers.add(classMapLayer2());
        this.classMapLayers.add(classMapLayer3());
        this.classMapLayers.add(classMapLayer4());
        this.classMapLayers.add(classMapLayer5());
    }

    /**
     * @author Victor VENULETH
     * @brief This function create a template of a class name Ramanujan
     * @return ClassMapLayer
     */
    private ClassMapLayer classMapLayer1(){
        ClassMapLayer classMapLayer = new ClassMapLayer("Room Ramanujan",6,10,BoardOrientation.north);
        classMapLayer.addDesk(new Desk(0,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(0,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(0,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(0,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(4,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(4,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(4,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(4,8,"duo",DeskOrientation.horizontal));
        return classMapLayer;
    }
    /**
     * @author Victor VENULETH
     * @brief This function create a template of a class name Room in U
     * @return ClassMapLayer
     */
    private ClassMapLayer classMapLayer2(){
        ClassMapLayer classMapLayer = new ClassMapLayer("Room U",8,10,BoardOrientation.north);
        classMapLayer.addDesk(new Desk(1,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,6,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(6.4,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(6.4,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(6.4,6,"duo",DeskOrientation.vertical));
        return classMapLayer;
    }
    /**
     * @author Victor VENULETH
     * @brief This function create a template of a class name Hell
     * @return ClassMapLayer
     */
    private ClassMapLayer classMapLayer3(){
        ClassMapLayer classMapLayer = new ClassMapLayer("Room Hell",19,8,BoardOrientation.north);
        classMapLayer.addDesk(new Desk(1,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(7,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(7,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(7,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,6,"duo",DeskOrientation.horizontal));
        return classMapLayer;
    }
    /**
     * @author Victor VENULETH
     * @brief This function create a template of a class name Euler
     * @return ClassMapLayer
     */
    private ClassMapLayer classMapLayer4(){
        ClassMapLayer classMapLayer = new ClassMapLayer("Room Euler",10,11,BoardOrientation.north);
        classMapLayer.addDesk(new Desk(1,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,6,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(1,8,"duo",DeskOrientation.vertical));

        classMapLayer.addDesk(new Desk(4,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(4,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(4,6,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(4,8,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(5,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(5,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(5,6,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(5,8,"duo",DeskOrientation.vertical));

        classMapLayer.addDesk(new Desk(8,2,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(8,4,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(8,6,"duo",DeskOrientation.vertical));
        classMapLayer.addDesk(new Desk(8,8,"duo",DeskOrientation.vertical));
        return classMapLayer;
    }
    /**
     * @author Victor VENULETH
     * @brief This function create a template of a class name Anjou
     * @return ClassMapLayer
     */
    private ClassMapLayer classMapLayer5(){
        ClassMapLayer classMapLayer = new ClassMapLayer("Room Anjou",32,14,BoardOrientation.north);
        classMapLayer.addDesk(new Desk(1,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(1,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(3,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(5,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(25,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(25,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(25,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(25,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(25,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(27,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(27,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(27,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(27,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(27,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(29,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(29,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(29,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(29,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(29,10,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(10,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(10,12,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(12,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(12,12,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(14,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(14,12,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(16,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(16,12,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(18,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(18,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(18,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(18,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(18,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(18,12,"duo",DeskOrientation.horizontal));

        classMapLayer.addDesk(new Desk(20,2,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(20,4,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(20,6,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(20,8,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(20,10,"duo",DeskOrientation.horizontal));
        classMapLayer.addDesk(new Desk(20,12,"duo",DeskOrientation.horizontal));
        return classMapLayer;
    }
    /**
     * @author Victor VENULETH
     * @brief This function return the list of classMapLayers
     * @return ClassMapLayers
     */
    public ArrayList<ClassMapLayer> getClassMapLayers(){
        return this.classMapLayers;
    }
}
