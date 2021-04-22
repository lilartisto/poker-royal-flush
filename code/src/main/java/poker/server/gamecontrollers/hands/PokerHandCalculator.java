package poker.server.gamecontrollers.hands;

import poker.server.data.cards.Card;

import java.util.*;

public class PokerHandCalculator {

	private final Card[] tableCards;
	private final int cardsInColor = 13;

	public PokerHandCalculator(Card[] tableCards){
		this.tableCards = tableCards;
	}

	public double handStrength(Card[] handCards){
		Card[] allCardsSorted = getAllCardsSorted(handCards);
		int[] cardCounter = cardCounter(allCardsSorted);
		double strength;

		if((strength = straightFlush(allCardsSorted)) != 0){
			return strength;
		} else if((strength = fourOfKind(allCardsSorted, cardCounter)) != 0){
			return strength;
		} else if((strength = fullHouse(cardCounter)) != 0){
			return strength;
		} else if((strength = flush(allCardsSorted)) != 0){
			return strength;
		} else if((strength = straight(allCardsSorted)) != 0){
			return strength;
		} else if((strength = threeOfKind(allCardsSorted, cardCounter)) != 0){
			return strength;
		} else if((strength = twoPairs(allCardsSorted, cardCounter)) != 0){
			return strength;
		} else if((strength = onePair(allCardsSorted, cardCounter)) != 0){
			return strength;
		} else {
			return highCard(allCardsSorted);
		}
	}

	private Card[] getAllCardsSorted(Card[] handCards){
		Card[] cards = copyHandAndTableCards(handCards);

		Arrays.sort(cards, ((o1, o2) -> o2.number - o1.number));
		return cards;
	}

	private Card[] copyHandAndTableCards(Card[] handCards){
		Card[] cards = new Card[tableCards.length + handCards.length];

		for(int i = 0; i < tableCards.length; i++){
			cards[i] = tableCards[i];
		}

		for(int i = 0; i < handCards.length; i++){
			cards[tableCards.length + i] = handCards[i];
		}

		return cards;
	}

	private int[] cardCounter(Card[] allCards){
		int[] counter = new int[cardsInColor];

		for(Card card: allCards){
			counter[card.number]++;
		}

		return counter;
	}

	private double straightFlush(Card[] sortedCards){
		double straight = straight(sortedCards);
		double flush = flush(sortedCards);

		if(straight != 0 && flush != 0){
			return flush + Math.floor(straight) - 1;
		}

		return 0;
	}

	private double fourOfKind(Card[] sortedCards, int[] cardCounter){
		for(int i = cardCounter.length - 1; i >= 0; i--){
			if(cardCounter[i] == 4){
				if(sortedCards[0].number == i){
					return 7 + 0.001*(13*i + sortedCards[4].number);
				} else {
					return 7 + 0.001*(13*i + sortedCards[0].number);
				}
			}
		}
		return 0;
	}

	private double fullHouse(int[] cardCounter){
 		int threeNumber = -1;
 		int pairNumber = -1;

 		for(int i = cardCounter.length - 1; i >= 0; i--){
 			if(cardCounter[i] == 3 && threeNumber == -1){
 				threeNumber = i;
			} else if(cardCounter[i] >= 2 && pairNumber == -1) {
				pairNumber = i;
			}

			if(threeNumber != -1 && pairNumber != -1){
				return 6 + 0.01*threeNumber + 0.0001*pairNumber;
			}
		}

 		return 0;
	}

	private double flush(Card[] cards){
		ArrayList<Card>[] cardsByColor = getCardsListsByColor(cards);

		for(ArrayList<Card> cardsOneColor: cardsByColor){
			if(cardsOneColor.size() >= 5){
				cardsOneColor.sort(Comparator.comparingInt((o) -> o.number));
				double points = 0;

				for(int i = 4; i >= 0; i--){
					points += Math.pow(cardsInColor, i) * cardsOneColor.get(i).number;
				}

				return 5 + points/1e6;
			}
		}

		return 0;
	}

	private ArrayList<Card>[] getCardsListsByColor(Card[] cards){
		int colors = 4;
		ArrayList<Card>[] cardsByColor = new ArrayList[colors];

		for(int i = 0; i < colors; i++){
			cardsByColor[i] = new ArrayList<>();
		}

		for(Card card: cards){
			cardsByColor[card.color].add(card);
		}

		return cardsByColor;
	}

	private double straight(Card[] sortedCards){
		Card highestCard;
		for(int i = 0; i < sortedCards.length-tableCards.length; i++){
			highestCard = sortedCards[i];
			if(isStraight(sortedCards, i)){
				return 4 + 0.01*highestCard.number;
			}
		}
		return 0;
	}

	private boolean isStraight(Card[] cards, int startIndex){
		for(int i = 0; i < tableCards.length - 1; i++){
			if(cards[startIndex + i].number != cards[startIndex + i + 1].number + 1){
				return false;
			}
		}
		return true;
	}

	private double threeOfKind(Card[] sortedCards, int[] cardCounter){
		for(int i = cardCounter.length - 1; i >= 0; i--){
			if(cardCounter[i] == 3){
				Card[] cards = sortedCards.clone();
				deleteCardsWithNumber(cards, i);
				return 3 + 0.01*i + 0.001*highCard(cards);
			}
		}
		return 0;
	}

	private double twoPairs(Card[] sortedCards, int[] cardCounter){
		List<Integer> pairValues = new ArrayList<>();
		Card[] cards = sortedCards.clone();

		for(int i = cardCounter.length - 1; i >= 0; i--){
			if(cardCounter[i] == 2){
				deleteCardsWithNumber(cards, i);
				pairValues.add(i);
			}
		}

		if(pairValues.size() < 2) {
			return 0;
		}

		return 2 + 0.001*(cardsInColor*pairValues.get(0) + pairValues.get(1)) + 0.001*highCard(cards);
	}

	private double onePair(Card[] sortedCards, int[] cardCounter){
		for(int i = cardCounter.length - 1; i >= 0; i--){
			if(cardCounter[i] == 2){
				Card[] cards = sortedCards.clone();
				deleteCardsWithNumber(cards, i);
				return 1 + 0.01*i + 0.001*highCard(cards);
			}
		}
		return 0;
	}

	private void deleteCardsWithNumber(Card[] cards, int cardNumber){
		for(int i = 0; i < cards.length; i++){
			if(cards[i] != null && cards[i].number == cardNumber){
				cards[i] = null;
			}
		}
	}

	private double highCard(Card[] sortedCards){
		double value = 0;
		int handLength = 5;
		int pow = handLength - 1;

		for(int i = 0; i < handLength; i++){
			if(sortedCards[i] != null){
				value += Math.pow(cardsInColor, pow) * sortedCards[i].number;
				pow--;
			}
		}

		return value/1e6;
	}

}