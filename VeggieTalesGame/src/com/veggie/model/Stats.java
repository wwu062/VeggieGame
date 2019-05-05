package com.veggie.model;

public class Stats {
	
	private int health;
	private int critrate;
	private int attack;
	
	
	public Stats(int ihealth, int icritrate, int iattack) {
		this.health = ihealth;
		this.critrate = icritrate;
		this.attack = iattack;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public int getCritrate() {
		return critrate;
	}


	public void setCritrate(int critrate) {
		this.critrate = critrate;
	}


	public int getAttack() {
		return attack;
	}


	public void setAttack(int attack) {
		this.attack = attack;
	}
	
}