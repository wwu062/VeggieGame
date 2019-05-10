package veggie.model;

public class Entity {

	private Stats statistics;

	private Moves[] moveList;

	private PlayerController controls;

	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;

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
	 * @return the player
	 */
	public PlayerController getControls() {
		return controls;
	}
}
