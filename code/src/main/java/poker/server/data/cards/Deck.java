package poker.server.data.cards;

import poker.properties.CardsProperties;

import java.util.Random;

public class Deck {

    private final boolean[] drawnCards;
    private int freeCards;

    public Deck() {
        freeCards = CardsProperties.DECKSIZE;
        drawnCards = new boolean[freeCards];
    }

    public Card getRandomCard() {
        if (freeCards <= 0) {
            throw new IllegalStateException("All cards have been drawn");
        }

        int i = getRandomCardIndex();
        drawnCards[i] = true;
        freeCards--;

        return new Card(i % CardsProperties.COLORSIZE, i / CardsProperties.COLORSIZE);
    }

    private int getRandomCardIndex() {
        Random random = new Random();
        int i = random.nextInt(drawnCards.length);

        while (drawnCards[i]) {
            i = random.nextInt(drawnCards.length);
        }

        return i;
    }
}