package veggie.model;

public class Moves {
	
	/**
	 * The name of the Move
	 */
	private String name;
	
	/**
	 * the attack value of the Move
	 */
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
