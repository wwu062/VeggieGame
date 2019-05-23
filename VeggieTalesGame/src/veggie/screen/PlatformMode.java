package veggie.screen;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gifAnimation.Gif;
import processing.core.PConstants;
import processing.core.PShape;
import veggie.model.PlayerManager;
import veggie.model.Moves;
import veggie.model.PlayerPlatform;
import veggie.model.PlayerBattle;

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

	//private ArrayList<PShape> obstacles;
	
	private PShape obstacles;

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
		
		

		
		//obstacles = surface.createShape(surface.GROUP);//new ArrayList<PShape>();
		

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
		
		// made it so that it automatically goes to battlemode for now. 
		bot.add(new PlayerManager(surface.tomatoAssets, istats, iplayerMovelist, 1200 / 2 - 100, 600 / 2 - 100));
		//bot.add(new PlayerManager(surface.tomatoAssets, istats, iplayerMovelist, 800 / 2 - 100, 600 / 2 - 100));
		
	}

	// public void runMe() {
	// runSketch();
	// }

	/**
	 * The statements in the setup() functions execute once when the program begins
	 */
	public void setup()
	{
		obstacles = surface.createShape(PConstants.GROUP);
		playerRun = (Gif) surface.lettuceAssets.get("run");
		playerRun.play();
		
		tomatoBounce = (Gif) surface.tomatoAssets.get("bounce");
		tomatoBounce.play();
		
		surface.pushStyle();
		surface.fill(165, 42, 42);
		
		PShape p1 = surface.createShape(PConstants.RECT, 200, 400, 400, 50);
		PShape p2 = surface.createShape(PConstants.RECT, 0, 250, 100, 50);
		PShape p3 = surface.createShape(PConstants.RECT, 700, 250, 100, 50);
		PShape p4 = surface.createShape(PConstants.RECT, 375, 300, 50, 100);
		PShape p5 = surface.createShape(PConstants.RECT, 300, 250, 200, 50);
		
		surface.popStyle();
		
		obstacles.addChild(p1);
		obstacles.addChild(p2);
		obstacles.addChild(p3);
		obstacles.addChild(p4);
		obstacles.addChild(p5);
		
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

		surface.shape(obstacles);

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
		if(surface.isPressed(KeyEvent.VK_SHIFT) && (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_LEFT)))
			player.getController().slide(true);
		else if(surface.isPressed(KeyEvent.VK_SHIFT)) {
			
		} else {
			player.getController().slide(false);
		}
		
		
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
	
	public void isReleased() {
		
	}

}
