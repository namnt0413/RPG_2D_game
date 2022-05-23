package entity;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasKey = 0;
	public boolean attackCanceled = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp); // call from super class
		
		this.keyH = keyH;

		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(); // x,y,width,height
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = gp.maxWorldWidth/600; //2400/600
//		speed = 4;
		direction = "down";
		
		//PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;	// the more strength , the damage has
		dexterity = 1;	// the more dexterity , the less damage received
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		attack = getAttack();
		defense = getDefense();
		
	}
	
	public int getAttack(){
		return attack = strength * currentWeapon.attackValue;	
	}

	public int getDefense(){
		return defense = strength * currentShield.defenseValue;	
	}
	
	public void getPlayerImage() {	// lay anh = thu vien anh java
			
		up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
		
		
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
		attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
		attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
		attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
		attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
		
	}
	
	public void update() {	//update position
		
		if( attacking ==true ) {
			attacking();
		}
		else if(keyH.upPressed ==true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
			|| keyH.enterPressed == true ) { 
			// Phai nhan 1 phim bat ky de lot vao ham nay. Neu khong co ham if nay thi se tu dong di thang xuong duoi
			
			if( keyH.upPressed == true) {
				direction = "up";
			} 
			else if( keyH.downPressed == true) {
				direction = "down";
			}
			else if( keyH.leftPressed == true) {
				direction = "left";
			}
			else if( keyH.rightPressed == true) {
				direction = "right";
			}
			
			//Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// check npc collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// check event
			gp.eHandler.checkEvent();
			
			//if collision is false, player can move
			if(collisionOn == false && keyH.enterPressed == false) {
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
			
			if( keyH.enterPressed == true && attackCanceled == false ) {
//				gp.playSE(7);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
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
			
		}
		
		//this needs to be outside of key if statement
		if( invincible == true ) {
			invincibleCounter++;
			if( invincibleCounter >60 ) {
				invincible = false;
				invincibleCounter = 0;
			}
			
		}

			
	}
	
	public void attacking() {
		spriteCounter++;
		
		if( spriteCounter <= 5) {
			spriteNum = 1;
		}
		if( spriteCounter >5 && spriteCounter <= 25) {
			spriteNum =2;
			
			// Save the current worldx, y , solid Area
			int currentWorldX = (int)worldX ;
			int currentWorldY = (int)worldY ;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//adjust player's worldx/y for solid area
			switch(direction) {
				case "up":		worldY -= attackArea.height;break;
				case "down":	worldY += attackArea.height;break;
				case "left":	worldX -= attackArea.width;break;
				case "right":	worldX += attackArea.width;break;
			} 			
			//attack area become solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//check monster collision with world x/y
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			//after collision restore origin data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if( spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	
	public void pickUpObject(int i) {
		if( i != 999 ) {
			
/*			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);	//play sound
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key !");
				break;
			case "Door":
				if(hasKey>0) {
					gp.playSE(3);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("You opened the door !");
				} else {
					gp.ui.showMessage("You need a key !");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed+=1;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up !");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				gp.ui.showMessage("Congratulations !");
				break;	
			}//end switch
*/		
		}
		
	}
	
	
	public void interactNPC(int i) {
		
	if( gp.keyH.enterPressed ==true ) {
		if( i != 999) {	// player touching npc
			attackCanceled = true;
			gp.gameState = gp.dialogueState;
			gp.npc[i].speak();
		}
		
	}	
}
		
//		gp.keyH.enterPressed = false;

	
	public void contactMonster( int i) {
		if( i!= 999 ) {
			
			if( invincible == false) {
				
				int damage = gp.monster[i].attack - defense;
				if( damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
				 gp.playSE(6);
			}
		
		}
	}
	
	public void damageMonster( int i) {
		if( i != 999) {
			if( gp.monster[i].invincible == false ) {
				
				int damage = attack - gp.monster[i].defense;
				if( damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage+"damage!");
				
				gp.monster[i].invincible = true;
				 gp.playSE(5);
				 gp.monster[i].damageReaction(); 
				 
	
				if( gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("You killed the" + gp.monster[i].name );
					gp.ui.addMessage("Exp + " + gp.monster[i].exp);
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		if( exp >= nextLevelExp ) {
			level ++;
			nextLevelExp = nextLevelExp *2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();// recalculate
			defense = getDefense();
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are up to level " + level +"now!\n" ;
		}
		
		
	}	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
			case "up":
				if( attacking == false) {
					if(spriteNum ==1 ) {
						image = up1;
					}
					if(spriteNum ==2) {
						image = up2;
					}
				}
				if(attacking == true) {
					tempScreenY = screenY - gp.tileSize;
					if(spriteNum ==1 ) {
						image = attackUp1;
					}
					if(spriteNum ==2) {
						image = attackUp2;
					}
				}
				break;

			case "down":
				if( attacking == false) {
					if(spriteNum ==1 ) {
						image = down1;
					}
					if(spriteNum ==2) {
						image = down2;
					}
				}
				if(attacking == true) {
					if(spriteNum ==1 ) {
						image = attackDown1;
					}
					if(spriteNum ==2) {
						image = attackDown2;
					}
				}
				break;
			case "left":
				if( attacking == false) {
					if(spriteNum ==1 ) {
						image = left1;
					}
					if(spriteNum ==2) {
						image = left2;
					}
				}
				if(attacking == true) {
					tempScreenX = screenX - gp.tileSize;
					if(spriteNum ==1 ) {
						image = attackLeft1;
					}
					if(spriteNum ==2) {
						image = attackLeft2;
					}
				}
				break;
				
			case "right":
				if( attacking == false) {
					if(spriteNum ==1 ) {
						image = right1;
					}
					if(spriteNum ==2) {
						image = right2;
					}
				}
				if(attacking == true) {
					if(spriteNum ==1 ) {
						image = attackRight1;
					}
					if(spriteNum ==2) {
						image = attackRight2;
					}
				}
				break;
		
		}
			
		// Lam mo nhan vat khi bi quai tan cong
			if( invincible ==true ) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			}
		
			g2.drawImage(image, tempScreenX, tempScreenY, null);
			
			//RESET ALPHA
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));			
	
			//DEBUG when collision with monster
//			g2.setFont(new Font("Arial",Font.PLAIN,26));
//			g2.setColor(Color.white);
//			g2.drawString("Invincible:"+invincibleCounter, 10, 400);
	}
	
	
	
}
