package snake;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class snake {

	private static final int[][] mapPoints = new int[18][18];
	private static int bodyLength = 4;

	private static int headX = 5, headY = 5;
	private static int fruitX = 8, fruitY = 8;
	private static char direction = 'w';// 上下颠倒,左右不颠倒
	// build snake initial body
	private static List<String> snakeLine = new LinkedList<String>();
	private boolean isFail = false;
	private static int speed = 1;

	public void init() {

		// build map without walls and snake
		for (int i = 0; i < mapPoints.length; i++) {
			for (int j = 0; j < mapPoints.length; j++) {
				mapPoints[i][j] = 0;
			}
		}
		// set snake body points
		for (int i = 0; i < bodyLength; i++) {
			mapPoints[i + 4][5] = 1;
			snakeLine.add((i + 4) + "-" + 5);
		}
		// build wall:flag=2
		for (int i = 0; i < mapPoints.length; i++) {
			mapPoints[i][0] = 2;
			mapPoints[i][17] = 2;
			mapPoints[0][i] = 2;
			mapPoints[17][i] = 2;
		}
		mapPoints[fruitX][fruitY] = 3;
		System.out.println("snake initial");
	}

	public String getFruitPosition() {
		return fruitX + "-" + fruitY;
	}

	public void move(char keyboard) {
		System.out.println("-----snake head:" + snakeLine.get(snakeLine.size() - 1));
		// for (int i = 0; i < mapPoints.length; i++) {
		// System.out.println("");
		// for (int j = 0; j < mapPoints.length; j++) {
		// System.out.print(" " + mapPoints[17 - j][17 - i]);//打印控制台地图
		// }
		// }
		// 按键keyboard与direction方向相反时需要进行处理,这里应当取direction作为惯性方向,而不是keyboard所代表的方向
		switch (direction) {
		// up
		case 'w':
		case 'i':
		case KeyEvent.VK_KP_UP:
			if (keyboard != 'a' || keyboard != 'k') {
				direction = keyboard;
				System.out.println("w-->up");
			} else {
				break;
			}
			if (!isFail) {
				if (headY < 17) {
					// 判断触碰蛇身
					judge(headX, headY + 1);
					headY++;
					moveBody();
				} else if (headY == 17) {
					isFail = true;
				}
			}
			break;
		// down
		case 's':
		case 'k':
		case KeyEvent.VK_KP_DOWN:
			if (keyboard != 'w' || keyboard != 'i') {
				direction = keyboard;
				System.out.println("s-->down");
			} else {
				break;
			}
			if (!isFail) {

				if (headY > 1) {
					// 判断触碰蛇身
					judge(headX, headY - 1);
					headY--;
					moveBody();
				} else if (headY == 1) {
					isFail = true;
				}
			}
			break;
		// left
		case 'a':
		case 'j':
		case KeyEvent.VK_KP_LEFT:
			if (keyboard != 'd' || keyboard != 'l') {
				direction = keyboard;
				System.out.println("a-->left");
			} else {
				break;
			}
			if (!isFail) {
				if (headX < 17) {
					judge(headX + 1, headY);
					headX++;
					moveBody();
				} else if (headX == 17) {
					isFail = true;
				}
			}
			break;
		// right
		case 'd':
		case 'l':
		case KeyEvent.VK_KP_RIGHT:
			if (keyboard != 'a' || keyboard != 'j') {
				direction = keyboard;
				System.out.println("d-->right");
			} else {
				break;
			}
			if (!isFail) {
				if (headX > 1) {
					// 判断触碰蛇身
					judge(headX - 1, headY);
					headX--;
					moveBody();
				} else if (headY == 1) {
					isFail = true;
				}
			}
			break;
		}
	}

	private void moveBody() {
		int tailX = Integer.valueOf(snakeLine.get(0).split("-")[0]);
		int tailY = Integer.valueOf(snakeLine.get(0).split("-")[1]);
		mapPoints[tailX][tailY] = 0;
		snakeLine.remove(0);
		mapPoints[headX][headY] = 1;
		snakeLine.add(headX + "-" + headY);
	}

	private void appendSection() {
		mapPoints[headX][headY] = 1;
		snakeLine.add(headX + "-" + headY);
	}

	private void judge(int a, int b) {
		// 判断触碰蛇身
		if (mapPoints[a][b] == 1 || mapPoints[a][b] == 2) {
			isFail = true;
		}
		// 判断触碰水果
		else if (mapPoints[a][b] == 3) {
			appendSection();
			produceFruit();
		}
	}

	private void produceFruit() {
		Random rand = new Random();
		while (true) {
			fruitX = rand.nextInt(16) + 1;
			fruitY = rand.nextInt(16) + 1;
			if (!(snakeLine.contains(fruitX + "-" + fruitY))) {
				mapPoints[fruitX][fruitY] = 3;
				break;
			}
		}
	}

	public int[][] getSnakeBody() {
		Iterator<String> it = snakeLine.iterator();
		int[][] XY = new int[snakeLine.size()][2];
		int size = snakeLine.size();
		while (it.hasNext()) {
			String tmp = it.next();
			size--;
			XY[size][0] = Integer.valueOf(tmp.split("-")[0]);
			XY[size][1] = Integer.valueOf(tmp.split("-")[1]);
			System.out.println(XY[size][0] + "---" + XY[size][1]);
		}
		System.out.println("from board.paintComponent() invoke int[][] getSnakeBody()");
		return XY;
	}

	public boolean getPlayable() {
		return !isFail;
	}

	public void moveToCurrentDirection() {
		move(direction);
	}

	public int getSpeed() {
		speed = snakeLine.size() / 4;
		return speed;
	}

}
