public class StartSimulation {

	public static void main(String[] args) {
		int x = 50;
		int y = 50;
		Field test = new Field(x,y);
		test.setUpField();
		System.out.println("doves: " +test.doveCounter);
		System.out.println("hawks: " +test.hawkCounter);
		StdDraw.enableDoubleBuffering();
		test.draw();
		StdDraw.show();
	}
}
