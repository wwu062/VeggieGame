package veggie.screen;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gifAnimation.Gif;
import processing.core.PImage;
import veggie.model.Entity;
import veggie.model.Moves;
import veggie.model.PlayerController;
import veggie.model.Stats;
import veggie.textReader.FileIO;

/**
 * 
 * @author awang104
 *
 *
 *         Creates the Platform game mode (think Mario)
 */
public class PlatformMode extends Screen {

	/**
	 * Screen drawing width
	 */
	public static final int DRAWING_WIDTH = 800;

	/**
	 * Screen drawing height
	 */
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private Entity player;
	
	private ArrayList<Entity> bot;

	private ArrayList<Shape> obstacles;

	private ArrayList<PImage> assets;

	private Gif playerimg;

	private Map<Integer, Moves> moves;

	private DrawingSurface surface;

	/**
	 * Initializes fields
	 * 
	 * @param surface the DrawingSurface ======= /** Creates a new platform for the
	 *                game
	 * 
	 * @param surface the DrawingSurface sued to draw the platform >>>>>>> branch
	 *                'master' of https://github.com/wwu062/VeggieGame.git
	 */
	public PlatformMode(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		assets = new ArrayList<PImage>();
		screenRect = new Rectangle(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(200, 400, 400, 50));
		obstacles.add(new Rectangle(0, 250, 100, 50));
		obstacles.add(new Rectangle(700, 250, 100, 50));
		obstacles.add(new Rectangle(375, 300, 50, 100));
		obstacles.add(new Rectangle(300, 250, 200, 50));

		moves = new HashMap<Integer, Moves>();

		bot = new ArrayList<Entity>();
		// reading moveList file
		FileIO translator = new FileIO();

		try {
			ArrayList<String> temp_moves = FileIO.readFile("res" + FileIO.fileSep + "moveList.txt");
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

		player = new Entity(playerimg, istats, iplayerMovelist, 800 / 2 - 100, 600 / 2 - 100);
	}
	
	public void spawnNewBot() {
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

		bot.add(new Entity(playerimg, istats, iplayerMovelist, 1200 / 2 - 100, 600 / 2 - 100));
	}

//	public void runMe() {
//		runSketch();
//	}

	/**
	 * The statements in the setup() functions execute once when the program begins
	 */
	public void setup() {

		playerimg = new Gif(surface, "images" + FileIO.fileSep + "lettuce-sprite.gif");

		playerimg.play();

		// size(0,0,PApplet.P3D);
		spawnNewPlayer();
		spawnNewBot();
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
		
		for(Entity b : bot) {
			b.getControls().draw(surface);
		}
	

		surface.popMatrix();

		// modifying stuff
		run();
		
	}
	
	public void pause() {
		for(Entity b : bot) {
			b.getControls().stop();
		}
		player.getControls().stop();
	}
	
	public void run() {
		
		for(int i = 0; i < bot.size(); i++) {
			if(player.getControls().battle(bot.get(i).getControls())) {
				surface.addScreen(new BattleMode(surface, player, bot.get(i)));
				bot.remove(i);
				pause(); 
			}
		}
		
		if (surface.isPressed(KeyEvent.VK_LEFT))
			player.getControls().walk(-1);
		if (surface.isPressed(KeyEvent.VK_RIGHT))
			player.getControls().walk(1);
		if (surface.isPressed(KeyEvent.VK_UP))
			player.getControls().jump();
		for(Entity b : bot) {
			b.getControls().fall();
			b.getControls().checkPlayer(obstacles);
		}
		
		player.getControls().fall();
		player.getControls().checkPlayer(obstacles);

		if (!screenRect.intersects(player.getControls().getBounds()))
			spawnNewPlayer();
	}

}
