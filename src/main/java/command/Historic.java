package command;

import log.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Stack;

/**
 * The invoker of the commands. Contains the historic of the commands
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Historic {

    private Stack<Command> historic;

    public Historic() {
        this.historic = new Stack<>();
    }

    /**
     * @param command The command to add to the Stack historic
     */
    public void storeCommand(Command command) {
        if (command != null)
            historic.push(command);
    }

    /**
     * Pop the last command of the Stack historic and call its undo()
     *
     * @see Command#undo()
     */
    public void undo() {
        Command command = historic.pop();
        Log.info("Undo " + command);
        command.undo();
    }

    /**
     * Convert each command of the Stack historic into a DOM Element<br/>
     * and add it to a DOM Document that will be returned
     *
     * @return A DOM Document containing all the commands
     */
    public Document convertToDOM() {
        // Creation of the Document object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Log.error("Unable to create a DocumentBuilder to convert the route into a DOM object");
            return null;
        }
        Document document = builder.newDocument();

        // Adding the root element
        Element route = document.createElement("route");
        document.appendChild(route);

        // Adding each command
        boolean lastCommandIsAPick = false;
        for (Command command : historic) {
            // To prevent adding the same room after picking an object, it select every command except the Enter right after a PickObject FIXME There might be a proper way to do it
            if (!(lastCommandIsAPick && command instanceof Enter))
                command.append(document);
            lastCommandIsAPick = command instanceof PickObject;
        }
        return document;
    }
}
