package Visitors;

import Enemies.Enemy;
import Platforms.Platform;
import PowerUps.PowerUp;
import Projectiles.Projectile;

public interface Visitor {
    public void visit(Object toVisit);
    public void visit(Platform platform);
    public void visit(Enemy enemy);
    public void visit(PowerUp powerUp);
    public void visit(Projectile projectile);
}
