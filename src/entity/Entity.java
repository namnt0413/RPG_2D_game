package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public double worldX, worldY;
	public double speed;
	
	public BufferedImage image,image2,image3;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction = "down";
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	
	public Rectangle solidArea = new Rectangle(0,0,48,48); // fix the player bounder
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false; // check collision
	String dialogues[] = new String[20];
	
	//STATE
	public boolean invincible = false;
	public int invincibleCounter = 0;
	int dialogueIndex = 0;
	public boolean collision = false;
	boolean attacking = false;
		
	// CHARACTER STATUS
	public String name;
	public int maxLife;
	public int life;
	public int type;	// 0 = player , 1 = npc, 2 = monster

	//COUNTER
	public int actionLockCounter = 0;
	public int spriteCounter = 0;// character walk
	public int spriteNum = 1;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
	
	public void setAction() {}
	public void speak() {
		if( dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this,false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster); 
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if( this.type ==2 && contactPlayer == true) {
			if( gp.player.invincible == false) {
				gp.player.life -= 1;
				gp.player.invincible = true;
			}
		}
		
		//if collision is false, player can move
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if( invincible == true ) {
			invincibleCounter++;
			if( invincibleCounter >40 ) {
				invincible = false;
				invincibleCounter = 0;
			}
			
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		double screenX = worldX - gp.player.worldX + gp.player.screenX;	// +gp.player.screenX: vi camera luon o chinh giua
		double screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if ( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			 worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			 worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			 worldY - gp.tileSize <	 gp.player.worldY + gp.player.screenY ) {
			
			switch(direction) {
			case "up":
				if(spriteNum ==1 ) {
					image = up1;
				}
				if(spriteNum ==2) {
					image = up2;
				}
				break;

			case "down":
				if(spriteNum ==1 ) {
					image = down1;
				}
				if(spriteNum ==2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum ==1 ) {
					image = left1;
				}
				if(spriteNum ==2) {
					image = left2;
				}
				break;
				
			case "right":
				if(spriteNum ==1 ) {
					image = right1;
				}
				if(spriteNum ==2) {
					image = right2;
				}
				break;
		
			}	//end switch
			
			if( invincible ==true ) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			}
			g2.drawImage(image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
			
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try	{
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image, width, height);
			
		} catch(IOException e) {
				e.printStackTrace();
		} 
		return image;
	}
	
}
