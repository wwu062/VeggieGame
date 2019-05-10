package veggie.screen;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;
import veggie.model.Entity;
import veggie.model.Moves;
import veggie.model.PlayerController;
import veggie.model.Stats;
import veggie.textReader.FileIO;

public class PlatformMode extends Screen {

	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private Entity player;
	private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;

	private ArrayList<PImage> assets;

	private Map<Integer, Moves> moves;
	
	private DrawingSurface surface;

	/**
	 * Initializes fields
	 * @param surface the Drawingsurface
	 */
	public PlatformMode(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(200, 400, 400, 50));
		obstacles.add(new Rectangle(0, 250, 100, 50));
		obstacles.add(new Rectangle(700, 250, 100, 50));
		obstacles.add(new Rectangle(375, 300, 50, 100));
		obstacles.add(new Rectangle(300, 250, 200, 50));

		moves = new HashMap<Integer, Moves>();

		// reading moveList file
		FileIO translator = new FileIO();

		try {
			ArrayList<String> temp_moves = FileIO.readFile("res" + FileIO.fileSep + "moveList.txt");
			System.out.println("test here");
			for (String s : temp_moves) {
				Moves m = translator.translateMoveList(s);
				moves.put(m.getAttackval(), m);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * creates a new controllable Entity object
	 */
	public void spawnNewPlayer() {
		
		// player creation
				Stats istats = new Stats(100, 0.1);

				Moves[] iplayerMovelist = new Moves[4];

				int i = 0;
				int k = 1;
				while (iplayerMovelist[3] == null) {
					if (moves.containsKey(k)) {
						iplayerMovelist[i] = moves.get(k);
					} else {
						i--;
					}
					i++;
					k++;
				}

		PlayerController playerC = new PlayerController(surface.playerimg, 800 / 2 - 100, 600/2 - 100);
		player = new Entity(istats, iplayerMovelist, playerC);
	}

//	public void runMe() {
//		runSketch();
//	}

	/**
	 * The statements in the setup() functions execute once when the program begins
	 */
	public void setup() {
		// size(0,0,PApplet.P3D);
		spawnNewPlayer();
	}

	/**
	 * Executes 60 times a second continuously until stopped
	 */
	public void draw() {
		PlayerController mainplayer = player.getControls();

		// drawing stuff

		surface.background(0, 255, 255);

		surface.pushMatrix();

		
		surface.scale(surface.ratioX, surface.ratioY);

		surface.fill(100);
		for (Shape s : obstacles) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle) s;
				surface.rect(r.x, r.y, r.width, r.height);
			}
		}

		mainplayer.draw(surface);

		surface.popMatrix();

		// modifying stuff

		
		if (surface.isPressed(KeyEvent.VK_LEFT))
			mainplayer.walk(-1);
		if (surface.isPressed(KeyEvent.VK_RIGHT))
			mainplayer.walk(1);
		if (surface.isPressed(KeyEvent.VK_UP))
			mainplayer.jump();

		player.getControls().act(obstacles);

		if (!screenRect.intersects(mainplayer.getBounds()))
			spawnNewPlayer();
	}

	

}
