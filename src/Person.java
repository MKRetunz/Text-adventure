
public class Person {
	int health;
	private Room currentRoom;
	private Inventory inv;
	
	public Person() {
		inv = new Inventory();
		currentRoom = null;
		health = 100;
		
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public void damage(int d) {
		health -= d;
		isAlive();
	}
	public void heal(int h) {
		health += h;
		if (health > 100) {
			health = 100;
		}
	}
	
	public boolean isAlive() {
		if (health <= 0) {
			health = 0;
			return false;
		}
		else {
			return true;
		}
	}
	
    public Inventory getInventory() {
    	return inv;
    }
    
    public void setInventory(Inventory inventory) {
    	this.inv = inventory;
    }
}
