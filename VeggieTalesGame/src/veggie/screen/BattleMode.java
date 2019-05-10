package veggie.screen;

import processing.core.PApplet;
import veggie.model.Entity;

public class BattleMode extends PApplet {

	private Entity player, enemy;

	
	public BattleMode(DrawingSurface surface, Entity player, Entity enemy) {
		this.player = player;
		this.enemy = enemy;
	}

	/**
	 * Draws the battle 
	 */
	public void draw() {
		background(255, 255, 255);
	}
}
