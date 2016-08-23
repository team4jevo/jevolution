import java.util.Random;

public class Field {
	int doveCounter = 0;
	int hawkCounter = 0;
	private Object[][] field;
	int[][] nextTurn;
	Random random;
	int x;
	int y;

	public Field(int x, int y) {
		field = new Object[y][x];
		nextTurn = new int[y][x];
		random = new Random();
		this.x = x;
		this.y = y;
	}

	void setUpField() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					field[i][j] = new Object(-1); // creates wall
				} else {
					if (doveCounter == 50 && hawkCounter == 50) {
						field[i][j] = new Object(0); // creates empy cell

					} else {
						if (hawkCounter == 50) {
							int randomNum = random.nextInt(100);
							if (randomNum >= 50) {
								field[i][j] = new Object(2); // creates a dove
								doveCounter++;
							} else {
								field[i][j] = new Object(0); // creates a wall
							}
						} else if (doveCounter == 50) {
							int randomNum = random.nextInt(100);
							if (randomNum >= 50) {
								field[i][j] = new Object(1); // creates a hawk
								hawkCounter++;
							} else {
								field[i][j] = new Object(0); // creates a wall
							}

						} else {
							field[i][j] = new Object(random.nextInt(2)); // creates
																			// random
																			// object
							if (field[i][j].type == 1) {
								hawkCounter++;
							}
							if (field[i][j].type == 2) {
								doveCounter++;
							}
						}
					}
				}

			}
		}
		shuffle(field, y, new Random());

		System.out.println("Field was made");
	}

	void draw() {
		StdDraw.clear();
		// StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(-x * 0.1, x * 1.2);
		StdDraw.setYscale(-y * 0.2, y * 1.2);
		StdDraw.filledSquare(x / 2.0, x / 2.0 + 1, x / 2.0);

		// Draw grid
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				switch (field[i][j].type) {
				case -1:
					StdDraw.setPenColor(StdDraw.BLACK);
					break;
				case 0:
					StdDraw.setPenColor(StdDraw.WHITE);
					break;
				case 1:
					StdDraw.setPenColor(StdDraw.RED);
					break;
				case 2:
					StdDraw.setPenColor(StdDraw.GREEN);
					break;

				}

				StdDraw.filledSquare(j + 0.5, y - i + 0.5, 0.49);

			}
		}
	}

	/** Shuffles a 2D array with the same number of columns for each row. */
	public static void shuffle(Object[][] matrix, int y, Random rnd) {
		int size = matrix.length * y;
		for (int i = size; i > 1; i--)
			swap(matrix, y, i - 1, rnd.nextInt(i));
	}

	/**
	 * Swaps two entries in a 2D array, where i and j are 1-dimensional indexes,
	 * looking at the array from left to right and top to bottom.
	 */
	public static void swap(Object[][] matrix, int y, int i, int j) {
		if (matrix[i / y][i % y].type == -1 || matrix[j / y][j % y].type == -1) {
			return;
		}
		Object tmp = matrix[i / y][i % y];
		matrix[i / y][i % y] = matrix[j / y][j % y];
		matrix[j / y][j % y] = tmp;
	}

	// Simulates next turn BUT doesn't actually move objects
	void nextTurn() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					nextTurn[i][j] = -1;// creates walls
				} else {
					switch (field[i][j].direction) {
					case 0:
						nextTurn[i][j] = 0;
						break;
					case 1:
						if (field[i][j + 1].direction == 5
								|| field[i][j + 1].direction == 0) {
							// if object wants to move to the right and there is
							// an object who wants to move left, they bounce
							nextTurn[i][j - 1] = field[i][j].type;
							nextTurn[i][j - 1] = 0;
							nextTurn[i][j+1] = field[i][j+1].type;
						}
						else {
							
							nextTurn[i][j] = 0;
							nextTurn[i][j+1] = field[i][j].type;
						}
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					default: nextTurn[i][j-1] = 0;
					}
				}
			}
		}

	}
	//moves objects
	void move(){
		
	}

}
