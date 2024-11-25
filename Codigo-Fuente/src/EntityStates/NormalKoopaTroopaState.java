package EntityStates;

import Enemies.KoopaTroopa;

public class NormalKoopaTroopaState extends KoopaTroopaState {


	public NormalKoopaTroopaState(KoopaTroopa koopaTroopa) {
		super(koopaTroopa);
	}

	@Override
	public void hitFromAbove() {
		koopaTroopa.swapState(new KoopaTroopaAliveShellState(koopaTroopa));
		koopaTroopa = null;
	}

	@Override
	public void initialize() {
		koopaTroopa.startAutomaticMovement(koopaTroopa.getRandomDirection());
		koopaTroopa.setWidth(36);
		koopaTroopa.setHeight(54);
		setNormalSprite();
	}

	private void setNormalSprite() {
		if (koopaTroopa.getSprite() != null)
			koopaTroopa.getSprite().setCurrentImagePath(koopaTroopa.getSprite().getBaseImagePath().concat("KoopaWalking" + koopaTroopa.getLastFacingDirection() + ".gif"));
	}

	@Override
	public void setAnimatedSprite() {
		if (koopaTroopa.getSprite() != null)
			koopaTroopa.getSprite().setCurrentImagePath(koopaTroopa.getSprite().getBaseImagePath().concat("KoopaWalking" + koopaTroopa.getLastFacingDirection() + ".gif"));
	}
	
}
