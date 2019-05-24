package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import processing.core.PGraphics;
import veggie.model.Moves;
import veggie.model.PlayerManager;

/**
 * The Battle Screen for the game. In this mode, there are 4 buttons at the
 * bottom of the screen that are the player's attack moves. Once they
 * 
 * @author williamwu
 */
public class BattleMode extends Screen
{
	private PlayerManager player, enemy;

	private int iplayerHealth, ienemyHealth;

	private DrawingSurface surface;

	private Rectangle[] button;

	private Rectangle textRect;

	private Gif hitImage, critImage;

	private int timer = 1;

	// 0 = player, 1 = enemy
	private int whichPlayer = 0;

	private boolean turnDone = false;

	// graphics for the panel, initial state of the players, and final state during
	// a move
	private PGraphics panels, attackScreen, healthPanel;

	// keeps check of if it is the players turn or the enemy's
	private int turnCounter = 0;

	// sets to 0 before and after each mouse click
	private int panelClick = 0;

	// private boolean poisoned = false;
	private int poisonTurnCounter = 0;
	private PlayerManager poisonedPlayer = null;


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
		player.getController().changeBy(125, 200);
		enemy.getController().changeBy(550, 200);

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

		textRect = new Rectangle(500, 400, 200, 150);

	}

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
	 * 
	 * @post changes background color
	 */
	public void draw()
	{
		
		panels.beginDraw();
		panels.textFont(surface.font);
		drawPanel();
		drawPanel(); // TEXT ONLY CENTERS IF I CALL IT TWICE ?
		panels.endDraw();
		surface.image(panels, 0, 0);

		// textScreen.beginDraw();
		// drawTextScreen();
		// textScreen.endDraw();
		// panels.image(textScreen, 0, 0);
		// surface.image(panels, 0, 0);

		// System.out.println("This is the ienemy: " + ienemyHealth);
		// System.out.println("This is the getStat health" +
		// enemy.getBattler().getHealth());

		timer++;

		attackScreen.beginDraw();
		attackScreen.background(255);

		drawHealthPanel();
		attackScreen.image(healthPanel, 0, 0);


		if(player.getBattler().getHealth() <= iplayerHealth / 2)
		{
			player.getController().draw(attackScreen, "hurt");
		} else
		{
			player.getController().draw(attackScreen, "bounce");
		}

		if(enemy.getBattler().getHealth() <= ienemyHealth / 2)
		{
			enemy.getController().draw(attackScreen, "hurt");
		} else
		{
			enemy.getController().draw(attackScreen, "bounce");
		}

		// System.out.println(enemy.getBattler().getHealth());
		long c = System.currentTimeMillis();


		if(turnDone)
		{
			attackScreen.beginDraw();
			attackScreen.background(255);
			attackScreen.image(healthPanel, 0, 0);
			attackScreen.endDraw();

			if(poisonTurnCounter > 0)
			{
				changeHealth(poisonedPlayer, 10);
				poisonTurnCounter--;
				// System.out.println("poisoned" + turnCounter);
			}

			if(whichPlayer == 0)
			{
				// System.out.println("hit");
				if(enemy.getBattler().getHealth() <= ienemyHealth / 2)
				{
					enemy.getController().draw(attackScreen, "hurt");
				} else
				{
					enemy.getController().draw(attackScreen, "bounce");
				}
				player.getController().draw(attackScreen, "attack");
				hitImage("enemy");
			}

			if(whichPlayer == 1)
			{
				if(player.getBattler().getHealth() <= iplayerHealth / 2)
				{
					player.getController().draw(attackScreen, "hurt");
				} else
				{
					player.getController().draw(attackScreen, "bounce");
				}
				enemy.getController().draw(attackScreen, "attack");// !@$!@$ CHANGE TO ATTACK LATER!! !@$!@#$(!@$
				hitImage("player");
			}

			if(timer % 10 == 0)
			{
				turnDone = false;
				turnCounter++;
			}

		}


		if(turnCounter % 2 == 0 & timer % 10 == 0)
		{
			checkpanelClick();
			whichPlayer = 0;
		}

		if(turnCounter % 2 != 0 && timer % 10 == 0)
		{
			int move = (int) (Math.random() * 4);
			drawMove(move, enemy, player);


			turnDone = true;
			timer = 1;
			whichPlayer = 1;
		}

		if(checkDead() == 1 || checkDead() == -1)
		{
			// System.out.println("is dead");
			surface.switchScreen(2);
		}

		attackScreen.endDraw();

		surface.image(attackScreen, 0, 0);

		System.out.println(System.currentTimeMillis() - c);
	}

	public void drawHealthPanel()
	{
		float playerHealth = player.getBattler().getHealth();
		float playerHealthRatio = playerHealth / iplayerHealth;

		float enemyHealth = enemy.getBattler().getHealth();
		float enemyHealthRatio = enemyHealth / ienemyHealth;

		healthPanel.beginDraw();
		// player
		healthPanel.fill(255);
		healthPanel.rect(50, 85, 250, 15, 20, 20, 20, 20);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(50, 85, playerHealthRatio * 250, 15, 20, 20, 20, 20);
		System.out.println(playerHealthRatio * 250);
		System.out.println("This is player health" + player.getBattler().getHealth());
		System.out.println("This is iplayer" + iplayerHealth);
		// enemy
		healthPanel.fill(255);
		healthPanel.rect(500, 85, 250, 15, 20, 20, 20, 20);
		healthPanel.fill(255, 0, 0);
		healthPanel.rect(500, 85, enemyHealthRatio * 250, 15, 20, 20, 20, 20);

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
		drawTextScreen();
	}

	private void drawTextScreen()
	{
		panels.pushStyle();
		panels.rect(textRect.x, textRect.y, textRect.width, textRect.height);
		float w = panels.textWidth("Hi");
		panels.fill(0);
		//panels.text("Hi", textRect.x + textRect.width / 2 - w / 2, textRect.y + textRect.height / 2);
		if(mouseHoverTest() != -1)
		{
			int hoverValue = mouseHoverTest();
			
			if(hoverValue == 0)
			{
				panels.text("ATK: " + player.getMoveList()[hoverValue].getAttackval(), textRect.x + 25, textRect.y + 45);
				panels.text("EFFECT: " + player.getMoveList()[hoverValue].getEffectName(), textRect.x + 25, textRect.y + 45 + 40);
			}
			
			if(hoverValue == 1)
			{
				panels.text("ATK: " + player.getMoveList()[hoverValue].getAttackval(), textRect.x + 25, textRect.y + 45);
				panels.text("EFFECT: " + player.getMoveList()[hoverValue].getEffectName(), textRect.x + 25, textRect.y + 45 + 40);
			}
			
			if(hoverValue == 2)
			{
				panels.text("ATK: " + player.getMoveList()[hoverValue].getAttackval(), textRect.x + 25, textRect.y + 45);
				panels.text("EFFECT: " + player.getMoveList()[hoverValue].getEffectName(), textRect.x + 25, textRect.y + 45 + 40);
			}
			
			if(hoverValue == 3)
			{
				panels.text("ATK: " + player.getMoveList()[hoverValue].getAttackval(), textRect.x + 25, textRect.y + 45);
				panels.text("EFFECT: " + player.getMoveList()[hoverValue].getEffectName(), textRect.x + 25, textRect.y + 45 + 40);
			}
		}
		panels.popStyle();
	}

	private void checkpanelClick()
	{
		if(0 != panelClick) // when mouse is clicked, clears with white screen.
		{
			turnDone = true;
			timer = 1;
			drawMove(panelClick - 1, player, enemy);
		}
	}

	// the entity that is being damaged
	private void drawMove(int num, PlayerManager attacker, PlayerManager opponent)
	{
		Moves move = attacker.getMoveList()[num];

		if(move.getEffectName().equalsIgnoreCase("poison"))
		{
			poisonTurnCounter += 3;
			poisonedPlayer = opponent;
		}

		if(move.getEffectName().equalsIgnoreCase("leech"))
		{
			if(attacker == player && attacker.getBattler().getHealth() != iplayerHealth)
			{
				changeHealth(attacker, -10);
			}
			if(attacker == enemy && attacker.getBattler().getHealth() != ienemyHealth)
			{
				changeHealth(attacker, -10);
			}
		}

		if(move.getEffectName().equalsIgnoreCase("heal"))
		{
			if(attacker == player && attacker.getBattler().getHealth() != iplayerHealth)
			{
				changeHealth(attacker, -20);
			}
			if(attacker == enemy && attacker.getBattler().getHealth() != ienemyHealth)
			{
				changeHealth(attacker, -20);
			}
			return;
		}

		if(move.getEffectName().equalsIgnoreCase("absorb"))
		{
			if(attacker == player && attacker.getBattler().getHealth() != iplayerHealth)
			{
				changeHealth(attacker, (move.getAttackval() / 5) * -1);
			}
			if(attacker == enemy && attacker.getBattler().getHealth() != ienemyHealth)
			{
				changeHealth(attacker, (move.getAttackval() / 5) * -1);
			}
		}

		boolean crit = crit(attacker.getBattler().getCritrate());
		if(crit)
		{
			changeHealth(opponent, attacker.getMoveList()[num].getAttackval() + 10);
		} else
		{
			changeHealth(opponent, attacker.getMoveList()[num].getAttackval());
		}

		panelClick = 0;
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
	public void changeHealth(PlayerManager e, int damage)
	{
		int health = e.getBattler().getHealth();

		e.getBattler().setHealth(health - damage);
	}

	/**
	 * returns whether or not there is a successful critical hit on the opponent
	 * 
	 * @param critChance the critical hit rate of the attacking entity
	 * @return true if there was a critical, else false.
	 */
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

	public int checkDead()
	{
		if(enemy.getBattler().getHealth() == 0)
			return -1;
		else if(player.getBattler().getHealth() == 0)
			return 1;
		else
			return 0;
	}
}
