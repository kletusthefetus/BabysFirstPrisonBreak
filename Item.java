/**
 * Name(s): James Maner
 * CPSC 1060: Assignment 2: RPG
 * Section 020
 * 4/28/2023
 */
public class Item
{
    //Declare needed variables
    private String name;
    private int dmg;
    private String description;

    /**
     * Constructor method for the creation of an item
     * @param name the name of the item being created
     */
    public Item(String name)
    {
        this.name = name;

        //Create inventory statistics depending on the item name
        if (name.equalsIgnoreCase("Torch"))
        {
            description = "Torch - A small torch to light the way\nDMG: 3";
            dmg = 3;
        }
        else if (name.equalsIgnoreCase("Hands"))
        {
            description = "Hands - Your own hands, do with them what you will\nDMG: 1";
            dmg = 1;
        }
        else if (name.equalsIgnoreCase("Sword"))
        {
            description = "Sword - A sharp and powerful weapon, probably belonged to someone that isn't you.\nDMG: 8";
            dmg = 8;
        }
        else if (name.equalsIgnoreCase("RustedKey"))
        {
            description = "Rusted Key - This rusty key seems to be able to unlock something important.\nThough I guess you could use it as a knife if you're really desperate\nDMG: 2";
            dmg = 2;
        }
        else if (name.equalsIgnoreCase("GoldenKey"))
        {
            description = "Golden Key - This golden key holds some heavy importance, the spirit protecting it really didn't want you to have this.\nDMG: 0";
            dmg = 0;
        }
        else if (name.equalsIgnoreCase("Map"))
        {
            description = "Map - A useful tool in navigating the twisting dungeon, not very useful as a weapon though.\nDMG: 0";
            dmg = 0;
        }
        else if (name.equalsIgnoreCase("Mirror"))
        {
            description = "Mirror - A fancy silver hand mirror, can be used to admire yourself.\nOr maybe for puzzle solving, if you're into that.\nDMG: 3";
            dmg = 3;
        }
        
    }

    //Small method to add some flavor to the action of using certain items
    public void use()
    {
        String useStr = "";
        if (name.equalsIgnoreCase("Torch"))
        {
            useStr = "You light up the room!";
            System.out.println(useStr);
        }
        else if (name.equalsIgnoreCase("Hands"))
        {
            useStr = "You have successfully reloaded your biceps, good job!";
            System.out.println(useStr);
        }
    }

    //Initialize getters for the Item class

    public String getDescription()
    {
        return this.description;
    }

    public int getDmg()
    {
        return this.dmg;
    }

    public String getName()
    {
        return this.name;
    }


}