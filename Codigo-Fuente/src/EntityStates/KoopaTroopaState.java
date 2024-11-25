package EntityStates;

import Enemies.KoopaTroopa;

public abstract class KoopaTroopaState {

	protected KoopaTroopa koopaTroopa;
	
	public KoopaTroopaState(KoopaTroopa koopaTroopa) {
		this.koopaTroopa = koopaTroopa;
	}
	

	public abstract void hitFromAbove();

	public abstract void initialize();


	public abstract void setAnimatedSprite();

}
