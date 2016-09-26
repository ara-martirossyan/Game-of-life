import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

public class ConwaysLife implements MouseListener, ActionListener, Runnable{

	JFrame frame = new JFrame("Aralmighty - Game Of Life");
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;	
	
	boolean[][] grid= new boolean[WIDTH][HEIGHT];
	MyPanel panel = new MyPanel(grid);
	
	// Buttons
	JButton step = new JButton("STEP");
	JButton play = new JButton("PLAY");
	JButton clear = new JButton("CLEAR");
	JButton gun = new JButton("GUN");
	JButton glider = new JButton("GLIDER");
	JButton spaceShip  = new JButton("SPACE SHIP");
	
	
	Container south = new Container();
	boolean running = false;
	
	
	
	public ConwaysLife() {
		frame.setSize(950, 950);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		// Set image icon for the frame window,
		ImageIcon logo = new ImageIcon("logo.jpg");
		frame.setIconImage(logo.getImage());
		
		
		// Create south container and add things to it.
		south.setLayout(new GridLayout(2, 3));
		
		south.add(step);
		step.addActionListener(this);
		south.add(play);
		play.addActionListener(this);
		south.add(clear);
		clear.addActionListener(this);
		south.add(gun);
		gun.addActionListener(this);
		south.add(glider);
		glider.addActionListener(this);
		south.add(spaceShip);
		spaceShip.addActionListener(this);
		
		frame.add(south, BorderLayout.SOUTH);
		panel.addMouseListener(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ConwaysLife();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(step) && !this.running) {
			step();
		}
		else if (e.getSource().equals(play)) {
			play();
		}
		else if (e.getSource().equals(clear)){
			clear();
		}
		else if (e.getSource().equals(gun)){
			gun();
		}
		else if (e.getSource().equals(glider)){
			glider();
		}
		else if (e.getSource().equals(spaceShip)){
			spaceShip();
		}
	}

