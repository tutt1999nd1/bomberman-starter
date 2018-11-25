package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import java.util.Random;

public class AiCreateboom {
    Bomber _player;
    Enemy _e;
    protected Random random = new Random();
    public AiCreateboom(Bomber player,Enemy e){
        _player=player;
        _e=e;
    }
}
