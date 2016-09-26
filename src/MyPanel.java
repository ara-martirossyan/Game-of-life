import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	boolean[][] grid;
	
	public void setGrid(boolean[][] grid) {
		this.grid = grid;
	}

	public MyPanel(boolean[][] grid) {
		this.setGrid(grid);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		double boxWidth =  (double)(this.getWidth())/ConwaysLife.WIDTH;
		double boxHeight =  (double)(this.getHeight())/ConwaysLife.HEIGHT;
		for (int i = 0; i < ConwaysLife.WIDTH; i++) {
			for (int j = 0; j < ConwaysLife.HEIGHT; j++) {
				if(grid[i][j] == true) {
					g.setColor(Color.BLACK);
					g.fillRect((int)(i*boxWidth), (int)(j*boxHeight), (int)(boxWidth), (int)(boxHeight));
				}
			}
			g.drawLine((int)(i*boxWidth), 0, (int)(i*boxWidth), (int)(this.getHeight()));
			g.drawLine(0, (int)(i*boxHeight), (int)(this.getWidth()), (int)(i*boxHeight));
		}
	}
}