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
	
	private ArrayList<Rectangle> obstacles;

	private Gif playerRun, tomatoBounce;

	private DrawingSurface surface;
	
	private double timer;
	
	private boolean pause;

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

		player = new PlayerManager(surface.lettuceAssets, istats, iplayerMovelist, 800 / 2 - 100, 400 / 2 - 100, false);
	}

	public void spawnNewBot(int x, int y)
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
		PlayerManager tempBot = new PlayerManager(surface.tomatoAssets, istats, iplayerMovelist, x, y, true);
		tempBot.getController().freeze();
		bot.add(tempBot);
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
		pause = false;
		timer = 1;
		bot = new ArrayList<PlayerManager>();
		
		obstacles = new ArrayList<Rectangle>();
		//obstacles = surface.createShape(PConstants.GROUP);
		playerRun = (Gif) surface.lettuceAssets.get("run");
		playerRun.play();
		
		tomatoBounce = (Gif) surface.tomatoAssets.get("bounce");
		tomatoBounce.play();
		
		surface.pushStyle();
		surface.fill(165, 42, 42);
		
		Rectangle r1 = new Rectangle(200, 400, 400, 50);
		Rectangle r2 = new Rectangle(0, 250, 100, 50);
		Rectangle r3 = new Rectangle(700, 250, 100, 50);
		Rectangle r4 = new Rectangle(375, 300, 50, 100);
		Rectangle r5 = new Rectangle(300, 250, 200, 50);
		
		/*
		PShape p1 = surface.createShape(PConstants.RECT, 200, 400, 400, 50);
		PShape p2 = surface.createShape(PConstants.RECT, 0, 250, 100, 50);
		PShape p3 = surface.createShape(PConstants.RECT, 700, 250, 100, 50);
		PShape p4 = surface.createShape(PConstants.RECT, 375, 300, 50, 100);
		PShape p5 = surface.createShape(PConstants.RECT, 300, 250, 200, 50);
		*/
		
		surface.popStyle();
		
		/*
		obstacles.addChild(p1);
		obstacles.addChild(p2);
		obstacles.addChild(p3);
		obstacles.addChild(p4);
		obstacles.addChild(p5);
		*/
		
		obstacles.add(r1);
		obstacles.add(r2);
		obstacles.add(r3);
		obstacles.add(r4);
		obstacles.add(r5);
		
		
		spawnNewPlayer();
		spawnNewBot(1200 / 2 - 100, 600 / 2 - 100);
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

		//surface.shape(obstacles);
		for(Rectangle rect : obstacles) {
			surface.rect(rect.x, rect.y, rect.width, rect.height);
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
		pause = true;
	}

	public void run()
	{
		for(int i = 0; i < bot.size(); i++)
		{
			if(player.getController().battle(bot.get(i).getController()))
			{
				/*
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				//surface.addScreen(new BattleMode(surface, player, bot.get(i)));
				//bot.remove(i);
				//pause();
			}
		}
		
		int accel = (int)timer/10;
		if(accel > 8) {
			accel = 8;
		}
		
		int shift = -1 - accel;
		if(pause) {
			for(PlayerManager b : bot)
			{
				b.getController().stop();
			}
			player.getController().stop();
			shift = 0;
			accel = 0;
			timer = 0;
			pause = false;
		}
		
		//shift(shift);
		shift(shift);


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
		
		botRun();
		
		
		for(PlayerManager b : bot)
		{
			b.getController().fall();
			b.getController().checkPlayer(obstacles);
		}

		player.getController().fall();
		player.getController().checkPlayer(obstacles);

		if(!screenRect.intersects(player.getController().getBounds()))
			surface.switchScreen(ScreenSwitcher.GAME_OVER);
		
		for(int i = 0; i < bot.size(); i++) {
			if(!screenRect.intersects(bot.get(i).getController().getBounds())) {
				bot.remove(i);
				i--;
			}
		}
		
		
		randomSpawnBots();
		
		timer += 1;
	}
	
	public void isReleased() {
		
	}
	
	/*
	private void shift(int shift) {
		for(int i = 0; i < obstacles.getChildCount(); i++) {
			obstacles.getChild(i).translate(shift, 0);
			
		}
		for(int i = 0; i < obstacles.getChildCount(); i++) {
			float[] params = obstacles.getChild(i).getParams();
			if(params[0] + params[3] <= 0) {
				obstacles.removeChild(i);
				i--;
			}
		}
	}
	*/
	
	private void shift(int shift) {
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() <= 0) {
				obstacles.remove(i);
				generateNewPlatform();
				i--;
			}
		}
		
		for(int i = 0; i < obstacles.size(); i++) {
			
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() - shift < 0)
				shift = (int)(obstacles.get(i).getWidth() + obstacles.get(i).getX());
			obstacles.get(i).translate(shift, 0);
		}
	}
	
	private void randomSpawnBots() {
		if(timer % 60 == 0) {
			spawnNewBot((int)(Math.random() * surface.width), 0);
		}
	}
	
	private void botRun() {
		for(PlayerManager tempBot : bot) {
			if(tempBot.getController().isFrozen() && tempBot.getController().isOnSurface())
				tempBot.getController().unFreeze();
			if(player.getController().getX() > tempBot.getController().getX())
				tempBot.getController().walk(1);
			else if(player.getController().getX() < tempBot.getController().getX())
				tempBot.getController().walk(-1);
			//if(player.getController().getY() < tempBot.getController().getY())
				//tempBot.getController().jump();
		} 
	}
	
	private void generateNewPlatform() {
		int x = surface.width;
		int y = (int)(Math.random()*(surface.height)*0.5 + surface.height*0.5);
		int height = (int)(Math.random() *(surface.height*0.25));
		int width = (int)(Math.random()*surface.width*0.9 + surface.width*0.1);
		Rectangle r = new Rectangle(x, y, width, height);
		obstacles.add(r);
	}
	
	

}
