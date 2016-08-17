package testAttacking;

import java.util.concurrent.ThreadLocalRandom;

public class Object {
	private int power;
	// int x;
	// int y;
	private boolean isAlive = false;

	int min = 1;
	int max = 10;

	public Object(boolean isAlive) {
		power = ThreadLocalRandom.current().nextInt(min, max + 1);
		this.isAlive = isAlive;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
