package Entities;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObjectDynamic extends GameObject {
	
	protected boolean exist; // còn sống 
	private Direction ObjectDricetion; // hướng di chuyển

	

	public enum Direction{
		UP,DOWN,RIGHT,LEFT
	}
	
	public GameObjectDynamic(int x, int y) {
		super(x, y);
		setExist(true);
		// TODO Auto-generated constructor stub
	}



	public boolean getExist() {
		return exist;
	}

	public void setExist(boolean x) {
		this.exist = x;
	}

	public Rectangle getBounds() { // khung bao quanh hình ảnh --> va chạm
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public Direction getObjectDricetion() {
		return ObjectDricetion;
	}

	public void setObjectDricetion(Direction objectDricetion) {
		ObjectDricetion = objectDricetion;
	};
}
