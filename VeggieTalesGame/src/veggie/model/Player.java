package veggie.model;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;

import processing.core.PImage;

/**
 * @author awang104 
 * PlayerController is the player character for the platform mode with physics traits.
 */
public class Player extends MovingImage {
	
	private Stats statistics;
	private Move[] moveList;
	
	private double velX;
	private double velY;
	private boolean onSurface;
	private boolean isWalking;
	private boolean sliding;
	private boolean isFrozen;
	
	private double maxVelX;
	private final double maxVelY;
	
	private static final double TERMINAL_VELOCITY = 14;
	private static final double PLAYER_MAX_VELX = 8;
	private static final double BOT_MAX_VELX = 3;
	private static final double IN_AIR_MODIFIER = 0.25;
	private static final double NO_MODIFIER = 1;
	private static final double COEF_FRICTION = -0.05;
	private static final double MAX_VELY_BOT = -10;
	private static final double MAX_VELY_PLAYER = -20;

	/** Creates a new PlayerController object
	 * 
	 * @param playerImages image texture of object
	 * @param x x coordinate of object
	 * @param y y coordinate of object
	 * @param isBot true if Player is a bot
	 * @param statistics a Stats object holding the statistics of the Player
	 * @param moveList an array of Moves that represent the player's moves.
	 */
	public Player(Map<String, PImage> playerImages, int x, int y, boolean isBot, Stats statistics, Move[] moveList)
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
			maxVelX = (BOT_MAX_VELX - 2) + 2*Math.random();
			maxVelY = MAX_VELY_BOT;
		} else {
			maxVelX = PLAYER_MAX_VELX;
			maxVelY = MAX_VELY_PLAYER;
		}
		
	}
	
	/**
	 * @param move  the new move
	 * @param rmove the move that will be replaced
	 */
	public void setMoveList(Move move, int rmove) {
		moveList[rmove] = move;
	}
	
	/**
	 * 
	 * @param i the index of the Move in the list of Moves.
	 * @return the Moves object of the move desired
	 */
	public Move getMove(int i) {
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
			if(Math.abs(velX) > maxVelX) {
				velX = dir*maxVelX;
			}
			isWalking = true;
		}
			
	}
	
	/**
	 * 
	 * @param status true if player is "sliding"
	 */
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
	/** Checks to see if the player can fall onto the platform.
	 * 
	 * @param platform an ArrayList of Rectangles that represents the platform the player is on.
	 */
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
			velY = maxVelY;
			
		}
	}

	/** Determines when to "battle" and switch to battle mode.
	 * 
	 * @param bot the other player this player object is battling
	 * @return true if the bot intersects and will battle with the player
	 */
	public boolean battle(Player bot)
	{
		return bot.getBounds().intersects(this.getBounds());
	}

	/**
	 * Completely stops the player's velocity.
	 */
	public void stop()
	{
		velX = 0;
		velY = 0;
	}
	
	/**
	 * Completely stops the player's horizontal velocity.
	 */
	public void stopHorizontal() {
		velX = 0;
	}
	
	/**
	 * Freezes the player, preventing it from changing velocity.
	 */
	public void freeze() {
		isFrozen = true;
	}
	
	/**
	 * 
	 * @return true if player is on a surface such as a platform.
	 */
	public boolean isOnSurface() {
		return onSurface;
	}
	
	/**
	 * Unfreezes the player, allowing them to accelerate again.
	 */
	public void unFreeze() {
		isFrozen = false;
	}

	/**
	 * 
	 * @return true if player is frozen and cannot accelerate.
	 */
	public boolean isFrozen() {
		return isFrozen;
	}
	
	/**
	 * Decreases the energy and max speed of the player
	 */
	public void drainLife() {
		maxVelX -= 2.5;
	}
	
	/**
	 * 
	 * @return true if player's max speed is essentially 0.
	 */
	public boolean noLife() {
		return maxVelX < 0;
	}
	
	/**
	 * Resets player health to full health.
	 */
	public void resetHealth() {
		statistics.setHealth(statistics.getTotalHP());
	}

}
