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
	//��Ϸ�������
	Random r = new Random();
	
	//��Ϸ�����ߵ��ٶȡ�����ǰһ��ķ����ߵ�����״̬����ײ״̬
	int level;
	int speed;
	String oldTowards;
	boolean die;
	int touch;
	
	//��ͷ������
	SnakePart head;
	SnakePart[] next = new SnakePart[100];// ��ʼ����һ����ͷ
	SnakePart nextSnake;
	ArrayList<SnakePart> body;
	
	
	String towards;// �ߵ��˶�����
	Image bodyCube;// �����ͼƬ
	Image bodyHead;// �����ͼƬ
	BufferedImage bi;// ��������
	Graphics g;// �õ�����Ļ���
	CPanel panel;// ������
	int length;
	int partX;
	int partY;

	ActionAdapter cation = new ActionAdapter() {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("������Ϸ")) {
				init();
			}
			if (e.getActionCommand().equals("�˳���Ϸ"))
				System.exit(0);
			if (e.getActionCommand().equals("��ʼ��Ϸ"))
				System.exit(0);
			if (e.getActionCommand().equals("���񼶱�")) {
				level = 0;
				init();
			}
			if (e.getActionCommand().equals("����Ա����")) {
				level = 1;
				init();
			}
			if (e.getActionCommand().equals("���񼶱�")) {
				level = 2;
				init();
			}
			if (e.getActionCommand().equals("����"))
				JOptionPane.showMessageDialog(null, "���ײ���̰����", "����",
						JOptionPane.INFORMATION_MESSAGE);
			if (e.getActionCommand().equals("��Ϸ˵��"))
				JOptionPane.showMessageDialog(null, "��������", "��Ϸ˵��",
						JOptionPane.INFORMATION_MESSAGE);
			;
		}
	};

	public Test() {
		// �����˵� ��ʼ---����
		MenuBar menuBar = new MenuBar();
		Menu start = new Menu("��ʼ");
		Menu about = new Menu("����");
		MenuItem aboutGame = new MenuItem("����");
		MenuItem introuduceGame = new MenuItem("��Ϸ˵��");
		about.add(introuduceGame);
		about.add(aboutGame);
		introuduceGame.addActionListener(cation);
		aboutGame.addActionListener(cation);
		Menu startGame = new Menu("��ʼ��Ϸ");
		MenuItem birdLevel = new MenuItem("���񼶱�");
		birdLevel.addActionListener(cation);
		MenuItem proLevel = new MenuItem("����Ա����");
		proLevel.addActionListener(cation);
		MenuItem godLevel = new MenuItem("���񼶱�");
		godLevel.addActionListener(cation);
		startGame.add(birdLevel);
		startGame.add(proLevel);
		startGame.add(godLevel);

		MenuItem resetGame = new MenuItem("������Ϸ");
		resetGame.addActionListener(cation);
		MenuItem exitGame = new MenuItem("�˳���Ϸ");
		exitGame.addActionListener(cation);

		start.add(startGame);
		start.add(resetGame);
		start.addSeparator();
		start.add(exitGame);
		menuBar.add(start);
		menuBar.add(about);
		// �������м��ϲ˵�
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
		// ���ô���Ϊ�ɼ�
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
		head = new SnakePart(0, 0);// ��ʼ��һ����ͷ����
		body = new ArrayList<SnakePart>();
		bodyCube = new ImageIcon("cube.png").getImage();// �����ͼƬ
		bodyHead = new ImageIcon("head.png").getImage();// �����ͼƬ
		bi = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);// ��������
		g = bi.getGraphics();// �õ�����Ļ���
		die = false; // �ߵ�����״̬

		body.add(head);// ����ͷ���뵽������

		Graphics g = bi.getGraphics();// �õ�����
		g.setColor(Color.WHITE);// ���û�����ɫΪ��ɫ
		g.fillRect(0, 0, 500, 400);// �Ѵ����Ϊ��ɫ
		g.drawImage(bodyHead, head.getX(), head.getY(), null);// ������ʼ��ͷ
		panel.setB(bi);
		repaint();// ������ͼ�񻭵�����֮��

	}

	public void start() {
		init();
		while (!die) {
			// ������ͷ������
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
			// �߽绹ûŪ�棬
			if (head.getX() >= 0 && head.getX() <= 482 && head.getY() >= 0
					&& head.getY() <= 336) {
				// ���ͼ��
				if (!towards.equals("stop")) {
					g.fillRect(0, 0, 500, 400);
				}
				// �����ɢ��������
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

				// ��ɢ������ͼ��
				g.drawImage(bodyCube, next[body.size() - 1].getX(),
						next[length - 1].getY(), null);

				// ������ͷ������
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

					// �ж���ͷ��������������Ƿ��غ�
					if (next[length - 1].getX() == head.getX()
							&& next[length - 1].getY() == head.getY()) {
						// ���غϣ���������뵽body��
						body.add(next[length - 1]);
					}

					// ����ǰ����������ͷ���꣬��������
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
				JOptionPane.showMessageDialog(null, "��Ϸ����", "��Ϸ���", 1);
				die = true;
			}

			repaint();

			// �ӳ�300����
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
