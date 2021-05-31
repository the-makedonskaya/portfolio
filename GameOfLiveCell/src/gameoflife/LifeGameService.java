package gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LifeGameService  {
		
	private List<LiveCell> cells = new LinkedList<>();

	private List<List<Integer>> lifeArea = new ArrayList<>();
	
	public LifeGameService() {
		restart();
	}
	
	private void restart() { 
		for(int i = 0; i < 500; i++) {
			cells.add(new LiveCell((int) Math.round((Math.random() * 40)), (int) Math.round((Math.random() * 40))));
		}
		for (int i = 0; i < 50; i++) {
			lifeArea.add(i, new ArrayList<Integer>());
			for (int j = 0; j < 50; j++) {
				lifeArea.get(i).add(0);
			}
		}
	}
	
	private void lifeCycle() {
		List<LiveCell> killerCells = new LinkedList<>();
		
		cells.forEach(c -> {
			int countLiveSosed = c.countNeighbours(lifeArea);
			if (countLiveSosed < 2 || countLiveSosed > 3) {
				killerCells.add(c);
			}
		});
		
		List<LiveCell> buffer = new LinkedList<>();
		cells.forEach(c -> {
			buffer.addAll(c.listNewNeighbours(lifeArea));
		});


		cells.removeAll(killerCells);
		killerCells.forEach(c -> {
			lifeArea.get(c.getX()).set(c.getY(), 0);
		});

		
		Map<List<Integer>, Long> countingMap = buffer.stream().collect(Collectors.groupingBy(c -> {
			return Arrays.asList(c.getX(), c.getY());
		}, Collectors.counting()));

		List<LiveCell> newLife = countingMap.entrySet().stream().filter(e -> {
			return e.getValue() == 3;
		}).map(e -> {
			return new LiveCell(e.getKey().get(0), e.getKey().get(1));
		}).collect(Collectors.toList());

		
		newLife.forEach(b -> {
			lifeArea.get(b.getX()).set(b.getY(), b.getLiveSign());
		});
		cells.addAll(newLife);
	}
	
	public List<List<Integer>> getCells() {
		lifeCycle();

		return lifeArea;
	}
	
	public void clearField() {
		cells.clear();
		lifeArea.clear();
		restart();
	}

}
