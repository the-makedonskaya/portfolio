package gameoflife;

import java.util.LinkedList;
import java.util.List;

public class LiveCell {
	
	private int x;
	
	private int y;	
	
	public LiveCell() {
		
	}
	
	public LiveCell(int x, int y) {
		this.x = x;
		this.y = y;	
	}
	
	public int countNeighbours(List<List<Integer>> listLife) {
		int countLiveSosed = 0;

		for (int i = this.getX() - 1; i <= this.getX() + 1; i++) {
			if (i == listLife.size() || i == -1) {
				continue;
			}
			for(int j = this.getY() - 1; j <= this.getY() + 1; j++) {
				if (j == listLife.size() || j == -1) {
					continue;
				}
				if(i == this.getX() && j == this.getY()) {
					continue;
				}
				if (listLife.get(i).get(j) != 0) {
					countLiveSosed++;
				} 
			}
		}
		return countLiveSosed;
	}
	
	public List<LiveCell> listNewNeighbours(List<List<Integer>> listLife) {
		List<LiveCell> buffer = new LinkedList<>();
		for (int i = this.getX() - 1; i <= this.getX() + 1; i++) {
			if (i == listLife.size() || i == -1) {
				continue;
			}
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++) {
				if (j == listLife.size() || j == -1) {
					continue;
				}
				if (i == this.getX() && j == this.getY()) {
					continue;
				}
				if (listLife.get(i).get(j) == 0) {
					buffer.add(new LiveCell(i, j));
				}
			}
		}

		return buffer;
	}
	
	public boolean shouldBeKilled(List<List<Integer>> listLife) {
		int count = this.countNeighbours(listLife);
		
		return count < 2 || count > 3;
	}
	
	public void setCellLife(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	public Integer getLiveSign() {
		return 7;
	}
		
}
