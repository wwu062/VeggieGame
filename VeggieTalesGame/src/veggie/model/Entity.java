package veggie.model;

import java.util.Map;

public class Entity {
	
	private Stats statistics;
	private int x, y;
	private Moves[] moveList;
	
	public Entity(Stats statistics, int x, int y, Moves[] moveList) {
		this.statistics = statistics;
		this.x = x;
		this.y = y;
		this.moveList = moveList;
	}
	
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	
}
