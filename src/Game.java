/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Person player;
    private Person enemy;
    int recovery;
    private Knife knife = new Knife();
    private Flare flare = new Flare();
    private Key key = new Key();
    boolean open;
    boolean hasKey = false;
    
    public static Room enemyDeathChamber, outside, storage, basement, secrettunnel, kitchen, diningarea, bathroom, bathroom2, attic, roof, terrace, guestroom, masterbedroom, hallway, secrethallway,servantshouse, outhouse, botany, staircase, hallway2, livingroom, study, hallway3, bedroom, diningarea2;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        recovery = 2;
        parser = new Parser();
        player = new Person();
        enemy = new Person();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        
    	enemyDeathChamber = new Room("Where enemies go to die");
      
        // create the rooms
        outside = new Room("outside the main entrance of the mansion");
        
        servantshouse = new Room("outside the servants living quarters");
        
        outhouse = new Room("outside the mansions outhouse");
        botany = new Room("in the botany");
        
        terrace = new Room("on the terrace, you can see the botany from here");
        
        bathroom = new Room("in the bathroom");
        bathroom2 = new Room("in the upstairs bathroom");
        
        staircase = new Room("on the staircase");
        
        livingroom = new Room("in the living room");
        
        attic = new Room("in the attic");
        
        storage = new Room("in the underground storage, there are various types of food on display");
        
        basement = new Room("in the basement");
        
        roof = new Room("on the roof");
        
        study = new Room("in the study");
        
        bedroom = new Room("in the servants bedroom");
        masterbedroom = new Room("in the master bedroom");
        guestroom = new Room("in the guest room");
        
        diningarea = new Room("in the dining area");
        diningarea2 = new Room("in the servants dining area");
        
        kitchen = new Room("in the kitchen");
        
        hallway3 = new Room("in the hallway of the sevants quarters");
        hallway2 = new Room("in the upstairs hallway");
        hallway = new Room("in the main hallway");
        
        secrethallway = new Room("in a secret hallway");
        secrettunnel = new Room("in a secret tunnel");
        
        kitchen.getInventory().addItem("knife", knife);
        outside.getInventory().addItem("flare", flare);
        basement.getInventory().addItem("key", key);

        // initialise room exits
        outside.setExit("north", hallway);
        outside.setExit("south", outhouse);
        outside.setExit("east", servantshouse);
        
        attic.setExit("down", hallway2);
        attic.setExit("up", roof);
        
        roof.setExit("down", attic);

        hallway.setExit("south", outside);
        hallway.setExit("up", staircase);
        hallway.setExit("north", livingroom);
        hallway.setExit("west", bathroom);
        hallway.setExit("down", secrettunnel);
        
        hallway2.setExit("down", staircase);
        hallway2.setExit("north", masterbedroom);
        hallway2.setExit("up", attic);
        hallway2.setExit("south", terrace);
        hallway2.setExit("east", guestroom);
        hallway2.setExit("west", bathroom2);
        
        hallway3.setExit("north", servantshouse);
        hallway3.setExit("east", bedroom);
        hallway3.setExit("south", diningarea2);
        
        secrethallway.setExit("east", masterbedroom);
        secrethallway.setExit("west", livingroom);
        
        secrettunnel.setExit("up", hallway);
        secrettunnel.setExit("down", basement);
        
        basement.setExit("up", secrettunnel);
        basement.setExit("north", storage);
        
        storage.setExit("up", diningarea);
        storage.setExit("south", basement);
        
        livingroom.setExit("south", hallway);
        livingroom.setExit("north", study);
        livingroom.setExit("east", secrethallway);
        livingroom.setExit("west", diningarea);
        
        study.setExit("south", livingroom);
        
        bedroom.setExit("west", hallway3);
        
        masterbedroom.setExit("south", hallway2);
        masterbedroom.setExit("west", secrethallway);
        
        guestroom.setExit("west", hallway2);
        
        diningarea.setExit("north", kitchen);
        diningarea.setExit("east", livingroom);
        diningarea.setExit("down", storage);
        
        diningarea2.setExit("north", hallway3);
        
        kitchen.setExit("south", diningarea);

        servantshouse.setExit("west", outside);
        servantshouse.setExit("south", hallway3);

        outhouse.setExit("north", outside);
        outhouse.setExit("west", botany);

        botany.setExit("east", outhouse);
        
        bathroom.setExit("east", hallway);
        
        bathroom2.setExit("east", hallway2);
        
        terrace.setExit("north", hallway2);
        
        staircase.setExit("down", hallway);
        staircase.setExit("up", hallway2);
        
        

        player.setCurrentRoom(outside);
        enemy.setCurrentRoom(botany);
        //player.getInventory().addItem("flare", flare);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        attic.lock();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You were attacked while camping in the woods!");
        System.out.println("After fending off the attacker you ran towards the nearest house, a creepy old mansion, to find help.");
        System.out.println("All you have left is a flare.");
        System.out.println("Now you have to find help before you bleed to death!");
        System.out.println("Type 'commands' if you need the commands.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        open = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        if (!player.isAlive()) {
        	wantToQuit = true;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("commands"))
        	commands(command);
        else if (commandWord.equals("look"))
        	looking(command);
        else if (commandWord.equals("recover"))
        	recover(command);
        else if (commandWord.equals("take"))
        	take(command);
        else if (commandWord.equals("check"))
        	check(command);
        else if (commandWord.equals("drop"))
        	drop(command);
        else if (commandWord.equals("use"))
        	use(command);
        else if (commandWord.equals("attack"))
        	attack(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
          return wantToQuit;
    }

    // implementations of user commands:

	private void attack(Command command) {
		if(command.hasSecondWord()) {
			if(command.getSecondWord().equals("enemy")) {
				enemy.damage(10);
				player.damage(5);
				System.out.println("You attacked the enemy!");
				System.out.println("The enemies health is: " + enemy.health);
				System.out.println("The enemy attacks back!");
				System.out.println("Your health is: " + player.health);
				if(enemy.health == 0) {
					enemy.setCurrentRoom(enemyDeathChamber);
				}
			}
		}
		else {
			System.out.println("Attack who?");
		}
	}

	private void use(Command command) {
		if(command.hasSecondWord()) {
			if(command.hasThirdWord()) {
				if(command.getSecondWord().equals("flare") && (command.getThirdWord().equals(roof))) {
					if(player.getCurrentRoom().equals(roof)) {
						System.out.println("You wave your flare and with it signal a nearby helicopter to pick you up.");
						 System.out.println("You found help.");
					     System.out.println("You win!");
					     System.exit(0);
					}
					else {
						System.out.println("You don't have that!");
					}
				}
				else {
					player.getInventory().useItem(knife, player.getCurrentRoom());
				}
			}
			else {
				System.out.println("Use what where?");
			}
		}
		else {
			System.out.println("Use what where?");
		}
	}

	private void commands(Command command) {
		System.out.println("Your command words are:");
        parser.showCommands();
	}

	private void drop(Command command) {
		if (command.hasSecondWord()) {
			if(command.getSecondWord().equals("knife")) {
				player.getInventory().dropItem(knife, player.getCurrentRoom(), "knife");
			}
			else if(command.getSecondWord().equals("flare")) {
				player.getInventory().dropItem(flare, player.getCurrentRoom(), "flare");
			}
			else if(command.getSecondWord().equals("key")) {
				player.getInventory().dropItem(key, player.getCurrentRoom(), "key");
			}
			else {
				System.out.println("I don't know what you mean.");
			}
		}
		else {
			System.out.println("Drop what?");
		}
	}

	private void check(Command command) {
		player.getInventory().checkItems();
		System.out.println("You have " + recovery + " heal(s) left.");
	}

	private void take(Command command) {
		if(command.hasSecondWord() == true) {
			if(command.getSecondWord().equals("knife")) {
				player.getInventory().takeItem(knife, player.getCurrentRoom(), "knife");
			}
			else if(command.getSecondWord().equals("flare")) {
				player.getInventory().takeItem(flare, player.getCurrentRoom(), "flare");
			}
			else if(command.getSecondWord().equals("key")) {
				player.getInventory().takeItem(key, player.getCurrentRoom(), "key");
				player.getInventory().removeItem("key");
				hasKey = true;
				System.out.println("You put the key in your pocket for later use.");
			}
			else {
				System.out.println("I don't know what you mean.");
			}
		}
		else {
			System.out.println("Take what?");
		}
	}

	/**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You found help.");
        System.out.println("You win!");
        System.exit(0);
    }
    
    private void looking(Command command) 
    {
    	if (command.hasSecondWord()) {
    		System.out.println("Look?");
    	}
    	else {
    	System.out.println(player.getCurrentRoom().getLongDescription());
    	player.getCurrentRoom().getInventory().checkItems();
    	if(player.getCurrentRoom().equals(enemy.getCurrentRoom())) {
    		System.out.println("There is an enemy here.");
    		}
    	}
    }
    
    private void recover(Command command)
    {
    	if (command.hasSecondWord()) {
    		System.out.println("Recover what?");
    	}
    	else if (recovery > 0) {
    	player.heal(15);
    	System.out.println("Your health is: " + player.health);
    	recovery--;
    	System.out.println("You have " + recovery + " heal(s) left.");
    	}
    	else {
    		System.out.println("You can't heal!");
    	}
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else if(nextRoom.getLock() == true) {
        		if (hasKey == true) {
        			attic.unlock();
        			System.out.println("You unlocked the door.");
        		}
        		else {
        			System.out.println("The room is locked");
        		}
        	}
        else {
        	if(player.getCurrentRoom().equals(enemy.getCurrentRoom())){
        		enemy.setCurrentRoom(nextRoom);
        		System.out.println("The enemy followed you.");
        	}
            player.setCurrentRoom(nextRoom);
          
            System.out.println(player.getCurrentRoom().getLongDescription());
            player.damage(9);
            if(player.getCurrentRoom().equals(enemy.getCurrentRoom())) {
            	player.damage(5);
            	System.out.println("You got attacked");
            }
            System.out.println("Your health is " + player.health + ".");
            player.isAlive();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
    
    
    public static void main(String[] args)
    {	
        Game game = new Game();
        game.play();
    }
}
