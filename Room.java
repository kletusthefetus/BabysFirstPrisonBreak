/**
 * Name(s): James Maner
 * CPSC 1060: Assignment 2: RPG
 * Section 020
 * 4/28/2023
 */
import java.util.ArrayList;

public class Room
{
    //Delcare all needed variables
    String description;
    String altDesc;
    String origDescription;
    ArrayList<String> exits;
    String startingExit;
    ArrayList<Item> items;
    NPC character;
    boolean escapable;
    
    /**
     * Constructor method for a room
     * @param description the read details of the room when entering into it
     */
    public Room(String description)
    {
        exits = new ArrayList<>();
        items = new ArrayList<>();
        this.description = description;
        origDescription = description;
        escapable = true;
    }
    
    /**
     * Adds an exit to the room
     * @param exit room name of the exit to be added to the room
     */
    public void addExit(String exit)
    {
        this.exits.add(exit);
    }

    /**
     * Sets the new alternative description for the room
     * @param desc new room description
     */
    public void setAlt(String desc)
    {
        this.altDesc = desc;
    }

    /**
     * Sets the emergency exit for the room as you enter it if it is inescapable
     * @param exit the exit that was just entered through
     */
    public void setStartExit(String exit)
    {
        this.startingExit = exit;
    }

    /**
     * Adds an item to the room to be interacted with
     * @param name the name of the new item
     */
    public void addItem(String name)
    {
        items.add(new Item(name));
    }

    /**
     * Removes an item from the room so that it cannot be interacted with more than once
     * @param name the name of the item being removed
     */
    public void removeItem(String name)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (name.equalsIgnoreCase(items.get(i).getName()))
            {
                items.remove(i);
            }
        }
    }

    /**
     * Swaps the current description for the alternative one
     */
    public void switchDesc()
    {
        description = altDesc;
    }

    /**
     * Adds an interactable npc to the room
     * @param name the name of the new npc
     * @param hp the health value of the new npc
     * @param attk the attack value of the new npc
     */
    public void addNPC(String name, int hp, int attk)
    {
        character = new NPC(name , hp, attk);
    }

    /**
     * Checks to see if the NPC in question is present in the room
     * @param name the name of the npc indicated
     * @return boolean returns true if the npc of that name is still present in the room
     */
    public boolean presentNPC(String name)
    {
        if (name.equalsIgnoreCase(character.getName()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the npc present in the room
     * @return NPC returns the npc type object in this room
     */
    public NPC getNPC()
    {
        return this.character;
    }

    /**
     * Sets the room to the "dark" condition where the player will not get details about the room
     */
    public void isDark()
    {
        description = "It's too dark to see in here, maybe a torch would help";
    }

    /**
     * Removes the darkness from the room once certain conditions are met
     */
    public void brighten()
    {
        description = origDescription;
    }

    /**
     * Checks if the exit string matches an exit for the room
     * @param exit room name of the exit to be checked
     * @return returns true if the exit name is valid
     */
    public boolean validExit(String exit)
    {
        boolean valid = false;

        if (escapable)
        {
            for (String str: exits)
            {
                if (exit.equalsIgnoreCase(str))
                {
                    valid = true;
                }
            }
        }
        else
        {
            if (exit.equalsIgnoreCase(startingExit))
            {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Sets this room as escapable or inescapable depending on certain conditions
     * @param escapable is true or false depending on if the game master wants the room to be escapable or not
     */
    public void setEscapable(boolean escapable)
    {
        this.escapable = escapable;
    }

    /**
     * Returns the exit that is still available if the room is inescapable
     * @return String returns the name of the exit still available
     */
    public String getStartExit()
    {
        return this.startingExit;
    }

    /**
     * Returns true if the room is escapable
     * @return boolean returns true or false if the room is escapable or not
     */
    public boolean getEscapable()
    {
        return this.escapable;
    }

    /**
     * List all of the rooms as a string
     * @return returns all of the names of the rooms on new lines
     */
    public String listExits()
    {
        String exitStr = "";
        for (int i = 0; i < this.exits.size(); i++)
        {
            exitStr += (exits.get(i)  + "\n");
        }
        return exitStr;
    }

    /**
     * Checks if the item in question is still present in the room
     * @param name indicates the item that is needed to be searched for
     * @return boolean returns true if the item is in the room
     */
    public boolean presentItem(String name)
    {
        boolean itemPresent = false;
        for (Item i: items)
        {
            if (name.equalsIgnoreCase(i.getName()))
            {
                itemPresent = true;
            }
        }
        return itemPresent;
    }

    /**
     * Generates a string representation of the room using the name and description and lists all of the exits.
     */
    public String toString()
    {
        String str = "";
        str += this.description + "\n";
        if (escapable)
        {
            str += "\nExits:\n";
            str += this.listExits();
        }
        else
        {
            str += "\nExit:\n";
            str += startingExit;
        }

        return str;
    }
}
