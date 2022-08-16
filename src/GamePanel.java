import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=20;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=200;
	final int []x=new int[GAME_UNITS];
	final int []Y=new int[GAME_UNITS];
	int bodyparts=5;
	int applesEaten;
	int Applesx;
	int Applesy;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	
	
	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension( SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new mykey());
		StartGame();
		
	}
	public void StartGame() {
		newApple();
		running = true;
		timer=new Timer(DELAY,this);//how fast the game is running
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
  if (running) {
		/*(for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE );
		}*/
		//g.setColor(Color.ORANGE);
		g.setColor(Color.blue);
		g.fillOval(Applesx, Applesy, UNIT_SIZE, UNIT_SIZE);
		
		for (int i=0;i<bodyparts;i++) {
			if(i==0) {
				//g.setColor(Color.black);
				g.setColor(Color.red);
				g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
			}else {
				//g.setColor(Color.red);
				g.setColor(Color.blue);
				g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
			}
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,45));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
	
		}
  }
      else {
		GameOver(g);
	}
	}
	public void newApple() {
		Applesx=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		Applesy=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		for (int i=bodyparts;i>0;i--) {
			x[i]=x[i-1];
			Y[i]=Y[i-1];
		}
		switch(direction) {
		case 'U':Y[0]=Y[0]-UNIT_SIZE;
		break;
		case 'D':Y[0]=Y[0]+UNIT_SIZE;
		break;
		case 'R':x[0]=x[0]+UNIT_SIZE;
		break;
		case 'L':x[0]=x[0]-UNIT_SIZE;
		break;
		}
	}
	public void checkApple() {
		if((x[0]==Applesx)&&(Y[0]==Applesy)) {
			bodyparts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i=bodyparts;i>0;i--) {
			if((x[0]==x[i])&&(Y[0]==Y[i])) {
				running = false;
			}	
		}
		//Checks if head touches left boarder
		if (x[0]<0) {
			running=false;
		}
		//Checks if head touches right boarder
		if (x[0]>SCREEN_WIDTH) {
			running=false;
		}
		//Checks if head touches top boarder
		if (Y[0]<0) {
			running=false;
		}
		//Checks if head touches botttom boarder
		if (Y[0]>SCREEN_HEIGHT) {
			running=false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	public void GameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,45));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
	
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics1=getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH-metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
	}
	
	public class mykey extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction!='R') {
					direction='L';
					}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction!='L') {
					direction='R';
					}
				break;
			case KeyEvent.VK_UP:
				if (direction!='D') {
					direction='U';
					}
				break;
			case KeyEvent.VK_DOWN:
				if (direction!='U') {
					direction='D';
					}
				break;
				}
			}
		}
	}



