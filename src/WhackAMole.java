import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;//import java.*로 합하려고 했을 경우 오류가 뜨게 된다.
import java.applet.AudioClip;
import java.net.URL;

import javax.swing.*;

public class WhackAMole extends JFrame implements MouseListener {

	JFrame frame;
	private  int mcX;//마우스의 위치값
	private  int mcY;
	private  int points =0;//점수
	int pt=8;
	private  int bcX;//고양이의 위치값
	private  int bcY;
	public int delay = 1300;//게임의 속도
	PosImageIcon image;
	public int num = 25;//고양이가 나타날 횟수
	public int number = 25;//클릭하지 않았을때와 다른곳을 클릭했을때 정지시키기위한 변수
	static String playerName;//플레이어 이름
	Timer timer;
	DrawPanel drawPanel;
	int hp = 5;
	int tmp=1;
	int level =1;
	int k = 0;//이미지 랜덤값
	Timer t;//빠르게 하기위한 타이머
	Timer slowtime;//속도를 느려지게 할 타이머
	int times = 0;// 시간을 세어주는 변수
	PosImageIcon doubleImage;
	PosImageIcon background;
	private final int WIDTH = 570;//프레임의 가로길이 
	private final int HEIGHT = 570;//프레임의 세로길이
	int tim = 0;//느리게 하는 타이머 변수
	int aaa= delay *2;
	JLayeredPane lp = new JLayeredPane();
	PosImageIcon cat;
	int catX= 0;
	int ti = 0;
	AudioClip firstBackSound;
	AudioClip secondBackSound;
	AudioClip thirdBackSound;
	AudioClip bang;//고양이가 맞았을 때 음향 
	AudioClip catSound;//고양이 대사
	AudioClip mouseSound;//쥐 대사
	AudioClip attackSound;//쥐가 맞았을 때 음향
	AudioClip slowSound;//느려졌을때 음향


	JButton next2 = new JButton("게임방법");
	JButton start = new JButton("게임시작");
	JButton suspend = new JButton("일시정지");
	JButton cont = new JButton("계속");
	JButton end = new JButton("종료");
	JLabel scoreLabel = new JLabel("현재점수: 0점");
	JLabel hpLabel = new JLabel("HP: 5");
	JLabel levelLabel = new JLabel("Level : 1");
	JLabel timing = new JLabel("시간 : 0분0초");
	JPanel coverPanel;
	JPanel aniPanel;
	JPanel methodPanel;


