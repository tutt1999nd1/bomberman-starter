package uet.oop.bomberman.entities.character.enemy.ai;


import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import static uet.oop.bomberman.entities.character.Character._board;

public class Avoid extends AI {
        private Enemy _e;
        private Bomber _player;

        public Avoid(Bomber bomber, Enemy e) {
            _e = e;
            _player = bomber;
        }

        @Override
        public int calculateDirection() {
            int duongthang = duongthangBomb(4);

            if (duongthang == 0){
                return random.nextInt(3) + 1;
            }
            if (duongthang == 1) {
                return (random.nextInt(3) + 2) % 4;
            }
            if (duongthang == 2){
                return (random.nextInt(3) + 3) % 4;
                }
            if (duongthang == 3) {
                return random.nextInt(3);
            }
            if(duongthang==4)return random.nextInt(4);

            int duongcheo=duongcheoBomb(2);
            int x=random.nextInt(4);
            if(duongcheo==0){

                    if(x==0){
                        x=2;
                    }
                    if(x==3){
                    x=1;
                }

                return x;
                }

            if(duongcheo==1){
                if(x==2){
                    x=0;
                }
                if(x==3){
                    x=1;
                }
                return x;
            }
            if(duongcheo==2){
                if(x==0){
                    x=2;
                }
                if(x==1){
                    x=3;
                }return x;
            }
            if(duongcheo==3){
                if(x==1){
                    x=3;
                }
                if(x==2){
                    x=0;
                }return x;
            }
            int duongcheolech=duongcheolechBomb(2);
            int y=random.nextInt(4);
            if(duongcheolech==0){

                if(x==0){
                    x=2;
                }
                if(x==3){
                    x=1;
                }

                return x;
            }

            if(duongcheolech==1){
                if(x==2){
                    x=0;
                }
                if(x==3){
                    x=1;
                }
                return x;
            }
            if(duongcheolech==2){
                if(x==0){
                    x=2;
                }
                if(x==1){
                    x=3;
                }return x;
            }
            if(duongcheolech==3){
                if(x==1){
                    x=3;
                }
                if(x==2){
                    x=0;
                }return x;
            }
            int duongcheolechx=duongcheolechXBomb(2);
            int z=random.nextInt(4);
            if(duongcheolechx==0){

                if(x==0){
                    x=2;
                }
                if(x==3){
                    x=1;
                }

                return x;
            }

            if(duongcheolechx==1){
                if(x==2){
                    x=0;
                }
                if(x==3){
                    x=1;
                }
                return x;
            }
            if(duongcheolechx==2){
                if(x==0){
                    x=2;
                }
                if(x==1){
                    x=3;
                }return x;
            }
            if(duongcheolechx==3){
                if(x==1){
                    x=3;
                }
                if(x==2){
                    x=0;
                }return x;
            }

            return -1;
        }

        private int duongthangBomb(int khoangcach) {
            int x = _e.getXTile();
            int y = _e.getYTile();
            if(_board.getBombAtBoss(x,y)!=null)return 4;
            for (int i = 0; i < khoangcach; i++) {
                if (_board.getBombAt(x, y - 0- i) != null) return 0;
                if (_board.getBombAtBoss(x, y - 1 - i) != null) return 0;
            }
            for (int i = 0; i < khoangcach; i++) {
                if (_board.getBombAt(x + 0 + i, y) != null) return 1;
                if (_board.getBombAtBoss(x + 1 + i, y) != null) return 1;
            }
            for (int i = 0; i < khoangcach; i++) {
                if (_board.getBombAt(x, y + 0 + i) != null) return 2;
                if (_board.getBombAtBoss(x, y + 1 + i) != null) return 2;
            }

            for (int i = 0; i < khoangcach; i++) {
                if (_board.getBombAt(x - 0 - i, y) != null) return 3;
                if (_board.getBombAtBoss(x - 1 - i, y) != null) return 3;
            }
            return -1;
        }

            private int duongcheoBomb(int khoangcach) {
                int x = _e.getXTile();
                int y = _e.getYTile();//duong cheo
            for (int i = 0; i < khoangcach; i++){
                if (_board.getBombAt(x  - i, y-i) != null) return 0;
                if (_board.getBombAtBoss(x  - i, y-i) != null) return 0;}
            for (int i = 0; i < khoangcach; i++){
                if (_board.getBombAt(x - i, y+i) != null) return 1;
                if (_board.getBombAtBoss(x - i, y+i) != null) return 1;}
            for (int i = 0; i < khoangcach; i++){
                if (_board.getBombAt(x  +i, y-i) != null) return 2;
                if (_board.getBombAtBoss(x  + i, y-i) != null) return 2;}
            for (int i = 0; i < khoangcach; i++){
                if (_board.getBombAt(x  +i, y+i) != null) return 3;
                if (_board.getBombAtBoss(x + i, y+i) != null) return 3;}

            return -1;
        }
    private int duongcheolechBomb(int khoangcach) {
        int x = _e.getXTile();
        int y = _e.getYTile();//duong cheo
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  - i, y-1-i) != null) return 0;
            if (_board.getBombAtBoss(x  - i, y-1-i) != null) return 0;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x - i, y+1+i) != null) return 1;
            if (_board.getBombAtBoss(x - i, y+1+i) != null) return 1;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  +i, y-1-i) != null) return 2;
            if (_board.getBombAtBoss(x  + i, y-1-i) != null) return 2;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  +i, y+1+i) != null) return 3;
            if (_board.getBombAtBoss(x + i, y+1+i) != null) return 3;}

        return -1;
    }
    private int duongcheolechXBomb(int khoangcach) {
        int x = _e.getXTile();
        int y = _e.getYTile();//duong cheo
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  -1- i, y-i) != null) return 0;
            if (_board.getBombAtBoss(x -1- i, y-i) != null) return 0;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x -1- i, y+i) != null) return 1;
            if (_board.getBombAtBoss(x - 1-i, y+i) != null) return 1;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  +1+i, y-i) != null) return 2;
            if (_board.getBombAtBoss(x  + 1+i, y-i) != null) return 2;}
        for (int i = 0; i < khoangcach; i++){
            if (_board.getBombAt(x  +1+i, y+i) != null) return 3;
            if (_board.getBombAtBoss(x +1+ i, y+i) != null) return 3;}

        return -1;
    }}


