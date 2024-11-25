package Visitors;

public interface Visitable {
    public void accept(Visitor v, String direction);
}
