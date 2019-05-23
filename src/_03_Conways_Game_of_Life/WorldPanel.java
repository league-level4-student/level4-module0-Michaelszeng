package _03_Conways_Game_of_Life;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;
	
	private Timer timer;
	
	//1. Create a 2D array of Cells. Do not initialize it.
	Cell[][] cells;
	
	
	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;
	
		//2. Calculate the cell size.
		cellSize = w/cellsPerRow;
		//3. Initialize the cell array to the appropriate size.
		cells = new Cell[cellsPerRow][cellsPerRow];
		//3. Iterate through the array and initialize each cell.
		//   Don't forget to consider the cell's dimensions when 
		//   passing in the location.
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				cells[r][c] = new Cell(r, c, cellSize);
			}
		}
		
	}
	
	public void randomizeCells() {
		//4. Iterate through each cell and randomly set each
		//   cell's isAlive memeber to true of false
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				double ran = Math.random();
				if (ran < 0.5) {
					cells[r][c].isAlive = true;
				}
			}
		}
		repaint();
	}
	
	public void clearCells() {
		//5. Iterate through the cells and set them all to dead.
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				cells[r][c].isAlive = false;
			}
		}
		repaint();
	}
	
	public void startAnimation() {
		timer.start();
	}
	
	public void stopAnimation() {
		timer.stop();
	}
	
	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//6. Iterate through the cells and draw them all
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				cells[r][c].draw(g);
			}
		}
		// draws grid
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
	
	//advances world one step
	public void step() {
		//7. iterate through cells and fill in the livingNeighbors array
		// . using the getLivingNeighbors method.
		int[][] livingNeighbors = new int[cellsPerRow][cellsPerRow];
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				livingNeighbors[r][c] = getLivingNeighbors(r, c);
			}
		}
		//8. check if each cell should live or die
		for (int r = 0; r<cellsPerRow; r++) {
			for (int c = 0; c<cellsPerRow; c++) {
				cells[r][c].liveOrDie(livingNeighbors[r][c]);
			}
		}
		repaint();
	}
	
	//9. Complete the method.
	//   It returns an int of 8 or less based on how many
	//   living neighbors there are of the 
	//   cell identified by x and y
	public int getLivingNeighbors(int x, int y){
		int numAlive = 0;
		if (x!=0 && y!=0 && cells[x-1][y-1].isAlive == true) {
			numAlive++;
		}
		if (y!=0 && cells[x][y-1].isAlive == true) {
			numAlive++;
		}
		if (y!=0 && x<cells.length-1 && cells[x+1][y-1].isAlive == true) {
			numAlive++;
		}
		if (x!=0 && cells[x-1][y].isAlive == true) {
			numAlive++;
		}
		if (x<cells.length-1 && cells[x+1][y].isAlive == true) {
			numAlive++;
		}
		if (x!=0 && y<cells[0].length-1 && cells[x-1][y+1].isAlive == true) {
			numAlive++;
		}
		if (y<cells[0].length-1 && cells[x][y+1].isAlive == true) {
			numAlive++;
		}
		if (y<cells[0].length-1 && x<cells.length-1 && cells[x+1][y+1].isAlive == true) {
			numAlive++;
		}
		
		
//		int numAlive = 0;
//		int lowerW = x-1;
//		int higherW = x+1;
//		int lowerH = y-1;
//		int higherH = y+1;
//		if (lowerW < 0) {
//			lowerW = 0;
//		}
//		if (lowerH < 0) {
//			lowerH = 0;
//		}
//		if (higherW > cells.length-1) {
//			higherW = 0;
//		}
//		if (higherH > cells[0].length-1) {
//			higherH = 0;
//		}
//		
//		
//		for (int r = lowerW; r<=higherW; r++) {
//			for (int c = lowerH; c<=higherH; c++) {
//				if ((r!=x && c!=y) && cells[r][c].isAlive == true) {
//					numAlive++;
//				}
//			}
//		}
		return numAlive;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) {
		//10. Use e.getX() and e.getY() to determine
		//    which cell is clicked. Then toggle
		//    the isAlive variable for that cell.
		int x = e.getX();
		int y = e.getY();
		int r = x/cellSize;
		int c = y/cellSize;
		
		cells[r][c].isAlive = !cells[r][c].isAlive;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();		
	}
}
