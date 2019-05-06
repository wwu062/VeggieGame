package com.veggie.model;

public class Stats {

	private int health;
	private int critrate;

	public Stats(int ihealth, int icritrate, int iattack1, int iattack2) {
		this.health = ihealth;
		this.critrate = icritrate;
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
	public int getCritrate() {
		return critrate;
	}

	/**
	 * @param critrate the critrate to set
	 */
	public void setCritrate(int critrate) {
		this.critrate = critrate;
	}
	
}