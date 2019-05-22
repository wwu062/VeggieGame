package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import processing.core.PGraphics;
import veggie.model.PlayerManager;

/**
 * The battle screen for the game (think Pokemon battle)
 * 
 * @author williamwu
 */
public class BattleMode extends Screen
{
	private PlayerManager player, enemy;

	private int iplayerHealth, ienemyHealth;

	private DrawingSurface surface;

	private Rectangle[] button;

	private Gif hitImage;

	private int timer = 1;

	private int whichPlayer = 0;

	private boolean turnDone = false;

	// graphics for the panel, initial state of the players, and final state during
	// a move
	private PGraphics panels, attackScreen, healthPanel;

	// keeps check of if it is the players turn or the enemy's
	private int turnCounter = 0;

	// sets to 0 before and after each mouse click
	private int panelClick = 0;

	/**
	 * initializes the fields
	 * 
	 * @param surface DrawingSurface object
	 * @param player  the Player Entity object
	 * @param enemy   the Enemy Entity object
	 */
	public BattleMode(DrawingSurface surface, PlayerManager player, PlayerManager enemy)
	{
		super(800, 600);
		this.player = player;
		this.enemy = enemy;

		iplayerHealth = player.getBattler().getHealth();
		ienemyHealth = enemy.getBattler().getHealth();

		// changes location of the player and enemy for battle arena
		//player.getController().changeBy(150, 200);
		//enemy.getController().changeBy(550, 200);

		this.surface = surface;

		button = new Rectangle[4];

		// attacks column 1 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i] = new Rectangle(150, 375 + 125 * i, 225, 75);
		}

