package uet.oop.bomberman.gui;


import uet.oop.bomberman.Game;
import uet.oop.bomberman.sound.GameSound;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

	private Game _game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		_game = new Game(frame);

		add(_game);
		GameSound.getIstance().getAudio(GameSound.MENU).play();
		GameSound.getIstance().getAudio(GameSound.MENU).loop();
		_game.setVisible(true);

		setVisible(true);
		setFocusable(true);

	}

	public Game getGame() {
		return _game;
	}
}
