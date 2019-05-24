package veggie.model;

public class PlayerBattle
{

	/**
	 * the health statistic
	 */
	private int health;

	/**
	 * the critical rate statistic
	 */
	private double critrate;

	/**
	 * Initializes fields
	 * 
	 * @param ihealth  initial health statistic
	 * @param critrate initial critical rate statistic
	 */
	public PlayerBattle(int ihealth, double critrate)
	{
		this.health = ihealth;
		this.critrate = critrate;
	}

	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health)
	{
		if(health < 0)
		{
			this.health = 0;
		} else
		{
			this.health = health;
		}
	}

	/**
	 * @return the critical rate
	 */
	public double getCritrate()
	{
		return critrate;
	}

	/**
	 * @param critrate the critical rate to set
	 */
	public void setCritrate(double critrate)
	{
		this.critrate = critrate;
	}
	
}