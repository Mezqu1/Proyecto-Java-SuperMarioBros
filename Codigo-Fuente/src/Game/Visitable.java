package Game;

import Visitors.Visitor;

public interface Visitable {
	public void accept(Visitor v);
}
