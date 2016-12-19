package command;

import log.Log;

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
}
