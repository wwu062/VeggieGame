package veggie.model;

public class Moves {
	
	private String name;
	private int attackval;
	
	public Moves(String name, int attackval) {
		this.name = name;
		this.attackval = attackval;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the attackval
	 */
	public int getAttackval() {
		return attackval;
	}
}
