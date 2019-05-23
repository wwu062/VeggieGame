package veggie.screen;

public abstract class Screen {
	/**
	 * the size of the screen being drawn in width and height
	 */
	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	/**
	 * initializes the fields
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	public abstract void setup();
	
	public abstract void draw();
	
	public void mousePressed() {
		
	}
	
	/**
	 * records the mouse the mouse being moved
	 */
	public void mouseMoved() {
		
	}
	
	/**
	 * records the mouse being dragged 
	 */
	public void mouseDragged() {
		
	}
	
	/**
	 * records the mouse being released
	 */
	public void mouseReleased() {
		
	}
}
	
