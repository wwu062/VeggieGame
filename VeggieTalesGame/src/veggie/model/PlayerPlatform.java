package veggie.model;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;

import processing.core.PImage;
import processing.core.PShape;

/**
 * @author awang104 PlayerController is the player character for the platform
 *         mode with physics traits.
 */
public class PlayerPlatform extends MovingImage
{
	private double velX;
	private double velY;
	private boolean onSurface;
	private boolean isWalking;

	/**
	 * Creates a new PlayerController object
	 * 
	 * @param x             x coordinate of object
	 * @param y             y coordinate of object
	 * @param lettuceAssets image texture of object
	 */
	public PlayerPlatform(Map<String, PImage> lettuceAssets, int x, int y)
	{
		super(x, y, lettuceAssets.get("bounce").width, lettuceAssets.get("bounce").height, lettuceAssets);
		velX = 0;
		velY = 0;
		onSurface = false;
		isWalking = false;
	}

	/**
	 * Increases horizontal velocity of object
	 * 
	 * @param dir 1 is right, -1 is left
	 */
	public void walk(int dir)
	{
		velX += dir * 0.5;
		if(Math.abs(velX) > 9) {
			velX = dir*9;
		}
		isWalking = true;

	}

	/**
	 * Changes vertical velocity of object and implements horizontal friction
	 */
	public void fall()
	{

		if(Math.abs(velX) < 0.2)
		{
			isWalking = false;
			velX = 0;
		}

		if(velY < 14)
			velY += 0.7;// GravityvelY);
		if(isWalking && onSurface)
			velX += -0.05 * (Math.abs(velX) / velX); // Friction


		onSurface = false;


	}

	public void checkPlayer(PShape platform)
	{
		moveBy(velX, velY);
		if(platform.getChildCount() != 0) {
			for(int i = 0; i < platform.getChildCount(); i++) {
				float[] params = platform.getChild(i).getParams();
				
				
				if(this.intersects(params[0], params[1], params[2]) && velY >= 0) {
					this.moveTo(this.getX(), params[1] - this.getHeight());
					onSurface = true;
					velY = 0;
					break;
				}
				
				
			}
		}
	}

	/**
	 * Changes vertical velocity of object
	 */
	public void jump()
	{
		if(onSurface)
		{
			velY = -15;
		}
	}

	public boolean battle(PlayerPlatform bot)
	{
		return bot.getBounds().intersects(this.getBounds());
	}

	public void stop()
	{
		velX = 0;
		velY = 0;
	}


}
