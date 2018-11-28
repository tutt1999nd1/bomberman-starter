package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AiAvoid;
import uet.oop.bomberman.entities.character.enemy.ai.Avoid;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BossBomb extends Enemy {

    private List<Bomb> _bombs;

    protected Keyboard _input;
    protected Random random = new Random();

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public BossBomb(int x, int y, Board board) {
        super(x, y, board, Sprite.boss_dead, Game.getBomberSpeed(), 100);

        _sprite = Sprite.boss_left1;
        _bombs = _board.get_bombsBoss();
        _ai = new AiAvoid(board.getBomber(), this);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.movingSprite(Sprite.boss_up1, Sprite.boss_up2, Sprite.boss_up3, _animate, 20);
                break;
            case 1:
                _sprite = Sprite.movingSprite(Sprite.boss_right1, Sprite.boss_right2, Sprite.boss_right3, _animate, 20);
                break;
            case 2:
                _sprite = Sprite.movingSprite(Sprite.boss_down1, Sprite.boss_down2, Sprite.boss_down3, _animate, 20);
                break;
            case 3:
                _sprite = Sprite.movingSprite(Sprite.boss_left1, Sprite.boss_left2, Sprite.boss_left3, _animate, 20);
                break;
        }
    }

    private void detectPlaceBomb() {


        if (Game.getBombRateBoss() > 0 && _timeBetweenPutBombs < 0) {
            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize());
            placeBomb(xt, yt);

            Game.addBombRateBoss(-1);
            _timeBetweenPutBombs = 350;
        }
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRateBoss(1);
            }
        }

    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, _board);
        _board.addBombBoss(b);
    }

    @Override
    public void update() {
        clearBombs();
        animate();

        if (!_alive) {
            afterKill();
            return;
        }

        if (_alive)
            calculateMove();
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        detectPlaceBomb();
    }
    @Override
    public boolean collide(Entity e) {

        if (e instanceof Flame) {
            this.kill();
            return true;
        }
        if(e instanceof Bomb) {
            double diffX = e.getX()*16 - Coordinates.tileToPixel(getX())/16;
            double diffY = e.getY()*16 - Coordinates.tileToPixel(getY())/16;
            System.out.println(diffX);


//            System.out.println(e.getXTile());
            if(diffX >- 10 && diffX <6&& diffY >=-22  && diffY <= -8 ){
                return true;
            }

            return false;
        }
        return true;

    }
}


