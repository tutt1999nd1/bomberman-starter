package uet.oop.bomberman.entities.character.enemy.ai;


import com.sun.org.apache.regexp.internal.RE;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AILow extends AI {
		Bomber _player;
		Enemy _e;
		public AILow(Bomber player, Enemy e){
			_player=player;
			_e=e;

		}
		@Override
		public int calculateDirection() {
			return random.nextInt(4);
		}

}
