package model;

/**
 * The model of an object in the maze<br/>
 * Pretty simple : a name<br/>
 * I named the class with a R because I didn't want any conflict with Object class. I can't remember why I chose a R ...
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class ObjectR {

    private String name;

    /**
     * @param name The name of the object
     */
    public ObjectR(String name) {
        this.name = name;
    }

    /**
     * @return The name of the object
     */
    public String getName() {
        return name;
    }

    /**
     * @param o The object to compare with
     * @return True if the name of the objects are equals, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectR)) return false;

        ObjectR objectR = (ObjectR) o;

        return getName().equals(objectR.getName());
    }

    /**
     * Unused
     *
     * @return The hashcode of the name
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    /**
     * @return A string describing the object
     */
    @Override
    public String toString() {
        return name;
    }
}
