package com.veggie.screen;

import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	/**
	 * if gamestate = 0, is main menu if gamestate = 1, is instruction page if
	 * gamestate = 2, is platform mode if gamestate = 3, is battle mode
	 */
	private int gamestate;
	private Entity player;
	

	public void setup() {
		gamestate = 0;
	}

	public void draw() {
		if (0 == gamestate) {

		}
		if (1 == gamestate) {

		}
		if (2 == gamestate) {

		}
		if (3 == gamestate) {
			BattleMode battle = new BattleMode(player, enemy);
		}
	}
}
