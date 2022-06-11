package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_60B;
	Font maruMonica,purisaB;	
	BufferedImage heart_full,heart_half,heart_blank, crystal_full, crystal_blank;
	public Boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;
	
	
	public boolean gameFinished = false;
	
	public String currentDialogue = "";
	
	public int commandNum = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// CREATE HUD OBJ
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}
	
	public void addMessage(String text) {
//		message = text;
//		messageOn = true;
		message.add(text);
		messageCounter.add(0);
	
	}
	
	

	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(maruMonica);
//		g2.setFont(purisaB);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if( gp.gameState == gp.titleState) {
			drawTitleScreen();
			
		}
		
		// PLAY STATE
		if( gp.gameState == gp.playState ) {
			drawPlayerLife();
			drawMessage();
		}
		
		// PAUSE STATE		
		if( gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if( gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		//CHARACTER STATE
		if( gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
		//OPTION STATE
		if( gp.gameState == gp.optionState) {
			drawOptionsScreen();
		}
		
		//GAME OVER STATE
		if( gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		
	
	} // end draw method
	
	public void drawPlayerLife() {
		
//		gp.player.life = 2;
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// DRAW BLANK HEART
		while( i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank,x,y,null);
			i++;
			x += gp.tileSize;
		} 
		
		//RESET 
		x = gp.tileSize/2;
		y= gp.tileSize/2;
		i=0;
		
		// DRAW CURRENT LIFE
		while( i < gp.player.life) {
			g2.drawImage(heart_half, x, y,null);
			i++;
			if( i < gp.player.life) {
				g2.drawImage(heart_full,x,y,null);
				
			}
			i++;
			x += gp.tileSize;
		}
		
		//DRAW MAX MANA
		x = gp.tileSize/2 - 5;
		y= (int)(gp.tileSize*1.5);
		i=0;
		while( i < gp.player.maxMana ) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		// DRAW CURRENT MANA
		x = gp.tileSize/2 - 5;
		y= (int)(gp.tileSize*1.5);
		i=0;
		while( i < gp.player.mana ) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
				
		
		
		
		
	}
	
	public void drawMessage(){
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for( int i=0 ; i < message.size(); i++ ) {
			if( message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				int counter = messageCounter.get(i) + 1 ; // messageCounter++
				messageCounter.set(i, counter );	//set the counter to array
				messageY += 50;
				
				if( messageCounter.get(i) > 180 ) {
					message.remove(i);
					messageCounter.remove(i);
					
				}
			}
			
		}
		
	}
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Blue Boy Adventure";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*3;
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// BLUE BOY IMG
		x = gp.screenWidth/2 - gp.tileSize;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
				
		// MENU 
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);	
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);	
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);	
		}
		
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize *4;
		drawSubWindow( x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,36F));
		x += gp.tileSize;
		y += gp.tileSize;
