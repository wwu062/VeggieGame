package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import processing.core.PGraphics;
import veggie.model.Moves;
import veggie.model.Player;

/**
 * The Battle Screen for the game. In this mode, there are 4 buttons at the
 * bottom of the screen that are the player's attack moves. They can be clicked
 * and the player will attack. Afterwards the enemy will attack. Whoever defeats
 * the other first wins.
 * 
 * @author williamwu
 */
public class BattleMode extends Screen
{	
	private DrawingSurface surface;
	private PGraphics panels, attackScreen, healthPanel;


	private Player player, enemy;
	private int iplayerHealth, ienemyHealth;

	private Rectangle[] button;
	private Rectangle textRect;

	private Gif hitImage, critImage;

	private int timer = 1;

	private boolean crit = false;

	private boolean selfAttack = false;

	// 0 = player, 1 = enemy
	private int whichPlayer = 0;

	private Moves prevPlayerMove = null;

	private Moves prevEnemyMove = null;

	private boolean turnDone = false;

	// graphics for the panel, initial state of the players, and final state during
	// a move

	// keeps check of if it is the players turn or the enemy's
	private int turnCounter = 0;

	// sets to 0 before and after each mouse click
	private int panelClick = 0;

	// private boolean poisoned = false;
	private int poisonTurns, poisonTurnCounter;
	private Player poisonedPlayer;


	/**
	 * Constructs a new BattleMode screen
	 * 
	 * @param surface DrawingSurface object
	 * @param player  the Player Entity object
	 * @param enemy   the Enemy Entity object
	 */
	public BattleMode(DrawingSurface surface, Player player, Player enemy)
	{
		super(800, 600);
		this.player = player;
		this.enemy = enemy;

		iplayerHealth = player.getHealth();
		ienemyHealth = enemy.getHealth();


		// changes location of the player and enemy for battle arena
		player.changeBy(125, 200);
		enemy.changeBy(550, 200);

		this.surface = surface;

		button = new Rectangle[4];

		// attacks column 1 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i] = new Rectangle(110, 410 + 70 * i, 185, 60);
		}

