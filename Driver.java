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

        System.out.println("(Tutorial: Use a verb next to a noun indicating your direction or the object being interacted with\nEx: Exit North)\nVerbs:\nUse\nFight\nExit\nGrab\nEquip\nOpen (put next to inventory to open inventory)\n");

        while (!gameOver)
        {
            keyStr = "{" + coordinates[0] + ", " + coordinates[1] + "}";
            currRoom = adMap.getRoom(keyStr);
            System.out.println(currRoom);

            playerVerb = scnr.next();
            playerNoun = scnr.next();

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
                    System.out.println("You cannot escape now! Go back or figure out the room.");
                }
            }
            else if (playerVerb.equalsIgnoreCase("Grab"))
            {
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
            else if (playerVerb.equalsIgnoreCase("Fight"))
            {
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
            else if (playerVerb.equalsIgnoreCase("Equip"))
            {
                if (explorer.inInventory(playerNoun))
                {
                    explorer.equip(playerNoun);
                }
                else
                {
                    System.out.println("You don't seem to have that item.\n");
                }
            }
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
            else if (playerVerb.equalsIgnoreCase("Use"))
            {
                if (explorer.inInventory(playerNoun))
                {
                    explorer.getItem(playerNoun).use();

                    if (playerNoun.equalsIgnoreCase("Torch"))
                    {
                        currRoom.brighten();
                    }
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
                    else if (playerNoun.equalsIgnoreCase("GoldenKey"))
                    {
                        if (keyStr.equals("{2, 0}"))
                        {
                            System.out.println("You use the golden key to unlock the exit!");
                            currRoom.setEscapable(true);
                            currRoom.switchDesc();
                        }
                    }
                    else if (playerNoun.equalsIgnoreCase("Map"))
                    {
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
                else
                {
                    System.out.println("That item doesn't seem to be in your inventory");
                }
            }
            else
            {
                System.out.println("Not sure what you mean, try a different command.");
            }
            
        }

        System.out.println("\nGAME OVER\nThanks for playing!");

        
    }

}

