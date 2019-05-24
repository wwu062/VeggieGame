package veggie.model;

import veggie.screen.DrawingSurface;

public class Moves
{

	/**
	 * The name of the Move
	 */
	private String name;

	/**
	 * the attack value of the Move
	 */
	private int attackval;

	private String effectName;


	public Moves(String name, int attackval, String effect)
	{
		this.name = name;
		this.attackval = attackval;
		this.effectName = effect;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public String getEffectName()
	{
		return effectName;
	}


	/**
	 * @return the attackval
	 */
	public int getAttackval()
	{
		return attackval;
	}
	
	public void draw(DrawingSurface marker, float x, float y, int radius) {
		marker.ellipse(x, y, radius*2, radius*2);
	}
	
	public void randomDraw(DrawingSurface marker, int width, int height) {
		float x = (float)(Math.random()*width);
		float y = (float)(Math.random()*height);
		this.draw(marker, x, y, 20);
	}
}
