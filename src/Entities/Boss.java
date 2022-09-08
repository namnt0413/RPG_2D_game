package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.GameObjectDynamic.Direction;
import Main.GamePanel;

public class Boss extends Monster {

	private List<Fireball> fireballs;
	private final int HP_MAX = 100;
	private int enrage = 0;
	private int attackSpeed = 0;

	public Boss(int x, int y) {
		super(x, y);
		fireballs = new ArrayList<>();
		getMonsterGP().loadImage("res/textures/img/bossdown.png");
		getMonsterGP().getImageDimension(); // lấy kích thước ảnh
		setLife(HP_MAX);
		setAttack(3);
		setDefense(2);
		setSpeed(1);
		setFlexible(0);
		setInvincibleCounter(0);
		setInvincible(false);
	}

	public void castFireball() { // quái ném đá
		int xz = 0, yz = 0;

		if (getMonsterGP().getObjectDricetion() == Direction.RIGHT) {
			xz = getMonsterGP().getX() + getMonsterGP().getWidth();
			yz = getMonsterGP().getY() + getMonsterGP().getHeight() / 2;
		} else if (getMonsterGP().getObjectDricetion() == Direction.LEFT) {
			xz = getMonsterGP().getX();
			yz = getMonsterGP().getY() + getMonsterGP().getHeight() / 2;
		} else if (getMonsterGP().getObjectDricetion() == Direction.DOWN) {
			xz = getMonsterGP().getX() + getMonsterGP().getWidth() / 2;
			yz = getMonsterGP().getY() + getMonsterGP().getHeight();
		} else if (getMonsterGP().getObjectDricetion() == Direction.UP) {
			xz = getMonsterGP().getX() + getMonsterGP().getWidth() / 2;
			yz = getMonsterGP().getY();
		}
		GameObjectDynamic bulletGP = new GameObjectDynamic(xz, yz);
		Fireball fireball_new = new Fireball(bulletGP);
		fireball_new.getBulletGP().setObjectDricetion(getMonsterGP().getObjectDricetion());
		;

		fireballs.add(fireball_new);
	}

	public List<Fireball> getFireballs() {
		return fireballs;
	}

	public void move(int heroY, int heroX) { // hàm move() có tham số >< move() kế thừa từ Monster

		if (getMonsterGP().getObjectDricetion() == Direction.RIGHT) {
			
			getMonsterGP().setX(getMonsterGP().getX()+ getSpeed()) ;
			getMonsterGP().loadImage("res/textures/img/eyeright.png");
		} else if (getMonsterGP().getObjectDricetion() == Direction.LEFT) {
			
			getMonsterGP().setX(getMonsterGP().getX()- getSpeed()) ;
			getMonsterGP().loadImage("res/textures/img/eyeleft.png");
		} else if (getMonsterGP().getObjectDricetion() == Direction.UP) {

			getMonsterGP().setY(getMonsterGP().getY()- getSpeed()) ;
			getMonsterGP().loadImage("res/textures/img/eyeup.png");
		} else if (getMonsterGP().getObjectDricetion() == Direction.DOWN) {
			getMonsterGP().setY(getMonsterGP().getY()+ getSpeed()) ;
			getMonsterGP().loadImage("res/textures/img/eyedown.png");
		}
		Random rd = new Random();
		int find_hero_speed = 40; // cứ sau 20s tìm hero 1 lần

		setFlexible(getFlexible() + 1);
		if (getFlexible() == 20) { // cứ sau 100 chu kỳ timer.DELAY lại chuyển hướng di chuyển
			// random hướng di chuyển
			// định 0
			switch (rd.nextInt(4)) {
				case 4:
					getMonsterGP().setObjectDricetion(Direction.DOWN);
					break;
				case 1:
					getMonsterGP().setObjectDricetion(Direction.LEFT);
					break;
				case 2:
					getMonsterGP().setObjectDricetion(Direction.UP);
					break;
				case 3:
					getMonsterGP().setObjectDricetion(Direction.RIGHT);
					break;

			}
			// tương ứng với đứng yên)
			setFlexible(0);
		}
		if (getMonsterGP().getX() < 1) {
			getMonsterGP().setX(1);
		} // ko cho di chuyển tràn khung
		if (getMonsterGP().getY() < 1) {
			getMonsterGP().setY(1) ;
		}

		getMonsterGP().getImageDimension();
		if (getMonsterGP().getX() > GamePanel.getSizeX() - 150 - getMonsterGP().getWidth() * 2) {
			getMonsterGP().setX(GamePanel.getSizeX() - 150 - getMonsterGP().getWidth() * 2) ;
		}
		if (getMonsterGP().getY() > GamePanel.getSizeY() - getMonsterGP().getHeight() * 2) {
			getMonsterGP().setY(GamePanel.getSizeY() - getMonsterGP().getHeight() * 2) ;
		}

//		if (enrage < find_hero_speed) // < 10
			enrage++; // số chu kì tìm hero
		if (enrage <= find_hero_speed) {
			if (getMonsterGP().getY() - heroY > getSpeed()) {
				getMonsterGP().setY(getMonsterGP().getY()-getSpeed());
			}
			if (getMonsterGP().getY() - heroY < -getSpeed()) {
				getMonsterGP().setY(getMonsterGP().getY()+getSpeed());
			}
			if (getMonsterGP().getX() - heroX > getSpeed()) {
				getMonsterGP().setX(getMonsterGP().getX()-getSpeed());
			}
			if (getMonsterGP().getX() - heroX < -getSpeed()) {
				getMonsterGP().setX(getMonsterGP().getX()+getSpeed());
			}
		}
		if (enrage > 70)
			enrage = 0;
		attackSpeed++;
		if (getMonsterGP().getY() - heroY < getMonsterGP().getHeight() && attackSpeed >= 200) {
			castFireball();
			attackSpeed = 0;
		}
		;
	}

	public int getHp() {
		return getLife();
	}

	public void setHp(int hp) {
		this.setLife(hp);
	}

	public int getHpMax() {
		return HP_MAX;
	}

}