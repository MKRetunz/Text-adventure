
public class Key extends Item {

	public Key() {
		
	}
	
	public void use(Room r) {
		System.out.println("using a Key on " + r);
		r.unlock();
	}
}
