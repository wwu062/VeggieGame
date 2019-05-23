package veggie.model;

public class Moves
{

	/**
	 * The name of the Move
	 */
	private String name;

	/**
	 * the attack value of the Move
	 */
	private int attackval;

	private String effectName;


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
