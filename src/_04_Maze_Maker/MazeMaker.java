package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		//4. select a random cell to start
		int x = (int) (Math.random() * w);
		int y = (int) (Math.random() * h);
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.getCell(x, y));
		return maze;
	}

	//6. Complete the selectNextPath Method
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. check for unvisited neighbors using the cell
		ArrayList<Cell> unvisited = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if (unvisited.size() > 0) {
			//C1. select one at random.
			int ran = (int) (Math.random() * unvisited.size());
			//C2. push it to the stack
			uncheckedCells.push(unvisited.get(ran));
			//C3. remove the wall between the two cells
			removeWalls(currentCell, unvisited.get(ran));
			//C4. make the new cell the current cell and mark it as visited
			currentCell = unvisited.get(ran);
			//currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		//D. if all neighbors are visited
		else {
			//D1. if the stack is not empty
			if (uncheckedCells.size() > 0) {
				// D1a. pop a cell from the stack
				Cell c = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = c;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		int x1 = c1.getX();
		int x2 = c2.getX();
		int y1 = c1.getY();
		int y2 = c2.getY();
		if (x1-x2 == 1 && y1 == y2) {
			c2.setEastWall(false);
			c1.setWestWall(false);
		}
		else if (x2-x1 == 1 && y1 == y2) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		else if (x1 == x2 && y1-y2 == 1) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}
		else if (x1 == x2 && y2-y1 == 1) {
			c2.setNorthWall(false);
			c1.setSouthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		int x = c.getX();
		int y = c.getY();
		ArrayList<Cell> unvisited = new ArrayList<Cell>();
		if (x!=0 && y!=0 && maze.getCell(x-1, y-1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x-1, y-1));
		}
		if (y!=0 && maze.getCell(x, y-1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x, y-1));
		}
		if (y!=0 && x<maze.cells.length-1 && maze.getCell(x+1, y-1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x+1, y-1));
		}
		if (x!=0 && maze.getCell(x-1, y).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x-1, y));
		}
		if (x<maze.cells.length-1 && maze.getCell(x+1, y).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x+1, y));
		}
		if (x!=0 && y<maze.cells[0].length-1 && maze.getCell(x-1, y+1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x-1, y+1));
		}
		if (y<maze.cells[0].length-1 && maze.getCell(x, y+1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x, y+1));
		}
		if (y<maze.cells[0].length-1 && x<maze.cells.length-1 && maze.getCell(x+1, y+1).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(x+1, y+1));
		}
		return unvisited;
	}
}
