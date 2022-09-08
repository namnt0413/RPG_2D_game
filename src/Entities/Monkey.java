package Entities;

import Entities.GameObjectDynamic.Direction;

public class Monkey extends Monster{


    private static final int SPEED = 1;
    private static final int MAX_LIFE = 4;
    private static final int ATTACK = 2;
	private static final int DEFENSE = 1;


	public Monkey(int x, int y) {
		super(x, y);
		setSpeed(SPEED);	
		setAttack(ATTACK);
		setDefense(DEFENSE);
		setLife(MAX_LIFE);
		setFlexible(0);
		setInvincibleCounter(0);
		setInvincible(false);
	}
	
    public void move() {
        move("res/textures/img/monkeyright.png","res/textures/img/monkeyleft.png","res/textures/img/monkeydown.png","res/textures/img/monkeyup.png");
    }
	

}