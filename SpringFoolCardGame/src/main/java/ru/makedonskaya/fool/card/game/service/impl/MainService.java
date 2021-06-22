package ru.makedonskaya.fool.card.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.springframework.stereotype.Service;

import ru.makedonskaya.fool.card.game.foolgame.Card;
import ru.makedonskaya.fool.card.game.foolgame.CardSuitEnum;
import ru.makedonskaya.fool.card.game.service.IMainService;

@Service
public class MainService implements IMainService {
	
	private List<Card> handGamer = new ArrayList<Card>();
	
	private List<Card> handBot = new ArrayList<Card>();
		
	private List<Card> graveyard = new ArrayList<Card>(); 
	
	private List<Card> field = new ArrayList<Card>();
	
	private CardSuitEnum trump;
	
	private Card trumpCard;
	
	private Stack<Card> deckOfCards = new Stack<Card>();
	
	private String winner;
	
	private boolean isPleerTurn;
	
	private boolean isQueuePleer;
	
	@Override
	public void newGame() {
		handGamer.clear();
		handBot.clear();
		graveyard.clear();
		field.clear();
		deckOfCards.clear();
		createNewDeckOfCards();
	}
	
	private void createNewDeckOfCards() {
		CardSuitEnum [] cardSuits = CardSuitEnum.values();
		for (int rankCard = 6; rankCard < 15; rankCard++) {
			for (CardSuitEnum cardSuit : cardSuits) {
				if (rankCard < 11) {
					Card card = new Card(rankCard, String.valueOf(rankCard), cardSuit);
					deckOfCards.push(card);
				} else {
					switch (rankCard) {
					case 11:
						Card card = new Card(rankCard, "J", cardSuit);
						deckOfCards.push(card);
						break;
					case 12:
						card = new Card(rankCard, "Q", cardSuit);	
						deckOfCards.push(card);
						break;
					case 13:
						card = new Card(rankCard, "K", cardSuit);	
						deckOfCards.push(card);
						break;
					case 14:
						card = new Card(rankCard, "A", cardSuit);	
						deckOfCards.push(card);
						break;
					}
				}	
			}		
		}
		Collections.shuffle(deckOfCards);
		trumpCard = new Card(deckOfCards.get(0).getRankCard(),deckOfCards.get(0).getNameRankCard(), deckOfCards.get(0).getCardSuit());
	}
	
	@Override
	public List<Card> start() {
		trump = trumpCard.getCardSuit();
		dealCards(handGamer);
		dealCards(handBot);
		if (determiningOrderOfMove(handBot) < determiningOrderOfMove(handGamer) || determiningOrderOfMove(handBot) == determiningOrderOfMove(handGamer)) {
			isPleerTurn = false;
			isQueuePleer = false;
			playBot();
		} else {
			isPleerTurn = true;
			isQueuePleer = true;
		}
		return field;
	}
	
	//если поле пустое бот ходит любой кроме козырки, если остались только козыри, то ходит меньшим
	//если поле не пусте и его очередность бот подкидывает
	//если поле не пустое и очередность не бота,  бот кроет
	private void playBot() {
		if (!isPleerTurn() && !isQueuePleer() && field.isEmpty()) {
			boolean isMove = false;
			for (int i = 0; i < handBot.size(); i++) {
				if (handBot.get(i).getCardSuit() != trump) {
					field.add(handBot.get(i));
					handBot.remove(i);
					isMove = true;
					break;
				}
			}
			if (!isMove) {
				for (int i = 0; i < handBot.size(); i++) {
					field.add(handBot.stream().min(Comparator.comparing(c -> c.getRankCard())).get());
					handBot.remove(i);
				}
			}
			isPleerTurn = true;
			return;
		}
		if (!isPleerTurn() && !isQueuePleer() && !field.isEmpty()) {
			if (!isThrowCardBot()) {
				graveyard.addAll(field);
				field.clear();
				dealCards(handBot);
				dealCards(handGamer);
				isQueuePleer = true;
				isPleerTurn = true;
				return;
			}
			isPleerTurn = true;
			return;
		}
		if ( !isPleerTurn() && isQueuePleer() && !field.isEmpty()) {
			Card card = field.get(field.size() - 1);
			beatCardBot(card);	
		}		
	}
	
	//проверка может ли бот подкинуть карту
	private boolean isThrowCardBot() {
		if (handGamer.size() > 0) {
			for(Card card : handBot) {
				for(Card cardField : field) {
					if ( card.getRankCard().equals(cardField.getRankCard()) ) {
						field.add(card);
						handBot.remove(card);
						return true;
					} 
				}	
			}
		}
		
		return false;
	}

	
	//игорок делает ход, подкидывает или кроет
	@Override
	public void playGamer(Integer index) {
		Card myCard = handGamer.get(index - 1);
		if (isQueuePleer() && isPleerTurn() && field.isEmpty()) {
			field.add(myCard);
			handGamer.remove(myCard);
			isPleerTurn = false;
			playBot();
			return;
		}
		if (isQueuePleer() && isPleerTurn() && !field.isEmpty()) {
			if (isThrowCardGamer(myCard)) {
				field.add(myCard);
				handGamer.remove(myCard);
				isPleerTurn = false;
				playBot();
				return;
			}
		}
		if (!isQueuePleer() && isPleerTurn()) {
			Card cardField = field.get(field.size() - 1);
			if (isCheckbeatCardGamer(myCard,cardField)) {
				field.add(myCard);
				handGamer.remove(myCard);
				isPleerTurn = false;
				playBot();
			}	
		}
	}
	
