package veggie.model;

public class Stats {

	private int health;
	private double critrate;

	public Stats(int ihealth, double critrate) {
		this.health = ihealth;
		this.critrate = critrate;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the critrate
	 */
	public double getCritrate() {
		return critrate;
	}

	/**
	 * @param critrate the critrate to set
	 */
	public void setCritrate(double critrate) {
		this.critrate = critrate;
	}
	
}