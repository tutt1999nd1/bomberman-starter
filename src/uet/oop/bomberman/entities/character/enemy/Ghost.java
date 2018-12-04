package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.ai.*;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Ghost extends Enemy{
    public Ghost(int x, int y, Board board) {
        super(x, y, board, Sprite.ghost_dead, Game.getBomberSpeed() / 3, 100);

        _sprite = Sprite.ghost_left1;

        _ai = new AiGhost(board.getBomber(),this);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.ghost_right1, Sprite.ghost_right2, Sprite.ghost_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.ghost_left1, Sprite.ghost_left2, Sprite.ghost_left3, _animate, 60);
                break;
        }
    }
    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double xr = _x, yr = _y - 16; //subtract y to get more accurate results

        //the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
        //we avoid the shaking inside tiles with the help of steps
        if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; }
        if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
        if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
        if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}

        int xx = Coordinates.pixelToTile(xr) +(int)x;
        int yy = Coordinates.pixelToTile(yr) +(int)y;

        Entity a = _board.getEntity(xx, yy, this); //entity of the position we want to go

        return collide(a);
    }
    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber
        if (e instanceof Flame) kill();
        if (e instanceof Bomber) ((Bomber) e).kill();
        if(e instanceof Wall)return false;
        if(e instanceof Brick)return false;
        return true;
    }


    }
