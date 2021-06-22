package ru.makedonskaya.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameOfLifeService  {
		
	private final static int SIZE_LIFE_AREA_X = 50;
	
	private final static int SIZE_LIFE_AREA_Y = 50;
	
	private final static int STARTING_LIVE_CELL_MAX = (int) ((SIZE_LIFE_AREA_X * SIZE_LIFE_AREA_Y) * 0.2);
	
	private final static int SIGN_FOR_EMPTY_CELL = 0;
	
	private List<LiveCell> cells = new LinkedList<>();

	private List<List<Integer>> lifeArea = new ArrayList<>();
	
	public GameOfLifeService() {
		setupNewFirstGeneration();
	}
	
	public void setupNewFirstGeneration() { 
		cells.clear();
		lifeArea.clear();
		
		for (int i = 0; i < STARTING_LIVE_CELL_MAX; i++) {
			cells.add(new LiveCell((int) Math.round((Math.random() * SIZE_LIFE_AREA_X * 0.9)), 
									(int) Math.round((Math.random() * SIZE_LIFE_AREA_Y * 0.9))));
		}
		
		for (int i = 0; i < SIZE_LIFE_AREA_X; i++) {
			lifeArea.add(i, new ArrayList<Integer>());
			for (int j = 0; j < SIZE_LIFE_AREA_Y; j++) {
				lifeArea.get(i).add(SIGN_FOR_EMPTY_CELL);
			}
		}
		cells.forEach(cell -> {
			for (int i = 0; i < 50; i++) {
				lifeArea.get(cell.getX()).set(cell.getY(), cell.getLiveSign());
			}
		});		
	}
	
	private void lifeCycle() {
		List<LiveCell> dyingLiveСells = new LinkedList<>();
		

		cells.forEach(cell -> {
			int countLiveNeighbour = cell.countNeighbours(lifeArea);
			if (countLiveNeighbour < 2 || countLiveNeighbour > 3) {
				dyingLiveСells.add(cell);
			}
		});
		
		List<LiveCell> allNeighbourEmptyAreaCells = new LinkedList<>();
		cells.forEach(cell -> {
			allNeighbourEmptyAreaCells.addAll(cell.collectNeighbourEmptyAreaCells(lifeArea));
		});

		cells.removeAll(dyingLiveСells);
		dyingLiveСells.forEach(cell -> {
			lifeArea.get(cell.getX()).set(cell.getY(), 0);
		});
		
		//по условию если рядом с пустой ячейкой окажется ровно 3 соседние LiveCell, то в этой ячейке рождается новая LiveCell.
		//собираем список координат ячеек, которые являются пустыми для 3 живых клеток
		Map<List<Integer>, Long> countingMap = allNeighbourEmptyAreaCells.stream().collect(Collectors.groupingBy(c -> {
			return Arrays.asList(c.getX(), c.getY());
		}, Collectors.counting()));

		List<LiveCell> newGenerationLiveCells = countingMap.entrySet().stream().filter(e -> {
			return e.getValue() == 3;
		}).map(e -> {
			return new LiveCell(e.getKey().get(0), e.getKey().get(1));
		}).collect(Collectors.toList());

		
		newGenerationLiveCells.forEach(cell -> {
			lifeArea.get(cell.getX()).set(cell.getY(), cell.getLiveSign());
		});
		cells.addAll(newGenerationLiveCells);
	}
	
	public List<List<Integer>> getLifeAreaCellsForPainting() {
		lifeCycle();

		List<List<Integer>> coordinatesCellsForPainting = cells.stream()
				.map(c -> Arrays.asList(c.getX(), c.getY()))
				.collect(Collectors.toList());
				
		return coordinatesCellsForPainting;
	}
	
}