	public static void main(String[] args){

		//playerName=JOptionPane.showInputDialog("이름을 입력해주세요 :");	// Player의 이름 입력
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

		//		url1 : 초기화면 배경음향,   url2 : 애니화면 배경음향,   url3 : 게임화면 배경음향
		//		url4 : 고양이를 클릭했을때 음향,  rul5 : 고양이 대사,   url6 : 쥐 대사,   url7 : 쥐 클릭했을때 음향
		//		url8 : 느려졌을때 음향


		//Obtain and store the audio clips to play
		try
		{
			url1 = new URL ("file", "localhost", "src/초기화면 배경음악.wav");
			url2 = new URL ("file", "localhost", "src/애니화면 배경음악.wav");
			url3 = new URL ("file", "localhost", "src/게임배경음악.wav");
			url4 = new URL ("file", "localhost", "src/뿅.wav");
			url7 = new URL ("file","localhost","src/아야.wav");
			url8 = new URL ("file","localhost","src/마술봉.wav");
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

		//delay 마이크로 초 마다 액션을 생성하는 Timer 생성. 핸들러는 ReboundListener에
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
		frame.setVisible(true);//화면을 보이게 하기 위한 것

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
		drawPanel.setBounds(0,0,WIDTH,HEIGHT-100);	//화면 크기
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
	//mouseListener를 인터페이스이기 때문에  mouseListener에 있는 메소드를 모두 구현해야만 한다. 

	public void mouseClicked(MouseEvent arg0) {
		mcX = arg0.getLocationOnScreen().x;//getLocationOnScreen은 스크린에 마우스를 클릭했을때 x좌표값이 나타남
		mcY = arg0.getLocationOnScreen().y;//getLocationOnScreen은 스크린에 마우스를 클릭했을때 Y좌표값이 나타남

		if(hp>0){
			//1점 
			if(0<k&&k<=10){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points++;
					scoreLabel.setText("현재점수: "+points+"점");
					tmp=points;
					drawPanel.repaint();
					timer.restart();	
					bang.play();

				}
			}
			//2점
			if(11<=k&&k<=14){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points++;
					scoreLabel.setText("현재점수: "+points+"점");
					tmp=points;
					drawPanel.repaint();
					timer.restart();
					bang.play();

				}
			}

			//-2점
			else if(15<=k&&k<=19){
				if (mcX < bcX + 150 && mcX > bcX && mcY < bcY + 150 && mcY > bcY ){

					points-=2;
					scoreLabel.setText("현재점수: "+points+"점");
					tmp=points;
					drawPanel.repaint();
					timer.restart();
					attackSound.play();


				}
			}
			//게임속도를 2배로 느려지게 함
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
	//초기화면을 나타내는 패널
	class CoverPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/초기화면.PNG",0,0,this.getWidth(),this.getHeight());
			image.draw(g);
		}
	}

/*	//애니메이션을 나타내는 패널
	class AniPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/ground.gif",0,0,this.getWidth(),this.getHeight());
			cat = new PosImageIcon("src/cat1.png",100,catX,this.getHeight()-150,120,120);			
			PosImageIcon mouse = new PosImageIcon("src/쥐.png",100,this.getWidth()-150,this.getHeight()-150,120,120);

			if(cat.collide( new Point(mouse.pX,mouse.pY))){
				aniPanel.setFocusable(false);	
				mousetimer.start();	
			}
			image.draw(g);
			cat.draw(g);
			mouse.draw(g);

		}
	}*/

	//사용설명서를 나타내는 패널
	class MethodPanel extends JPanel{
		public void paintComponent(Graphics g){
			PosImageIcon image = new PosImageIcon("src/게임방법.PNG",0,0,this.getWidth(),this.getHeight());

			image.draw(g);					
		}
	}


	//게임 실행 패널
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
				doubleImage = new PosImageIcon("src/SLOW.PNG",500,50,110,80);//2배 느리다는 이미지 넣기
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
	//hp체크
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){		

			if(tmp!=points){
				if(0<k && k<=15){
					//hp삭감
					//hp-=1;
					hpLabel.setText("HP: "+hp);
				}
				else if(15<=k&&k<=19){
					hpLabel.setText("HP: "+hp);
				}
				else if(20<=k && k<=22){
					//hp삭감
					//hp-=1;
					hpLabel.setText("HP: "+hp);
				}
			}

			drawPanel.repaint();


		}
	}   
	//시작화면에서 애니메이션 화면으로 넘어가는 버튼
	class Next1Listener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "오른쪽 방향키를 눌러주세요.");

			lp.setLayer(aniPanel,4);
			aniPanel.setFocusable(true);					// gamePanel이 포커싱될 수 있게 함
			aniPanel.requestFocus();						// 포커싱을 맞춰줌(이것 반드시 필요)
			aniPanel.repaint();
			firstBackSound.stop();
			secondBackSound.loop();
			frame.repaint();

		}
	}

	//게임을 시작하거나 재시작하는 버튼
	class StartListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			lp.setLayer(drawPanel,6);
			timer.start();
			timing.setText("시간 : 0분 0초");
			scoreLabel.setText("현재점수: "+0+"점");
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
	//게임을 일시정지 시키는 버튼
	class SuspendListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
			t.stop();

		}
	}
	//게임을 재시작 하는 버튼
	class ContListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.restart();
			t.restart();
			catSound.play();
			mouseSound.play();

		}
	}
	//게임을 종료시키는 버튼
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
	//게임 실행시간을 세어주는 타이머
	private class T_Listener implements ActionListener{

		public void actionPerformed(ActionEvent event){
			times++;
			timing.setText("시간 : " + times/60+"분" + times%60+"초");
		}
	}
	
	//게임중 2배 느려지게하는 타이머
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








