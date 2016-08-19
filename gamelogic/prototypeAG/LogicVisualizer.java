package gamelogic;

import java.awt.Font;
import edu.princeton.cs.algs4.StdDraw;


public class LogicVisualizer {
    // Delay in seconds
    private static final int DELAY = 25;

    public static void draw(GameLogic gameLogic) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-gameLogic.getBoardLength() * 0.1, gameLogic.getBoardLength() * 1.2);
        StdDraw.setYscale(-gameLogic.getBoardWidth() * 0.2, gameLogic.getBoardWidth() * 1.2);
        StdDraw.filledSquare(gameLogic.getBoardLength() / 2.0, gameLogic.getBoardLength() / 2.0 + 1,
                gameLogic.getBoardLength() / 2.0);

        // Draw grid
        for (int row = 0; row < gameLogic.getBoardLength(); row++) {
            for (int col = 0; col < gameLogic.getBoardWidth(); col++) {
                Creature cr = (Creature) gameLogic.getGridObjects()[col][row];
                if (cr.getStatus()) {
                    if (cr.getType().equals("CreatureSimple")) {
                        StdDraw.setPenColor(StdDraw.BLACK);
                    } else if (cr.getType().equals("CreatureDependant")) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                    } else if (cr.getType().equals("CreatureNonDependant")) {
                        StdDraw.setPenColor(StdDraw.GREEN);
                    }
                } else {
                    StdDraw.setPenColor(StdDraw.WHITE);
                }
                StdDraw.filledSquare(col + 0.5, gameLogic.getBoardWidth() - row + 0.5, 0.49);
            }
        }

        // Write number of live creatures
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.2 * gameLogic.getBoardLength(), -0.05 * gameLogic.getBoardWidth(),
                "Blue creatures: " + gameLogic.getNumberOfAliveCreatures().get("CreatureDependant"));
        StdDraw.text(0.2 * gameLogic.getBoardLength(), -0.1 * gameLogic.getBoardWidth(),
                "Green creatures: " + gameLogic.getNumberOfAliveCreatures().get("CreatureNonDependant"));
        StdDraw.text(0.2 * gameLogic.getBoardLength(), -0.15 * gameLogic.getBoardWidth(),
                "Black creatures: " + gameLogic.getNumberOfAliveCreatures().get("CreatureSimple"));
    }

    public static void main(String[] args) throws Exception {
        Grid grid = new Grid(25, 25);
        GameLogic gameLogic = new GameLogic(grid);
        //gameLogic.initializeGrid("classic");
        // Turn on animation mode
        StdDraw.enableDoubleBuffering();
        LogicVisualizer.draw(gameLogic);
        StdDraw.show();
        StdDraw.pause(LogicVisualizer.DELAY);
        for (int i = 0; i < 5000; i++) {
            gameLogic.nextTurn();
            LogicVisualizer.draw(gameLogic);
            StdDraw.show();
            StdDraw.pause(LogicVisualizer.DELAY);
        }
        /*
        int greenCount = 0;
        int blueCount = 0;
        int blackCount = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 1000; i++) {
                gameLogic.nextTurn();
                LogicVisualizer.draw(gameLogic);
                StdDraw.show();
                StdDraw.pause(LogicVisualizer.DELAY);
            }
            greenCount += gameLogic.getNumberOfAliveCreatures().get("CreatureNonDependant");
            blueCount += gameLogic.getNumberOfAliveCreatures().get("CreatureDependant");
            blackCount += gameLogic.getNumberOfAliveCreatures().get("CreatureSimple");
            grid = new Grid(25, 25);
            gameLogic = new GameLogic(grid);
        }
        System.out.format("Green: %.1f\n", 1.0 * (greenCount) / (greenCount+blueCount+blackCount));
        System.out.format("Blue: %.1f\n", 1.0 * (blueCount) / (greenCount+blueCount+blackCount));
        System.out.format("Black: %.1f\n", 1.0 * (blackCount) / (greenCount+blueCount+blackCount));
        */
        
    }
}
