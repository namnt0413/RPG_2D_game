package Entities;

import Entities.GameObjectDynamic.Direction;

public class MagicBall extends Bullet {

//	private final int bullet_speed = 5;
//	private final int bullet_length = 250;
//	private int useCost = 1;
	
	public MagicBall(GameObjectDynamic bulletGP) {
        super(bulletGP);
        bulletGP.loadImage("res/textures/img/magic_ball.png");
        bulletGP.getImageDimension();
		setBullet_speed(2);
		setBullet_length(200);
		setUseCost(0);
		setFly(0);
    }

    public void move() {
    	if (getBulletGP().getObjectDricetion() == Direction.RIGHT ) getBulletGP().setX(getBulletGP().getX()+getBullet_speed()) ; // \
        if (getBulletGP().getObjectDricetion() == Direction.UP) getBulletGP().setY(getBulletGP().getY()-getBullet_speed()); // \
        if (getBulletGP().getObjectDricetion() == Direction.LEFT ) getBulletGP().setX(getBulletGP().getX()-getBullet_speed());
        if (getBulletGP().getObjectDricetion() == Direction.DOWN) getBulletGP().setY(getBulletGP().getY()+getBullet_speed());
        setFly(getFly() + getBullet_speed()  ); 
        if (getFly() > getBullet_length()) {
        	getBulletGP().setExist(false);
        }
        if(getBulletGP().getX()<0||getBulletGP().getX()>550||getBulletGP().getY()<0||getBulletGP().getY()>550) {
        	getBulletGP().setExist(false);
        }
    }
}