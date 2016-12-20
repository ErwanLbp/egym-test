package command;

import model.Room;
import org.w3c.dom.Document;

/**
 * <h1>command Command</h1>
 * Interface to modelize the command pattern, it can execute itself and undo what it did
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public interface Command {

    /**
     * Execute the command
     */
    void execute();

    /**
     * Undo the command
     */
    void undo();

    /**
     * Add the command to the DOM document
     *
     * @param document The DOM object to add the command
     */
    void append(Document document);
}
