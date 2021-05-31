package ru.makedonskaya.tictac;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TicTacBotService {
	
	private List<List<String>> array = new ArrayList<List<String>>();
	
	private int count = 0;
	
	private String winner = "";
	
	public List<List<String>> getNewPlayField() {
		array = new ArrayList<List<String>>();
		for (int i = 0; i < 3; i++) {
			array.add(i, new ArrayList<String>());
			for (int j = 0; j < 3; j++) {
					array.get(i).add("-");
			}
		}
		winner = "";
		count = 0;
		
		return array;
	}
	
	public String getWinner() {		 
		 return this.winner;
	 }
	
	public List<List<String>> safekeeping(int row, int col) {
		if (array.get(row).get(col).equals("-")) {
			if (count % 2 == 0) {
				array.get(row).set(col, "X");
				count++;
				moveBot(row,col);
			} 
			if (count > 4) {
				winnerCheck(array);
			}
			if (count == 9 & winner == "") {
				winner = "?";
			}
		}
		
		return array;
	}
	
	
	
	public List<List<String>> moveBot(int row, int col) {
		if (count == 9) {
			return array;
		}
		if (count % 2 != 0) {
			int result = -1;
		
			if (row == 0) {
				while (result != 0) {
					result = moveBot2(row, col);
					if (row != 2 && result != 0) {
						row++;
					}
				}
				row = 0;
			}
			if (row == 1) {
				result = moveBot2(row, col);
				if (result != 0) {
					result = moveBot2(row + 1, col);
					if (result != 0) {
						result = moveBot2(row - 1, col);
					}
				}
			}
			if (row == 2) {
				while (result != 0) {
					result = moveBot2(row, col);
					if (row >= 0 & result != 0) {
						row--;
					}
				}
			}
		}
		count++;
		
		return array;
	}
			
	private int moveBot2(int row, int col) {
		List<String> stroka = new ArrayList<>();
		stroka.addAll(array.get(row));
		if (stroka.contains("-")) {
			for(int i = 0; i < stroka.size(); i++) {
				if (stroka.get(i).equals("-")) {
					array.get(row).set(i, "O");
					return 0;	
				}
			}	
		}		
		return -1;
	}
	
	private void winnerCheck(List<List<String>> list) {
		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();
		Set<String> set3 = new HashSet<>();
		Set<String> set4 = new HashSet<>();
		
		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.size(); j++) {
				set1.add(list.get(i).get(j));
				set2.add(list.get(j).get(i));
			}
			checkXorO(set1);
			checkXorO(set2);
			set1.clear();
			set2.clear();
			
			set3.add(list.get(i).get(i));
			set4.add(list.get(i).get(list.size() - 1 - i));
		}
		checkXorO(set3);
		checkXorO(set4);
		set3.clear();
		set4.clear();
	}
	
	private void checkXorO(Set<String> set) {
		if (set.size() != 1) {
			return;
		}
		if (winner.equals("X") || winner.equals("O")) {
			return;
		}
		if (set.contains("X")) {
			winner = "X";
		} 
		if (set.contains("O")) {
			winner = "O";
		}
	}
}
