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
	private boolean sliding;
	
	private static final double TERMINAL_VELOCITY = 14;
	private static final double MAX_VELX = 8;

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
		sliding = false;
	}

	/**
	 * Increases horizontal velocity of object
	 * 
	 * @param dir 1 is right, -1 is left
	 */
	public void walk(int dir)
	{
		if(!sliding)
			velX += dir * 0.5;
			if(Math.abs(velX) > MAX_VELX) {
				velX = dir*MAX_VELX;
			}
			isWalking = true;
	}
	
	public void slide() {
		sliding = true;
	}

	/**
	 * Changes vertical velocity of object and implements horizontal friction
	 */
	public void fall()
	{
		if(Math.abs(velX) < 0.02) {
			velX = 0;
			isWalking = false;
		}

		velY += 0.7;
		if(velY > TERMINAL_VELOCITY)
			velY = TERMINAL_VELOCITY;// GravityvelY);
		
		if(isWalking && onSurface && !sliding)
			velX += -0.05 * (Math.abs(velX) / velX);// Friction


		onSurface = false;


	}

	public void checkPlayer(PShape platform)
	{
		moveBy(velX, velY);
		if(platform.getChildCount() != 0) {
			for(int i = 0; i < platform.getChildCount(); i++) {
				float[] params = platform.getChild(i).getParams();
				
				if(this.intersects(params[0], params[1], params[2]) && velY > 0) {
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
			sliding = false;
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
