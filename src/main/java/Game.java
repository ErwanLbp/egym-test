import command.Enter;
import command.Historic;
import command.PickObject;
import factory.MazeFactory;
import iterator.ClosestObjectIterator;
import log.Log;
import model.Maze;
import model.ObjectR;
import model.Player;
import org.w3c.dom.Document;
import parse.InOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private String outputFilename;

    public static void main(String[] args) {
        Log.info("***** BEGIN *****");

        Game game = new Game();
        if (!game.configuration(args))
            System.exit(1);

        if (!game.run())
            System.exit(1);

        if (!game.save())
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
        if (roomIterator instanceof ClosestObjectIterator)
            ((ClosestObjectIterator) roomIterator).setObjectsToFind(player.getObjectsStillToFind());

        while (!player.hasFoundEverything()) {
            Enter enter = (Enter) roomIterator.next();
            if (enter != null) {
                enter.setPlayer(player);
                enter.execute();
                historic.storeCommand(enter);
            }

            List<ObjectR> objectsPlayerNeedInTheRoom = player.seekForObjects();
            for (ObjectR object : objectsPlayerNeedInTheRoom) {
                PickObject pickObject = new PickObject(player, player.getLocation(), object);
                pickObject.execute();
                historic.storeCommand(pickObject);
            }
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

        outputFilename = args[2];
        if (outputFilename == null) {
            Log.error("Output filename argument not found, will be : output.xml");
            outputFilename = "output.xml";
        }

        Maze.getInstance().load(mapDOM);

        player = MazeFactory.getInstance().createPlayer(Maze.getInstance().getRoom(idInitialRoom), configContent.subList(1, configContent.size()));

        return true;
    }

    private boolean save() {
        Document docDOM = historic.convertToDOM();
        return InOut.saveOutputXML(docDOM, outputFilename);
    }
}
