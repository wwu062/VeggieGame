package com.veggie.model;

public class Stats {

	private int health;
	private int critrate;
	private int attack1;
	private int attack2;

	public Stats(int ihealth, int icritrate, int iattack1, int iattack2) {
		this.health = ihealth;
		this.critrate = icritrate;
		this.attack1 = iattack1;
		this.attack2 = iattack2;
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

	/**
	 * @return the attack1
	 */
	public int getAttack1() {
		return attack1;
	}

	/**
	 * @param attack1 the attack1 to set
	 */
	public void setAttack1(int attack1) {
		this.attack1 = attack1;
	}

	/**
	 * @return the attack2
	 */
	public int getAttack2() {
		return attack2;
	}

	/**
	 * @param attack2 the attack2 to set
	 */
	public void setAttack2(int attack2) {
		this.attack2 = attack2;
	}
	
	
}