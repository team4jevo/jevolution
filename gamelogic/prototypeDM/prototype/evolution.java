
public class evolution {

	/**
	 * @param args
	 */
	private static final int DELAY = 50;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 50;
		int y = 50;
		Grid test = new Grid(x, y);
		test.setUpField();
		StdDraw.enableDoubleBuffering();
		test.draw();
		StdDraw.show();
		StdDraw.pause(DELAY);
		while (true) {
            test.changeCell();
            test.draw();
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
		

	}
}
