package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AiGhost  extends  AI{
    private AIMedium aiMedium;
    private Avoid aiAvoid;

    public AiGhost(Bomber player, Enemy enemy){
        aiMedium= new AIMedium(player,enemy);
        aiAvoid= new Avoid(player,enemy);
    }
    @Override
    public int calculateDirection() {
        int directionAvoid = aiAvoid.calculateDirection();
        if (directionAvoid != -1) return directionAvoid;
        return aiMedium.calculateDirection();
    }
}
