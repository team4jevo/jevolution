import java.util.Random;

public class Object {
	int direction = 0;
	/*	1 - East (right)
	 * 	2 - South East
	 * 	3 - South
	 * 	4 - South West
	 * 	5 - West
	 *  6 - North West
	 *  7 - North
	 *  8 - North East
	 * 
	 * 
	 */
	int type;
	int score = 0;
	Random random = new Random();
	

	/*
	 * -1 = wall 0 = empty cell 1 = hawk 2 = dove
	 */

	public Object(int type) {
		if (type == -1 && type == 0) {
			this.type = type;
			return;
		}
		direction = random.nextInt(8) + 1;
		this.type = type;
		score = 0;
	}

}
