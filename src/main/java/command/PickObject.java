package command;

import model.ObjectR;
import model.Player;
import model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <h1>command PickObject</h1>
 * Command to pick an object in the room
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class PickObject implements Command {

    private Player player;
    private Room room;
    private ObjectR objectR;

    /**
     * @param player  The player which will pick an object
     * @param room    The room where is the player and the object
     * @param objectR The object to pick
     */
    public PickObject(Player player, Room room, ObjectR objectR) {
        this.player = player;
        this.room = room;
        this.objectR = objectR;
    }

    /**
     * Execute the command<br/>
     * If the room contains the object, the player picks it, otherwise do nothing
     */
    @Override
    public void execute() {
        if (room.contains(objectR))
            player.pickObject(objectR);
    }

    /**
     * Undo the command<br/>
     * The player drops the object of the command, so it cancel the execute()
     */
    @Override
    public void undo() {
        player.dropObject(objectR);
    }

    /**
     * Add an object in the DOM object, with its name<br/>
     * Add it to the room of the object if its already in the Document, otherwise add the room to the Document
     *
     * @param document The DOM object to add the command
     */
    @Override
    public void append(Document document) {
        Element roomElement = document.getElementById(room.getId() + "");
        if (roomElement == null) {
            roomElement = document.createElement("room");
            roomElement.setAttribute("id", room.getId() + "");
            roomElement.setAttribute("name", room.getName());
            roomElement.setIdAttribute("id", true);
            document.getDocumentElement().appendChild(roomElement);
        }

        Element objectElement = document.createElement("object");
        objectElement.setAttribute("name", objectR.getName());
        roomElement.appendChild(objectElement);
    }

    /**
     * @return A string describing the command
     */
    @Override
    public String toString() {
        return "In the room " + room + ", pick object " + objectR;
    }
}
