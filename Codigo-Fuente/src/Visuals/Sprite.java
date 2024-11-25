package Visuals;

public class Sprite {
	
	protected String baseImagePath;
	protected String currentImagePath;
	
	public Sprite (String imagePath) {
		this.baseImagePath = imagePath;
	}
	
	public String getBaseImagePath() {
		return baseImagePath;
	}

	public void setBaseImagePath(String newImagePath) {
		this.baseImagePath = newImagePath;
	}

	public String getCurrentImagePath() {
		return currentImagePath;
	}

	public void setCurrentImagePath(String newImagePath) {
		this.currentImagePath = newImagePath;
	}
}
