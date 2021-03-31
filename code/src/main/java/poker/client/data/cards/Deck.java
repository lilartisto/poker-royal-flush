package poker.client.data.cards;

public class Deck {

	private final Card[] cards;

	public Deck(){
		cards = new Card[53];
	}

	public Card getCard(int color, int number){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public Card getBackCard(){
		return cards[cards.length-1];
	}

}