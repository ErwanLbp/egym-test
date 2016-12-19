package model;

/**
 * <h1>model ObjectR</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class ObjectR {

    private String name;

    public ObjectR(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectR)) return false;

        ObjectR objectR = (ObjectR) o;

        return getName().equals(objectR.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
