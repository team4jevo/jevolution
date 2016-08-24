package jevo;

/**
 * Created by LongJohn on 8/19/2016.
 */





    /* interface
        this class should have following stuff
        methods
            Constructor (GraphicEngine ge)
                should take in pointer to graphic engine object
                game engine usage examples
                        field [x][y] = new GameObject("f",x,y);
                        field [x][y].setToken (ge.createToken (field [x][y]));
                            GameObject.setToken connects game object ( creature food etc in logical simulation) with token - representation on UI level
                            each game object that is to be displayed in UI have to be connected to a token
                            game object and token have pointers to each other, that allows them to call methods in both deirections
                        field [x][y].getToken().move(1, -1);
                        field [x][y].move(1, -1);
                            this can be rewritten ( move method can be added to GameObject
                            move method moves token to new position

                        field [x][y].removeToken();
                            removes token
                            is not done yet
                     note that in GameLogic everywhere x and y are Logical coordinates, not graphical in pixels

            newGame ()
                should start new game
                     (currently on newGame remains of previous
            nextTurn ()
                controller calls it each time when new turn is needed
                amount of actions made in one turn is up to a GameLogic - it can be single action of one organism or processing all of the organisms or anything else

    */


public class GameLogic {
    GraphicsEngine ge;
    int tmpX = 0;
    GameObject [][] field; // x y

    public GameLogic (GraphicsEngine ge) {

        this.ge = ge;
    }

    public GraphicsEngine getGe () {
        return ge;
    }

    public void nextTurn ()throws Exception {


    }



    public void newGame ()throws Exception {


    }



}

