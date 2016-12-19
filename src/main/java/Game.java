import command.Enter;
import command.Historic;
import factory.MazeFactory;
import iterator.Foo;
import log.Log;
import model.Maze;
import model.Player;
import org.w3c.dom.Document;
import parse.InOut;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1>PACKAGE_NAME Game</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Game {

    private Player player;
    private Historic historic;

    public static void main(String[] args) {
        Log.info("***** BEGIN *****");

        Game game = new Game();
        if (!game.configuration(args))
            System.exit(1);

        if (!game.run())
            System.exit(1);

        Log.info("***** END *****");
        System.exit(0);
    }

    public Game() {
        this.historic = new Historic();
    }


    private boolean run() {
        Log.info("===== GAME START =====");
        Iterator roomIterator = player.getLocation().iterator();
        if (roomIterator instanceof Foo)
            ((Foo) roomIterator).setObjectsToFind(player.getObjectsStillToFind());

        while (!player.hasFoundEverything()) {
            Enter enter = (Enter) roomIterator.next();
            enter.setPlayer(player);
            historic.storeCommand(enter);

            historic.executeLast();
            player.seekForObjects();
        }
        Log.success("Player found every objects");
        Log.info("===== GAME END =====");
        return true;
    }

    private boolean configuration(String args[]) {

        // If we don't receive the map.xml and the config.txt file we can't start the game
        if (args.length < 2) {
            Log.error("Not enough program arguments received");
            return false;
        }

        String mapXml = args[0];
        Document mapDOM = InOut.loadMapXml(mapXml);
        if (mapDOM == null)
            return false;

        ArrayList<String> configContent = InOut.parseConfigTxt(args[1]);
        if (configContent == null)
            return false;

        int idInitialRoom;
        try {
            idInitialRoom = Integer.parseInt(configContent.get(0));
        } catch (Exception e) {
            Log.error("Unable to parse the id of the room the player starts in, will be the first room");
            idInitialRoom = 1;
        }

        String outputXml = args[2];
        if (outputXml == null) {
            Log.error("Output filename argument not found, will be : output.xml");
            outputXml = "output.xml";
        }

        Maze.getInstance().load(mapDOM);

        player = MazeFactory.getInstance().createPlayer(Maze.getInstance().getRoom(idInitialRoom), configContent.subList(1, configContent.size()));

        return true;
    }
}
