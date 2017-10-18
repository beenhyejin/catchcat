import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;//import java.*�� ���Ϸ��� ���� ��� ������ �߰� �ȴ�.
import java.applet.AudioClip;
import java.net.URL;

import javax.swing.*;

public class WhackAMole extends JFrame implements MouseListener {

	JFrame frame;
	private  int mcX;//���콺�� ��ġ��
	private  int mcY;
	private  int points =0;//����
	int pt=8;
	private  int bcX;//������� ��ġ��
	private  int bcY;
	public int delay = 1300;//������ �ӵ�
	PosImageIcon image;
	public int num = 25;//����̰� ��Ÿ�� Ƚ��
	public int number = 25;//Ŭ������ �ʾ������� �ٸ����� Ŭ�������� ������Ű������ ����
	static String playerName;//�÷��̾� �̸�
	Timer timer;
	DrawPanel drawPanel;
	int hp = 5;
	int tmp=1;
	int level =1;
	int k = 0;//�̹��� ������
	Timer t;//������ �ϱ����� Ÿ�̸�
	Timer slowtime;//�ӵ��� �������� �� Ÿ�̸�
	int times = 0;// �ð��� �����ִ� ����
	PosImageIcon doubleImage;
	PosImageIcon background;
	private final int WIDTH = 570;//�������� ���α��� 
	private final int HEIGHT = 570;//�������� ���α���
	int tim = 0;//������ �ϴ� Ÿ�̸� ����
	int aaa= delay *2;
	JLayeredPane lp = new JLayeredPane();
	PosImageIcon cat;
	int catX= 0;
	int ti = 0;
	AudioClip firstBackSound;
	AudioClip secondBackSound;
	AudioClip thirdBackSound;
	AudioClip bang;//����̰� �¾��� �� ���� 
	AudioClip catSound;//����� ���
	AudioClip mouseSound;//�� ���
	AudioClip attackSound;//�㰡 �¾��� �� ����
	AudioClip slowSound;//���������� ����


	JButton next2 = new JButton("���ӹ��");
	JButton start = new JButton("���ӽ���");
	JButton suspend = new JButton("�Ͻ�����");
	JButton cont = new JButton("���");
	JButton end = new JButton("����");
	JLabel scoreLabel = new JLabel("��������: 0��");
	JLabel hpLabel = new JLabel("HP: 5");
	JLabel levelLabel = new JLabel("Level : 1");
	JLabel timing = new JLabel("�ð� : 0��0��");
	JPanel coverPanel;
	JPanel aniPanel;
	JPanel methodPanel;


