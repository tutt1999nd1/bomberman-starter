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

public class B2 extends Enemy {

    private List<Bomb> _bombs;

    protected Keyboard _input;
    protected Random random = new Random();

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public B2(int x, int y, Board board) {
        super(x, y, board, Sprite.boss_dead, Game.getBomberSpeed(), 100);

        _sprite = Sprite.boss_left1;
        _bombs = _board.get_bombsBoss();
//
        _input = _board.getInput();
    }
    @Override
    public void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if(_input.W) ya--;
        if(_input.S) ya++;
        if(_input.A) xa--;
        if(_input.D) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _moving = false;
        }

    }
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0

        if (_input.B && Game.getBombRateBoss() > 0 && _timeBetweenPutBombs < 0) {
            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize()); //subtract half player height and minus 1 y position

            placeBomb(xt, yt);
            Game.addBombRateBoss(-1);
            _timeBetweenPutBombs = 30;
        }


    }
    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBombBoss(b);
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
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        // lay to do chinh giua
        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);
        Entity nextEntity = _board.getEntity(tileX, tileY, this);

        return collide(nextEntity);
    }
    private boolean CanmoveLayeredEntity(double x, double y) {
        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);
        Entity nextEntity = _board.getEntity(tileX, tileY, this);
        if(nextEntity instanceof Wall)return false;
        if (nextEntity instanceof LayeredEntity) {
            Entity topEntity = ((LayeredEntity) nextEntity).getTopEntity();
            if (topEntity instanceof Brick) return false;
            return  true;
        }
        return true;
    }


    public void moveCenterX() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.getRealWidth() / 2;
        int tileCenterX = Coordinates.pixelToTile(centerX);
        _x = Coordinates.tileToPixel(tileCenterX) + pixelOfEntity / 2 - _sprite.getRealWidth() / 2;
    }

    public void moveCenterY() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerY = _y - _sprite.getRealHeight() / 2;
        int tileCenterY = Coordinates.pixelToTile(centerY);
        _y = Coordinates.tileToPixel(tileCenterY) + pixelOfEntity / 2 + _sprite.getRealHeight() / 2;
    }

    private void MoveCenter() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;

        if( !CanmoveLayeredEntity(centerX, centerY - pixelOfEntity / 2))moveCenterY();
        if( !CanmoveLayeredEntity(centerX, centerY + pixelOfEntity / 2))moveCenterY();
        if( !CanmoveLayeredEntity(centerX - pixelOfEntity / 2, centerY))moveCenterX();
        if ( !CanmoveLayeredEntity(centerX + pixelOfEntity / 2, centerY))moveCenterX();

    }
    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển

        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;

        if (xa > 0) _direction = 1;
        if (xa < 0) _direction = 3;
        if (ya > 0) _direction = 2;
        if (ya < 0) _direction = 0;
        if (canMove(centerX + xa, centerY + ya)) {
            _x += xa;
            _y += ya;
        }
        MoveCenter();
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy
        if (e instanceof Flame) {
            this.kill();
            return true;
        }

        if (e instanceof Enemy) {
            this.kill();
            return true;
        }




        if (e instanceof Wall) return false;
        if (e instanceof Brick) return false;
        if (e instanceof LayeredEntity) return e.collide(this);
        //xu ly bomb di qua bom lan dau
        if(e instanceof Bomb) {
            double diffX = e.getX()*16 - Coordinates.tileToPixel(getX())/16;
            double diffY = e.getY()*16 - Coordinates.tileToPixel(getY())/16;
            System.out.println(diffX);


            System.out.println(e.getX()*16+" "+Coordinates.tileToPixel(getX()/16));

            if(diffX >- 10 && diffX <6&& diffY >=-22  && diffY <= -8 ){
                return true;
            }

            return false;
        }
        return true;

    }
    public boolean handleCollidePortal() {
        if (_board.detectNoEnemies()) {
            _board.nextLevel();
            return true;
        }

        return false;
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite=Sprite.boss_up1;
                if(_moving)
                _sprite = Sprite.movingSprite(Sprite.boss_up1, Sprite.boss_up2, Sprite.boss_up3, _animate, 20);
                break;
            case 1:
                _sprite=Sprite.boss_right1;
                if(_moving)
                _sprite = Sprite.movingSprite(Sprite.boss_right1, Sprite.boss_right2, Sprite.boss_right3, _animate, 20);
                break;
            case 2:
                _sprite=Sprite.boss_down1;
                if(_moving)
                _sprite = Sprite.movingSprite(Sprite.boss_down1, Sprite.boss_down2, Sprite.boss_down3, _animate, 20);
                break;
            case 3:
                _sprite=Sprite.boss_left1;
                if(_moving)
                _sprite = Sprite.movingSprite(Sprite.boss_left1, Sprite.boss_left2, Sprite.boss_left3, _animate, 20);
                break;
        }
    }


}



