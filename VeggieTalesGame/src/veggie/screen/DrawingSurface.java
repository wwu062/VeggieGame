package veggie.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import veggie.model.Entity;
import veggie.model.Moves;
import veggie.model.Stats;
import veggie.textReader.FileIO;
import veggie.textReader.ReadFile;

public class DrawingSurface extends PApplet {

	/**
	 * if gamestate = 0, is main menu if gamestate = 1, is instruction page if
	 * gamestate = 2, is platform mode if gamestate = 3, is battle mode
	 */
	private int gamestate;
	/**
	 * player character
	 */
	private Entity player; // remember to instantiate!!!

	private Entity[] enemy;
	private Map<Integer, Moves> moves;

	public DrawingSurface() {
		gamestate = 0;
		moves = new HashMap<Integer, Moves>();
		enemy = new Entity[2];
	}

	public void setup() {
				
		ReadFile translator = new ReadFile();

		try {
			ArrayList<String> temp_moves = FileIO.readFile("res" + FileIO.fileSep + "moveList.txt");

			System.out.println("test here");
			for (String s : temp_moves) {
				Moves m = translator.translateMoveList(s);
				if (m == null) throw new AssertionError("movie is null while s=" +s);
				moves.put(m.getAttackval(), m);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//player creation
		Stats istats = new Stats(100, 0.1);
		
		Moves[] iplayerMovelist = new Moves[4];
		
		int i = 0;
		int k = 1;
		while(iplayerMovelist[3] == null) {
			if(moves.containsKey(k)) {
				iplayerMovelist[i] = moves.get(k);
			}
			else {
				i--;
			}
			i++;
			k++;
		}
		
		player = new Entity(istats, 0, 0, iplayerMovelist);
		
		

		for(int a = 0; a < 2; a++) {
			Moves[] enemyMovelist = new Moves[4];
			int i = 0;
			int k = 1;
			while(enemyMovelist[3] == null) {
				if(moves.containsKey(k)) {
					enemyMovelist[i] = moves.get(k);
				}
				else {
					i--;
				}
				i++;
				k++;
			}
		}
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
