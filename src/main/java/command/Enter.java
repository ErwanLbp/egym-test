package command;

import model.Direction;
import model.Player;
import model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A Command to enter a room of the maze
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Enter implements Command {

    private Room room;
    private Direction direction;
    private Player player;

    /**
     * @param player    The player who use the command
     * @param room      The room in which the command take place
     * @param direction The direction of the side to go, it can lead to a Wall
     */
    public Enter(Player player, Room room, Direction direction) {
        this.player = player;
        this.room = room;
        this.direction = direction;
    }

    /**
     * @param player The new player, used when we have access to the player only after creation of the command
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Execute the command<br/>
     * If it's a door, move the player to the room the direction lead, otherwise do nothing
     */
    @Override
    public void execute() {
        if (isPossible())
            player.moveTo(direction);
    }

    /**
     * Undo the command<br/>
     * Move the player to the opposite direction, so it cancel the {@link #execute() execute()}
     */
    @Override
    public void undo() {
        if (isPossible())
            player.moveTo(direction.opposite());
    }

    /**
     * Add a room object in the DOM object, with its id and name
     *
     * @param document The DOM object to add the command
     * @see Historic#convertToDOM()
     */
    @Override
    public void append(Document document) {
        Element roomElement = document.createElement("room");
        roomElement.setAttribute("id", room.getId() + "");
        roomElement.setAttribute("name", room.getName());
        roomElement.setIdAttribute("id", true);
        document.getDocumentElement().appendChild(roomElement);
    }

    /**
     * @return True if it's possible to go through the side pointed by the direction
     */
    private boolean isPossible() {
        return room.getSide(direction).canGoThrough();
    }

    /**
     * @return A string describing the command
     */
    @Override
    public String toString() {
        return "In the room " + room + ", go " + direction;
    }
}