	//проверка можно подкинуть или нет
	private boolean isThrowCardGamer(Card myCard) {
		for (Card card : field) {
			if (card.getRankCard().equals(myCard.getRankCard())) {
				return true;
			}
		}

		return false;
	}
	
	
	//проверка кроет карта или нет
	private boolean isCheckbeatCardGamer(Card myCard, Card cardOnField) {
		if ( myCard.getCardSuit().equals(cardOnField.getCardSuit()) && 
				myCard.getRankCard() > cardOnField.getRankCard() ) {
			return true;
		}
		if ( myCard.getCardSuit().equals(trump) && !cardOnField.getCardSuit().equals(trump) ) {
			return true;
		}
		
		return false;		
	}
	

	//opredelyaet kto hodit
	private Integer determiningOrderOfMove(List<Card> handPlayer) {
		 Optional<Integer> result = handPlayer.stream()
				.filter(c -> c.getCardSuit().equals(trump))
				.map(c -> c.getRankCard())
				.min(Integer::min);
		 if (result.isPresent()) {
			 return result.get();
		 } else {
			 return 0;
		 }
	}
	
	// dobavit card v ruku
	private void dealCards(List<Card> handPlayer) {
		while (handPlayer.size() < 6) {
			if (!deckOfCards.isEmpty()) {
				handPlayer.add(deckOfCards.pop());
			} else {
				break;
			}
		}
	}
	//показывает сколько карт в колоде
	@Override
	public int getNumCardsInDeck() {
		return deckOfCards.size();
	}
	
    //показывает сколько карт у бота осталось
	@Override
	public int getNumCardsOfBot() {
		return handBot.size();
	}
	
	//показывает какая козырка
	@Override
	public String getTrump() {		
		return trump.toString();
	}
	
	//кнопка бито
	@Override
	public List<Card> clearField() {
		if (field.size() % 2 == 0) {
			graveyard.addAll(field);
			field.clear();
			dealCards(handGamer);
			dealCards(handBot);
			isPleerTurn = false;
			isQueuePleer = false;
			playBot();
		}	
		return field;
	}
	
	//кнопка взять карты
	@Override
	public List<Card> takeCards() {
		if (!isQueuePleer()) {
			if (isPleerTurn()) {
				handGamer.addAll(field);
				field.clear();
			}
			dealCards(handBot);
			dealCards(handGamer);
			isQueuePleer = false;
			isPleerTurn = false;
			playBot();
		}						
		return field;
	}

	
	private void beatCardBot(Card card) {
		//ищу в руке карту той же масти которая на поле и самый маленький ранг, но больше чем у карты которая на поле
		Optional<Card> result = handBot.stream()
				.filter(c -> c.getCardSuit().equals(card.getCardSuit()))
				.filter(c -> c.getRankCard() > card.getRankCard())
				.reduce((c1, c2) -> c1.getRankCard() < c2.getRankCard() ? c1 : c2);
		
		//если такая карта не нашлась и карта на поле не козырная, то ищу у себя среди козырных чтобы покрыть иначе беру		
		if (!result.isPresent() && !card.getCardSuit().equals(trump)) {
			result = handBot.stream()
					.filter(c -> c.getCardSuit().equals(trump))
					.reduce((c1, c2) -> c1.getRankCard() < c2.getRankCard() ? c1 : c2);
		}
		
		if (!result.isPresent()) {
			handBot.addAll(field);
			field.clear();
			dealCards(handBot);
			dealCards(handGamer);
			isPleerTurn = true;
			isQueuePleer = true;
		}
		
		//если нашлась нужная карта	то кладу на поле и удаляю из руки	
		else {
			Card resultCard = handBot.get(handBot.indexOf(result.get()));
			field.add(resultCard);
			handBot.remove(resultCard);
			isPleerTurn = true;
		}		
	}


	@Override
	public List<Card> getHandGamer() {
		return handGamer;
	}

	@Override
	public List<Card> getField() {
		return field;
	}
	
	
	public Stack<Card> getDeckOfCards() {
		return deckOfCards;
	}

	private boolean isPleerTurn() {
		return isPleerTurn;
	}
	
	private boolean isQueuePleer() {
		return isQueuePleer;
	}

	@Override
	public List<Card> getHandBot() {
		return handBot;
	}

	@Override
	public String getPleerTurn() {
		if (isPleerTurn()) {
			return "Ваш ход";
		}
		return "Ход противника";
	}

	@Override
	public String getQueuePleer() {
		if (isQueuePleer()) {
			return "Отбиватеся противник";
		}
		return "Вы отбиваетесь";
	}
	
	@Override
	public int getSizeGraveyard() {
		return graveyard.size();
	}
	
	@Override
	public String showMassage() {
		if (deckOfCards.size() == 0 && handBot.size() == 0) {
			return this.winner = "Bot";
		}
		
		if (deckOfCards.size() == 0 && handGamer.size() == 0) {
			return this.winner = "Gamer";
		}
		
		return this.winner = "";
	}
	
	@Override
	public Card showImgTrumpCard() {	
		return this.trumpCard;
	}
	
	@Override
	public List<Card> sortedHand(List<Card> list) {
		list.sort(Comparator.comparing(Card::getCardSuit).thenComparing(Card::getRankCard));
		return list;
	}
	
}
