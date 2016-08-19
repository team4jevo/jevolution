package evolution;

import java.util.Random;

public class evolution {

	/**
	 * @param args
	 */

	static void setUpField(Cell[][] field, int x, int y) {
		Random random = new Random();
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					field[i][j] = new Cell(-1);
				} else {
					field[i][j] = new Cell(random.nextInt(2));
				}

			}
		}
		System.out.println("Field was made");
		System.out.println("-------------------");
	}

	static void changeCell(Cell[][] field, int x, int y) {
		int counter = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					continue;
				} else {
					if (field[i][j].isAlive == 1) {
						counter = checkAdjanced(field, i, j);
						if (counter >= 5 || counter <= 2) {
							field[i][j].isAlive = 0;
						}
					} else if (field[i][j].isAlive == 0) {
						counter = checkAdjanced(field, i, j);
						if (counter == 3) {
							field[i][j].isAlive = 1;
						}
					}

				}

			}
		}
	}

	static void printField(Cell[][] field, int x, int y) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					System.out.print('*');
				} else {
					if (field[i][j].isAlive == 1) {
						System.out.print('o');
					} else {
						System.out.print('-');
					}
				}

			}
			System.out.println();
			
		}
		System.out.println("-------------------");
	}

	static int checkAdjanced(Cell[][] field, int i, int j) {
		int counter = 0;
		for (int m = i - 1; m <= i + 1; m++) {
			for (int n = j - 1; n <= j + 1; n++) {
				if (m == i && n == j) {
					continue;
				}
				if (field[m][n].isAlive == 1) {
					counter++;
				}
			}
		}

		return counter;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 100;
		int y = 20;
		Cell[][] cell = new Cell[y][x];
		setUpField(cell, x, y);
		printField(cell, x, y);
		for (int i = 0; i<20 ; i++){
			changeCell(cell,x,y);
			printField(cell, x, y);
		}

	}
}
