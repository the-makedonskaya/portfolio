package ru.makedonskaya.fool.card.game.foolgame;


public class Card {
	
	private Integer rankCard;
		
	private String nameRankCard;
	
	private CardSuitEnum cardSuit;

	public Card(Integer rankCard, String nameRankCard, CardSuitEnum cardSuit) {
		super();
		this.rankCard = rankCard;
		this.nameRankCard = nameRankCard;
		this.cardSuit = cardSuit;
	}
		
	public String getNameRankCard() {
		return nameRankCard;
	}

	public Integer getRankCard() {
		return rankCard;
	}

	public CardSuitEnum getCardSuit() {
		return cardSuit;
	}

}
