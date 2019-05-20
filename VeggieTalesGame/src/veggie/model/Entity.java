package veggie.model;

import java.util.HashMap;
import java.util.Map;

import processing.core.PImage;

public class Entity {


	private Stats statistics;

	private Moves[] moveList;

	private PlayerController controller;

	/**
	 * Initializes the fields
	 * @param lettuceAssets image of the Entity object
	 * @param statistics initial statistics of the Entity
	 * @param moveList the moves that the Entity has
	 * @param x the initial x-coordinate of the object
	 * @param y the initial y-coordinate of the object
	 */
	public Entity(Map<String, PImage> lettuceAssets, Stats statistics, Moves[] moveList, int x, int y) {
		this.statistics = statistics;
		this.moveList = moveList;
		this.controller = new PlayerController(lettuceAssets, x, y);
	}

	/**
	 * @return the statistics
	 */
	public Stats getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(Stats statistics) {
		this.statistics = statistics;
	}

	/**
	 * @return the moveList
	 */
	public Moves[] getMoveList() {
		return moveList;
	}

	/**
	 * @param move  the new move
	 * @param rmove the move that will be replaced
	 */
	public void setMoveList(Moves move, Moves rmove) {
		for (int i = 0; i < moveList.length; i++) {
			if(moveList[i].getName().equals(rmove.getName())) {
				moveList[i] = move;
				break;
			}
		}
	}

	/**
	 * @return the controls
	 */
	public PlayerController getController() {
		return controller;
	}
}
