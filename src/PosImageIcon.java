import javax.swing.*;

import java.awt.*;

// ImageIcon에 좌표의 위치를 부여하고자 ImageIcon 클래스를 상속함
public class PosImageIcon extends ImageIcon {
	int pX;				// ImageIcon의 X좌표
	int pY;				// ImageIcon의 y좌표
	int width;			// ImageIcon의 넓이
	int height;			// ImageIcon의 높이
	String image;
	int margin;
	//int xDirection;
	//int yDirection;


	public PosImageIcon(String img, int x, int y, int width, int height) {
		super(img);
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
	
	}
	public PosImageIcon(String img,int margin, int x, int y, int width, int height) {
		super(img);
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
		this.margin = margin;
	}
	


	public void move(int x, double y) {
		pX += x;
		pY += y;		
	}

	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pX, pY, width, height, null);
	}

	public boolean collide (Point p2) {
		Point p = new Point(this.pX, this.pY);
		if (p.distance(p2) <= margin) 
			return true;
		return false;
	}

}


