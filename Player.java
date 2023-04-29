/**
 * Name(s): James Maner
 * CPSC 1060: Assignment 2: RPG
 * Section 020
 * 4/28/2023
 */
import java.util.ArrayList;

public class Player
{
    //Declare all needed objects and variables
    private ArrayList<Item> inventory;
    private int health;
    private Item equipped = new Item("Hands");
    private int dmg = 1;

    //Player constructor method, sets initial hp to 20 and opens inventory list
    public Player()
    {
        health = 20;
        inventory = new ArrayList<>();
        inventory.add(equipped);
    }

    /**
     * Getter method to return an item in the player's inventory
     * @param name indicates the item to get in the player's inventory
     * @return Item is the item object from the inventory being returned
     */
    public Item getItem(String name)
    {
        Item retItem = null;
        for (int i = 0; i < inventory.size(); i++)
        {
            if (name.equalsIgnoreCase(inventory.get(i).getName()))
            {
                retItem = inventory.get(i);
            }
        }

        return retItem;
    }

    /**
     * Method to add an item to the player's inventory
     * @param pickup the item being added to the inventory
     */
    public void addItem(Item pickup)
    {
        inventory.add(pickup);
        System.out.println("Added " + pickup.getName() + " to inventory\n");
    }

    /**
     * Method to print out the player's inventory
     */
    public void inventory()
    {
        System.out.println("\nInventory: ");
        for (Item str: inventory)
        {
            System.out.println(str.getDescription());
        }
        System.out.println();
    }

    /**
     * Equips an item to the player to use in combat
     * @param name indicates the item that is being equipped
     */
    public void equip(String name)
    {
        for (Item i: inventory)
        {
            if (name.equalsIgnoreCase(i.getName()))
            {
                equipped = i;
                dmg = i.getDmg();
                System.out.println("Equipped " + name + "\n");
            }
        }
    }

    /**
     * Checks if the item in question is in the player's inventory
     * @param name indicates the item that is needed to be searched for
     * @return boolean returns true if the item is in the inventory
     */
    public boolean inInventory(String name)
    {
        boolean in = false;
        for (Item i: inventory)
        {
            if (name.equalsIgnoreCase(i.getName()))
            {
                in = true;
            }
        }

        return in;
    }

    /**
     * Subtracts an amount of damage from the player's health
     * @param hit the amount of damage being taken
     */
    public void lostHealth(int hit)
    {
        this.health -= hit;
    }

    //Initialize getters for the player class

    public Item getEquipped()
    {
        return this.equipped;
    }
    
    public int getHP()
    {
        return this.health;
    }

    public int getDmg()
    {
        return this.dmg;
    }
}