	public static void main(String[] args){

		//playerName=JOptionPane.showInputDialog("�̸��� �Է����ּ��� :");	// Player�� �̸� �Է�
		WhackAMole gui = new WhackAMole();
		gui.go();      
	}

	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);

		frame.addMouseListener(this);  

		URL url1,url2,url3,url4,url7,url8;
		url1=url2=url3=url4=url7 =url8 = null;

		//		url1 : �ʱ�ȭ�� �������,   url2 : �ִ�ȭ�� �������,   url3 : ����ȭ�� �������
		//		url4 : ����̸� Ŭ�������� ����,  rul5 : ����� ���,   url6 : �� ���,   url7 : �� Ŭ�������� ����
		//		url8 : ���������� ����


		//Obtain and store the audio clips to play
		try
		{
			url1 = new URL ("file", "localhost", "src/�ʱ�ȭ�� �������.wav");
			url2 = new URL ("file", "localhost", "src/�ִ�ȭ�� �������.wav");
			url3 = new URL ("file", "localhost", "src/���ӹ������.wav");
			url4 = new URL ("file", "localhost", "src/��.wav");
			url7 = new URL ("file","localhost","src/�ƾ�.wav");
			url8 = new URL ("file","localhost","src/������.wav");
			firstBackSound = JApplet.newAudioClip (url1);
			secondBackSound = JApplet.newAudioClip (url2);
			thirdBackSound = JApplet.newAudioClip (url3);
			bang = JApplet.newAudioClip (url4);
			attackSound = JApplet.newAudioClip (url7);
			slowSound = JApplet.newAudioClip (url8);
		}

		catch (Exception exception) {}

		firstBackSound.loop();

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(next2);
		buttonPanel.add(start);
		buttonPanel.add(suspend);
		buttonPanel.add(cont);
		buttonPanel.add(end);
	//	buttonPanel.add(timing);

		//delay ����ũ�� �� ���� �׼��� �����ϴ� Timer ����. �ڵ鷯�� ReboundListener��
		timer = new Timer(delay,new TimerListener());
		JPanel scorePanel = new JPanel(new GridLayout(1,3));
		scorePanel.add(scoreLabel);
		scorePanel.add(hpLabel);
		scorePanel.add(levelLabel);
		drawPanel = new DrawPanel();
		frame.getContentPane().add(BorderLayout.NORTH,scorePanel);
		frame.add(lp);
		frame.add(BorderLayout.CENTER, lp);
		frame.add(BorderLayout.SOUTH,buttonPanel);
		frame.setVisible(true);//ȭ���� ���̰� �ϱ� ���� ��

		//next1.addActionListener(new Next1Listener());
		//next2.addActionListener(new Next2Listener());
		start.addActionListener(new StartListener());
		suspend.addActionListener(new SuspendListener());
		cont.addActionListener(new ContListener());
		end.addActionListener(new EndListener());

		T_Listener clockListener = new T_Listener();
		t = new Timer(1000,clockListener);

		coverPanel = new CoverPanel();
		methodPanel = new MethodPanel();

		coverPanel.setBounds(0,0,WIDTH-15,HEIGHT-100);
		drawPanel.setBounds(0,0,WIDTH,HEIGHT-100);	//ȭ�� ũ��
		methodPanel.setBounds(0,0,WIDTH,HEIGHT-100);

		lp.add(drawPanel,new Integer(0));
		lp.add(methodPanel,new Integer(1));
		lp.add(coverPanel,new Integer(2));


		frame.setVisible(true);


	}


	@Override
	public void mouseEntered(MouseEvent arg0) {

	}
	@Override
	public void mouseExited(MouseEvent arg0) {

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
	//mouseListener�� �������̽��̱� ������  mouseListener�� �ִ� �޼ҵ带 ��� �����ؾ߸� �Ѵ�. 

	public void mouseClicked(MouseEvent arg0) {
		mcX = arg0.getLocationOnScreen().x;//getLocationOnScreen�� ��ũ���� ���콺�� Ŭ�������� x��ǥ���� ��Ÿ��
		mcY = arg0.getLocationOnScreen().y;//getLocationOnScreen�� ��ũ���� ���콺�� Ŭ�������� Y��ǥ���� ��Ÿ��

		if(hp>0){
			//1�� 
			if(0<k&&k<=10){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points++;
					scoreLabel.setText("��������: "+points+"��");
					tmp=points;
					drawPanel.repaint();
					timer.restart();	
					bang.play();

				}
			}
			//2��
			if(11<=k&&k<=14){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points++;
					scoreLabel.setText("��������: "+points+"��");
					tmp=points;
					drawPanel.repaint();
					timer.restart();
					bang.play();

				}
			}

			//-2��
			else if(15<=k&&k<=19){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points-=2;
					scoreLabel.setText("��������: "+points+"��");
					tmp=points;
					drawPanel.repaint();
					timer.restart();
					attackSound.play();


				}
			}
			//���Ӽӵ��� 2��� �������� ��
			else if(20<=k&&k<=22){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					SlowTimeListener slowListener = new SlowTimeListener();
					slowtime = new Timer(1000,slowListener);
					slowSound.play();
					slowtime.start();
					drawPanel.repaint();
					timer.restart();

				}
			}
		}
		else if(hp<0){

		}



	}
	//�ʱ�ȭ���� ��Ÿ���� �г�
	class CoverPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/�ʱ�ȭ��.PNG",0,0,this.getWidth(),this.getHeight());
			image.draw(g);
		}
	}

