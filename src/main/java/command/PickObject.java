package command;

import model.ObjectR;
import model.Player;
import model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <h1>command PickObject</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class PickObject implements Command {

    private Player player;
    private Room room;
    private ObjectR objectR;

    public PickObject(Player player, Room room, ObjectR objectR) {
        this.player = player;
        this.room = room;
        this.objectR = objectR;
    }

    @Override
    public void execute() {
        if (room.contains(objectR))
            player.pickObject(objectR);
    }

    @Override
    public void undo() {
        player.dropObject(objectR);
    }

    @Override
    public void append(Document document) {
        Element roomElement = document.createElement("room");
        roomElement.setAttribute("id", room.getId() + "");
        roomElement.setAttribute("name", room.getName());
        roomElement.setIdAttribute("id", true);
        document.getDocumentElement().appendChild(roomElement);

        Element objectElement = document.createElement("object");
        objectElement.setAttribute("name", objectR.getName());
        roomElement.appendChild(objectElement);
    }

    @Override
    public String toString() {
        return "In the room " + room + ", pick object " + objectR;
    }
}
