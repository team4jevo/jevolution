import java.util.Random;

public class Individual {
	public static final int SIZE = 10; // stat array size
	public int[] fighterArray;
	final Random rand = new Random();
	boolean wall = true;

	// 1 kills 3
	// 3 kills 2
	// 2 kills 1
	// everything kills 0
	// in same number situation both lose

	public Individual() {
		fighterArray = new int[SIZE];
		wall = false; //not a wall
		
	}
	public Individual(boolean wall){
		this.wall = wall; //this is wall
	}

	void random() {
		for (int i = 0; i < fighterArray.length; i++) {
			fighterArray[i] = rand.nextInt(4);
			//System.out.print(fighterArray[i]);
		}
	}
	//int fitness(){
		
	//}

}
