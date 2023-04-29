/**
 * Name(s): James Maner
 * CPSC 1060: Assignment 2: RPG
 * Section 020
 * 4/28/2023
 */
public class NPC
{
    //Initialize needed variables to keep track for each npc
    int hp;
    int attk;
    String name;

    /**
     * Constructor method for the creation of an NPC
     * @param name the name of the npc used for context in text
     * @param hp the health value of the npc used in combat to decide when they are defeated
     * @param attk the damage amount of the npc in combat
     */
    public NPC(String name, int hp, int attk)
    {
        this.name = name;
        this.hp = hp;
        this.attk = attk;
    }

    /**
     * Method to remove health from the npc
     * @param dmg the amount of health lost
     */
    public void lostHealth(int dmg)
    {
        this.hp -= dmg;
    }

    //Initialize getter and setter helper methods for the NPC class

    public int getDmg()
    {
        return this.attk;
    }

    public String getName()
    {
        return this.name;
    }

    public int getHP()
    {
        return this.hp;
    }
}