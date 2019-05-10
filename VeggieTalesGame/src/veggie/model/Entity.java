package veggie.model;

public class Entity {

	/**
	 * the Stats of the Entity object
	 */
	private Stats statistics;

	/**
	 * the movelist that the Entity object has
	 */
	private Moves[] moveList;

	/**
	 * The controls that move the Entity object around
	 */
	private PlayerController controls;

	public Entity(Stats statistics, Moves[] moveList, PlayerController player) {
		this.statistics = statistics;
		this.moveList = moveList;
		this.controls = player;
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
	public PlayerController getControls() {
		return controls;
	}
}
