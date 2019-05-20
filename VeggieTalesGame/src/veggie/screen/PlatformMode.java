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
import veggie.model.PlayerManager;
import veggie.model.Moves;
import veggie.model.PlayerPlatform;
import veggie.model.PlayerBattle;
import veggie.textReader.FileIO;

/**
 * @author awang104 Creates the Platform game mode (think Mario)
 */
public class PlatformMode extends Screen
{

	/**
	 * Screen drawing width
	 */
	public static final int DRAWING_WIDTH = 800;

	/**
	 * Screen drawing height
	 */
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private PlayerManager player;

	private ArrayList<PlayerManager> bot;

	private ArrayList<Shape> obstacles;

	private Gif playerRun, tomatoBounce;

	private DrawingSurface surface;

	/**
	 * Initializes fields
	 * 
	 * @param surface the DrawingSurface ======= /** Creates a new platform for the
	 *                game
	 * @param surface the DrawingSurface sued to draw the platform >>>>>>> branch
	 *                'master' of https://github.com/wwu062/VeggieGame.git
	 */
	public PlatformMode(DrawingSurface surface)
	{
		super(800, 600);
		this.surface = surface;
		screenRect = new Rectangle(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(200, 400, 400, 50));
		obstacles.add(new Rectangle(0, 250, 100, 50));
		obstacles.add(new Rectangle(700, 250, 100, 50));
		obstacles.add(new Rectangle(375, 300, 50, 100));
		obstacles.add(new Rectangle(300, 250, 200, 50));


		bot = new ArrayList<PlayerManager>();

	}

	/**
	 * creates a new controllable Entity object
	 */
	public void spawnNewPlayer()
	{

		// player creation
		PlayerBattle istats = new PlayerBattle(100, 0.1);

		Moves[] iplayerMovelist = new Moves[4];

		int i = 0;
		int k = 1;
		while(iplayerMovelist[3] == null)
		{
			if(surface.moves.containsKey(k))
			{
				iplayerMovelist[i] = surface.moves.get(k);
			} else
			{
				i--;
			}
			i++;
			k++;
		}

		player = new PlayerManager(surface.lettuceAssets, istats, iplayerMovelist, 800 / 2 - 100, 600 / 2 - 100);
	}

	public void spawnNewBot()
	{
		PlayerBattle istats = new PlayerBattle(100, 0.1);

		Moves[] iplayerMovelist = new Moves[4];

		int i = 0;
		int k = 1;
		while(iplayerMovelist[3] == null)
		{
			if(surface.moves.containsKey(k))
			{
				iplayerMovelist[i] = surface.moves.get(k);
			} else
			{
				i--;
			}
			i++;
			k++;
		}
		

		bot.add(new PlayerManager(surface.tomatoAssets, istats, iplayerMovelist, 1200 / 2 - 100, 600 / 2 - 100));
	}

	// public void runMe() {
	// runSketch();
	// }

	/**
	 * The statements in the setup() functions execute once when the program begins
	 */
	public void setup()
	{

		playerRun = (Gif) surface.lettuceAssets.get("run");
		playerRun.play();
		
		tomatoBounce = (Gif) surface.tomatoAssets.get("bounce");
		tomatoBounce.play();
		
		
		// size(0,0,PApplet.P3D);
		spawnNewPlayer();
		spawnNewBot();
	}

	/**
	 * Executes 60 times a second continuously until stopped
	 */
	public void draw()
	{
		PlayerPlatform mainplayer = player.getController();

		// drawing stuff

		surface.background(0, 255, 255);

		surface.pushMatrix();

		surface.scale(surface.ratioX, surface.ratioY);

		surface.fill(100);
		for(Shape s : obstacles)
		{
			if(s instanceof Rectangle)
			{
				Rectangle r = (Rectangle) s;
				surface.rect(r.x, r.y, r.width, r.height);
			}
		}

		mainplayer.draw(surface, "run");

		for(PlayerManager b : bot)
		{
			b.getController().draw(surface, "bounce");
		}


		surface.popMatrix();

		// modifying stuff
		run();

	}

	public void pause()
	{
		for(PlayerManager b : bot)
		{
			b.getController().stop();
		}
		player.getController().stop();
	}

	public void run()
	{

		for(int i = 0; i < bot.size(); i++)
		{
			if(player.getController().battle(bot.get(i).getController()))
			{
				surface.addScreen(new BattleMode(surface, player, bot.get(i)));
				bot.remove(i);
				pause();
			}
		}

		if(surface.isPressed(KeyEvent.VK_LEFT))
			player.getController().walk(-1);
		if(surface.isPressed(KeyEvent.VK_RIGHT))
			player.getController().walk(1);
		if(surface.isPressed(KeyEvent.VK_UP))
			player.getController().jump();
		for(PlayerManager b : bot)
		{
			b.getController().fall();
			b.getController().checkPlayer(obstacles);
		}

		player.getController().fall();
		player.getController().checkPlayer(obstacles);

		if(!screenRect.intersects(player.getController().getBounds()))
			spawnNewPlayer();
	}

}
