package veggie.screen;

public class Instructions extends Screen{
	
	private DrawingSurface surface;

	public Instructions(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}
	
	public void draw() {
		surface.text("These are the instructions", 400, 300);
	}
	
}
