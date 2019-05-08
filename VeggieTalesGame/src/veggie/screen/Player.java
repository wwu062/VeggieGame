package veggie.screen;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

public class Player extends MovingImage {
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;
	
	private int velX;
	private int velY;

	public Player(int x, int y, int width, int height, PImage image) {
		super(x, y, width, height, image);
		velX = 0;
		velY = 0;
	}
	
	public Player(PImage image, int x, int y) {
		super(x, y, image.width, image.height, image);
		velX = 0;
		velY = 0;
		System.out.println("hello");
	}
	
	public void walk(int dir) {
		velX += dir;
	}
	
	private void fall(ArrayList<Shape> platform) {
		for(Shape s : platform) {
			if(contains(s)) {
				velY = 0;
				break;
			} else {
				velY++;
			}
		}
	}
	
	public void jump() {
		velY--;
	}
	
	public void act(ArrayList<Shape> platform) {
		fall(platform);
		moveBy(velX, velY);
	}
	
}
