package uet.oop.bomberman.entities.character.enemy.ai;


import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AiAvoid extends AI{
    private AILow aiLow;
    private Avoid aiAvoid;

    public AiAvoid(Bomber player, Enemy enemy){
        aiLow= new AILow(player,enemy);
        aiAvoid= new Avoid(player,enemy);
    }
    @Override
    public int calculateDirection() {
        int directionAvoid = aiAvoid.calculateDirection();
        if (directionAvoid != -1) return directionAvoid;
        return aiLow.calculateDirection();
    }

}
