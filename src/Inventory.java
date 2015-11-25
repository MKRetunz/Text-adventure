import java.util.HashMap;

class Inventory { 
	
	private HashMap<String, Item> items;
	
	public Inventory() {
		items = new HashMap<String, Item>();
	}

	public void takeItem(Item i, Room r, String s) {
		boolean temp = r.getInventory().hasItem();
		if (temp == false) {
			System.out.println("The " + s + " is not in this room");
		}
		else {
			this.items.put(s, i);
			r.getInventory().removeItem(s);
			System.out.println("You have taken the " + s + ".");
		}
	}
	
	public HashMap<String, Item> checkItems() {
		if (items.size() <= 0) {
			System.out.println("No items!");
		}
		else {
			System.out.println("You see these things: ");
			for (String name : this.items.keySet()) {
				System.out.println(name);
			}
		}
		return items;
	}
	
	private boolean checkInv(Room r, Item i) {
		if(r.getInventory().equals(i)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void dropItem(Item i, Room r, String s) {
		Item temp = this.items.get(s);
		if (temp == null) {
			System.out.println("The " + s + " is not in your inventory.");
		}
		else {
			r.getInventory().addItem(s, temp);
			this.items.remove(s);
			System.out.println("You have dropped the " + s + ".");
		}
	}
	
	public boolean hasItem() {
		if (items.size() >= 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean getItem(Room r, String s) {
		if(r.getInventory().equals(s)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void useItem(Item i, Room r) {
		i.use(r);
	}
	
	public void addItem(String s, Item i) {
		items.put(s, i);
	}
	
	public void removeItem(String s) {
		this.items.remove(s); 
	}
}