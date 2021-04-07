package poker.server.data.cards;

import java.util.Random;

public class Deck {

	private boolean[] drawnCards;
	private int freeCards;

	public Deck(){
		freeCards = 52;
		drawnCards = new boolean[freeCards];
	}

	public Card getRandomCard(){
		if(freeCards <= 0){
			throw new IllegalStateException("All cards have been drawn");
		}

		Random random = new Random();
		int i = random.nextInt(drawnCards.length);

		while(drawnCards[i]){
			i = random.nextInt(drawnCards.length);
		}
		drawnCards[i] = true;
		freeCards--;

		return new Card(i % 13, i /13);
	}
}