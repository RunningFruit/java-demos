package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

public class board extends JLabel implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int cellSize = 20;

    private snake ss = new snake();

    public board() {
        this.setPreferredSize(new Dimension(cellSize * 18, cellSize * 18));
        this.setVisible(true);
        ss.init();
        System.out.println("After ss.init()");
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void setKeyType(char keyType) {
        ss.move(keyType);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cellSize * 18, cellSize * 18);
        g.setColor(Color.white);

        int[][] bodyFlag = ss.getSnakeBody();

        for (int i = 0; i < bodyFlag.length; i++) {
            System.out.println("snakeBodyPointFlag[i][0]" + bodyFlag[i][0] + "<-->" + bodyFlag[i][1]);
            g.drawRect((17 - bodyFlag[i][0]) * cellSize, (17 - bodyFlag[i][1]) * cellSize, cellSize, cellSize);
        }
        g.setColor(Color.RED);
        String[] fruitXY = ss.getFruitPosition().split("-");
        // g.drawRect((17 - Integer.valueOf(fruitXY[0])), (17 -
        // Integer.valueOf(fruitXY[1])), cellSize, cellSize);
        g.drawRect((17 - Integer.valueOf(fruitXY[0])) * cellSize, (17 - Integer.valueOf(fruitXY[1])) * cellSize, cellSize, cellSize);
        System.out.println("paint is invoked");
        super.paintComponent(g);
    }

    public void run() {
        System.out.println("运动中");
        System.out.println("ss.getPlayable()" + ss.getPlayable());
        while (true) {
            if (ss.getPlayable()) {
                ss.moveToCurrentDirection();
                try {
                    Thread.sleep(1000 + 500 / ss.getSpeed());
                    this.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("finished!");
                break;
            }
        }
    }


}
