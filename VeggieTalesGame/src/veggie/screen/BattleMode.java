package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import processing.core.PGraphics;
import processing.core.PImage;
import veggie.model.Move;
import veggie.model.Player;

/**
 * The Battle Screen for the game. 
 * 
 * @author williamwu
 */
public class BattleMode extends Screen
{
	private DrawingSurface surface;
	private PGraphics panels, attackScreen, healthPanel;

	private Rectangle[] button;
	private Rectangle textRect, surroundRect;
	// sets to 0 before and after each mouse click
	private int panelClick = 0;

	private Gif hitImage, critImage;

	private Player player, enemy;
	private final int playerSize = 250;
	private final int enemySize = 125;
	private int iplayerHealth, ienemyHealth;
	// 0 = player, 1 = enemy
	private int whichPlayer = 0;

	private boolean crit = false;
	private boolean selfAttack = false;
	private Move prevPlayerMove = null;
	private Move prevEnemyMove = null;
	private boolean turnDone = false;
	private PImage backimg;
	
	private boolean[] frozen = new boolean[] { false, false };
	private int frozenCounter = 0;

	private boolean[] reduce = new boolean[] { false, false };
	private int[] reduceCounter = new int[] { 0, 0 };

	
	private int timer = 1;


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
		player.changeBy(100, 200);
		enemy.changeBy(550, 200);

		this.surface = surface;

		button = new Rectangle[4];

