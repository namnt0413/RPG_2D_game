package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	protected GamePanel gp;
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
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;	//bat khi player tan cong quai
	public boolean destructible = false;
		
	// CHARACTER ATTRIBUTE
	public String name;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int ammo;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int size; //part 33
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	// ITEM ATTRIBUTE
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;

	//COUNTER
	public int actionLockCounter = 0;
	public int spriteCounter = 0;// character walk
	public int spriteNum = 1;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	public int shotAvaiableCounter = 0; // kiem soat so lan ban
	
	// TYPE IN GAME
	public int type;	// 0 = player , 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickupOnly = 7;
	
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
	public void damageReaction() {}
	public void use(Entity entity) {}	// overried in player class
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

	public void checkDrop() {
		
		
	}
	
	public void dropItem( Entity droppedItem ) {
		
		for( int i = 0; i < gp.obj.length ; i++) {
			if( gp.obj[i] == null ){
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;		
				break; 
			}
			
		}
		
	}
	
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp,target,color,size,(int)speed,maxLife,-2,-1); 
		Particle p2 = new Particle(gp,target,color,size,(int)speed,maxLife,2,-1); 
		Particle p3 = new Particle(gp,target,color,size,(int)speed,maxLife,-2,1); 
		Particle p4 = new Particle(gp,target,color,size,(int)speed,maxLife,2,1); 

		
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);

	}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this,false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster); 
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if( this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
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
		
		if( shotAvaiableCounter < 30 ) {
			shotAvaiableCounter++;
			
		}
		
	}
	
	public void damagePlayer( int attack ) {
		if( gp.player.invincible == false) {
			
			int damage = attack - gp.player.defense; 
			if( damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			gp.player.invincible = true;
			gp.playSE(6);
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
			
			// MONSTER HP BAR
			if( type == 2 && hpBarOn == true ) {
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect((int)screenX -1 , (int)screenY - 16, gp.tileSize + 2, 12);
				
				g2.setColor(new Color(255,0,30));
				g2.fillRect((int)screenX, (int)screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				if( hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}

			if( invincible ==true ) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2,0.4F);

			}
			if( dying == true) {
				dyingAnimation(g2);
				
			}
			
			g2.drawImage(image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
			changeAlpha(g2,1F);
		}
			
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		//int i = 5;
		if( dyingCounter <= 5 ) {
			changeAlpha(g2,0f);	
		}
		if( dyingCounter > 5 && dyingCounter <= 10 ) {
			changeAlpha(g2,1f);	
		}
		if( dyingCounter > 10 && dyingCounter <= 15) {
			changeAlpha(g2,0f);		
		}
		if( dyingCounter > 15 && dyingCounter <= 20 ) {
			changeAlpha(g2,1f);	
		}
		if( dyingCounter > 20 && dyingCounter <= 25) {
			changeAlpha(g2,0f);	
		}
		if( dyingCounter > 25 && dyingCounter <= 30 ) {
			changeAlpha(g2,1f);	
		}
		if( dyingCounter > 30 && dyingCounter <= 35 ) {
			changeAlpha(g2,0f);	
		}
		if( dyingCounter > 35 && dyingCounter <= 40 ) {
			changeAlpha(g2,1f);	
		}
		if( dyingCounter > 40 ) {
//			dying = false;
			alive = false;
		}
	}
	
	public void changeAlpha( Graphics2D g2, float alphaValue) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
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
