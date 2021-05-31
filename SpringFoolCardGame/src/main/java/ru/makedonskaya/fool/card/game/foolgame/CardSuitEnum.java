package ru.makedonskaya.fool.card.game.foolgame;

import java.util.HashMap;
import java.util.Map;

public enum CardSuitEnum {
	
	HEARTS("Hearts"),
	
	DIAMONDS("Diamonds"),
	
	CLUBS("Clubs"),
	
	SPADES("Spades");
	
	static Map<String, CardSuitEnum> helper = new HashMap<String, CardSuitEnum>();
	
	private String suit;

	private CardSuitEnum(final String suit) {
        this.suit = suit;
    }	
	
	static {
		for (CardSuitEnum cs : values()) {
			helper.put(cs.suit, cs);
		}
	}

	public static CardSuitEnum getBySuit(String suit) {
		return helper.get(suit);
	}

	@Override
	public String toString() {
		return suit;
	}
	
	

}
