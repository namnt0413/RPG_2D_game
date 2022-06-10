package entity;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasKey = 0;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int maxInventorySize = 20;
	
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
		
		// ATTACK AREA
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		setDefaultValues();
		setItems();
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
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
		
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		
	}
		
	public int getAttack(){
		attackArea = currentWeapon.attackArea;
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
		
		if( currentWeapon.type == type_sword ) {
			attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
		}
		if( currentWeapon.type == type_axe ) {
			attackUp1 = setup("/player/boy_axe_up_1",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2",gp.tileSize*2,gp.tileSize);
		}

		
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
			
			// check interactive collision
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			
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
		
		if( gp.keyH.shotKeyPressed == true && projectile.alive == false 
			  && shotAvaiableCounter == 60 && projectile.haveResource(this) == true ) {
			//Set default coordinate, direction and user
			projectile.set( (int)worldX , (int)worldY, direction, true, this );	
			
			//SUBTRACT THE COST( mana, armor)
			projectile.subtractResource(this);
			
			//add it to array list
			gp.projectileList.add(projectile);
			shotAvaiableCounter = 0;	// 2 lan ban lien tiep phai tu 30 frame tro len 
			
			gp.playSE(10);
		}
		
		//this needs to be outside of key if statement
		if( invincible == true ) {
			invincibleCounter++;
			if( invincibleCounter >60 ) {
				invincible = false;
				invincibleCounter = 0;
			}	
		}
		
		if( shotAvaiableCounter < 60 ) {
			shotAvaiableCounter++;
			
		}
		if( life > maxLife ) {
			life = maxLife;
		}
		if( mana > maxMana) {
			mana = maxMana;
		}
		if( life < 0 ) {
			life = 0;
		}
		if( mana < 0 ) {
			mana = 0;
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
			damageMonster(monsterIndex, attack);
		
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
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
			// PICK UP ONLY ITEM
			if( gp.obj[i].type == type_pickupOnly ) {
				
				gp.obj[i].use(this);
				gp.obj[i] = null;
				
			}
			// INVENTORY ITEM 
			else {
				String text;
				if( inventory.size() != maxInventorySize ) {
					inventory.add(gp.obj[i]);
					gp.playSE(1);
					text = "You got a "+ gp.obj[i].name + "!";
				}
				else {
					text = "Your bag is full!";
				}
				gp.ui.addMessage(text);	
				gp.obj[i] = null ;
			}
			
		

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
			
			if( invincible == false && gp.monster[i].dying == false ) {
				
				int damage = gp.monster[i].attack - defense;
				if( damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				if(life < 0) {
					life = 0;
				}
				invincible = true;
				 gp.playSE(6);
			}
		
		}
	}
	
	public void damageMonster( int i , int attack) {
		if( i != 999) {
			if( gp.monster[i].invincible == false ) {
				
				int damage = attack - gp.monster[i].defense;
				if( damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage+" damage!");
				
				gp.monster[i].invincible = true;
				 gp.playSE(5);
				 gp.monster[i].damageReaction(); 
				 
	
				if( gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("You killed the " + gp.monster[i].name );
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
			gp.ui.currentDialogue = "You are up to level " + level +" now!\n" ;
		}
		
		
	}	
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		if( itemIndex < inventory.size() ) {
			Entity selectedItem = inventory.get(itemIndex);
			if( selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if( selectedItem.type == type_shield ) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if( selectedItem.type == type_consumable ) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
			
		}	
	}
	
	public void damageInteractiveTile( int i) {
		if( i != 999 && gp.iTile[i].destructible == true && gp.iTile[i].isCorrectItem(this) == true
				&& gp.iTile[i].invincible == false  ) {
			gp.iTile[i].playSE();
			gp.iTile[i].life--;
			gp.iTile[i].invincible = true;
			
			//generate Particle
			generateParticle(gp.iTile[i],gp.iTile[i]);
			
			if(gp.iTile[i].life == 0) {
				gp.iTile[i] = gp.iTile[i].getDestroyedForm();
			}

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
