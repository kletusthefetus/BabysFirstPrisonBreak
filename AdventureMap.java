/**
 * Name(s): James Maner
 * CPSC 1060: Assignment 2: RPG
 * Section 020
 * 4/28/2023
 */
import java.util.HashMap;

public class AdventureMap
{
    //Declare needed variables
    HashMap<String, Room> map;
    String keyStr;

    /**
     * Constructor for the adventure map
     */
    public AdventureMap()
    {
        //Initialize the map and populate each room with its elements
        map = new HashMap<String, Room>();
        Room intro = new Room("You find yourself in a darkly lit room. Stone walls, small torches in sconces on the walls, the roof is caved in.\nThe last thing you remember is falling.\nThe only thing you know now is you have to survive!");
        intro.setAlt("This room has become slightly darker with the loss of a torch, how sad.");
        intro.addExit("North");
        intro.addExit("East");
        intro.addExit("West");
        intro.addItem("Torch");

        Room leftOne = new Room("Moving into the left wing, you see a small goblin who seems to be eager for a fight blocking your way!\nBe careful!");
        leftOne.setEscapable(false);
        leftOne.addNPC("Goblin", 5, 2);
        leftOne.setAlt("This darkened room, newly empty, you can still see the marks on the floor from where a goblin paced the floor.\nIn the dirt floor, you see faint wisps of paper, what looks like a map is on the floor!");
        leftOne.addItem("Map");
        leftOne.addExit("West");
        leftOne.addExit("North");
        leftOne.addExit("East");

        Room leftOneNorth = new Room("After clearing the darkness in the room, you see a small square room with an old rustedkey dug into the dirt");
        leftOneNorth.isDark();
        leftOneNorth.addItem("RustedKey");
        leftOneNorth.setAlt("A looted room stands before you.");
        leftOneNorth.addExit("South");

        Room leftTwo = new Room("A room with a large rusted chest in the middle of it. You try to open yet it seems locked.\nIf only there were some unlocking tool around here");
        leftTwo.addExit("East");
        leftTwo.setAlt("All that remains is an empty open chest. Maybe it was an old family heirloom?");

        Room northOne = new Room("This room seems lived in, fancier than other rooms.\nSmall pillows on a couch in the corner, extinguished candles on a dinner table, and a fancy looking hand mirror\n");
        northOne.addItem("Mirror");
        northOne.setAlt("The dream of the self-concious, a living space without a mirror");
        northOne.addExit("South");
        northOne.addExit("East");

        Room northOneRight = new Room("As soon as you clear the darkness, you are attacked by a moving shadow!");
        northOneRight.setEscapable(false);
        northOneRight.isDark();
        northOneRight.addNPC("Shadow", 20, 5);
        northOneRight.setAlt("Now that the shadow is gone, you can see what it was protecting.\nA goldenkey hanging from a hook on a pedestel, how could it be this important?");
        northOneRight.addItem("GoldenKey");
        northOneRight.addExit("West");
        northOneRight.addExit("South");

        Room rightOne = new Room("The moment you walk into the room, you are blinded by a bright white light!");
        rightOne.setAlt("Now with the light gone, you can see this room for what it is. The last resort for stopping escaping prisoners.");
        rightOne.setEscapable(false);
        rightOne.addExit("West");
        rightOne.addExit("North");
        rightOne.addExit("East");

        Room finalRoom = new Room("You stand before a large door. Looking closely, you can just make out the light of day through the cracks of the door.\nA golden lock under the handle.");
        finalRoom.setEscapable(false);
        finalRoom.setAlt("The way out is clear! You have escaped!");
        finalRoom.addExit("West");
        finalRoom.addExit("East");

        //Assign each room with an int array string representing the coordinates of the rooms
        int[] coordinates = new int[2];
        coordinates[0] = 0;
        coordinates[1] = 0;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, intro);
        coordinates[0]--;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, leftOne);
        coordinates[1]++;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, leftOneNorth);
        coordinates[1]--;
        coordinates[0]--;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, leftTwo);
        coordinates[0] = 0;
        coordinates[1] = 1;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, northOne);
        coordinates[0]++;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, northOneRight);
        coordinates[1]--;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, rightOne);
        coordinates[0]++;
        keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
        map.put(keyStr, finalRoom);

    }

    /**
     * Returns the Room assocaited with the string name given
     * @param currRoom the name of the room to be returned, room name must be valid
     * @return the Room object associated with the name
     */
    public Room getRoom(String currRoom)
    {
        return map.get(currRoom);
    }
}
