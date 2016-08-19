

import java.util.Random;

public class Cell {
	int isAlive = 0;
	Random random = new Random();
	int type;

	public Cell(int isAlive) {
		this.isAlive = isAlive;
		type = random.nextInt(2); /// trying different method
	}

	void randomCell() {
		int randomNumber = random.nextInt(2);
		if (randomNumber == 0){
			this.isAlive = 0;
		}
		else {
			this.isAlive = 1;
		}
	}
	

}
