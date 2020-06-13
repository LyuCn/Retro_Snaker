
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;


//��Ϸ���
public class GamePanel extends JPanel implements ActionListener{
	
	// �����ߵ����ݽṹ
	int length;//����
	int[] snakeX = new int[600];//x����
	int[] snakeY = new int[500];//y����
	int level;
	
	
	String current_fx; //��ʼ����
	String before_fx;//ԭ������
	// ʳ������
	int foodx;
	int foody;
	Random random = new Random();
	
	int score; //�ɼ�
	
	// ��Ϸ��ǰ״̬
	boolean isStart = false; 	//Ĭ�ϲ���ʼ
	
	boolean isFail = false; //��Ϸ״̬
	
	// ��ʱ��
	Timer timer = new Timer(100, this);	 //100msִ��һ��
	
	
	// ��ʼ������
	public void init() {
		length = 3;
		snakeX[0] = 100;snakeY[0] = 100;//ͷ������
		snakeX[1] = 75;snakeY[1] = 100;//��һ����������
		snakeX[2] = 50;snakeY[2] = 100;//��������������
		current_fx = before_fx = "R";
		//ʳ������ֲ�
		foodx = 25 + 25*random.nextInt(34);
		foody = 75 + 25*random.nextInt(24);
		score = 0;
		level = 1;
		
		timer.start();//��Ϸ��ʼ��������ʱ��
		
	}
	
	// ������
	public GamePanel() {
		// TODO Auto-generated constructor stub
		init();
		//��ý���ͼ����¼�
		this.setFocusable(true);//��ý����¼�
		this.addKeyListener(new KeySpaceListener()); //��ü��̼����¼�
	}
	
	
	//�������,��Ϸ�е����ж�����ʹ���������
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g); //����
		this.setBackground(Color.black);
		//����
		Data.header.paintIcon(this, g, 25, 11);
		g.fillRect(25, 75, 850, 600);
		
		//������
		g.setColor(Color.white);
		g.setFont(new Font("΢���ź�",Font.BOLD,18));
		g.drawString("���ȣ� "+length, 750, 20);
		g.drawString("������ "+score, 750, 40);
		g.drawString("�ȼ��� "+level, 750, 60);

		
		// ��ʳ��
		Data.food.paintIcon(this, g, foodx, foody);

		//��С��
		if(current_fx.equals("R")) {
			Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);//ͷ������
		}else if(current_fx.equals("L")) {
			Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);//ͷ������
		}else if(current_fx.equals("U")) {
			Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);//ͷ������
		}else if(current_fx.equals("D")) {
			Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);//ͷ������
		}
		for(int i = 1; i < length; i++) {
			Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);//����1����
		}
		
	
		
		// ��Ϸ״̬
		if(isStart == false) {
			g.setColor(Color.white);
			g.setFont(new Font("΢���ź�",Font.BOLD,40));
			// ��������
			g.drawString("���¿ո�ʼ��Ϸ��",300,300);
		}
		
		if(isFail == true) {
			g.setColor(Color.red);
			g.setFont(new Font("΢���ź�",Font.BOLD,40));
			// ��������
			g.drawString("ʧ�ܣ����ո����¿�ʼ��",300,300);
		}
	}
	
	
	
	// ���̼���
	class KeySpaceListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_SPACE) {
				if(isFail) {
				//���¿�ʼ
					isFail = false;
					init();
				}else {
					isStart = !isStart;
					repaint();
				}
				
			}
			
			//С���ƶ�
			if(keyCode == KeyEvent.VK_UP) {
				if(before_fx.equals("D")) {
					current_fx = "D";
				}else {
					current_fx = before_fx = "U";
				}
				
			}else if(keyCode == KeyEvent.VK_DOWN) {
				if(before_fx.equals("U")) {
					current_fx = "U";
				}else {
					current_fx = before_fx = "D";
				}
				
			}else if(keyCode == KeyEvent.VK_LEFT) {
				if(before_fx.equals("R")) {
					current_fx = "R";
				}else {
					current_fx = before_fx= "L";
				}
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				if(before_fx.equals("L")) {
					current_fx = "L";
				}else {
					current_fx = before_fx= "R";
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}


	// �¼�����  1s = 10��
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(isStart && isFail == false) { //��ο�ʼ���߶�
			
			
			//��ʳ��
			if(snakeX[0] == foodx && snakeY[0] == foody) {
				length++;//���ȼ�1
				score+=10;//������10
				
				// �����Ѷ�
				if(score % 100 == 0) {
					level+=1;
					if(level>=10) {
						level = 9;
					}
				}
				timer.setDelay(110 - 10*level);
				
				//���ʳ��
				foodx = 25 + 25*random.nextInt(34);
				foody = 75 + 25*random.nextInt(24);
				
			}
			
			//����
			for(int i =length-1; i>0; i--) {//��ǰ�ƶ�,��һ���ƶ���ǰһ��
				snakeX[i] = snakeX[i-1];
				snakeY[i] = snakeY[i-1];
			}
			
			// ����
			if(current_fx.equals("R")) {
				snakeX[0] = snakeX[0]+25;
				// �߽��ж�
				if(snakeX[0]>850) {
					snakeX[0]=25;
				}
			}else if(current_fx.equals("L")) {
				snakeX[0] = snakeX[0]-25;
				// �߽��ж�
				if(snakeX[0]<25) {
					snakeX[0]=850;
				}
			}else if(current_fx.equals("U")) {
				snakeY[0] = snakeY[0]-25;
				// �߽��ж�
				if(snakeY[0] < 75) {
					snakeY[0]=650;
				}
			}else if(current_fx.equals("D")) {
				snakeY[0] = snakeY[0]+25;
				// �߽��ж�
				if(snakeY[0]>650) {
					snakeY[0]=75;
				}
			}
			//ʧ���ж�,ײ���Լ�
			for(int i =1; i<length;i++) {
				if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i] ) {
					isFail = true;
				}
			}
			
			repaint();	//�ػ�ҳ��
		}
		
	}
}




