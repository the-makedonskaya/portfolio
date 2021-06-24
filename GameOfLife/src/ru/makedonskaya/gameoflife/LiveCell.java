package ru.makedonskaya.gameoflife;

import java.util.LinkedList;
import java.util.List;

public class LiveCell {
	
	private int x;
	
	private int y;	
	
	private final static int SIGN_LIVE_CELL = 7;
	
	public LiveCell(int x, int y) {
		this.x = x;
		this.y = y;	
	}
	
	public int countNeighbours(List<List<Integer>> lifeArea) {
		int countLiveNeighbour = 0;

		for (int i = this.getX() - 1; i <= this.getX() + 1; i++) {
			if (i == lifeArea.size() || i == -1) {
				continue;
			}
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++) {
				if (j == lifeArea.size() || j == -1) {
					continue;
				}
				if (i == this.getX() && j == this.getY()) {
					continue;
				}
				if (lifeArea.get(i).get(j) != 0) {
					countLiveNeighbour++;
				} 
			}
		}
		return countLiveNeighbour;
	}
	
	public List<LiveCell> collectNeighbourEmptyAreaCells(List<List<Integer>> lifeArea) {
		List<LiveCell> allNeighbourEmptyAreaCells = new LinkedList<>();
		for (int i = this.getX() - 1; i <= this.getX() + 1; i++) {
			if (i == lifeArea.size() || i == -1) {
				continue;
			}
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++) {
				if (j == lifeArea.size() || j == -1) {
					continue;
				}
				if (i == this.getX() && j == this.getY()) {
					continue;
				}
				if (lifeArea.get(i).get(j) == 0) {
					allNeighbourEmptyAreaCells.add(new LiveCell(i, j));
				}
			}
		}

		return allNeighbourEmptyAreaCells;
	}
		
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public Integer getLiveSign() {
		return SIGN_LIVE_CELL;
	}
	
	
}