/*	//�ִϸ��̼��� ��Ÿ���� �г�
	class AniPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/ground.gif",0,0,this.getWidth(),this.getHeight());
			cat = new PosImageIcon("src/cat1.png",100,catX,this.getHeight()-150,120,120);			
			PosImageIcon mouse = new PosImageIcon("src/��.png",100,this.getWidth()-150,this.getHeight()-150,120,120);

			if(cat.collide( new Point(mouse.pX,mouse.pY))){
				aniPanel.setFocusable(false);	
				mousetimer.start();	
			}
			image.draw(g);
			cat.draw(g);
			mouse.draw(g);

		}
	}*/

	//��뼳���� ��Ÿ���� �г�
	class MethodPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/���ӹ��.PNG",0,0,this.getWidth(),this.getHeight());

			image.draw(g);					
		}
	}


	//���� ���� �г�
	private class DrawPanel extends JPanel{
		public void paintComponent (Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			background = new PosImageIcon("src/ground.gif",0,0,this.getWidth(),this.getHeight());
			background.draw(g);

			bcX = (int)(Math.random()*450)+1;
			bcY = (int)(Math.random()*400)+1;

			k = (int)(Math.random()*22)+1;

			if(0<k&&k<=6){
				image = new PosImageIcon("src/img/cat2.PNG",bcX,bcY,70,70);
			}
			else if(7<=k&&k<=12){
				image = new PosImageIcon("src/img/cat3.PNG",bcX,bcY,60,60);
			}
			else if(13<=k&&k<=17){
				image = new PosImageIcon("src/img/mouse1.png",bcX,bcY,55,55);
			}
			else if(18<=k&&k<=22){
				image = new PosImageIcon("src/img/mouse2.PNG",bcX,bcY,50,50);
			}
			
			if(points>=pt){
				level +=1;
				pt+=8;
				//delay -= 650;
				levelLabel.setText("Level : "+level);
			}
			tmp++;

			if(delay==aaa){
				doubleImage = new PosImageIcon("src/SLOW.PNG",500,50,110,80);//2�� �����ٴ� �̹��� �ֱ�
				doubleImage.draw(g2d);
			}


			System.out.println("points : " + points);
			System.out.println("hp : " + hp);
			System.out.println("tmp : " + tmp);
			if(hp<1){
				timer.stop();	
				t.stop();
				thirdBackSound.stop();
			}
			image.draw(g2d);



		}
	}
	//hpüũ
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){		

			if(tmp!=points){
				if(0<k && k<=15){
					//hp�谨
					//hp-=1;
					hpLabel.setText("HP: "+hp);
				}
				else if(15<=k&&k<=19){
					hpLabel.setText("HP: "+hp);
				}
				else if(20<=k && k<=22){
					//hp�谨
					//hp-=1;
					hpLabel.setText("HP: "+hp);
				}
			}

			drawPanel.repaint();


		}
	}   
	//����ȭ�鿡�� �ִϸ��̼� ȭ������ �Ѿ�� ��ư
	class Next1Listener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "������ ����Ű�� �����ּ���.");

			lp.setLayer(aniPanel,4);
			aniPanel.setFocusable(true);					// gamePanel�� ��Ŀ�̵� �� �ְ� ��
			aniPanel.requestFocus();						// ��Ŀ���� ������(�̰� �ݵ�� �ʿ�)
			aniPanel.repaint();
			firstBackSound.stop();
			secondBackSound.loop();
			frame.repaint();

		}
	}

	//������ �����ϰų� ������ϴ� ��ư
	class StartListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			lp.setLayer(drawPanel,6);
			timer.start();
			timing.setText("�ð� : 0�� 0��");
			scoreLabel.setText("��������: "+0+"��");
			hp=5;
			hpLabel.setText("HP: " + hp);
			times = 0;
			points = 0;
			t.start();
			firstBackSound.stop();
			secondBackSound.stop();
			thirdBackSound.loop();
			frame.repaint();
			//next1.setEnabled(false);
			next2.setEnabled(false);
		//	mousetimer.stop();
		}
	}
	//������ �Ͻ����� ��Ű�� ��ư
	class SuspendListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
			t.stop();

		}
	}
	//������ ����� �ϴ� ��ư
	class ContListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.restart();
			t.restart();
			catSound.play();
			mouseSound.play();

		}
	}
	//������ �����Ű�� ��ư
	class EndListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
			t.stop();
			firstBackSound.stop();
			secondBackSound.stop();
			thirdBackSound.stop();
			System.exit(0);

			JOptionPane.showMessageDialog(null, "YOU HAD " + points + " points");
		}
	}
	//���� ����ð��� �����ִ� Ÿ�̸�
	private class T_Listener implements ActionListener{

		public void actionPerformed(ActionEvent event){
			times++;
			timing.setText("�ð� : " + times/60+"��" + times%60+"��");
		}
	}
	
	//������ 2�� ���������ϴ� Ÿ�̸�
	private class SlowTimeListener implements ActionListener{
		public void actionPerformed(ActionEvent event){

			tim++;  


			if(delay!=aaa){ 
				ti=tim+6;  
				delay  = delay*3;
				timer.setDelay(delay);
				timer.restart();
			}

			if(tim==ti){ 
				delay  = delay/3;
				timer.setDelay(delay);
				slowtime.stop();
			}
		}
	}

}








