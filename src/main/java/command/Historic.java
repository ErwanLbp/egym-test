package command;

import log.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Stack;

/**
 * <h1>command Historic</h1>
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

    public void storeCommand(Command command) {
        historic.push(command);
    }

    public void executeAll() {
        for (Command command : historic)
            command.execute();
    }

    public void undoLast() {
        Command command = historic.pop();
        Log.info("Undo " + command);
        command.undo();
    }

    public void executeLast() {
        historic.peek().execute();
    }

    public Document convertToDOM() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Log.error("Unable to create a DocumentBuilder to convert the route into a DOM object");
            return null;
        }
        Document document = builder.newDocument();
        Element route = document.createElement("route");
        document.appendChild(route);

        boolean lastCommandIsAPick = false;
        for (Command command : historic) {
            if (!(lastCommandIsAPick && command instanceof Enter))
                command.append(document);
            lastCommandIsAPick = command instanceof PickObject;
        }

        return document;
    }
}
