package command;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <h1>model Command</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public interface Command {

    void execute();

    void undo();

    void append(Document document);
}