	@Override
	public void run() {
		while (running) {
			step();
			frame.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	// define torus coordinates
	private int pX(int a, int b) { return (a+b) % WIDTH; }
	private int pY(int a, int b) { return (a+b) % HEIGHT; }
	private int mX(int a, int b) { return (a-b + WIDTH) % WIDTH; }
	private int mY(int a, int b) { return (a-b + HEIGHT) % HEIGHT; }
	
	public int countAliveNeighbors(int x, int y) {
		int aliveNeighborCount = 0;
		
		if (grid[mX(x,1)][y] == true) {
			++aliveNeighborCount;
		}
		if (grid[pX(x,1)][y] == true) {
			++aliveNeighborCount;
		}

		if (grid[x][mY(y,1)] == true) {
			++aliveNeighborCount;
		}
		if (grid[x][pY(y,1)] == true) {
			++aliveNeighborCount;
		}
		
		if (grid[mX(x,1)][mY(y,1)] == true) {
			++aliveNeighborCount;
		}
		if (grid[pX(x,1)][pY(y,1)] == true) {
			++aliveNeighborCount;
		}
		
		if (grid[mX(x,1)][pY(y,1)] == true) {
			++aliveNeighborCount;
		}
		if (grid[pX(x,1)][mY(y,1)] == true) {
			++aliveNeighborCount;
		}
		
		return aliveNeighborCount;
	}
	
	public void step() {
		boolean[][] nextGenerationGrid = new boolean[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				int aliveNeighborCount = this.countAliveNeighbors(x, y);
				// Alive cell
				if (grid[x][y] == true) {
					if (aliveNeighborCount == 2 || aliveNeighborCount == 3) {
						nextGenerationGrid[x][y] = true;
					}
					else {
						nextGenerationGrid[x][y] = false;
					}					
				}
				// Dead cell
				else {
					if (aliveNeighborCount == 3) {
						nextGenerationGrid[x][y] = true;
					}
					else {
						nextGenerationGrid[x][y] = false;
					}
				}
			}
		}
		this.grid = nextGenerationGrid;
		this.panel.setGrid(nextGenerationGrid);	
		frame.repaint();
	}
	
	public void play() {
		if (!this.running) {
			this.running = true;				
			Thread t =  new Thread(this);
			this.play.setText("PAUSE");
			t.start();
		} else {
			this.running = false;
			this.play.setText("PLAY");
		}
	}
	
	public void clear() {
		this.running = false;
		this.play.setText("PLAY");
		this.grid = new boolean[WIDTH][HEIGHT];
		this.panel.setGrid(this.grid);
		this.frame.repaint();
	}
	
	public void gun() {
		clear();
		int x = 0;
		int y = 4;		
		// Blocker
		grid[pX(x,45)][pY(y,27)] = grid[pX(x,44)][pY(y,27)] = grid[pX(x,44)][pY(y,28)] = grid[pX(x,45)][pY(y,29)] = grid[pX(x,46)][pY(y,29)] = grid[pX(x,47)][pY(y,29)] = grid[pX(x,47)][pY(y,30)] = true;
		// First square
		grid[x][y] = grid[x][pY(y,1)] = grid[pX(x,1)][y] = grid[pX(x,1)][pY(y,1)] = true;		
		// First gun
		grid[pX(x,10)][y] = grid[pX(x,10)][pY(y,1)] = grid[pX(x,10)][pY(y,2)] = true;
		grid[pX(x,11)][mY(y,1)] = grid[pX(x,11)][pY(y,3)] = grid[pX(x,12)][mY(y,2)] = grid[pX(x,13)][mY(y,2)] = grid[pX(x,12)][pY(y,4)] = grid[pX(x,13)][pY(y,4)] = true;
		grid[pX(x,14)][pY(y,1)] = true;
		grid[pX(x,15)][mY(y,1)] = grid[pX(x,15)][pY(y,3)] = true;
		grid[pX(x,16)][y] = grid[pX(x,16)][pY(y,1)] = grid[pX(x,16)][pY(y,2)] = true;
		grid[pX(x,17)][pY(y,1)] = true;		
		// Second gun
		grid[pX(x,20)][y] = grid[pX(x,20)][mY(y,1)] = grid[pX(x,20)][mY(y,2)] = true;
		grid[pX(x,21)][y] = grid[pX(x,21)][mY(y,1)] = grid[pX(x,21)][mY(y,2)] = true;
		grid[pX(x,22)][mY(y,3)] = grid[pX(x,22)][pY(y,1)] = true;
		grid[pX(x,24)][mY(y,4)] = grid[pX(x,24)][mY(y,3)] = grid[pX(x,24)][pY(y,1)] = grid[pX(x,24)][pY(y,2)] = true;		
		// Second square
		grid[pX(x,34)][mY(y,2)] = grid[pX(x,35)][mY(y,1)] = grid[pX(x,35)][mY(y,2)] = grid[pX(x,34)][mY(y,1)] = true;		
		
		this.frame.repaint();
	}

	public void glider() {
//		clear();
		int x, y;
		Random randX = new Random();
    	x = randX.nextInt(WIDTH);
		Random randY = new Random();
		y = randY.nextInt(HEIGHT);
		
		Random randomDirection = new Random();
		int direction = randomDirection.nextInt(4);
		switch (direction) {
		case 0:
			grid[x][y] = grid[pX(x,1)][pY(y,1)] = grid[pX(x,1)][pY(y,2)] = grid[x][pY(y,2)] = grid[mX(x,1)][pY(y,2)] = true;
			break;
		case 1:
			grid[x][y] = grid[mX(x,1)][pY(y,1)] = grid[mX(x,2)][pY(y,1)] = grid[mX(x,2)][y] = grid[mX(x,2)][mY(y,1)] = true;
			break;
		case 2:
			grid[x][y] = grid[mX(x,1)][mY(y,1)] = grid[mX(x,1)][mY(y,2)] = grid[x][mY(y,2)] = grid[pX(x,1)][mY(y,2)] = true;
			break;
		case 3:
			grid[x][y] = grid[pX(x,1)][mY(y,1)] = grid[pX(x,2)][mY(y,1)] = grid[pX(x,2)][y] = grid[pX(x,2)][pY(y,1)] = true;
			break;
		default:
			break;
		}
		
		this.frame.repaint();
	}
	
	public void spaceShip() {
//		clear();
		int x, y;
		Random randX = new Random();
    	x = randX.nextInt(WIDTH);
		Random randY = new Random();
		y = randY.nextInt(HEIGHT);
		
		Random randomDirection = new Random();
		int direction = randomDirection.nextInt(4);
		switch (direction) {
		case 0:
			grid[pX(x,1)][y] = grid[x][pY(y,1)] = grid[x][pY(y,2)] =
		    grid[x][pY(y,3)] = grid[pX(x,1)][pY(y,3)] = grid[pX(x,2)][pY(y,3)] = 
		    grid[pX(x,3)][pY(y,3)] = grid[pX(x,4)][y] = grid[pX(x,4)][pY(y,2)] = true;
			break;
		case 1:
			grid[x][y] = grid[pX(x,1)][y] = grid[pX(x,2)][y] = 
			grid[x][pY(y,1)] = grid[x][pY(y,2)] = grid[x][pY(y,3)] = 
			grid[pX(x,3)][pY(y,1)] = grid[pX(x,3)][pY(y,4)] = grid[pX(x,1)][pY(y,4)] = true;
			break;
		case 2:			
			grid[pX(x,1)][y] = grid[pX(x,2)][y] = grid[pX(x,3)][y] = 
			grid[pX(x,4)][y] = 	grid[x][pY(y,1)] = grid[pX(x,4)][pY(y,1)] = 
			grid[pX(x,4)][pY(y,2)] = grid[x][pY(y,3)] = grid[pX(x,3)][pY(y,3)] = true;
			break;
		case 3:
			grid[x][y] = grid[pX(x,2)][y] = grid[x][pY(y,3)] = 			
			grid[pX(x,3)][pY(y,1)] = grid[pX(x,3)][pY(y,2)] = grid[pX(x,3)][pY(y,3)] = 
			grid[pX(x,3)][pY(y,4)] = grid[pX(x,1)][pY(y,4)] = grid[pX(x,2)][pY(y,4)] = true;
			break;
		default:
			break;
		}
		
		this.frame.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getX() + ", " +e.getY()); 
		 
		int row = Math.min(e.getX()/(panel.getWidth()/WIDTH), WIDTH-1);
		int col = Math.min(e.getY()/(panel.getHeight()/HEIGHT), HEIGHT-1);
		grid[row][col] = !grid[row][col];
		frame.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}