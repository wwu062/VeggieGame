package com.veggie.controller;

import com.veggie.model.Entity;

import processing.core.PApplet;

public class Playercontroller extends PApplet{
	Entity player;

	public Playercontroller(Entity player) {
		this.player = player;
	}
	
	public void keyPressed() {
		if (keyCode == W) {
			player.move();
		} else if (keyCode == LEFT) {
			person.movex(-3);
		}
	}
}
