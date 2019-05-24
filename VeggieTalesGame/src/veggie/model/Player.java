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
public class Player extends MovingImage {
	
	private Stats statistics;
	private Moves[] moveList;
	
	private double velX;
	private double velY;
	private boolean onSurface;
	private boolean isWalking;
	private boolean sliding;
	private boolean isFrozen;
	
	private final double MAX_VELX;
	
	private static final double TERMINAL_VELOCITY = 14;
	private static final double PLAYER_MAX_VELX = 8;
	private static final double BOT_MAX_VELX = 4.5;
	private static final double IN_AIR_MODIFIER = 0.25;
	private static final double NO_MODIFIER = 1;
	private static final double COEF_FRICTION = -0.05;

	/**
	 * Creates a new PlayerController object
	 * 
	 * @param x             x coordinate of object
	 * @param y             y coordinate of object
	 * @param playerImages image texture of object
	 */
	public Player(Map<String, PImage> playerImages, int x, int y, boolean isBot, Stats statistics, Moves[] moveList)
	{
		
		super(x, y, playerImages.get("run").width, playerImages.get("run").height, playerImages);
		
		this.statistics = statistics;
		this.moveList = moveList;
		
		velX = 0;
		velY = 0;
		onSurface = false;
		isWalking = false;
		sliding = false;
		isFrozen = false;
		
		if(isBot) {
			MAX_VELX = BOT_MAX_VELX;
		} else {
			MAX_VELX = PLAYER_MAX_VELX;
		}
		
	}
	
	/**
	 * @param move  the new move
	 * @param rmove the move that will be replaced
	 */
	public void setMoveList(Moves move, int rmove) {
		moveList[rmove] = move;
	}
	
	/**
	 * 
	 * @param i the index of of MovesList.
	 */
	public Moves getMove(int i) {
		return moveList[i];
	}
	
	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return statistics.getHealth();
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health)
	{
		if(health < 0)
		{
			statistics.setHealth(0);
		} else
		{
			statistics.setHealth(health);;
		}
	}

	/**
	 * @return the critical rate
	 */
	public double getCritrate()
	{
		return statistics.getCritrate();
	}

	/**
	 * @param critrate the critical rate to set
	 */
	public void setCritrate(double critrate)
	{
		statistics.setCritrate(critrate);
	}
	
	/**
	 * Increases horizontal velocity of object
	 * 
	 * @param dir 1 is right, -1 is left
	 */
	public void walk(int dir) {
	
		if(isFrozen) {
			return;
		}
		
		double modifier = NO_MODIFIER;
		if(!onSurface)
			modifier = IN_AIR_MODIFIER;
		if(!sliding) {
			velX += dir * 0.5 * modifier;
			if(Math.abs(velX) > MAX_VELX) {
				velX = dir*MAX_VELX;
			}
			isWalking = true;
		}
			
	}
	
	
	public void slide(boolean status) {
		if(isFrozen) {
			return;
		}
		
		sliding = status;
	}
	

	/**
	 * Changes vertical velocity of object and implements horizontal friction
	 */
	public void fall()
	{
		
		if(Math.abs(velX) < 0.03) {
			velX = 0;
			isWalking = false;
		}

		velY += 0.7;
		if(velY > TERMINAL_VELOCITY)
			velY = TERMINAL_VELOCITY;// GravityvelY);
		
		if(isWalking && onSurface && !sliding)
			velX += COEF_FRICTION * (Math.abs(velX) / velX);// Friction


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
	
	public void checkPlayer(ArrayList<Rectangle> platform)
	{
		moveBy(velX, velY);
		for(Rectangle rect : platform) {
			if(this.intersects(rect.x, rect.y, rect.width) && velY > 0) {
				this.moveTo(this.getX(), rect.y - this.getHeight());
				onSurface = true;
				velY = 0;
				break;
			}
		}
		
	}
	

	/**
	 * Changes vertical velocity of object
	 */
	public void jump()
	{
		if(isFrozen) {
			return;
		}
		
		if(onSurface)
		{
			sliding = false;
			velY = -15;
		}
	}

	public boolean battle(Player bot)
	{
		return bot.getBounds().intersects(this.getBounds());
	}

	public void stop()
	{
		velX = 0;
		velY = 0;
	}
	
	public void stopHorizontal() {
		velX = 0;
	}
	
	public void freeze() {
		isFrozen = true;
	}
	
	public boolean isOnSurface() {
		return onSurface;
	}
	
	public void unFreeze() {
		isFrozen = false;
	}

	public boolean isFrozen() {
		return isFrozen;
	}
	
	public double getX() {
		return super.getX();
	}
	
	public double getY() {
		return super.getY();
	}

}
