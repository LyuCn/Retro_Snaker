
import javax.swing.*;

public class StartGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setBounds(10, 10, 900, 700);
		frame.setResizable(false);//窗口大小不可变
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 面板 
		frame.add(new GamePanel());
	
	}
}
