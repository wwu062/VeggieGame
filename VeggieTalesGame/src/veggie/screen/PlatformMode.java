package veggie.screen;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gifAnimation.Gif;
import processing.core.PConstants;
import processing.core.PShape;
import veggie.model.Moves;
import veggie.model.Player;
import veggie.model.Stats;

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

	private Player player;

	private ArrayList<Player> bot;

	//private ArrayList<PShape> obstacles;
	
	private ArrayList<Rectangle> obstacles;

	private Gif playerRun, tomatoBounce;

	private DrawingSurface surface;
	
	private double timer;
	
	private boolean pause;
	
	private ArrayList<Rectangle> items;
	
	private int spawnRequirement;
	
	private static final int[] LEVELS = new int[] {2, 4, 6, 8, 10};
	
	private int level;
	
	private Rectangle bottomRect;

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
		bottomRect = new Rectangle(0, DRAWING_HEIGHT - 1, DRAWING_WIDTH, 1);
	}

	/**
	 * creates a new controllable Entity object
	 */
	public void spawnNewPlayer()
	{

		// player creation
		Stats istats = new Stats(100, 0.1);

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

		player = new Player(surface.lettuceAssets, 800 / 2 - 100, 400 / 2 - 100, false, istats, iplayerMovelist);
	}

	public void spawnNewBot(int x, int y)
	{
		Stats istats = new Stats(100, 0.1);

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
		Player tempBot = new Player(surface.tomatoAssets, x, y, true, istats, iplayerMovelist);
		tempBot.freeze();
		bot.add(tempBot);
		//bot.add(new Player(surface.tomatoAssets, istats, iplayerMovelist, 800 / 2 - 100, 600 / 2 - 100));
		
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
		spawnRequirement = (int)(Math.random()*120);
		level = 0;
		bot = new ArrayList<Player>();
		items = new ArrayList<Rectangle>();
		obstacles = new ArrayList<Rectangle>();
		
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
		// drawing stuff

		surface.background(0, 255, 255);

		surface.pushMatrix();

		surface.scale(surface.ratioX, surface.ratioY);

		surface.fill(100);

		//surface.shape(obstacles);
		for(Rectangle rect : obstacles) {
			surface.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		surface.pushStyle();
		surface.fill(255,0,0);
		for(Rectangle rect : items) {
			surface.rect(rect.x, rect.y, rect.width, rect.height);
		}
		surface.popStyle();
		
		player.draw(surface, "run");

		for(Player b : bot)
		{
			b.draw(surface, "bounce");
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
			if(player.battle(bot.get(i)))
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
			for(Player b : bot)
			{
				b.stop();
			}
			player.stop();
			shift = 0;
			accel = 0;
			timer = 0;
			pause = false;
		}
		
		//shift(shift);
		shift(shift);


		if(surface.isPressed(KeyEvent.VK_LEFT))
			player.walk(-1);
		if(surface.isPressed(KeyEvent.VK_RIGHT))
			player.walk(1);
		if(surface.isPressed(KeyEvent.VK_UP))
			player.jump();
		if(surface.isPressed(KeyEvent.VK_SHIFT) && (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_LEFT)))
			player.slide(true);
		else if(surface.isPressed(KeyEvent.VK_SHIFT)) {
			
		} else {
			player.slide(false);
		}
		
		botRun();
		
		
		for(Player b : bot)
		{
			b.fall();
			b.checkPlayer(obstacles);
		}

		player.fall();
		player.checkPlayer(obstacles);

		if(!screenRect.intersects(player.getBounds())) {
			player.stopHorizontal();
			System.out.println("stop horizontal");
		}
		
		if(bottomRect.intersects(player.getBounds()))
			surface.switchScreen(ScreenSwitcher.GAME_OVER);
		
		for(int i = 0; i < bot.size(); i++) {
			if(!screenRect.intersects(bot.get(i).getBounds())) {
				bot.remove(i);
				i--;
			}
		}
		
		
		randomSpawnBots();
		spawnNewItems();
		
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
		//platform
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() <= 0) {
				obstacles.remove(i);
				i--;

				if(timer%3 == 0)
					generateNewPlatform();
			}
		}
		
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() - shift < 0)
				shift = (int)(obstacles.get(i).getWidth() + obstacles.get(i).getX());
			obstacles.get(i).translate(shift, 0);
		}
		//items
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getWidth() + items.get(i).getX() <= 0) {
				items.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getWidth() + items.get(i).getX() - shift < 0)
				shift = (int)(items.get(i).getWidth() + items.get(i).getX());
			items.get(i).translate(shift, 0);
		}
		
		
	}
	
	private void randomSpawnBots() {
		if(timer % 60 == 0) {
			spawnNewBot((int)(Math.random() * surface.width), 0);
		}
	}
	
	private void botRun() {
		for(Player tempBot : bot) {
			if(tempBot.isFrozen() && tempBot.isOnSurface())
				tempBot.unFreeze();
			if(player.getX() > tempBot.getX())
				tempBot.walk(1);
			else if(player.getX() < tempBot.getX())
				tempBot.walk(-1);
			//if(player.getY() < tempBot.getY())
				//tempBot.jump();
		} 
	}
	
	private void generateNewPlatform() {
		
		
		level += Math.random()*4 + 1;
		level %= 5;
		
		int x = surface.width;
		int y = (int)(Math.random()*(surface.height)/LEVELS[level] + surface.height*0.2);
		int height = (int)(Math.random()*(surface.height/LEVELS[level]) + (surface.height*0.1));
		int width = (int)(Math.random()*surface.width*0.7 + surface.width*0.3);
		Rectangle r = new Rectangle(x, y, width, height);
		obstacles.add(r);
	}
	
	private void setNewMove() {
		//if(player.intersects(rectangle))
		//String s = (int)((Math.random() * 3) + 5) + "";
		
	}
	
	private void spawnNewItems() {
		if(timer%spawnRequirement == 0) {
			Rectangle r = new Rectangle(surface.width, 100, 20, 20);
			items.add(r);
			spawnRequirement = (int)(Math.random()*3600 + 1800);
		}
			
	}
	

}