		surroundRect = new Rectangle(0, 430, 800, 150);
		textRect = new Rectangle(surroundRect.x + 510, surroundRect.y + 10, 280, 130);
		// attacks column 1 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i] = new Rectangle(surroundRect.x + 10, surroundRect.y + 10 + 70 * i, 240, 60);
		}

		// attacks column 2 initialization
		for(int i = 0; i < 2; i++)
		{
			button[i + 2] = new Rectangle(surroundRect.x + 10 + 240 + 10, surroundRect.y + 10 + 70 * i, 240, 60);
		}
	}

	/**
	 * Sets up the BattleMode screen for gameplay
	 */
	public void setup()
	{
		
		backimg = surface.assets.get("background2");
		backimg.resize(surface.width, surface.height);


		hitImage = (Gif) surface.assets.get("hit");
		hitImage.play();

		critImage = (Gif) surface.assets.get("crit");
		critImage.play();

		panels = surface.createGraphics(800, 600);
		attackScreen = surface.createGraphics(800, surroundRect.y);
		healthPanel = surface.createGraphics(800, 300);
		// surface.createGraphics(800, 300);
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

		timer++;

		attackScreen.beginDraw();
		attackScreen.background(255);

		drawHealthPanel();
		attackScreen.image(healthPanel, 0, 0);


		if(frozen[0])
		{
			player.draw(attackScreen, "frozen", playerSize);

		} else
		{
			player.draw(attackScreen, "bounce", playerSize);
		}
		if(frozen[1])
		{
			enemy.draw(attackScreen, "frozen", enemySize);

		} else
		{
			enemy.draw(attackScreen, "bounce", enemySize);
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


			if(whichPlayer == 0)
			{
				if(frozen[1])
				{
					enemy.draw(attackScreen, "frozen", enemySize);

				} else
				{
					enemy.draw(attackScreen, "hurt", enemySize);
				}
				player.draw(attackScreen, "attack", playerSize);
				hitImage("enemy");
				critImage(crit);
			}

			if(whichPlayer == 1)
			{
				if(frozen[0])
				{
					player.draw(attackScreen, "frozen", playerSize);

				} else
				{
					player.draw(attackScreen, "hurt", playerSize);
				}
				enemy.draw(attackScreen, "attack", enemySize);// !@$!@$ CHANGE TO ATTACK LATER!! !@$!@#$(!@$
				hitImage("player");
				critImage(crit);
			}

			if(timer % 80 == 0)
			{
				turnDone = false;
				if(whichPlayer == 0)
				{
					whichPlayer = 1;
				} else
				{
					whichPlayer = 0;
				}

				if(frozenCounter > 0)
				{
					frozen[0] = false;
					frozen[1] = false;
					frozenCounter = 0;
				}

				crit = false;
				selfAttack = false;

			}

		}

		surface.image(panels, 0, 0);


		if(whichPlayer == 0 & timer % 80 == 0)
		{
			if(reduce[1])
			{
				reduceCounter[1]++;
			}

			if(!frozen[0])
			{

				checkpanelClick();
			}

			if(frozen[0] && !frozen[1])
			{
				frozenCounter++;
				whichPlayer = 1;
			}
		}

		if(whichPlayer == 1 && timer % 130 == 0)
		{
			if(reduce[0])
			{
				reduceCounter[0]++;
			}

			if(!frozen[1])
			{
				int move = (int) (Math.random() * 4);
				prevEnemyMove = enemy.getMove(move);
				drawMove(move, enemy, player, 1);
				turnDone = true;
				timer = 1;
			}

			if(frozen[1] && !frozen[0])
			{
				frozenCounter++;
				whichPlayer = 0;
			}
		}

		if(timer % 80 == 0)
		{
			if(isDead() == -1)
			{
				player.resetHealth();
				player.moveTo(surface.width/2, 100);
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
	 * draws the different panel that can be clicked by the player
	 */
	private void drawPanel()
	{
		panels.pushStyle();
		panels.fill(0);
		panels.rect(surroundRect.x, surroundRect.y, surroundRect.width, surroundRect.height);
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
				effect = "Enemy" + " is " + moveEffect + "ed";
			} else
			{
				effect = "Player is " + moveEffect + "ed";
			}

			if(selfAttack)
			{
				effect = player + " " + moveEffect + "ed";
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
			drawMove(panelClick - 1, player, enemy, 0);
		}
	}
	
	/**
	 * Checks the attack move has an effect leech and changes health as a result
	 * @param attacker the attacking character
	 * @param opponent the defending character
	 * @param move the move that is being used
	 */
	private void ifLeech(Player attacker, Player opponent, Move move)
	{
		if(move.getEffectName().equalsIgnoreCase("leech"))
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
	}

	/**
	 * Checks if the attack move has a heal and changes health as a result
	 * @param attacker the attacking character
	 * @param opponent the defending character
	 * @param move the move that is being used
	 */
	private void ifHeal(Player attacker, Player opponent, Move move)
	{
		if(move.getEffectName().equalsIgnoreCase("heal"))
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
	}

	/**
	 * Checks if a attack move has a reduce status effect and changes the reduce field to true to reflect this.
	 * @param attacker the attacking character
	 * @param opponent the defending character
	 * @param move the move that is being used
	 * @param whichPlayer the integer for which 0 is the player and 1 is the enemy
	 */
	private void ifReduce(Player attacker, Player opponent, Move move, int whichPlayer)
	{
		if(move.getEffectName().equalsIgnoreCase("reduce"))
		{
			selfAttack = true;

			if(whichPlayer == 0)
			{
				reduce[0] = true;
			} else
			{
				reduce[1] = true;
			}
		}
	}

	/**
	 * Checks if a attack move has a frozen status effect and changes the frozen field to true to reflect this.
	 * @param attacker the attacking character
	 * @param opponent the defending character
	 * @param move the move that is being used
	 * @param whichPlayer the integer for which 0 is the player and 1 is the enemy
	 */
	private void ifFrozen(Player attacker, Player opponent, Move move, int whichPlayer)
	{
		if(move.getEffectName().equalsIgnoreCase("frozen"))
		{
			if(whichPlayer == 0)
			{
				frozen[1] = true;
			} else
			{
				frozen[0] = true;
			}
		}
	}

	/**
	 * Changes health based on attack value of a move and checks for different status effects
	 * 
	 * @param num the value of the key to a certain move out of 4 for the attacker
	 * @param attacker  the attacking character
	 * @param opponent the defending character
	 * @param whichPlayer the integer for which 0 is the player and 1 is the enemy
	 */
	private void drawMove(int num, Player attacker, Player opponent, int whichPlayer)
	{
		Move move = attacker.getMove(num);

		attackScreen.beginDraw();
		double reducePercent = 1;
		if(whichPlayer == 0 && reduce[1] && reduceCounter[1] > 0)
		{
			reducePercent = 0.6;
			reduce[1] = false;
			reduceCounter[1] = 0;
		}
		if(whichPlayer == 1 && reduce[0] && reduceCounter[0] > 0)
		{
			reducePercent = 0.6;
			reduce[0] = false;
			reduceCounter[0] = 0;
		}

		boolean crit = crit(attacker.getCritrate());
		if(crit)
		{
			this.crit = true;
			changeHealth(opponent, (int) ((attacker.getMove(num).getAttackval() + 10) * reducePercent));
		} else
		{
			changeHealth(opponent, (int) ((attacker.getMove(num).getAttackval()) * reducePercent));
		}
		attackScreen.endDraw();

		ifLeech(attacker, opponent, move);
		ifHeal(attacker, opponent, move);
		ifFrozen(attacker, opponent, move, whichPlayer);
		ifReduce(attacker, opponent, move, whichPlayer);


		panelClick = 0;
	}

	/**
	 * Draws the critical hit image. 
	 * @param crit the parameter that indicates if there was a critical hit
	 */
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
	 * Checks if the hit image should land on the player or enemy then draws the gif.
	 * Argument only takes "player" or "enemy".
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

	/**
	 * calculates the critical hit chance
	 * @param critChance
	 * @return
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

	/**
	 * checks if a mouse is hovering over the attack panels
	 * @return
	 */
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
