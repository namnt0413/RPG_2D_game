package Entities;

public class StaticEntity {
	private boolean collision;
	private GameObject graphics;
	private String name;
	
	
	public StaticEntity(GameObject graphics,String name, boolean collision) {
		this.collision = collision;
		this.graphics = graphics;
		this.setName(name);
	}
	public boolean isCollision() {
		return collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	public GameObject getGraphics() {
		return graphics;
	}
	public void setGraphics(GameObject graphics) {
		this.graphics = graphics;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
