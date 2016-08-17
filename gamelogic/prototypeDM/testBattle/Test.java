import java.util.Random;

import testAttacking.Object;

public class Test {
	final static int x = 4;
	final static int y = 4;
	final Random rand = new Random();
	Individual[][] population = new Individual[y][x];
	final int populationSize = (x-1)*(y-1);
	final int parentUsePercent = 10;
	public Test(){
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					System.out.print('*');
					population[i][j] = new Individual(false);
				} else {
					System.out.print('X');
					Individual individual = new Individual();
					individual.random();
					population[i][j] = individual;
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test t = new Test();
	}

}
