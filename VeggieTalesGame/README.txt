README
Alex Wang and William Wu


—Header—
Lettuce RPG Platformer


— Introduction—


Your a piece of lettuce on the run, getting chased by tomatoes and burgers. You die when you hit a tomato or a burger. At some point, you will need to fight your way through burgers and tomatoes via an rpg-style turn-based battle system. Going off the screen will kill you if you go too far. Your goal is to survive as long as possible. You can pick up red boxes to swap out your moves.
It's a risk, though, as the moves may not be better and are randomly swapped.


— Instructions—


Move around and interact with NPCs. Initiate battles with enemies to gain experience. Use a battle system similar to many other RPG turn-based games. Select abilities to use that ability for that turn. You can swap out abilities as you level up. Your ultimate goal is to defeat all bosses.


Platform mode: 
*  WASD - used to move up, left, down, and right, respectively. Space also moves up
Battle mode:
* WASD - choose abilities to use against the opponent
* Enter - use ability


— Feature List—


Must have features:
* Menu with play button and instructions
* Player has health bar and can win or die
* Two dimensional graphics with basic symbols
* Turn based attack system
* Players play against a bot
* Movement system while not in battle
* Platformer with physics
Want to have features:
* Item system used for stat boosting
* Potions, etc.
* Dialogue boxes similar to those in RPG games
* NPCs to talk and trade with using the item system
* NPC accessibility changes with progress
* Add a twist to battle system
   * Set up grid positioning
   * Clicking to see who wins with certain moves
* Multiscreen
* Different bots with different difficulties
* Swap out abilities as player levels up
Stretch features:
* A difficulty level system
* Add additional characters to party
* Multiplayer mode
* 3D environment instead of 2D grid for movement and for battle
* Real-time instead of turn-based
* Music incorporated into game
* Sidescrolling map
* Multiple levels


— Class List—
* veggie.screen
   * Screen: abstract class that sets up the drawing width and height along with methods
   * ScreenSwitcher: interface that switches between different screens
   * BattleMode: class that draws the battle between player and the enemy to the screen
   * PlatformMode: draws the platforming part of the game to the screen
   * Menu: draws the menu of the game to the screen
   * Instructions: draws the instructions of the game to the screen 
   * DrawingSurface: draws the different screens.
   * Main: starts the program
   * GameOver: game over screen when you lose
* veggie.model
   * Moves: an attack move that an Entity object can have
   * MovingImage: a shape that adheres to physics such as gravity, consistent with platformers
   * Player: models the player object
   * Stats: the stats that an Entity object can have
* veggie.textReader
   * FileIO: reads in a text file and translates it into different Move objects.


—Responsibilities list—


* Alex
   * The Platforming part of the game
	   * MovingImage
	   * PlayerController
	   * PlatformMode
	   * Main
	   * Stats
   
  
* William
   * The Battle part of the game 
	   * Entity
	   * Moves
	   * Instructions
	   * Menu
	   * DrawingSurface
	   * FileIO, moveList.txt
	   * BattleMode
	   * Gifs, Images, Textures


— Resources — 

* block.png = https://lh3.googleusercontent.com/eT33UQU7wHS0fx19MgjXGBOfbRTMLNBAfH5RZhYrd6IcYAU71xbJ0H72LgVsFJ8kH9zVgTlE7UaAcjMQpa_-MBw=s400
* clouds.png = http://tse3.mm.bing.net/th?id=OIP.4_5G3toRzBjrgizBzuvDfAHaFj
* Shelby Screen Switching Demo
* 
* AnimationDemo