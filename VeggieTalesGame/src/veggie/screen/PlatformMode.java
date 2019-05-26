package veggie.screen;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gifAnimation.Gif;
import processing.core.PImage;
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

	// private ArrayList<PShape> obstacles;

	private ArrayList<Rectangle> obstacles;

	private Gif playerRun, tomatoBounce;

	private PImage backimg;

	private DrawingSurface surface;

	private int timer, spawnTimer;
	private boolean pause;
	private int distanceTracker;
	private ArrayList<Rectangle> items;
	private Rectangle bottomPlatform;

	private int spawnRequirement;

	private int battleRequirement = 10;

	/**
	 * Constructs a new Screen used for modeling the platforming part of the game.
	 * 
	 * @param surface the DrawingSurface used to draw the platform
	 */
	public PlatformMode(DrawingSurface surface)
	{
		super(800, 600);
		this.surface = surface;
		screenRect = new Rectangle(-100, -100, DRAWING_WIDTH + 200, DRAWING_HEIGHT + 200);
	}

	/**
	 * Creates a new controllable Entity object
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

	/**
	 * Spawns a new bot at the designated coordinates
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
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


		Player tempBot;
		if(((int) (Math.random() * 2)) == 1)
			tempBot = new Player(surface.tomatoAssets, x, y, true, istats, iplayerMovelist);
		else
			tempBot = new Player(surface.veggieKingAssets, x, y, true, istats, iplayerMovelist);
		tempBot.freeze();
		bot.add(tempBot);

	}

	// public void runMe() {
	// runSketch();
	// }

	/**
	 * The statements in the setup() functions execute once when the program begins
	 */
	public void setup()
	{
		distanceTracker = 0;
		pause = false;
		timer = 1;
		spawnTimer = 0;
		spawnRequirement = (int) (Math.random() * 20) + 100;

		bot = new ArrayList<Player>();
		items = new ArrayList<Rectangle>();
		obstacles = new ArrayList<Rectangle>();

		playerRun = (Gif) surface.lettuceAssets.get("run");
		playerRun.play();

		tomatoBounce = (Gif) surface.tomatoAssets.get("bounce");
		tomatoBounce.play();

		surface.pushStyle();
		surface.fill(165, 42, 42);

		bottomPlatform = new Rectangle(-200, (int) (DRAWING_HEIGHT - 68), DRAWING_WIDTH + 200, 500);

		Rectangle r1 = new Rectangle(200, 400, 400, 50);
		Rectangle r2 = new Rectangle(0, 250, 100, 50);
		Rectangle r3 = new Rectangle(700, 250, 100, 50);
		Rectangle r4 = new Rectangle(375, 300, 50, 100);
		Rectangle r5 = new Rectangle(300, 250, 200, 50);

		/*
		 * PShape p1 = surface.createShape(PConstants.RECT, 200, 400, 400, 50); PShape
		 * p2 = surface.createShape(PConstants.RECT, 0, 250, 100, 50); PShape p3 =
		 * surface.createShape(PConstants.RECT, 700, 250, 100, 50); PShape p4 =
		 * surface.createShape(PConstants.RECT, 375, 300, 50, 100); PShape p5 =
		 * surface.createShape(PConstants.RECT, 300, 250, 200, 50);
		 */

		surface.popStyle();

		/*
		 * obstacles.addChild(p1); obstacles.addChild(p2); obstacles.addChild(p3);
		 * obstacles.addChild(p4); obstacles.addChild(p5);
		 */

		obstacles.add(r1);
		obstacles.add(r2);
		obstacles.add(r3);
		obstacles.add(r4);
		obstacles.add(r5);
		spawnNewPlayer();
		// spawnNewBot(1200 / 2 - 100, 600 / 2 - 100);


		backimg = surface.assets.get("background2");
		backimg.resize(surface.width, surface.height);
	}

	/**
	 * Executes 60 times a second continuously until stopped
	 */
	public void draw()
	{
		// drawing stuff
		surface.background(255);


		surface.pushMatrix();

		surface.scale(surface.ratioX, surface.ratioY);

		surface.fill(100);


		// surface.shape(obstacles);
		for(Rectangle rect : obstacles)
		{
			surface.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// surface.rect(bottomPlatform.x, bottomPlatform.y, bottomPlatform.width,
		// bottomPlatform.height);

		surface.pushStyle();
		surface.fill(255, 0, 0);
		for(Rectangle rect : items)
		{
			surface.rect(rect.x, rect.y, rect.width, rect.height);
		}
		surface.popStyle();


		for(Player b : bot)
		{
			b.draw(surface, "bounce");
		}

		player.draw(surface, "run");


		surface.popMatrix();

		// modifying stuff
		run();
		surface.pushStyle();
		surface.fill(255, 0, 0);
		surface.text("Battle Upcoming", 0 + 5 * ((battleRequirement - 3) * 60 - distanceTracker), 50);
		surface.popStyle();
	}

	public void run()
	{
		if(distanceTracker == 60 * battleRequirement)
		{
			spawnNewBot(100, 100);
			surface.addScreen(new BattleMode(surface, player, bot.get(bot.size() - 1)));
			bot = new ArrayList<Player>();
			// bot.remove(bot.size()-1);
			distanceTracker = 0;
			battleRequirement += 5;
		}

		for(int i = 0; i < bot.size(); i++)
		{
			if(player.battle(bot.get(i)))
			{
				surface.switchScreen(ScreenSwitcher.GAME_OVER);
			}

		}

		int accel = (int) timer / 10;
		if(accel > 8)
		{
			accel = 8;
		}

		int shift = -1 - accel;
		if(pause)
		{
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

		// shift(shift);
		shift(shift);


		if(surface.isPressed(KeyEvent.VK_LEFT))
			player.walk(-1);
		if(surface.isPressed(KeyEvent.VK_RIGHT))
			player.walk(1);
		if(surface.isPressed(KeyEvent.VK_UP))
			player.jump();
		if(surface.isPressed(KeyEvent.VK_SHIFT) && (surface.isPressed(KeyEvent.VK_RIGHT) || surface.isPressed(KeyEvent.VK_LEFT)))
			player.slide(true);
		else if(surface.isPressed(KeyEvent.VK_SHIFT))
		{

		} else
		{
			player.slide(false);
		}

		botRun();

		obstacles.add(bottomPlatform);
		for(Player b : bot)
		{
			b.fall();
			b.checkPlayer(obstacles);
		}

		player.fall();
		player.checkPlayer(obstacles);
		obstacles.remove(obstacles.size() - 1);

		if(!screenRect.intersects(player.getBounds()))
			surface.switchScreen(ScreenSwitcher.GAME_OVER);

		for(int i = 0; i < bot.size(); i++)
		{
			if(!screenRect.intersects(bot.get(i).getBounds()))
			{
				bot.remove(i);
				i--;
			}
		}

		// int chance = (int)((Math.random()*2)+1);

		if(spawnTimer > 180)
			randomSpawnBots();
		spawnNewItems();
		setNewMove();

		if(timer % 45 == 0)
			for(int c = 0; c < 3; c++)
			{
				generateNewPlatform();
			}


		timer++;
		spawnTimer++;
		distanceTracker++;
	}

	private void shift(int shift)
	{
		// platform
		for(int i = 0; i < obstacles.size(); i++)
		{
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() <= 0)
			{
				obstacles.remove(i);
				i--;
			}
		}

		for(int i = 0; i < obstacles.size(); i++)
		{
			if(obstacles.get(i).getWidth() + obstacles.get(i).getX() - shift < 0)
				shift = (int) (obstacles.get(i).getWidth() + obstacles.get(i).getX());
			obstacles.get(i).translate(shift, 0);
		}
		// items
		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).getWidth() + items.get(i).getX() <= 0)
			{
				items.remove(i);
				i--;
			}
		}

		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).getWidth() + items.get(i).getX() - shift < 0)
				shift = (int) (items.get(i).getWidth() + items.get(i).getX());
			items.get(i).translate(shift, 0);
		}


	}

	private void randomSpawnBots()
	{
		if(timer % (5 * 60) == 0)
		{
			/*
			 * if(((int)(Math.random()*2)) < 1.5) spawnNewBot((int)(Math.random() *
			 * DRAWING_WIDTH), 0);
			 */
			// else
			spawnNewBot(-64, DRAWING_HEIGHT - 100 - 70);
		}
	}

	private void botRun()
	{
		for(Player tempBot : bot)
		{
			if(tempBot.isFrozen() && tempBot.isOnSurface())
				tempBot.unFreeze();
			if(player.getX() > tempBot.getX())
				tempBot.walk(1);
			else if(player.getX() < tempBot.getX())
				tempBot.walk(-1);
			if(player.getY() < tempBot.getY())
				tempBot.jump();
		}
	}

	private void generateNewPlatform()
	{
		Rectangle r = null;
		boolean contains = true;
		while(contains)
		{
			int y = DRAWING_HEIGHT / (int) (8 * Math.random() + 1) + 100;
			r = new Rectangle(DRAWING_WIDTH, y, (int) (200 * Math.random()) + 50, (int) (DRAWING_HEIGHT / 15));
			for(Rectangle plat : obstacles)
			{
				contains = r.intersects(plat) || r.contains(plat);
			}
		}

		if(r != null)
			obstacles.add(r);
	}

	private void setNewMove()
	{

		for(int i = 0; i < items.size(); i++)
		{
			Rectangle r = items.get(i);
			if(i > 20)
			{
				break;
			}
			if(player.getBounds().intersects(r))
			{
				Integer key = (int) (Math.random() * surface.moves.size() + 1);
				Moves temp = surface.moves.get(key);
				player.setMoveList(temp, (int) (Math.random() * 4));
				items.remove(i);
				i--;
			}
		}

	}

	private void spawnNewItems()
	{
		if(timer % spawnRequirement == 0)
		{
			Rectangle r = null;
			boolean contains = true;
			while(contains)
			{
				r = new Rectangle(DRAWING_WIDTH, DRAWING_HEIGHT - 200 - (int) (Math.random() * 4) * 100, 20, 20);
				for(Rectangle plat : obstacles)
				{
					contains = plat.intersects(r) || plat.contains(r);
				}
			}

			if(r != null)
				items.add(r);
			spawnRequirement = (int) (Math.random() * 20 * 60);
		}

	}


}