		// attacks column 2 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i + 2] = new Rectangle(305, 410 + 70 * i, 185, 60);
		}

		textRect = new Rectangle(500, 400, 260, 150);

	}

	/**
	 * Sets up the BattleMode screen for gameplay
	 */
	public void setup()
	{

		hitImage = (Gif) surface.assets.get("hit");
		hitImage.play();

		critImage = (Gif) surface.assets.get("crit");
		critImage.play();

		panels = surface.createGraphics(800, 600);
		attackScreen = surface.createGraphics(800, 340);
		healthPanel = surface.createGraphics(800, 300);
		// surface.createGraphics(800, 300);

		surface.background(255, 255, 255);
	}

	/**
	 * Draws the battle
	 */
	public void draw()
	{
		panels.beginDraw();
		panels.textFont(surface.font);
		drawPanel();
		drawPanel(); // TEXT ONLY CENTERS IF I CALL IT TWICE ?
		panels.endDraw();

		// textScreen.beginDraw();
		// drawTextScreen();
		// textScreen.endDraw();
		// panels.image(textScreen, 0, 0);
		// surface.image(panels, 0, 0);

		// System.out.println("This is the ienemy: " + ienemyHealth);
		// System.out.println("This is the getStat health" +
		// enemy.getHealth());

		timer++;

		attackScreen.beginDraw();
		attackScreen.background(255);

		drawHealthPanel();
		attackScreen.image(healthPanel, 0, 0);


		if(player.getHealth() <= iplayerHealth / 2)
		{
			player.draw(attackScreen, "hurt");
		} else
		{
			player.draw(attackScreen, "bounce");
		}

		if(enemy.getHealth() <= ienemyHealth / 2)
		{
			enemy.draw(attackScreen, "hurt");
		} else
		{
			enemy.draw(attackScreen, "bounce");
		}


		if(turnDone)
		{
			panels.beginDraw();
			drawTextAttackScreen();
			panels.endDraw();

			attackScreen.beginDraw();
			attackScreen.background(255);
			attackScreen.image(healthPanel, 0, 0);
			attackScreen.endDraw();

			if(poisonTurns > 0)
			{
				changeHealth(poisonedPlayer, 10);
				poisonTurns--;
				// System.out.println("poisoned" + turnCounter);
			}

			if(whichPlayer == 0)
			{
				// System.out.println("hit");
				if(enemy.getHealth() <= ienemyHealth / 2)
				{
					enemy.draw(attackScreen, "hurt");
				} else
				{
					enemy.draw(attackScreen, "bounce");
				}
				player.draw(attackScreen, "attack");
				hitImage("enemy");
				critImage(crit);
			}

			if(whichPlayer == 1)
			{
				if(player.getHealth() <= iplayerHealth / 2)
				{
					player.draw(attackScreen, "hurt");
				} else
				{
					player.draw(attackScreen, "bounce");
				}
				enemy.draw(attackScreen, "attack");// !@$!@$ CHANGE TO ATTACK LATER!! !@$!@#$(!@$
				hitImage("player");
				critImage(crit);
			}

			if(timer % 30 == 0)
			{
				turnDone = false;
				turnCounter++;
				crit = false;
				selfAttack = false;
			}

		}

		surface.image(panels, 0, 0);

		if(turnCounter % 2 == 0 & timer % 30 == 0)
		{
			checkpanelClick();
			whichPlayer = 0;
		}

		if(turnCounter % 2 != 0 && timer % 60 == 0)
		{
			int move = (int) (Math.random() * 4);
			prevEnemyMove = enemy.getMove(move);
			drawMove(move, enemy, player);

			turnDone = true;
			timer = 1;
			whichPlayer = 1;
		}

		if(timer % 30 == 0)
		{
			if(isDead() == -1)
			{
				player.resetHealth();
				surface.switchScreen(2);
				surface.removeScreen(ScreenSwitcher.BATTLE);
			} else if(isDead() == 1)
			{
				surface.switchScreen(ScreenSwitcher.GAME_OVER);
			}
		}

		attackScreen.endDraw();

		surface.image(attackScreen, 0, 0);

		// System.out.println(System.currentTimeMillis() - c);
	}

	/**
	 * draws the health bars for the players
	 */
	public void drawHealthPanel()
	{
		float playerHealth = player.getHealth();
		float playerHealthRatio = playerHealth / iplayerHealth;

		float enemyHealth = enemy.getHealth();
		float enemyHealthRatio = enemyHealth / ienemyHealth;

		healthPanel.beginDraw();
		// player
		healthPanel.fill(255);
		healthPanel.rect(50, 85, 250, 15, 20, 20, 20, 20);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(50, 85, playerHealthRatio * 250, 15, 20, 20, 20, 20);
		// System.out.println(playerHealthRatio * 250);
		// System.out.println("This is player health" + player.getHealth());
		// System.out.println("This is iplayer" + iplayerHealth);
		// enemy
		healthPanel.fill(255);
		healthPanel.rect(500, 85, 250, 15, 20, 20, 20, 20);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(500, 85, enemyHealthRatio * 250, 15, 20, 20, 20, 20);

		healthPanel.endDraw();
	}

	/**
	 * draws the different clickable panels
	 */
	private void drawPanel()
	{

		panels.pushStyle();
		panels.fill(255);
		panels.rect(100, 400, 400, 150);
		panels.popStyle();

		for(int i = 0; i < 4; i++)
		{
			panels.pushStyle();
			// System.out.println("button" + i + " " + button[i].x + button[i].y + ", " +
			// button[i].width + ", " + button[i].height);
			panels.rect(button[i].x, button[i].y, button[i].width, button[i].height);
			panels.fill(0);
			String move1 = player.getMove(i).getName();
			float w = panels.textWidth(move1);
			panels.text(move1, button[i].x + button[i].width / 2 - w / 2, button[i].y + button[i].height / 2);
			panels.popStyle();
		}


		drawTextScreen();
	}

	/**
	 * draws the text that shows effect and damage of abilities to the screen
	 */
	private void drawTextScreen()
	{
		panels.pushStyle();
		panels.rect(textRect.x, textRect.y, textRect.width, textRect.height);
		// float w = panels.textWidth("Hi");
		panels.fill(0);
		// panels.text("Hi", textRect.x + textRect.width / 2 - w / 2, textRect.y +
		// textRect.height / 2);
		if(mouseHoverTest() != -1)
		{
			int hoverValue = mouseHoverTest();

			if(hoverValue == 0)
			{
				panels.text("ATK: " + player.getMove(hoverValue).getAttackval(), textRect.x + 10, textRect.y + 45);
				panels.text("EFFECT: " + player.getMove(hoverValue).getEffectName(), textRect.x + 10, textRect.y + 45 + 40);
			}

			if(hoverValue == 1)
			{
				panels.text("ATK: " + player.getMove(hoverValue).getAttackval(), textRect.x + 10, textRect.y + 45);
				panels.text("EFFECT: " + player.getMove(hoverValue).getEffectName(), textRect.x + 10, textRect.y + 45 + 40);
			}

			if(hoverValue == 2)
			{
				panels.text("ATK: " + player.getMove(hoverValue).getAttackval(), textRect.x + 10, textRect.y + 45);
				panels.text("EFFECT: " + player.getMove(hoverValue).getEffectName(), textRect.x + 10, textRect.y + 45 + 40);
			}

			if(hoverValue == 3)
			{
				panels.text("ATK: " + player.getMove(hoverValue).getAttackval(), textRect.x + 10, textRect.y + 45);
				panels.text("EFFECT: " + player.getMove(hoverValue).getEffectName(), textRect.x + 10, textRect.y + 45 + 40);
			}
		}
		panels.popStyle();
	}

	/**
	 * draws the text showing what occurred during an attack
	 */
	private void drawTextAttackScreen()
	{
		panels.pushStyle();
		panels.rect(textRect.x, textRect.y, textRect.width, textRect.height);
		panels.fill(0);

		String player = "";
		String move = "";
		String moveEffect = "";

		if(whichPlayer == 0)
		{
			player = "Player";
			move = prevPlayerMove.getName();
			moveEffect = prevPlayerMove.getEffectName();

		}
		if(whichPlayer == 1)
		{
			player = "Enemy";
			move = prevEnemyMove.getName();
			moveEffect = prevEnemyMove.getEffectName();
		}

		String total = player + " used " + move;

		// for(char e: total) {
		// System.out.print(e);
		// }

		panels.text(total, textRect.x + 15, textRect.y + 25, textRect.width, textRect.height);

		if(!moveEffect.equalsIgnoreCase("none"))
		{
			String effect = "";
			if(player.equalsIgnoreCase("player"))
			{
				effect = "Enemy" + " is " + moveEffect;
			} else
			{
				effect = "Player is " + moveEffect;
			}

			if(selfAttack)
			{
				effect = player + " " + moveEffect;
			}
			panels.text(effect, textRect.x + 15, textRect.y + 25 + 40, textRect.width, textRect.height);
		}

		panels.popStyle();
	}

	/**
	 * checks if a panel was clicked.
	 */
	private void checkpanelClick()
	{
		if(0 != panelClick) // when mouse is clicked, clears with white screen.
		{
			turnDone = true;
			timer = 1;
			prevPlayerMove = player.getMove(panelClick - 1);
			drawMove(panelClick - 1, player, enemy);
		}
	}

	/**
	 * Changes health depending
	 * 
	 * @param num
	 * @param attacker
	 * @param opponent
	 */
	private void drawMove(int num, Player attacker, Player opponent)
	{
		Moves move = attacker.getMove(num);

		if(move.getEffectName().equalsIgnoreCase("poisoned"))
		{
			poisonTurns += 3;
			poisonedPlayer = opponent;
		}

		if(move.getEffectName().equalsIgnoreCase("leeched"))
		{
			selfAttack = true;

			if(attacker == player && attacker.getHealth() + 10 <= iplayerHealth)
			{
				changeHealth(attacker, -10);
			}
			if(attacker == enemy && attacker.getHealth() + 10 <= ienemyHealth)
			{
				changeHealth(attacker, -10);
			}
		}

		if(move.getEffectName().equalsIgnoreCase("healed"))
		{
			selfAttack = true;

			if(attacker == player && attacker.getHealth() + 20 <= iplayerHealth)
			{
				changeHealth(attacker, -20);
			}
			if(attacker == enemy && attacker.getHealth() <= ienemyHealth)
			{
				changeHealth(attacker, -20);
			}
			return;
		}

		if(move.getEffectName().equalsIgnoreCase("absorbed"))
		{
			selfAttack = true;

			if(attacker == player && attacker.getHealth() != iplayerHealth)
			{
				changeHealth(attacker, (move.getAttackval() / 5) * -1);
			}
			if(attacker == enemy && attacker.getHealth() != ienemyHealth)
			{
				changeHealth(attacker, (move.getAttackval() / 5) * -1);
			}
		}

		attackScreen.beginDraw();

		boolean crit = crit(attacker.getCritrate());
		if(crit)
		{
			this.crit = true;
			changeHealth(opponent, attacker.getMove(num).getAttackval() + 10);
		} else
		{
			changeHealth(opponent, attacker.getMove(num).getAttackval());

		}
		attackScreen.endDraw();


		panelClick = 0;
	}

	private void critImage(boolean crit)
	{
		attackScreen.beginDraw();
		if(crit)
		{
			attackScreen.image(critImage, 350, 100);
		}
		attackScreen.endDraw();
	}

	/**
	 * Checks if the hit image should land on the player or enemy then draws the gif
	 * 
	 * @pre Argument only takes "player" or "enemy"
	 * @param Entity the "player" or "enemy" that will be damaged
	 */
	private void hitImage(String Entity)
	{
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
	public void mousePressed()
	{
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
	public void changeHealth(Player e, int damage)
	{
		int health = e.getHealth();

		e.setHealth(health - damage);
	}

	private boolean crit(double critChance)
	{
		double crit = Math.random();

		if(crit <= critChance)
		{
			return true;
		} else
		{
			return false;
		}
	}

	private int mouseHoverTest()
	{
		int hoverValue = -1;
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if(button[0].contains(p))
			hoverValue = 0;
		if(button[1].contains(p))
			hoverValue = 1;
		if(button[2].contains(p))
			hoverValue = 2;
		if(button[3].contains(p))
			hoverValue = 3;

		return hoverValue;
	}

	/**
	 * @return -1 if enemy is dead, 1 if player is dead, 0 if neither
	 */
	public int isDead()
	{
		if(enemy.getHealth() == 0)
			return -1;
		else if(player.getHealth() == 0)
			return 1;
		else
			return 0;
	}
}