		// attacks column 2 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i + 2] = new Rectangle(425, 375 + 125 * i, 225, 75);
		}

	}

	public void setup()
	{
		hitImage = (Gif) surface.assets.get("hit");
		hitImage.play();

		panels = surface.createGraphics(800, 600);
		attackScreen = surface.createGraphics(800, 300);
		healthPanel = surface.createGraphics(800, 300);
		surface.createGraphics(800, 300);

		surface.background(255, 255, 255);

		panels.beginDraw();
		drawPanel();
		drawPanel(); // TEXT ONLY CENTERS IF I CALL IT TWICE ?
		panels.endDraw();
		surface.image(panels, 0, 0);
	}

	/**
	 * Draws the battle
	 * 
	 * @post changes background color
	 */
	public void draw()
	{	
		timer++;

		attackScreen.beginDraw();
		attackScreen.background(255);

		drawHealthPanel();
		attackScreen.image(healthPanel, 0, 0);


		//if(player.getBattler().getHealth() <= iplayerHealth / 2)
		//{
		//	player.getController().draw(attackScreen, "hurt");
		//} else
		//{
			player.getController().draw(attackScreen, "bounce");
		//}

		//if(enemy.getBattler().getHealth() <= ienemyHealth / 2)
		//{
		//	enemy.getController().draw(attackScreen, "hurt");
		//} else
		//{
			enemy.getController().draw(attackScreen, "bounce");
	//	}

		if(turnDone)
		{
			//System.out.println(whichPlayer);
			attackScreen.beginDraw();
			attackScreen.background(255);
			attackScreen.image(healthPanel, 0, 0);
			attackScreen.endDraw();

			if(whichPlayer == 0) {
				
				if(enemy.getBattler().getHealth() <= ienemyHealth)
				{
					enemy.getController().draw(attackScreen, "hurt");
				} else
				{
					enemy.getController().draw(attackScreen, "bounce");
				}
				player.getController().draw(attackScreen, "attack");
				hitImage("enemy");
				
			} else	{
				if(player.getBattler().getHealth() <= iplayerHealth) {
					player.getController().draw(attackScreen, "hurt");
				} else {
					player.getController().draw(attackScreen, "bounce");
				}
				enemy.getController().draw(attackScreen, "attack");// !@$!@$ CHANGE TO ATTACK LATER!! !@$!@#$(!@$
				hitImage("player");
			}

			if(timer % 20 == 0)
			{
				if(isDead() == 1 || isDead() == -1)	{
					surface.switchScreen(2);
					surface.removeScreen(3);
				}
				turnDone = false;
				turnCounter++;
			}
			System.out.println(panelClick);

		} else if(turnCounter % 2 == 0) {
			checkpanelClick();
			whichPlayer = 0;
		} else {
			int move = (int) (Math.random() * 4);
			drawMove(move, "enemy");

			turnDone = true;
			timer = 1;
			whichPlayer = 1;
		}

		attackScreen.endDraw();

		surface.image(attackScreen, 0, 0);

		// System.out.println(System.currentTimeMillis() - c);
	
	}

	public void drawHealthPanel()
	{
		healthPanel.beginDraw();
		// player
		healthPanel.fill(0);
		healthPanel.rect(100, 50, 100, 25);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(100, 50, player.getBattler().getHealth(), 25);
		// enemy
		healthPanel.fill(0);
		healthPanel.rect(600, 50, 100, 25);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(600, 50, enemy.getBattler().getHealth(), 25);

		healthPanel.endDraw();
	}

	private void drawPanel()
	{
		for(int i = 0; i < 4; i++)
		{
			panels.pushStyle();
			// System.out.println("button" + i + " " + button[i].x + button[i].y + ", " +
			// button[i].width + ", " + button[i].height);
			panels.rect(button[i].x, button[i].y, button[i].width, button[i].height);
			panels.fill(0);
			String move1 = player.getMoveList()[i].getName();
			float w = panels.textWidth(move1);

			panels.text(move1, button[i].x + button[i].width / 2 - w / 2, button[i].y + button[i].height / 2);
			panels.popStyle();
		}
	}

	private void checkpanelClick()
	{
		if(0 != panelClick) // when mouse is clicked, clears with white screen.
		{
			turnDone = true;
			timer = 1;
			drawMove(panelClick - 1, "player");
		}
	}

	private void drawMove(int num, String entity)
	{
		if(entity.equalsIgnoreCase("player"))
		{
			boolean crit = crit(player.getBattler().getCritrate());
			if(crit)
			{
				changeHealth(enemy, player.getMoveList()[num].getAttackval() + 10);
			} else
			{
				changeHealth(enemy, player.getMoveList()[num].getAttackval());
			}
		}

		if(entity.equalsIgnoreCase("enemy"))
		{
			boolean crit = crit(enemy.getBattler().getCritrate());
			if(crit)
			{
				changeHealth(player, enemy.getMoveList()[num].getAttackval() + 10);
			} else
			{
				changeHealth(player, enemy.getMoveList()[num].getAttackval());
			}
		}

		panelClick = 0;
	}

	/**
	 * Checks if the hit image should land on the player or enemy then draws the gif
	 * 
	 * @pre Argument only takes "player" or "enemy"
	 * @param Entity the "player" or "enemy" that will be damaged
	 */
	private void hitImage(String Entity) {
		attackScreen.beginDraw();
		if(Entity.equalsIgnoreCase("enemy"))
		{
			attackScreen.image(hitImage, 590, 190, 32, 32);
		}
		if(Entity.equalsIgnoreCase("player"))
		{
			attackScreen.image(hitImage, 190, 190, 32, 32);
		}
		attackScreen.endDraw();
	}

	/**
	 * checks if mouse is being pressed and switches screens if it is on a button
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if(button[0].contains(p))
			panelClick = 1;
		if(button[1].contains(p))
			panelClick = 2;
		if(button[2].contains(p))
			panelClick = 3;
		if(button[3].contains(p))
			panelClick = 4;
	}

	/**
	 * changes the health of the player or the enemy to account for healing or
	 * damage done
	 * 
	 * @param e      the Entity that is being attacked/ healed
	 * @param damage the damage/healing that is occurring to the Entity object
	 */
	public void changeHealth(PlayerManager e, int damage) {
		int health = e.getBattler().getHealth();

		e.getBattler().setHealth(health - damage);
	}

	/**
	 * returns whether or not there is a successful critical hit on the opponent
	 * 
	 * @param critChance the critical hit rate of the attacking entity
	 * @return true if there was a critical, else false.
	 */
	private boolean crit(double critChance) {
		double crit = Math.random();

		if(crit <= critChance)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public int isDead() {
		if(enemy.getBattler().getHealth() == 0)
			return -1;
		else if(player.getBattler().getHealth() == 0)
			return 1;
		else
			return 0;
	}
}
