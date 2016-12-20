package game;

import command.Enter;
import command.Historic;
import command.PickObject;
import factory.MazeFactory;
import inout.InOut;
import iterator.ClosestObjectIterator;
import log.Log;
import model.Maze;
import model.ObjectR;
import model.Player;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A game in the maze, with its configuration and save functions
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Game {

    private Player player;
    private Historic historic;
    private String outputFilename;

    public Game() {
        this.historic = new Historic();
    }

    /**
     * The entrance of the program<br/>
     * <b>It has to receive at least <u>2</u> parameters</b> :<br/>
     * <b>First</b> : An xml file with the map of the maze<br/>
     * <b>Second</b> : A file with on the first line the id of the room the player starts in, then on each line an object to pick in the maze<br/>
     * <b>Third (Optional)</b> : The name of the output file, will be "output.xml" if it can't be found<br/>
     *
     * @param args Arguments received by the program
     */
    public static void main(String[] args) {
        Log.info("***** BEGIN *****");

        Game game = new Game();

        // If a problem occurs during configuration, the program can't continue
        if (!game.configuration(args))
            return;

        // If a problem occurs during the running of the game, the program can't continue
        if (!game.run())
            return;

        // If a problem occurs during the saving of the game, the program can't continue
        if (!game.save())
            return;

        Log.info("***** END *****");
        // Proper exit of the program
    }

    /**
     * The course of the game, will make the player move in the maze until he has found every object
     *
     * @return True if everything went well, otherwise false
     */
    public boolean run() {
        Log.info("===== GAME START =====");
        // Get an iterator on the room the player is in
        Iterator roomIterator = player.getLocation().iterator();
        // If the iterator is a ClosestObjectIterator, we have to add the list of objects to find
        // FIXME Not very good... I didn't find an other option, because I wanted to keep the possibility to change the iterator used
        if (roomIterator instanceof ClosestObjectIterator)
            ((ClosestObjectIterator) roomIterator).setObjectsToFind(player.getObjectsStillToFind());

        while (!player.hasFoundEverything()) {
            // Getting the next room to enter in
            Enter enter = (Enter) roomIterator.next();

            // If the iterator consider we don't need to enter an other room, we don't have an Enter command to execute
            if (enter != null) {
                // The iterator decide which room to go without the player, so we have to add it to the command
                // FIXME May be no the best way to do it
                enter.setPlayer(player);
                enter.execute();
                // We store the command to, then, save it to the output file
                historic.storeCommand(enter);
            }

            // At each iteration we look in the room if the player can pick some objects
            List<ObjectR> objectsPlayerNeedInTheRoom = player.seekForObjects();
            for (ObjectR object : objectsPlayerNeedInTheRoom) {
                // For each object the player can pick, we create the command, execute it, and store it in the historic
                PickObject pickObject = new PickObject(player, player.getLocation(), object);
                pickObject.execute();
                historic.storeCommand(pickObject);
            }
        }
        Log.success("Player found every objects");
        Log.info("===== GAME END =====");
        return true;
    }

    public boolean configuration(String args[]) {

        // If we don't receive the map.xml and the config.txt file we can't start the game
        if (args.length < 2) {
            Log.error("Not enough program arguments received");
            return false;
        }

        // Loading of the XML file in a DOM object with the first argument of the program
        // If there is a problem, the program will stop
        Document mapDOM = InOut.loadMapXml(args[0]);
        if (mapDOM == null)
            return false;

        // Loading the configuration file in a list of String with the second argument of the program
        // If there is a problem, the program will stop
        ArrayList<String> configContent = InOut.parseConfigTxt(args[1]);
        if (configContent == null)
            return false;

        // Converting the first line of the configuration to an int : the initial room the player starts in
        // If there is a problem, the program will stop
        // FIXME We could say that the player starts in the first room available if the initial room isn't found
        int idInitialRoom;
        try {
            idInitialRoom = Integer.parseInt(configContent.get(0));
        } catch (Exception e) {
            Log.error("Unable to parse the id of the room the player starts in");
            return false;
        }

        // Getting the third argument of the program in the output filename, if the program didn't received three argument, the output filename will be "output.xml"
        outputFilename = args[2];
        if (outputFilename == null) {
            Log.error("Output filename argument not found, will be : output.xml");
            outputFilename = "output.xml";
        }

        // Converting the DOM object in a maze
        Maze.getInstance().load(mapDOM);

        // Creation of the player with the id of the initial room and the sublist of the configuration, which is the list from the index 1 to the end
        player = MazeFactory.getInstance().createPlayer(Maze.getInstance().getRoom(idInitialRoom), configContent.subList(1, configContent.size()));

        return true;
    }

    /**
     * Save the route to find every objects <br/>
     * It will convert the historic to a DOM object then will save it to the output file
     *
     * @return True if everything went well, otherwise false
     */
    public boolean save() {
        Document docDOM = historic.convertToDOM();
        return InOut.saveOutputXML(docDOM, outputFilename);
    }
}
