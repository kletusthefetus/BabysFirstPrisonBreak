/**
 * Name(s): James Maner and Chase Harris
 * CPSC 1061: Lab 12
 * Section 003
 * 4/11/2023
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Driver
{

    public static void main(String[] args)
    {
        //Delcare all needed variables and objects for the game
        Random ranGen = new Random();
        Player explorer = new Player();
        boolean gameOver = false;
        FileInputStream mapPrinter;
        AdventureMap adMap = new AdventureMap();
        Scanner scnr = new Scanner(System.in);
        Room currRoom;
        String playerVerb;
        String nextKey;
        String playerNoun;
        String keyStr;
        String combatAction = "";
        int[] coordinates = new int[2];
        coordinates[0] = 0;
        coordinates[1] = 0;

        //Print the tutorial
        System.out.println("(Tutorial: Use a verb next to a noun indicating your direction or the object being interacted with\nEx: Exit North)\nVerbs:\nUse\nFight\nExit\nGrab\nEquip\nOpen (put next to inventory to open inventory)\n");

        //Loop until the game is over
        while (!gameOver)
        {
            //Create the string representing the current room
            keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
            currRoom = adMap.getRoom(keyStr);
            System.out.println(currRoom);

            //Take in the player input to determine the action
            playerVerb = scnr.next();
            playerNoun = scnr.next();

            //Change the room if the player exits towards a valid direction
            if (playerVerb.equalsIgnoreCase("Exit"))
            {
                if (currRoom.getEscapable())
                {
                    //If the input exit is a valid exit for the current room
                    if (currRoom.validExit(playerNoun))
                    {
                        if (playerNoun.equalsIgnoreCase("North"))
                        {
                            coordinates[1]++;
                            nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                            adMap.getRoom(nextKey).setStartExit("South");
                        }
                        else if (playerNoun.equalsIgnoreCase("South"))
                        {
                            coordinates[1]--;
                            nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                            adMap.getRoom(nextKey).setStartExit("North");
                        }
                        else if (playerNoun.equalsIgnoreCase("East"))
                        {
                            coordinates[0]++;
                            if (coordinates[0] == 3)
                            {
                                gameOver = true;
                                break;
                            }

                            nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                            adMap.getRoom(nextKey).setStartExit("West");
                        }
                        else if (playerNoun.equalsIgnoreCase("West"))
                        {
                            coordinates[0]--;
                            nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                            adMap.getRoom(nextKey).setStartExit("East");
                        }
        
                    }
                    else
                    {
                        //Print if the exit is not valid
                        System.out.println("Invalid exit.");
                    }
                }
                //If the room is considered inescapable, then the only way out is the way you came in
                else if (playerNoun.equalsIgnoreCase(currRoom.getStartExit()))
                {
                    if (playerNoun.equalsIgnoreCase("North"))
                    {
                        coordinates[1]++;
                        nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                        adMap.getRoom(nextKey).setStartExit("South");
                    }
                    else if (playerNoun.equalsIgnoreCase("South"))
                    {
                        coordinates[1]--;
                        nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                        adMap.getRoom(nextKey).setStartExit("North");
                    }
                    else if (playerNoun.equalsIgnoreCase("East"))
                    {
                        coordinates[0]++;
                        nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                        adMap.getRoom(nextKey).setStartExit("West");
                    }
                    else if (playerNoun.equalsIgnoreCase("West"))
                    {
                        coordinates[0]--;
                        nextKey = "{" + coordinates[0] + ", " + coordinates[1] + "}";
                        adMap.getRoom(nextKey).setStartExit("East");
                    }
                }
                else
                {
                    System.out.println("You cannot escape that way now! Go back or figure out the room.");
                }
            }
            //Pick up the item indicated by the player
            else if (playerVerb.equalsIgnoreCase("Grab"))
            {
                //Catch an exception if the player tries to grab an item where there is none
                try 
                {
                    if (currRoom.presentItem(playerNoun))
                    {
                        explorer.addItem(new Item(playerNoun));
                        currRoom.removeItem(playerNoun);

                        if (keyStr.equals("{-1, 0}"))
                        {
                            currRoom.setAlt("All thats left in the room are the remains of a battle and the tracks of an impatient enemy.");
                        }
                        else if (keyStr.equals("{1, 1}"))
                        {
                            currRoom.setAlt("The empty room seems that it used to hold some importance, but now it holds nothing.");
                        }

                        currRoom.switchDesc();
                    }
                    else 
                    {
                        System.out.println("Invalid item pickup");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("You may not be able to grab that here.");
                }
            }
            //Initiate combat with the npc indicated by the player
            else if (playerVerb.equalsIgnoreCase("Fight"))
            {
                //Catch an exception if the player tries to fight an npc that is not there
                try
                {
                    if (currRoom.presentNPC(playerNoun))
                    {
                        combatAction = "";
                        System.out.println("Combat Start!\n");
                        System.out.println("(Tutorial: Use a verb like \"Hit\", \"Flee\", or \"Dodge\" to determine your actions in combat.\nBoth you and your enemy have a chance to miss!");
                        while (explorer.getHP() > 0 && currRoom.getNPC().getHP() > 0 && !combatAction.equalsIgnoreCase("Flee"))
                        {
                            System.out.println(currRoom.getNPC().getName() + "'s HP: " + currRoom.getNPC().getHP());
                            System.out.println("Your HP: " + explorer.getHP());
                            System.out.println("What will you do?");
                            combatAction = scnr.next();

                            if (combatAction.equalsIgnoreCase("Hit"))
                            {
                                int hitChance = ranGen.nextInt(10) + 1;

                                if (hitChance > 3)
                                {
                                    System.out.println("\nWhack! You hit " + currRoom.getNPC().getName() + " with your " + explorer.getEquipped().getName());
                                    currRoom.getNPC().lostHealth(explorer.getDmg());
                                }
                                else
                                {
                                    System.out.println("\nWhiff! You missed the " + currRoom.getNPC().getName() + "!");
                                }
                            }

                            if (combatAction.equalsIgnoreCase("Dodge"))
                            {
                                System.out.println("You dodged the incoming attack!");
                            }
                            else if (currRoom.getNPC().getHP() > 0)
                            {
                                int hitChance = ranGen.nextInt(10) + 1;

                                if (hitChance > 4)
                                {
                                    System.out.println("Scrape! You were hit by the " + currRoom.getNPC().getName() + "!");
                                    explorer.lostHealth(currRoom.getNPC().getDmg());
                                }
                                else
                                {
                                    System.out.println("Nice one! The " + currRoom.getNPC().getName() + " missed you!");
                                }
                                
                            }

                            
                        }

                        if (explorer.getHP() <= 0)
                        {
                            System.out.println("The " + currRoom.getNPC().getName() + " killed you!\n");
                            gameOver = true;
                        }
                        else if (currRoom.getNPC().getHP() <= 0)
                        {
                            System.out.println("You killed the " + currRoom.getNPC().getName() + "!\nVictory!\n");
                            currRoom.setEscapable(true);
                            currRoom.switchDesc();
                        }
                        else
                        {
                            System.out.println("You escaped the " + currRoom.getNPC().getName() + ", now what will you do?");
                        }
                    }
                    else
                    {
                        System.out.println("You can't fight that here, you must be itching to punch something!");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Even though there's nothing here, I like your spirit.");
                }
            }
            //Equips the indicated item in the players inventory
            else if (playerVerb.equalsIgnoreCase("Equip"))
            {
                //Only equips if the item is in the inventory
                if (explorer.inInventory(playerNoun))
                {
                    explorer.equip(playerNoun);
                }
                else
                {
                    System.out.println("You don't seem to have that item.\n");
                }
            }
            //Gives the player the option to open their inventory and see everything in there
            else if (playerVerb.equalsIgnoreCase("Open"))
            {
                if (playerNoun.equalsIgnoreCase("Inventory"))
                {
                    explorer.inventory();
                }
                else
                {
                    System.out.println("You don't seem to be able to open that right now.");
                }
            }
            //Uses the indicated item with its intended purpose
            else if (playerVerb.equalsIgnoreCase("Use"))
            {
                //only uses the item if it is in the players inventory
                if (explorer.inInventory(playerNoun))
                {
                    explorer.getItem(playerNoun).use();

                    //Uses the torch to light up rooms
                    if (playerNoun.equalsIgnoreCase("Torch"))
                    {
                        currRoom.brighten();
                    }
                    //Uses the key to unlock a specific chest
                    else if (playerNoun.equalsIgnoreCase("RustedKey"))
                    {
                        if (keyStr.equals("{-2, 0}"))
                        {
                            System.out.println("You've unlocked and opened the chest!");
                            explorer.addItem(new Item("Sword"));
                            currRoom.switchDesc();
                        }
                        else
                        {
                            System.out.println("The Rusted Key doesn't seem to work anywhere in here");
                        }
                    }
                    //Uses the mirror for a puzzle
                    else if (playerNoun.equalsIgnoreCase("Mirror"))
                    {
                        if (keyStr.equals("{1, 0}"))
                        {
                            System.out.println("You reflect the light back towards a large system of lenses, which bounces back and destroys the mechanism!");
                            currRoom.setEscapable(true);
                            currRoom.switchDesc();
                        }
                        else
                        {
                            System.out.println("There doesnt seem to be anything to reflect here");
                        }
                    }
                    //Uses the golden key for the final door
                    else if (playerNoun.equalsIgnoreCase("GoldenKey"))
                    {
                        if (keyStr.equals("{2, 0}"))
                        {
                            System.out.println("You use the golden key to unlock the exit!");
                            currRoom.setEscapable(true);
                            currRoom.switchDesc();
                        }
                        else
                        {
                            System.out.println("The Golden Key doesn't seem to work anywhere in here");
                        }
                    }
                    //Using the map prints out the map
                    else if (playerNoun.equalsIgnoreCase("Map"))
                    {
                        //Catches an exception if the map text file cannot be found
                        try 
                        {
                            mapPrinter = new FileInputStream("map.txt");

                            int i = mapPrinter.read();

                            while(i != -1) 
                            {
                                System.out.print((char)i);

                                // Reads next byte from the file
                                i = mapPrinter.read();
                            }

                        }
                        catch (FileNotFoundException e)
                        {
                            System.out.println("map.txt cannot be found.");
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Some error has occurred.");
                        }

                    }
                }
                //Prints if the noun is not in the player inventory
                else
                {
                    System.out.println("That item doesn't seem to be in your inventory");
                }
            }
            //Prints if the command does not follow the rules
            else
            {
                System.out.println("Not sure what you mean, try a different command.");
            }
            
        }

        //Prints at the very end of the game
        System.out.println("\nGAME OVER\nThanks for playing!");

        
    }

}

