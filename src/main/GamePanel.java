package main;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
	
		// SCREEN SETTINGS
		final int originalTileSize = 16; // 16x16 tile 
		final int scale = 3;
		
		public int tileSize = originalTileSize * scale; // 48x48 tile
		public int maxScreenCol = 16;
		public int maxScreenRow = 12;
		public int screenWidth = tileSize * maxScreenCol; // 768 pixels
		public int screenHeight = tileSize * maxScreenRow; // 576 pixels
		
		//WORLD SETTING
		public final int maxWorldCol = 50;
		public final int maxWorldRow = 50;
		public final int maxWorldWidth = tileSize * maxWorldCol;
		public final int maxWorldHeight = tileSize * maxWorldRow;
		
		int FPS = 60;
		
		//SYSTEM
		TileManager tileM = new TileManager(this);
		KeyHandler keyH = new KeyHandler(this);
		Sound se = new Sound();
		Sound music = new Sound();  
		public CollisionChecker cChecker = new CollisionChecker(this);
		public AssetSetter aSetter = new AssetSetter(this);
		public UI ui = new UI(this);
		Thread gameThread;
		
		// ENTITY AND OBJECT
		public Player player = new Player(this,keyH);
		public SuperObject obj[] = new SuperObject[10];
		public Entity npc[] = new Entity[10];
		
		//GAME STATE
		public int gameState;
		public final int playState = 1;
		public final int pauseState = 2;
//		public Entity[] target;
		
		
		public GamePanel() {
			this.setPreferredSize(new Dimension(screenWidth, screenHeight));
			this.setBackground(Color.black);
			this.setDoubleBuffered(true); // improve performance 
			this.addKeyListener(keyH);
			this.setFocusable(true); // with this, this GamePanel will focus to receive key input
			
		}
		
		public void setupGame() {
			aSetter.setObject();
			aSetter.setNPC();
			
			playMusic(0);
			stopMusic();
			gameState = playState;
		}
		
		public void zoomInOut( int i) {
			
			int oldWorldWidth = tileSize * maxWorldCol;//2400
			tileSize += i;
			int newWorldWidth = tileSize * maxWorldCol;//2350
			
			player.speed = (double)newWorldWidth/600;
			
			double multiplier = (double)newWorldWidth/oldWorldWidth;
			double newPlayerWorldX = player.worldX * multiplier;
			double newPlayerWorldY = player.worldY * multiplier;
			
			player.worldX = newPlayerWorldX;
			player.worldY = newPlayerWorldY;
		}
		
		public void startGameThread() { // Khoi chay 1 luong game
			gameThread = new Thread(this);
			gameThread.start();
		}
		
		@Override
		public void run() {
			while(gameThread != null) {
				
				double drawInterval = 1000000000/FPS; // 0.01666 s
				double nextDrawTime = System.nanoTime() + drawInterval;
				
				//thoi gian: long currentTime = System.nanoTime();
				//long currentTime2 = System.currentTimeMillis();
				
				//1: update postion info
				update();
				
				//2 draw
				repaint();
				

				try {
					double remainingTime  = nextDrawTime - System.nanoTime();
					remainingTime = remainingTime/1000000;
					
					if( remainingTime < 0 ) {
						remainingTime = 0 ;
					}
					
					Thread.sleep((long) remainingTime);
					
					nextDrawTime += drawInterval; 
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						 
			}
		}
		
//		public void run() (
//				double drawInterval= 1000000000/FPS;
//				double delta = 0;
//				long lastTime =  System.nanoTime();
//				long currentTime;
//
//				while (gameThread != null) {
//					currentTime= System.nanoTime();
//					delta += (currentTime - lastTime) / drawInterval;
//					lastTime = currentTime;
//
//				if (delta >= 1) {
//					update();
//					repaint();
//					delta--;
//				}
//		}
//}
		
		public void update() {	//update position
			
			if( gameState == playState ) {
				player.update();
					
				for(int i =0; i<npc.length ; i++) {
					if(npc[i] != null) {
						npc[i].update();
					}
				}
			}
			if( gameState == pauseState ) {
				//nothing
				
			}
			

			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			// tile draw
			tileM.draw(g2);
			
			//NPC
			for( int i = 0; i < npc.length ; i++) {
				if( npc[i] != null ) {	// check if slot is not emty to advoid null ptr
					npc[i].draw(g2);
				}
				
			}
			
			//object
			for( int i=0; i<obj.length; i++ ) {
				if( obj[i] != null ) {	// check if slot is not emty to advoid null ptr
					obj[i].draw(g2,this);
				}
				
			}
			
			// player draw
			player.draw(g2);
			
			//UI
			ui.draw(g2);
			
			g2.dispose();  // release all system resources using
			
		}
		
		public void playMusic(int i) {
			
			music.setFile(i);
			music.play();
			music.loop();
		}
		
		public void stopMusic() {
			
			music.stop();
		}

		
		public void playSE(int i) {
			
			se.setFile(i);
			se.play();
			
		}
		
		
}
