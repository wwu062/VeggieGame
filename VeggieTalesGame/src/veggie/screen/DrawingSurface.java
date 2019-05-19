package veggie.screen;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PImage;
import veggie.model.Moves;
import veggie.textReader.FileIO;

public class DrawingSurface extends PApplet implements ScreenSwitcher
{

	/**
	 * ratio for when the screen is resized
	 */
	public float ratioX, ratioY;

	private Screen activeScreen;

	private ArrayList<Screen> screens;

	// used to hold key codes from the keyboard
	private ArrayList<Integer> keys;

	protected Map<String, PImage> lettuceAssets;
	protected Map<String, PImage> tomatoAssets;

	protected Map<String, PImage> assets;
	protected Map<Integer, Moves> moves;


	/**
	 * initializes fields
	 */
	public DrawingSurface()
	{
		lettuceAssets = new HashMap<String, PImage>();
		assets = new HashMap<String, PImage>();
		moves = new HashMap<Integer, Moves>();
		tomatoAssets = new HashMap<String, PImage>();


		screens = new ArrayList<Screen>();

		keys = new ArrayList<Integer>();

		Menu screen1 = new Menu(this);
		screens.add(screen1);

		Instructions screen2 = new Instructions(this);
		screens.add(screen2);

		PlatformMode screen3 = new PlatformMode(this);
		screens.add(screen3);

		switchScreen(ScreenSwitcher.MENU);
	}

	/**
	 * initializes the size of the screen
	 */
	public void settings()
	{
		// size(DRAWING_WIDTH, DRAWING_HEIGHT, P2D);
		size(activeScreen.DRAWING_WIDTH, activeScreen.DRAWING_HEIGHT);
		pixelDensity(displayDensity());
	}

	/**
	 * runs setup() in the different screens
	 */
	public void setup()
	{
		lettuceAssets.put("attack", new Gif(this, "images" + FileIO.fileSep + "lettuce-sprite-attack.gif"));
		lettuceAssets.put("run", new Gif(this, "images" + FileIO.fileSep + "lettuce-sprite-run.gif"));
		lettuceAssets.put("bounce", new Gif(this, "images" + FileIO.fileSep + "lettuce-sprite-bounce.gif"));

		assets.put("hit", new Gif(this, "images" + FileIO.fileSep + "hit-effect.gif"));
		assets.put("background1", loadImage("images" + FileIO.fileSep + "clouds.png"));
		assets.put("logo", loadImage("images" + FileIO.fileSep + "veggie-tales-logo.png"));

		tomatoAssets.put("bounce", new Gif(this, "images" + FileIO.fileSep + "tomato-sprite-bounce.gif"));


		// reading moveList file
		FileIO translator = new FileIO();
		try
		{
			ArrayList<String> temp_moves = FileIO.readFile("res" + FileIO.fileSep + "moveList.txt");
			for(String s : temp_moves)
			{
				Moves m = translator.translateMoveList(s);
				moves.put(m.getAttackval(), m);
			}

		} catch(IOException e)
		{
			e.printStackTrace();
		}


		surface.setResizable(true);
		for(Screen s : screens)
			s.setup();

	}

	/**
	 * draws the screen
	 */
	public void draw()
	{
		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		pushMatrix();

		scale(ratioX, ratioY);

		activeScreen.draw();

		popMatrix();

	}

	/**
	 * returns the current x and y coordinate correctly modified by screen size
	 * 
	 * @param actual the Point
	 * @return the new Point that is at the correct position with relation to screen
	 *         size
	 */
	public Point actualCoordinates(Point actual)
	{
		return new Point((int) (actual.getX() / ratioX), (int) (actual.getY() / ratioY));
	}

	@Override
	/**
	 * switches the screen to the indicated one
	 * 
	 * @param i the screen to switch to
	 */
	public void switchScreen(int i)
	{
		activeScreen = screens.get(i);
	}

	/**
	 * records the key that is pressed
	 */
	public void keyPressed()
	{
		keys.add(keyCode);
	}

	/**
	 * records which key has been released
	 */
	public void keyReleased()
	{
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	/**
	 * records the key that is pressed
	 * 
	 * @param code the code of the key that is pressed
	 * @return
	 */
	public boolean isPressed(Integer code)
	{
		return keys.contains(code);
	}

	/**
	 * records the mouse being pressed
	 */
	public void mousePressed()
	{
		activeScreen.mousePressed();
	}

	/**
	 * records the mouse the mouse being moved
	 */
	public void mouseMoved()
	{
		activeScreen.mouseMoved();
	}

	/**
	 * records the mouse being dragged
	 */
	public void mouseDragged()
	{
		activeScreen.mouseDragged();
	}

	/**
	 * records the mouse being released
	 */
	public void mouseReleased()
	{
		activeScreen.mouseReleased();
	}

	public void addScreen(Screen temp)
	{
		temp.setup();
		screens.add(temp);
		activeScreen = temp;
	}

	public void removeScreen(int i)
	{
		screens.remove(screens.size() - 1);
	}
}
