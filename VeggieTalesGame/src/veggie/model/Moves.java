package veggie.model;

public class Moves {
	
	private String name;

	private int attackval;

	private String effectName;

	/**
	 * 
	 * @param name name of the move
	 * @param attackval attackvalue of the move
	 * @param effect effect of the move (leeched, heal, poisoned, absorbed, reduced, etc.)
	 */
	public Moves(String name, int attackval, String effect)
	{
		this.name = name;
		this.attackval = attackval;
		this.effectName = effect;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public String getEffectName()
	{
		return effectName;
	}


	/**
	 * @return the attackval
	 */
	public int getAttackval()
	{
		return attackval;
	}
}
