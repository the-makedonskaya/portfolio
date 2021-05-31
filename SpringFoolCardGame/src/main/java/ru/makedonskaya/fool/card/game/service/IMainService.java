package ru.makedonskaya.fool.card.game.service;

import java.util.List;
import java.util.Stack;

import ru.makedonskaya.fool.card.game.foolgame.Card;

public interface IMainService {
	
	void newGame();
	
	List<Card> start();
	
	int getNumCardsInDeck();
	
	int getNumCardsOfBot();
	
	String getTrump();
	
	void playGamer(Integer index);
	
	List<Card> clearField();
	
	List<Card> takeCards();
		
	List<Card> getHandGamer();
	
	List<Card> getField();
	
	Stack<Card> getDeckOfCards();
	
	List<Card> getHandBot();
	
	String getPleerTurn();
	
	String getQueuePleer();
	
	int getSizeGraveyard();
	
	String showMassage();
	
	Card showImgTrumpCard();
	
	List<Card> sortedHand(List<Card> list);
	
}
