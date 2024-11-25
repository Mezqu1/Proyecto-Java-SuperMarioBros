package Visuals;

import Game.GameEntity;

public class EntityObserver extends GraphicsObserver implements Observer {

	private static final long serialVersionUID = -7773635513797255611L;

	public EntityObserver(GameEntity observedEntity) {
		super(observedEntity);
	}
}
