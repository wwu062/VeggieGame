package veggie.controller;

import processing.core.PApplet;
import veggie.model.Entity;

public class Playercontroller extends PApplet{
	Entity player;

	public Playercontroller(Entity player) {
		this.player = player;
	}
	
//	public void keyPressed() {
//		if (keyCode == W) {
//			player.move();
//		} else if (keyCode == LEFT) {
//			person.movex(-3);
//		}
//	}
}
