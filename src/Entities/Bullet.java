package Entities;

abstract class Bullet {
	private int bullet_length;
	private int bullet_speed;
	private int useCost;
	private int fly;
	private GameObjectDynamic bulletGP;
    public Bullet(GameObjectDynamic bulletGP) {
		this.bulletGP = bulletGP;
	}
	public abstract void move();
	
	public int getBullet_length() {
		return bullet_length;
	}
	public int getBullet_speed() {
		return bullet_speed;
	}
	public int getFly() {
		return fly;
	}
	public void setFly(int fly) {
		this.fly = fly;
	}
	public void setBullet_length(int bullet_length) {
		this.bullet_length = bullet_length;
	}
	public void setBullet_speed(int bullet_speed) {
		this.bullet_speed = bullet_speed;
	}
	public GameObjectDynamic getBulletGP() {
		return bulletGP;
	}
	public void setBulletGP(GameObjectDynamic bulletGP) {
		this.bulletGP = bulletGP;
	}
	public int getUseCost() {
		return useCost;
	}
	public void setUseCost(int useCost) {
		this.useCost = useCost;
	}

}
