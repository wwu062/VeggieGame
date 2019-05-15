README
Alex Wang and William Wu


—Header—
Lettuce RPG Platformer


— Introduction—


You are lettuce. Seemingly nothing special, just an ordinary piece of lettuce. However, you don’t want to stick with the crowd; you want to show that you are not the same as everyone else. You want to prove that you are the veggie king. In an effort to do so, you will have to trek across entire supermarkets, and fight a variety of vegetables and vegan foods until you finally arrive at the palace of the Veggie burger king. As lettuce, you will do this through a platform system where you have to survive until you reach the bosses. At a boss battle, you will have to utilize a turn based system with different movies to ultimately win. And only by defeating the veggie burger will you show that you are the best lettuce, and the true veggie king.


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
* veggie.model
   * Entity: the player or enemy object 
   * Moves: an attack move that an Entity object can have
   * MovingImage: a shape that adheres to physics such as gravity, consistent with platformers
   * PlayerController: moves the Entity object around
   * Stats: the stats that an Entity object can have
* veggie.textReader
   * FileIO: reads in a text file and translates it into different Move objects.


—Responsibilities list—


* Alex
   * MovingImage
   * PlayerController
   * PlatformMode
   * Main
   * Stats
   
  
* William
   * Entity
   * Moves
   * Instructions
   * Menu
   * DrawingSurface
   * FileIO
   * BattleMode
   * Gifs, Images, Textures


— Resources — 


* Shelby Screen Switching Demo
* AnimationDemo
* Used to implement gifs correctly into processing : http://www.science.smith.edu/dftwiki/index.php/Processing_Tutorial_--_Showing_Animated_Gifs