import model.Maze;
import org.w3c.dom.Document;
import parse.InOut;

import java.util.ArrayList;

/**
 * <h1>PACKAGE_NAME Game</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Game {
    public static void main(String[] args) {
        System.out.println("***** BEGIN *****");

        // If we don't receive the map.xml and the config.txt file we can't start the game
        if (args.length < 2) {
            System.out.println("Not enough program arguments received");
            System.exit(1);
            return;
        }

        String mapXml = args[0];
        Document mapDOM = InOut.loadMapXml(mapXml);
        if (mapDOM == null) {
            System.exit(1);
            return;
        }

        String configTxt = args[1];
        ArrayList<String> configContent = InOut.parseConfigTxt(args[1]);
        if (configContent == null) {
            System.exit(1);
            return;
        }

        String outputXml = args[2];
        if (outputXml == null) {
            System.out.println("Output filename argument not found, will be : output.xml");
            outputXml = "output.xml";
        }

        Maze.getInstance().load(mapDOM);

        System.out.println("***** END *****");
    }
}
