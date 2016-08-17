public class TestAttack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int y = 10;
		int x = 20;

		Object[][] field = new Object[y][x];

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					System.out.print('*');
					field[i][j] = new Object(false);
				} else {
					System.out.print('X');
					field[i][j] = new Object(true);
				}

			}
			System.out.println();
		}
		System.out.println("--------------------------------------");
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (!field[i][j].isAlive()) {
					continue;
				} else {
					// attacking object to the right
					if (field[i][j].getPower() > field[i][j + 1].getPower()) {
						field[i][j + 1].setAlive(false);
						System.out.println("Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower()
								+ " killed Object[" + i + "][" + (j + 1)
								+ "], power: " + field[i][j + 1].getPower());
					} else if (field[i][j].getPower() < field[i][j + 1]
							.getPower()) {
						field[i][j].setAlive(false);
						System.out.println("Object[" + i + "][" + (j + 1)
								+ "], power: " + field[i][j + 1].getPower()
								+ " killed Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower());
					}
					// attacking object downwards
					if (field[i][j].getPower() > field[i + 1][j].getPower()) {
						field[i + 1][j].setAlive(false);
						System.out.println("Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower()
								+ " killed Object[" + (i + 1) + "][" + j
								+ "], power: " + field[i + 1][j].getPower());

					} else if (field[i][j].getPower() < field[i + 1][j]
							.getPower()) {
						field[i][j].setAlive(false);
						System.out.println("Object[" + (i + 1) + "][" + j
								+ "], power: " + field[i + 1][j].getPower()
								+ " killed Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower());
					}
					// attacking object downwards and right
					if (field[i][j].getPower() > field[i + 1][j + 1].getPower()) {
						field[i + 1][j + 1].setAlive(false);
						System.out.println("Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower()
								+ " killed Object[" + (i + 1) + "][" + (j + 1)
								+ "], power: " + field[i + 1][j+1].getPower());
					} else if (field[i][j].getPower() < field[i + 1][j + 1]
							.getPower()) {
						field[i][j].setAlive(false);
						System.out.println("Object[" + (i + 1) + "][" + (j + 1)
								+ "], power: " + field[i+1][j+1].getPower()
								+ " killed Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower());
					}
					// attacking object downwards left
					if (field[i][j].getPower() > field[i + 1][j - 1].getPower()) {
						field[i + 1][j - 1].setAlive(false);
						System.out.println("Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower()
								+ " killed Object[" + (i + 1) + "][" + (j - 1)
								+ "], power: " + field[i + 1][j-1].getPower());
					} else if (field[i][j].getPower() < field[i + 1][j - 1]
							.getPower()) {
						field[i][j].setAlive(false);
						System.out.println("Object[" + (i + 1) + "][" + (j - 1)
								+ "], power: " + field[i+1][j-1].getPower()
								+ " killed Object[" + i + "][" + j
								+ "], power: " + field[i][j].getPower());
					}
				}

			}
		}
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i == 0 || j == 0 || i == y - 1 || j == x - 1) {
					System.out.print('*');
					continue;
				}
				if (field[i][j].isAlive()) {
					System.out.print('X');

				} else {
					System.out.print('-');

				}

			}
			System.out.println();
		}



	}

	public static void setRow(Object[] row) {
		for (Object i : row) {
			if (i.isAlive() == true) {
				System.out.print('X');
			} else
				System.out.print('-');
		}

	}
}
