package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Test extends JFrame {
	//游戏的随机器
	Random r = new Random();
	
	//游戏级别、蛇的速度、蛇在前一秒的方向、蛇的死亡状态、碰撞状态
	int level;
	int speed;
	String oldTowards;
	boolean die;
	int touch;
	
	//蛇头、蛇身
	SnakePart head;
	SnakePart[] next = new SnakePart[100];// 初始化下一个蛇头
	SnakePart nextSnake;
	ArrayList<SnakePart> body;
	
	
	String towards;// 蛇的运动方向
	Image bodyCube;// 蛇身的图片
	Image bodyHead;// 蛇身的图片
	BufferedImage bi;// 建立缓冲
	Graphics g;// 得到缓冲的画笔
	CPanel panel;// 主画板
	int length;
	int partX;
	int partY;

	ActionAdapter cation = new ActionAdapter() {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("重新游戏")) {
				init();
			}
			if (e.getActionCommand().equals("退出游戏"))
				System.exit(0);
			if (e.getActionCommand().equals("开始游戏"))
				System.exit(0);
			if (e.getActionCommand().equals("菜鸟级别")) {
				level = 0;
				init();
			}
			if (e.getActionCommand().equals("程序员级别")) {
				level = 1;
				init();
			}
			if (e.getActionCommand().equals("大神级别")) {
				level = 2;
				init();
			}
			if (e.getActionCommand().equals("关于"))
				JOptionPane.showMessageDialog(null, "简易菜鸟贪吃蛇", "关于",
						JOptionPane.INFORMATION_MESSAGE);
			if (e.getActionCommand().equals("游戏说明"))
				JOptionPane.showMessageDialog(null, "正在整理", "游戏说明",
						JOptionPane.INFORMATION_MESSAGE);
			;
		}
	};

	public Test() {
		// 创建菜单 开始---关于
		MenuBar menuBar = new MenuBar();
		Menu start = new Menu("开始");
		Menu about = new Menu("关于");
		MenuItem aboutGame = new MenuItem("关于");
		MenuItem introuduceGame = new MenuItem("游戏说明");
		about.add(introuduceGame);
		about.add(aboutGame);
		introuduceGame.addActionListener(cation);
		aboutGame.addActionListener(cation);
		Menu startGame = new Menu("开始游戏");
		MenuItem birdLevel = new MenuItem("菜鸟级别");
		birdLevel.addActionListener(cation);
		MenuItem proLevel = new MenuItem("程序员级别");
		proLevel.addActionListener(cation);
		MenuItem godLevel = new MenuItem("大神级别");
		godLevel.addActionListener(cation);
		startGame.add(birdLevel);
		startGame.add(proLevel);
		startGame.add(godLevel);

		MenuItem resetGame = new MenuItem("重新游戏");
		resetGame.addActionListener(cation);
		MenuItem exitGame = new MenuItem("退出游戏");
		exitGame.addActionListener(cation);

		start.add(startGame);
		start.add(resetGame);
		start.addSeparator();
		start.add(exitGame);
		menuBar.add(start);
		menuBar.add(about);
		// 给窗体中加上菜单
		setMenuBar(menuBar);

		panel= new CPanel();
		add(panel);
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (!towards.equals("down"))
						towards = "up";
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (!towards.equals("up"))
						towards = "down";
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (!towards.equals("right"))
						towards = "left";
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (!towards.equals("left"))
						towards = "right";
				} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					speed -= 20;
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!towards.equals("stop")) {
						oldTowards = towards;
						towards = "stop";
					} else {
						towards = oldTowards;
					}

				}
			}
		});
		// 设置窗体为可见
		setSize(500, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		if (level == 0)
			speed = 300;
		else if (level == 1)
			speed = 150;
		else if (level == 2)
			speed = 100;
		towards = "stop";
		partX = r.nextInt(23) * 21;
		partY = r.nextInt(9) * 21;
		length = 0;
		head = new SnakePart(0, 0);// 初始化一个蛇头坐标
		body = new ArrayList<SnakePart>();
		bodyCube = new ImageIcon("cube.png").getImage();// 蛇身的图片
		bodyHead = new ImageIcon("head.png").getImage();// 蛇身的图片
		bi = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);// 建立缓冲
		g = bi.getGraphics();// 得到缓冲的画笔
		die = false; // 蛇的死亡状态

		body.add(head);// 将舌头加入到蛇身中

		Graphics g = bi.getGraphics();// 得到画笔
		g.setColor(Color.WHITE);// 设置画笔颜色为白色
		g.fillRect(0, 0, 500, 400);// 把窗填充为白色
		g.drawImage(bodyHead, head.getX(), head.getY(), null);// 画出初始蛇头
		panel.setB(bi);
		repaint();// 将缓冲图像画到画板之上

	}

	public void start() {
		init();
		while (!die) {
			// 计算蛇头的坐标
			if (towards.equals("up")) {
				head.setY(head.getY() - 21);
				head.setX(head.getX());
			} else if (towards.equals("down")) {
				head.setY(head.getY() + 21);
				head.setX(head.getX());
			} else if (towards.equals("left")) {
				head.setX(head.getX() - 21);
				head.setY(head.getY());
			} else if (towards.equals("right")) {
				head.setX(head.getX() + 21);
				head.setY(head.getY());
			}
			// 边界还没弄玩，
			if (head.getX() >= 0 && head.getX() <= 482 && head.getY() >= 0
					&& head.getY() <= 336) {
				// 清空图缓
				if (!towards.equals("stop")) {
					g.fillRect(0, 0, 500, 400);
				}
				// 计算分散蛇身坐标
				if (body.size() != length) {
					boolean is = true;
					int t =0;
					while (is) {
						partX = r.nextInt(23) * 21;
						partY = r.nextInt(9) * 21;
						for(SnakePart sp:body){
							if(!(sp.getX()==partX&&sp.getY()==partY))
								t++;
						}
						if(t==body.size())
							is=false;
					}
					next[body.size() - 1] = new SnakePart(partX, partY);
					length++;
				}

				// 分散蛇身画入图缓
				g.drawImage(bodyCube, next[body.size() - 1].getX(),
						next[length - 1].getY(), null);

				// 计算蛇头的坐标
				// if (towards.equals("up")) {
				// head.setY(head.getY() - 21);
				// head.setX(head.getX());
				// } else if (towards.equals("down")) {
				// head.setY(head.getY() + 21);
				// head.setX(head.getX());
				// } else if (towards.equals("left")) {
				// head.setX(head.getX() - 21);
				// head.setY(head.getY());
				// } else if (towards.equals("right")) {
				// head.setX(head.getX() + 21);
				// head.setY(head.getY());
				// }
				if (!towards.equals("stop")) {

					// 判断蛇头坐标和蛇身坐标是否重合
					if (next[length - 1].getX() == head.getX()
							&& next[length - 1].getY() == head.getY()) {
						// 若重合，将蛇身加入到body中
						body.add(next[length - 1]);
					}

					// 利用前面计算出的蛇头坐标，画出蛇体
					for (int i = 0; i < body.size(); i++) {
						if (i == 0) {
							g.drawImage(bodyHead, head.getX(), head.getY(),
									null);
						} else {
							body.get(i).setX(body.get(i - 1).getPreX());
							body.get(i).setY(body.get(i - 1).getPreY());
							g.drawImage(bodyCube, body.get(i).getX(),
									body.get(i).getY(), null);
						}
					}

				}
			} else {
				JOptionPane.showMessageDialog(null, "游戏结束", "游戏结果", 1);
				die = true;
			}

			repaint();

			// 延迟300毫秒
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		Test t = new Test();
		t.start();
	}

}

class SnakePart {
	private int x;
	private int y;
	private int preX;
	private int preY;

	public SnakePart(int x, int y) {
		this.x = x;
		this.y = y;
		this.preX = x;
		this.preY = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.preX = this.x;
		// this.preY = this.y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		// this.preX = this.x;
		this.preY = this.y;
		this.y = y;
	}

	public int getPreX() {
		return preX;
	}

	public int getPreY() {
		return preY;
	}

}
