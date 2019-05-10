package veggie.screen;

import veggie.model.Entity;

public class BattleMode extends Screen {
	
	/**
	 * the Entity objects that will battle 
	 */
	private Entity player, enemy;
	
	/**
	 * used to draw the battle
	 */
	private DrawingSurface surface;

	/**
	 * initializes the fields
	 * @param surface DrawingSurface object
	 * @param player the Player Entity object
	 * @param enemy the Enemy Entity object
	 */
	public BattleMode(DrawingSurface surface, Entity player, Entity enemy) {
		super(800, 600);
		this.player = player;
		this.enemy = enemy;
		this.surface = surface;
	}

	/**
	 * Draws the battle 
	 * @post changes background color
	 */
	public void draw() {
		surface.background(255, 255, 255);
	}
}
