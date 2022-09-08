package Main;

import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import Entities.*;
import Entities.GameObjectDynamic.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	private static final int SIZE_X = 750;
	private static final int SIZE_Y = 600;
	private static final int tileSize = 32; // thay cho so 32 hien tai
	private final int TOADO_X = 10;
	private final int TOADO_Y = 10;
	private final int DELAY = 10; // toc do quet su kien
	// protected Image logo;
	private MagicCat magicCat;
	private Boss boss;
	private Timer timer;
	private boolean ingame; // danh dau chuyen canh
	private boolean started; // danh dau bat dau -> chuyen sang image->game over
	private double commandNum; // de chuyen trang thai giua play game, option va quit game
	private boolean showOption = false; // khi an phim ESC hien cac options: continue, newgame, exit
	private boolean pauseCharacter = false; // nguoi va quai dung yen khi nhan ESC
	private boolean boss_died = false; // boss chet ->gameover
	private static boolean boss_appared = false;
	private static boolean door_appared = false; // het quai thi se lo ra canh cua
	private final double magicCatHpBar = 10.66; // thanh mau cua nguoi choi
	private final double magicCatManaBar = 6.4;// thanh mana cua nguoi choi
	private String pathMap = ""; // duong dan den Map
	private List<Monkey> monkeys; // mang quai
	private List<Mummy> mummys;
	private boolean win = false;
	private final int[][] position = { // vi tri quai->thay = random

			{ 0, 0 } };
	// { 400, 310 }, { 420, 420 }, { 350, 500 }, { 230, 460 }, { 370, 280 },
	// { 30, 40 }, { 60, 60 }

	// khong fix nua
	// Something

	public static Map map;

	Color bgcolor = new Color(207, 207, 207);

	public GamePanel() {
		initGamePanel();
	}

	public void initGamePanel() {
		addKeyListener(new KeyHandler()); // Nhan su kien tu ban phim
		setFocusable(true); // xu li su kien
		setBackground(Color.BLACK); // mau nen
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		map = new Map("res/worlds/map.txt");
		ingame = true;
		started = false;
		commandNum = 0;
		magicCat = new MagicCat(TOADO_X, TOADO_Y);
		initMonsters();
		boss = new Boss(SIZE_X - 250, SIZE_Y / 2); // khoi tao boss ben phai man hinh
		timer = new Timer(DELAY, this); // nhan su kien sau 10dvth
		timer.start(); // gui toi actionPerformed
	}

	public void reset() {
		commandNum = 0;
		setFocusable(true); // xu li su kien
		setBackground(Color.BLACK); // mau nen
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		updateMonster();
		map = new Map("res/worlds/map.txt");
		ingame = true;
		started = true;
		magicCat = new MagicCat(TOADO_X, TOADO_Y);
		initMonsters();
		if (boss_appared == true) {
			boss_appared = false;
		}
		boss = new Boss(SIZE_X - 250, SIZE_Y / 2); // khoi tao boss ben phai man hinh
		timer = new Timer(DELAY, this); // nhan su kien sau 10dvth
	}

	public void initMonsters() {
		monkeys = new ArrayList<>();
		mummys = new ArrayList<>();
		for (int[] p : position) { // them monster
			monkeys.add(new Monkey(p[0], p[1]));
			mummys.add(new Mummy(p[0], p[1]));
		}
	}

	public static void setDoor_appared() {
		door_appared = false;
		boss_appared = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!ingame) {
			timer.stop();
		} // game over ( ko nhan su kien nhan nua )
		updateMagicBalls();
		updateSkillshots();
		updateMagicCat();
		updateMonster();
		updateBoss();
		if (door_appared) {

			if (!pathMap.equals("res/worlds/map2.txt")) {
				map.newGameMap("res/worlds/map2.txt");
				// m.openFile(pathMap);
				// m.readFile(); // Nho readfile lai 1 lan nua

			}
		}
		if (boss_appared) {
			if (!pathMap.equals("res/worlds/map3.txt")) {
				magicCat.getMagicCatGP().setX(300);
				magicCat.getMagicCatGP().setY(300);
				System.out.println(pathMap);
				pathMap = "res/worlds/map3.txt";
				map.newGameMap("res/worlds/map3.txt");
			}
			updateFireball();
		}
		checkCollisions(); // kiem tra va chạm
		repaint(); // goi ra paintComponent
		// System.out.println(magicCat.getLife());
	}

	public void updateMagicBalls() {
		List<MagicBall> MagicBalls = magicCat.getMagicBalls();
		for (int i = 0; i < MagicBalls.size(); i++) {
			MagicBall MagicBall = MagicBalls.get(i); // doi tg 1viendan = mangdan.thui
			if (MagicBall.getBulletGP().getExist()) {
				MagicBall.move();
			} // vien dan di chuyen
			else {
				MagicBalls.remove(i);
			} // xoa vien dan khi ham move
		}

	}

	public void updateSkillshots() {
		List<Skillshot> skillshots = magicCat.getSkillshots();
		for (int j = 0; j < skillshots.size(); j++) {
			Skillshot skillshot = skillshots.get(j); // doi tg 1viendan = mangdan.thui
			if (skillshot.getBulletGP().getExist()) {
				skillshot.move();
			} // vien dan di chuyen
			else {
				skillshots.remove(j);
			} // xoa vien dan khi ham move
		}

	}

	public void updateFireball() {
		List<Fireball> fireballs = boss.getFireballs();
		for (int i = 0; i < fireballs.size(); i++) {
			Fireball fireball = fireballs.get(i);
			if (fireball.getBulletGP().getExist()) {
				fireball.move();
			} else
				fireballs.remove(i);
		}
	}

	public void updateMagicCat() {
		if (magicCat.getMagicCatGP().getExist() && pauseCharacter == false) {
			magicCat.move();
		}
		if (!magicCat.getMagicCatGP().getExist()) {
			ingame = false;
		}
		if (magicCat.getShotAvailable() < 60) {
			magicCat.setShotAvailable(magicCat.getShotAvailable() + 1);
		}
		if (magicCat.getSkillAvailable() < 60) {
			magicCat.setSkillAvailable(magicCat.getSkillAvailable() + 1);
		}
	}

	public void updateBoss() {
		if (boss_appared && pauseCharacter == false) {
			boss.move(magicCat.getMagicCatGP().getY(), magicCat.getMagicCatGP().getX());
			if (boss.getHp() <= 0) {
				win = true;
				ingame = false;
			}

		}
	}

	public void updateMonster() {
		if (mummys.isEmpty() && monkeys.isEmpty() && boss_died) { // mang quai rong va boss chet
			ingame = false;
			return;
		}
		for (int i = 0; i < monkeys.size(); i++) {
			Monkey m = monkeys.get(i);
			if (m.getMonsterGP().getExist() && pauseCharacter == false) {
				m.move();
			}
			if (!m.getMonsterGP().getExist() && pauseCharacter == false) {
				monkeys.remove(i);
			}
		}
		for (int i = 0; i < mummys.size(); i++) {
			Mummy m = mummys.get(i);
			if (m.getMonsterGP().getExist() && pauseCharacter == false) {
				m.move();
			}
			if (!m.getMonsterGP().getExist() && pauseCharacter == false) {
				mummys.remove(i);
			}
		}
	}

	public void checkCollisions() {
		Rectangle mc = magicCat.getMagicCatGP().getBounds(); // tao khung bao quanh nv
		for (Monkey monster : monkeys) { // kiem tra quai dam vao ng choi
			Rectangle ms = monster.getMonsterGP().getBounds();
			if (mc.intersects(ms)) { // 2 khung cham vao nhau
				if (magicCat.isInvincible() == false) {
					magicCat.setLife(magicCat.getLife() - (monster.getAttack() - magicCat.getDefense()));
					if (magicCat.getLife() <= 0)
						magicCat.getMagicCatGP().setExist(false);
					// monster.setLife(monster.getLife() - 1);
					if (monster.getLife() <= 0)
						monster.getMonsterGP().setExist(false);
					magicCat.setInvincible(true);
					// magicCat.setCollided(true);
				}
			}
		}
		for (Mummy monster : mummys) { // kiem tra quai dam vao ng choi
			Rectangle ms = monster.getMonsterGP().getBounds();
			if (mc.intersects(ms)) { // 2 khung cham vao nhau
				if (magicCat.isInvincible() == false) {
					magicCat.setLife(magicCat.getLife() - (monster.getAttack() - magicCat.getDefense()));
					if (magicCat.getLife() <= 0)
						magicCat.getMagicCatGP().setExist(false);
					// monster.setLife(monster.getLife() - 1);
					if (monster.getLife() <= 0)
						monster.getMonsterGP().setExist(false);
					magicCat.setInvincible(true);
					// magicCat.setCollided(true);
				}
			}
		}

		List<MagicBall> mbs = magicCat.getMagicBalls(); // mb : mang cac magic ball cua hero
		for (MagicBall mb : mbs) {
			Rectangle khung_mb = mb.getBulletGP().getBounds(); // lay khung hinh dan ban ra
			for (Monkey monster : monkeys) {
				Rectangle ms = monster.getMonsterGP().getBounds(); // lay hinh tung con quai
				if (ms.intersects(khung_mb) && monster.isInvincible() == false) { // va cham dan va quai
					mb.getBulletGP().setExist(false);
					monster.setInvincible(true);
					monster.setLife(monster.getLife() - (magicCat.getAttack() - monster.getDefense()));
					if (monster.getLife() <= 0) {
						monster.getMonsterGP().setExist(false);
					}
				}
			}
			for (Mummy monster : mummys) {
				Rectangle ms = monster.getMonsterGP().getBounds(); // lay hinh tung con quai
				if (ms.intersects(khung_mb) && monster.isInvincible() == false) { // va cham dan va quai
					mb.getBulletGP().setExist(false);
					monster.setInvincible(true);
					monster.setLife(monster.getLife() - (magicCat.getAttack() - monster.getDefense()));
					if (monster.getLife() <= 0) {
						monster.getMonsterGP().setExist(false);
					}
				}
			}
			Rectangle bs = boss.getMonsterGP().getBounds(); // va cham dan va boss
			if (khung_mb.intersects(bs) && boss.isInvincible() == false) {
				boss.setHp(boss.getHp() - (magicCat.getAttack() - boss.getDefense()));
				mb.getBulletGP().setExist(false);

				boss.setInvincible(true);
			}
		}

		List<Skillshot> sks = magicCat.getSkillshots(); // fr : mang cac fire cua hero
		for (Skillshot sk : sks) {
			// System.out.println(collisionMonster);
			Rectangle khung_fr2 = sk.getBulletGP().getBounds(); // lay khung hinh dan ban ra
			for (Monkey monster : monkeys) {
				Rectangle ms = monster.getMonsterGP().getBounds(); // lay hinh tung con quai

				if (ms.intersects(khung_fr2) && monster.isInvincible() == false) { // va cham dan va quai
					// sk.setExist(false);
					monster.setLife(monster.getLife() - (magicCat.getSkillAttack() - monster.getDefense()));
					monster.setInvincible(true);
					if (monster.getLife() <= 0) {
						monster.getMonsterGP().setExist(false);
						// playSE(1);
					}
				}
			}
			for (Mummy monster : mummys) {
				Rectangle ms = monster.getMonsterGP().getBounds(); // lay hinh tung con quai

				if (ms.intersects(khung_fr2) && monster.isInvincible() == false) { // va cham dan va quai
					// sk.setExist(false);
					monster.setLife(monster.getLife() - (magicCat.getSkillAttack() - monster.getDefense()));
					monster.setInvincible(true);
					if (monster.getLife() <= 0) {
						monster.getMonsterGP().setExist(false);
						// playSE(1);
					}
				}
			}

			Rectangle bs = boss.getMonsterGP().getBounds(); // va cham dan va boss
			if (khung_fr2.intersects(bs) && boss.isInvincible() == false) {
				// sk.setExist(false);
				boss.setHp(boss.getHp() - (magicCat.getSkillAttack() - boss.getDefense()));
				boss.setInvincible(true);
			}

		}

		if (boss_appared) {
			List<Fireball> Fireballs = boss.getFireballs(); // xu li va cham stones vs nhan vat
			for (Fireball fb : Fireballs) {
				Rectangle fb_rec = fb.getBulletGP().getBounds();
				if (mc.intersects(fb_rec) && magicCat.isInvincible() == false) {
					// playSE(3);
					magicCat.setLife(magicCat.getLife() - (boss.getAttack() - magicCat.getDefense()));
					if (magicCat.getLife() <= 0) {
						magicCat.getMagicCatGP().setExist(false);
					} else {
						fb.getBulletGP().setExist(false);
						magicCat.setInvincible(true);
					}
				}
				Rectangle bs = boss.getMonsterGP().getBounds();
				if (bs.intersects(mc) && magicCat.isInvincible() == false) {
					magicCat.setLife(magicCat.getLife() - 1);
					if (magicCat.getLife() <= 0) {
						magicCat.getMagicCatGP().setExist(false);
					}
					magicCat.setInvincible(true);
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		Font smallFont = new Font("Helvetica", Font.BOLD, 20);
		FontMetrics fome = getFontMetrics(smallFont);
		g.setColor(Color.white);
		g.setFont(smallFont);
		// g.drawString(mesg, (SIZE_X - fome.stringWidth(mesg)) / 2, SIZE_Y / 2+150);
		// g.drawString(quit, (SIZE_X - fome.stringWidth(quit)) / 2, SIZE_Y / 2+180);

		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 50, 50);

		if (started == false) {
			if (showOption == false) {

				// TITLE NAME
				g.setFont(g.getFont().deriveFont(Font.BOLD, 50F));
				String text = "MAGIC CAT";
				int a = (SIZE_X - fome.stringWidth(text)) / 2 - 90;
				int b = SIZE_Y / 2 - 200;

				// SHADOW
				g.setColor(Color.gray);
				g.drawString(text, a + 5, b + 5);

				// MAIN COLOR
				g.setColor(Color.white);
				g.drawString(text, a, b);

				// MAGIC CAT IMG
				a = SIZE_X / 2 - 52;
				b += 84;
				g.drawImage(magicCat.getMagicCatGP().getImage(), a, b, 100, 100, null);

				// MENU
				g.setFont(g.getFont().deriveFont(Font.BOLD, 24F));

				text = "NEW GAME";
				a = getXforCenteredText(text, g);
				b += 196;
				g.drawString(text, a, b);
				if (commandNum == 0) {
					g.drawString(">", a - getTilesize(), b);
				}

				text = "OPTION";
				a = getXforCenteredText(text, g);
				b += 64;
				g.drawString(text, a, b);
				if (commandNum == 1) {
					g.drawString(">", a - getTilesize(), b);
				}

				text = "QUIT";
				a = getXforCenteredText(text, g);
				b += 64;
				g.drawString(text, a, b);
				if (commandNum == 2) {
					g.drawString(">", a - getTilesize(), b);
				}
			}
		}
		if (started == false) {
			if (showOption == true) {

				pauseCharacter = true;
				drawMap(g);
				g2.drawImage(magicCat.getMagicCatGP().getImage(), magicCat.getMagicCatGP().getX(),
						magicCat.getMagicCatGP().getY(), this);
				// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				for (Monkey monster : monkeys) { // ve quai
					if (monster.getMonsterGP().getExist()) {
						g2.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
								monster.getMonsterGP().getY(), this);
					}
				}
				for (Mummy monster : mummys) { // ve quai
					if (monster.getMonsterGP().getExist()) {
						g2.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
								monster.getMonsterGP().getY(), this);
					}
				}
				if (boss_appared) {
					g2.drawImage(boss.getMonsterGP().getImage(), boss.getMonsterGP().getX(), boss.getMonsterGP().getY(), this);
				}

				// hien thi cac options

				String text = "";

				int a = (SIZE_X - fome.stringWidth(text)) / 2 - 90;
				int b = SIZE_Y / 2 - 200;
				g.setColor(Color.white);
				g.drawString(text, a, b);

				text = "CONTINUE";
				a = getXforCenteredText(text, g);
				b += 196;
				g.drawString(text, a, b);
				if (commandNum == 0) {
					g.drawString(">", a - 32, b);
				}

				text = "NEW GAME";
				a = getXforCenteredText(text, g);
				b += 64;
				g.drawString(text, a, b);
				if (commandNum == 1) {
					g.drawString(">", a - 32, b);
				}

				text = "QUIT";
				a = getXforCenteredText(text, g);
				b += 64;
				g.drawString(text, a, b);
				if (commandNum == 2) {
					g.drawString(">", a - 32, b);
				}

			}
		}
		if (started == true) {
			drawMap(g);

			if (win) {
				String msg = "You Win!";
				Font small = new Font("Helvetica", Font.BOLD, 20);
				FontMetrics fm = getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString(msg, (SIZE_X - fm.stringWidth(msg)) / 2, SIZE_Y / 2);
			} else if (ingame) {
				Font small = new Font("Helvetica", Font.BOLD, 15);
				FontMetrics fm = getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				// g.drawImage(magicCat.getMagicCatGP().getImage(),
				// magicCat.getMagicCatGP().getX(), magicCat.getMagicCatGP().getY(), this); //
				// ve hero
				if (magicCat.isInvincible() == true) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				}
				g2.drawImage(magicCat.getMagicCatGP().getImage(), magicCat.getMagicCatGP().getX(),
						magicCat.getMagicCatGP().getY(), this);
				// RESET ALPHA
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

				List<MagicBall> MagicBalls = magicCat.getMagicBalls();
				for (MagicBall MagicBall : MagicBalls) { // ve dan
					g.drawImage(MagicBall.getBulletGP().getImage(), MagicBall.getBulletGP().getX(),
							MagicBall.getBulletGP().getY(), this);
				}

				// ve skillshot
				List<Skillshot> skillshots = magicCat.getSkillshots();
				for (Skillshot skillshot : skillshots) { // ve dan
					g.drawImage(skillshot.getBulletGP().getImage(), skillshot.getBulletGP().getX(),
							skillshot.getBulletGP().getY(), this);

				}

				for (Monkey monster : monkeys) { // ve quai
					if (monster.getMonsterGP().getExist()) {
						Integer hpBar = 8;
						Integer hpBarValue = hpBar * monster.getLife();
						if (monster.isInvincible() == true) {
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
						}
						g2.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
								monster.getMonsterGP().getY(),
								this);
						// RESET ALPHA
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
						// g.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
						// monster.getMonsterGP().getY(), this);
						g.setColor(Color.black);
						g.fillRect(monster.getMonsterGP().getX() - 1, monster.getMonsterGP().getY() - 16, 34, 12);
						g.setColor(Color.red);
						g.fillRect(monster.getMonsterGP().getX(), monster.getMonsterGP().getY() - 15, hpBarValue, 10);
					}
				}
				for (Mummy monster : mummys) { // ve quai
					if (monster.getMonsterGP().getExist()) {
						Integer hpBar = 8;
						Integer hpBarValue = hpBar * monster.getLife();
						if (monster.isInvincible() == true) {
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
						}
						g2.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
								monster.getMonsterGP().getY(),
								this);
						// RESET ALPHA
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
						// g.drawImage(monster.getMonsterGP().getImage(), monster.getMonsterGP().getX(),
						// monster.getMonsterGP().getY(), this);
						g.setColor(Color.black);
						g.fillRect(monster.getMonsterGP().getX() - 1, monster.getMonsterGP().getY() - 16, 34, 12);
						g.setColor(Color.red);
						g.fillRect(monster.getMonsterGP().getX(), monster.getMonsterGP().getY() - 15, hpBarValue, 10);
					}
				}
				List<Fireball> Fireballs = boss.getFireballs();
				for (Fireball Fireball : Fireballs) {
					g.drawImage(Fireball.getBulletGP().getImage(), Fireball.getBulletGP().getX(), Fireball.getBulletGP().getY(),
							this);
				}
				if (magicCat.isInvincible() == true) {
					magicCat.setInvincibleCounter(magicCat.getInvincibleCounter() + 1);
					if (magicCat.getInvincibleCounter() > 60) {
						magicCat.setInvincible(false);
						magicCat.setInvincibleCounter(0);
					}
				}
				if (boss.isInvincible() == true) {
					boss.setInvincibleCounter(boss.getInvincibleCounter() + 1);
					if (boss.getInvincibleCounter() > 60) {
						boss.setInvincible(false);
						boss.setInvincibleCounter(0);
					}
				}
				for (Monkey monster : monkeys) {
					if (monster.isInvincible() == true) {
						monster.setInvincibleCounter(monster.getInvincibleCounter() + 1);
						if (monster.getInvincibleCounter() > 30) {
							monster.setInvincible(false);
							monster.setInvincibleCounter(0);
						}
					}
				}
				for (Mummy monster : mummys) {
					if (monster.isInvincible() == true) {
						monster.setInvincibleCounter(monster.getInvincibleCounter() + 1);
						if (monster.getInvincibleCounter() > 30) {
							monster.setInvincible(false);
							monster.setInvincibleCounter(0);
						}
					}
				}
				if (boss_appared) {
					Double bossHp = 0.32;
					Double bossHpValue = bossHp * boss.getHp();
					Integer display = (int) Math.round(bossHpValue);
					// g.drawImage(boss.getMonsterGP().getImage(), boss.getMonsterGP().getX(),
					// boss.getMonsterGP().getY(), this);
					if (boss.isInvincible() == true) {
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
					}
					g2.drawImage(boss.getMonsterGP().getImage(), boss.getMonsterGP().getX(), boss.getMonsterGP().getY(), this);
					// RESET ALPHA
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

					g.setColor(Color.black);
					g.fillRect(boss.getMonsterGP().getX() - 1, boss.getMonsterGP().getY() - 16, 34, 12);
					g.setColor(Color.red);
					g.fillRect(boss.getMonsterGP().getX(), boss.getMonsterGP().getY() - 15, display, 10);
				}
				g.setColor(Color.white);
				if (monkeys.isEmpty() && mummys.isEmpty()) {
					if (!boss_appared)
						door_appared = true;
				}

				if (door_appared) {
					g.drawString("Canh cua khong gian xuat hien! ", SIZE_X - 120, SIZE_Y / 4);
				}

				if (boss_appared) {
					door_appared = false;
					g.drawString("BOSS ", SIZE_X - 100, SIZE_Y / 4);
					g.drawString("HP : " + boss.getHp(), SIZE_X - 100, SIZE_Y / 4 + 20);
					if (boss_appared == false)
						boss_appared = true;
				} else {
					if (!door_appared) {
						g.drawString("Monsters: " + monkeys.size(), SIZE_X - 100, SIZE_Y / 4); // 10,10 : k/c tinh tu
						g.drawString("Monsters: " + mummys.size(), SIZE_X - 100, SIZE_Y / 4 + 32); // 10,10 : k/c tinh tu
					}
					// goc trai
					// man hinh
				}
				g.drawString("Health: ", SIZE_X - 100, SIZE_Y / 4 + 50);
				g.setColor(Color.white);
				g.fillRect(SIZE_X - 100 - 2, SIZE_Y / 4 + 60 - 1, 88, 12);
				g.setColor(Color.red);
				g.fillRect(SIZE_X - 100, SIZE_Y / 4 + 60, (int) Math.round(magicCatHpBar * magicCat.getLife()), 10);
				g.setColor(Color.white);
				// g.drawString("Speed : " + magicCat.getSpeed(), SIZE_X - 100, SIZE_Y / 4 +
				// 80);
				g.drawString("Mana : ", SIZE_X - 100, SIZE_Y / 4 + 90);
				g.setColor(Color.white);
				g.fillRect(SIZE_X - 100 - 2, SIZE_Y / 4 + 100 - 1, 80, 12);
				g.setColor(Color.blue);
				g.fillRect(SIZE_X - 100, SIZE_Y / 4 + 100, (int) Math.round(magicCatManaBar * magicCat.getMana()), 10);
				g.setColor(Color.white);
				g.drawString("Attack : " + magicCat.getAttack(), SIZE_X - 100, SIZE_Y / 4 + 130);
				g.drawString("Defense : " + magicCat.getDefense(), SIZE_X - 100, SIZE_Y / 4 + 150);
				// g.drawString(" : " + magicCat.getShotAvailable(), SIZE_X - 100, SIZE_Y / 4 + 170);
				// g.drawString("Collision : " + magicCat.getCollided(), SIZE_X - 100, SIZE_Y /
				// 4 +
				// 150);
				// g.drawString("Invincible : " + magicCat.getInvincibleCounter(), SIZE_X - 100,
				// SIZE_Y / 4 + 170);

			} else {
				String msg = "Game Over";
				Font small = new Font("Helvetica", Font.BOLD, 20);
				FontMetrics fm = getFontMetrics(small);
				g.setColor(Color.white);
				g.setFont(small);
				g.drawString(msg, (SIZE_X - fm.stringWidth(msg)) / 2, SIZE_Y / 2);
			}
		} // end started
	} // end paint component

	public void drawMap(Graphics g) {

		for (int i = 0; i < map.getMapRow(); i++) {
			for (int j = 0; j < map.getMapCol(); j++) {
				g.drawImage(map.gameMap[i][j].getGraphics().getImage(), i * getTilesize(), j * getTilesize(), null);
			}
		}
	}

	public class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
				if (magicCat.getShotAvailable() == 60) {
					magicCat.castMagicBall();
					magicCat.setShotAvailable(0);
				}
			}
			if (key == KeyEvent.VK_A) {
				if (magicCat.getMana() > 0 && magicCat.getSkillAvailable() == 60) {
					magicCat.castSkillshot();
					magicCat.setSkillAvailable(0);
				}
			}
			if (key == KeyEvent.VK_LEFT && pauseCharacter == false) {
				// magicCat.setDx(-magicCat.getSpeed());
				magicCat.getMagicCatGP().setX(magicCat.getMagicCatGP().getX() - magicCat.getSpeed());
				magicCat.getMagicCatGP().loadImage("res/textures/img/left.png");
				magicCat.getMagicCatGP().setObjectDricetion(Direction.LEFT);
			}
			if (key == KeyEvent.VK_RIGHT && pauseCharacter == false) {
				// magicCat.setDx(magicCat.getSpeed());
				magicCat.getMagicCatGP().setX(magicCat.getMagicCatGP().getX() + magicCat.getSpeed());
				magicCat.getMagicCatGP().loadImage("res/textures/img/right.png");
				magicCat.getMagicCatGP().setObjectDricetion(Direction.RIGHT);
			}
			if (key == KeyEvent.VK_UP && pauseCharacter == false) {
				// magicCat.setDy(-magicCat.getSpeed());
				magicCat.getMagicCatGP().setY(magicCat.getMagicCatGP().getY() - magicCat.getSpeed());
				magicCat.getMagicCatGP().loadImage("res/textures/img/up.png");
				magicCat.getMagicCatGP().setObjectDricetion(Direction.UP);
			}
			if (key == KeyEvent.VK_DOWN && pauseCharacter == false) {
				// magicCat.setDy(magicCat.getSpeed());
				magicCat.getMagicCatGP().setY(magicCat.getMagicCatGP().getY() + magicCat.getSpeed());
				magicCat.getMagicCatGP().loadImage("res/textures/img/down.png");
				magicCat.getMagicCatGP().setObjectDricetion(Direction.DOWN);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode(); // danh dau vao game
			if (key == KeyEvent.VK_SPACE) {
			}
			if (key == KeyEvent.VK_A) {
			}
			if (key == KeyEvent.VK_LEFT) {

			}
			if (key == KeyEvent.VK_RIGHT) {
				;
			}
			if (key == KeyEvent.VK_UP) {

			}
			if (key == KeyEvent.VK_DOWN) {

			}

			if (started == false) {
				titleState(key);
				// if (key == 's' || key == 'S')
				// started = true;
			}
			// if (key == 'n' || key == 'N') { // Khi bam N thi se doc file path khac
			// Board.m.openFile("res/worlds/map2.txt");
			// Board.m.readFile(); // Nho readfile lai 1 lan nua
			// }

			if (key == KeyEvent.VK_ESCAPE && started == true) {

				started = false;
				showOption = true;

			}

			if (started == false && showOption == true) {

			}
		}
	}

	public void titleState(int code) {
		if (started == false && showOption == false) {
			if (code == KeyEvent.VK_UP) {
				commandNum--;
				if (commandNum < 0) {
					commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_DOWN) {
				commandNum++;
				if (commandNum > 2) {
					commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (commandNum == 0) {
					started = true;
				}
				if (commandNum == 1) {
					// OPTION DO LATER
				}
				if (commandNum == 2) {
					System.exit(0);
				}
			}
		}
		if (started == false && showOption == true) {
			if (code == KeyEvent.VK_UP) {
				commandNum--;
				if (commandNum < 0) {
					commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_DOWN) {
				commandNum++;
				if (commandNum > 2) {
					commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (commandNum == 0) {
					started = true;
					pauseCharacter = false;
				}
				if (commandNum == 1) {
					reset();
					pauseCharacter = false;

				}
				if (commandNum == 2) {
					System.exit(0);
				}

			}
		}
	}

	public int getXforCenteredText(String text, Graphics g) {
		int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth(); // can doan text ra chinh giua
		int x = SIZE_X / 2 - length / 2;
		return x;
	}

	public static int getSizeX() {
		return SIZE_X;
	}

	public static int getSizeY() {
		return SIZE_Y;
	}

	public static int getTilesize() {
		return tileSize;
	}
}