//		g2.drawString(currentDialogue, x, y);
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawCharacterScreen(){
		//CREATE A FRAME 
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize *5;
		final int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		//NAME
		g2.drawString("Level", textX, textY);
		textY+= lineHeight;
		g2.drawString("Life", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Mana", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Strength", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Dexterity", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Attack", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Defense", textX, textY);		
		textY+= lineHeight;
		g2.drawString("EXP", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Next Level Exp", textX, textY);		
		textY+= lineHeight;
		g2.drawString("Coin", textX, textY);		
		textY+= lineHeight+10;
		g2.drawString("Weapon", textX, textY);		
		textY+= lineHeight+15;
		g2.drawString("Shield", textX, textY);		
		textY+= lineHeight; 
		
		//VALUES
		int tailX = ( frameX + frameWidth) - 30;
		//RESET textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife );
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana );
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAligndToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-25, null );
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-25, null );	
		
	}
	
	public void drawSubWindow( int x, int y, int width, int height){
		
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height , 35, 35);	
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke( new BasicStroke(5));	// define the width of outlines of graphics which are  rendered 
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public void drawInventory() {
		//FRAME
		final int frameX = gp.tileSize*12;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize *6;
		final int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		// SLOT
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize+3;
		
		//PLAYER's ITEM
		for( int i = 0; i < gp.player.inventory.size() ; i++) {
			
			//EQUIP CURSOR
			if( gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if( i == 4 || i == 9 || i ==14) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		
		//CURSOR	
		int cursorX = slotXStart + ( slotSize * slotCol );
		int cursorY = slotYStart + ( slotSize * slotRow );
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		//DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight , 10, 10 );
		
		// DESCRIPTION FRAME
		final int dFrameX = frameX;
		final int dFrameY = frameY + frameHeight;
		final int dFrameWidth = frameWidth;
		final int dFrameHeight = gp.tileSize * 3 ;
		
		// DRAW DESCRIPTION TEXT	
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
		
		if( itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
			
			for( String line : gp.player.inventory.get(itemIndex).description.split("\n") ) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}

	}
	
	public void drawOptionsScreen(){
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//SUB WINDOW
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize *8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		switch( subState ) {
			case 0:	options_top(frameX,frameY); break;
			case 1: options_fullScreenNotification(frameX,frameY); break;
			case 2: options_control(frameX,frameY); break;
			case 3: options_endGameConfirmation( frameX, frameY); break;

		}
		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX; int textY;
		
		//TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if( commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true ) {
				
				if( gp.fullScreenOn == false ) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true ) {
					gp.fullScreenOn = false;
				}
				subState = 1;	
				
			}
			
		}
		
		
		
		//MUSIC
		textY += gp.tileSize;		
		g2.drawString("Music", textX, textY);
		if( commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}

		//SE
		textY += gp.tileSize;		
		g2.drawString("Sound Effect", textX, textY);
		if( commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		//CONTROL
		textY += gp.tileSize;		
		g2.drawString("Control", textX, textY);
		if( commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		//END GAME
		textY += gp.tileSize;		
		g2.drawString("End Game", textX, textY);
		if( commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		//BACK
		textY += gp.tileSize*2;		
		g2.drawString("Back", textX, textY);
		if( commandNum == 5) {
			g2.drawString(">", textX-25, textY);
		}
		
		// FULLSCREEN CHECK BOX
		textX = frameX + (int)(gp.tileSize*4.5) ;
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect( textX, textY, 24, 24);
		if( gp.fullScreenOn == true ) {
			g2.fillRect(textX, textY , 24,24);
		}
		
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect( textX, textY, 120, 24 );
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24 );
		
		//SE VOLUME
		textY += gp.tileSize;
		g2.drawRect( textX, textY, 120, 24 );
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24 );
		
		gp.config.saveConfig();
		
	}
	
	public void options_fullScreenNotification( int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will take effect\n after restarting the game";
		
		for( String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//BACK 
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if( commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true ) {
				subState = 0;
			}
		}
	}
	
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize; 
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;	
		
		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;
		g2.drawString("ASDW", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("J", textX, textY);
		textY += gp.tileSize;
		g2.drawString("C", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;
		
		//BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if( commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true ) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "Quit the game and\n return the menu";
		
		for( String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//YES
		String text = "YES";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if( commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true ) {
				subState = 0;
				gp.gameState = gp.titleState;
			}
		}
		
		//NO
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if( commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if( gp.keyH.enterPressed == true ) {
				subState = 0;
				commandNum = 4;;
			}
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		
		text = "Game Over";
		//Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text,x,y);
		//Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Play again";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if( commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// Back to title
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55 ;
		g2.drawString(text, x, y);
		if( commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow) * 5;
		return itemIndex;
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // can doan text ra chinh giua
		int x = gp.screenWidth/2 -length/2;
		return x;
	}
	
	public int getXforAligndToRightText(String text , int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // can doan text ra chinh giua
		int x = tailX - length;
		return x;
	}
	
}
