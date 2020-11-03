package snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class gameStart extends JFrame {

	private static final long serialVersionUID = 1L;
	private board b = new board();

	public gameStart() {
		super("贪食蛇游戏");
		this.addKeyListener(new adapter());

		this.getContentPane().add(b);

		this.pack();
		this.setLocation(450, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	class adapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keypress -->" + e.getKeyChar());
			b.setKeyType(e.getKeyChar());
		}
	}

	public static void main(String[] args) {
		new gameStart();
	}
}
