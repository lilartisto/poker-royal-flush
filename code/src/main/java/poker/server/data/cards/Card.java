package poker.server.data.cards;

public class Card {

	public final int number;
	public final int color;

	public Card(int number, int color){
		this.number = number;
		this.color = color;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Card){
			Card card = (Card)obj;
			return card.number == number && card.color == color;
		}
		return false;
	}
}