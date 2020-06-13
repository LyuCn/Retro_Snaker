
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;


//游戏面板
public class GamePanel extends JPanel implements ActionListener{
	
	// 定义蛇的数据结构
	int length;//长度
	int[] snakeX = new int[600];//x坐标
	int[] snakeY = new int[500];//y坐标
	int level;
	
	
	String current_fx; //初始方向
	String before_fx;//原来方向
	// 食物坐标
	int foodx;
	int foody;
	Random random = new Random();
	
	int score; //成绩
	
	// 游戏当前状态
	boolean isStart = false; 	//默认不开始
	
	boolean isFail = false; //游戏状态
	
	// 定时器
	Timer timer = new Timer(100, this);	 //100ms执行一次
	
	
	// 初始化方法
	public void init() {
		length = 3;
		snakeX[0] = 100;snakeY[0] = 100;//头部坐标
		snakeX[1] = 75;snakeY[1] = 100;//第一个身体坐标
		snakeX[2] = 50;snakeY[2] = 100;//第三个身体坐标
		current_fx = before_fx = "R";
		//食物随机分布
		foodx = 25 + 25*random.nextInt(34);
		foody = 75 + 25*random.nextInt(24);
		score = 0;
		level = 1;
		
		timer.start();//游戏开始，启动定时器
		
	}
	
	// 构造器
	public GamePanel() {
		// TODO Auto-generated constructor stub
		init();
		//获得焦点和键盘事件
		this.setFocusable(true);//获得焦点事件
		this.addKeyListener(new KeySpaceListener()); //获得键盘监听事件
	}
	
	
	//绘制面板,游戏中的所有东西都使用这个画笔
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g); //清屏
		this.setBackground(Color.black);
		//绘制
		Data.header.paintIcon(this, g, 25, 11);
		g.fillRect(25, 75, 850, 600);
		
		//画积分
		g.setColor(Color.white);
		g.setFont(new Font("微软雅黑",Font.BOLD,18));
		g.drawString("长度： "+length, 750, 20);
		g.drawString("分数： "+score, 750, 40);
		g.drawString("等级： "+level, 750, 60);

		
		// 画食物
		Data.food.paintIcon(this, g, foodx, foody);

		//画小蛇
		if(current_fx.equals("R")) {
			Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);//头部向右
		}else if(current_fx.equals("L")) {
			Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);//头部向右
		}else if(current_fx.equals("U")) {
			Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);//头部向右
		}else if(current_fx.equals("D")) {
			Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);//头部向右
		}
		for(int i = 1; i < length; i++) {
			Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);//身体1坐标
		}
		
	
		
		// 游戏状态
		if(isStart == false) {
			g.setColor(Color.white);
			g.setFont(new Font("微软雅黑",Font.BOLD,40));
			// 设置字体
			g.drawString("按下空格开始游戏！",300,300);
		}
		
		if(isFail == true) {
			g.setColor(Color.red);
			g.setFont(new Font("微软雅黑",Font.BOLD,40));
			// 设置字体
			g.drawString("失败，按空格重新开始！",300,300);
		}
	}
	
	
	
	// 键盘监听
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
				//重新开始
					isFail = false;
					init();
				}else {
					isStart = !isStart;
					repaint();
				}
				
			}
			
			//小蛇移动
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


	// 事件监听  1s = 10次
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(isStart && isFail == false) { //如何开始，蛇动
			
			
			//吃食物
			if(snakeX[0] == foodx && snakeY[0] == foody) {
				length++;//长度加1
				score+=10;//分数加10
				
				// 增加难度
				if(score % 100 == 0) {
					level+=1;
					if(level>=10) {
						level = 9;
					}
				}
				timer.setDelay(110 - 10*level);
				
				//随机食物
				foodx = 25 + 25*random.nextInt(34);
				foody = 75 + 25*random.nextInt(24);
				
			}
			
			//右移
			for(int i =length-1; i>0; i--) {//向前移动,后一节移动到前一节
				snakeX[i] = snakeX[i-1];
				snakeY[i] = snakeY[i-1];
			}
			
			// 走向
			if(current_fx.equals("R")) {
				snakeX[0] = snakeX[0]+25;
				// 边界判断
				if(snakeX[0]>850) {
					snakeX[0]=25;
				}
			}else if(current_fx.equals("L")) {
				snakeX[0] = snakeX[0]-25;
				// 边界判断
				if(snakeX[0]<25) {
					snakeX[0]=850;
				}
			}else if(current_fx.equals("U")) {
				snakeY[0] = snakeY[0]-25;
				// 边界判断
				if(snakeY[0] < 75) {
					snakeY[0]=650;
				}
			}else if(current_fx.equals("D")) {
				snakeY[0] = snakeY[0]+25;
				// 边界判断
				if(snakeY[0]>650) {
					snakeY[0]=75;
				}
			}
			//失败判定,撞到自己
			for(int i =1; i<length;i++) {
				if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i] ) {
					isFail = true;
				}
			}
			
			repaint();	//重画页面
		}
		
	}
}




