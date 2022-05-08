package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public double worldX, worldY;
	public double speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;// character walk
	public int spriteNum = 1;
	public Rectangle solidArea; // fix the player bounder
	public boolean collisionOn = false;
}
