import java.util.Random;

public class Grid {
	Cell[][] grid;
	boolean[][] cellChecker;
	Random random;
	int x;
	int y;

	public Grid(int x, int y) {
		grid = new Cell[y][x];
		cellChecker = new boolean[y][x];
		random = new Random();
		this.x = x;
		this.y = y;
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	void setUpField() {
		Random random = new Random();
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					grid[i][j] = new Cell(-1);
				} else {
					grid[i][j] = new Cell(random.nextInt(2));
				}

			}
		}
		System.out.println("Field was made");
		//System.out.println("-------------------");
	}
	void changeCell() {
		int counter = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					continue;
				} else {
					if (grid[i][j].isAlive == 1) {
						counter = checkAdjanced(grid, i, j);
						if (counter >= 4 || counter <= 1) {
							// field[i][j].isAlive = 0;
							cellChecker[i][j] = false;
						}
					} else if (grid[i][j].isAlive == 0) {
						counter = checkAdjanced(grid, i, j);
						if (counter == 3) {
							// field[i][j].isAlive = 1;
							cellChecker[i][j] = true;
						}
					}

				}

			}
		}
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					continue;
				}
				if (cellChecker[i][j] == false) {
					grid[i][j].isAlive = 0;
				} else {
					grid[i][j].isAlive = 1;
				}
			}
		}
	}
	void changeCell2(){
		int counter = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					continue;
				} else {
					if (grid[i][j].isAlive == 1) {
						if (grid[i][j].type == 1){
							
						}
						counter = checkAdjanced(grid, i, j);
						if (counter >= 4 || counter <= 1) {
							// field[i][j].isAlive = 0;
							cellChecker[i][j] = false;
						}
					} else if (grid[i][j].isAlive == 0) {
						counter = checkAdjanced(grid, i, j);
						if (counter == 3) {
							// field[i][j].isAlive = 1;
							cellChecker[i][j] = true;
						}
					}

				}

			}
		}
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					continue;
				}
				if (cellChecker[i][j] == false) {
					grid[i][j].isAlive = 0;
				} else {
					grid[i][j].isAlive = 1;
				}
			}
		}
	}
	

	int checkAdjanced(Cell[][] field, int i, int j) {
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
	
	void printField() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					System.out.print('*');
				} else {
					if (grid[i][j].isAlive == 1) {
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
	 void draw() {
		StdDraw.clear();
		//StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(-x * 0.1, x * 1.2);
		StdDraw.setYscale(-y * 0.2, y * 1.2);
		StdDraw.filledSquare(x / 2.0, x / 2.0 +1, x / 2.0);
		
		//Draw grid
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (grid[i][j].isAlive == -1) {
					StdDraw.setPenColor(StdDraw.BLACK);
				} else if (grid[i][j].isAlive == 1){
					StdDraw.setPenColor(StdDraw.BLUE);
					
				}else if (grid[i][j].isAlive == 0){
					StdDraw.setPenColor(StdDraw.WHITE);
				}
				StdDraw.filledSquare(j + 0.5, y - i + 0.5, 0.49);

			}
		}
	}
	// void checkSides



}
