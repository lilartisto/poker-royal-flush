package poker.server.gamecontrollers.hands;

import org.junit.jupiter.api.Test;
import poker.server.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PokerHandCalculatorTest {

    @Test
    public void shouldReturnFullHouseWhenCardsAreFullHouse(){
        Card[] tableCards = new Card[5];
        tableCards[0] = new Card(4, 0);
        tableCards[1] = new Card(11, 1);
        tableCards[2] = new Card(4, 2);
        tableCards[3] = new Card(6, 3);
        tableCards[4] = new Card(5, 2);

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);

        Card[] strongerHandCards = new Card[2];
        strongerHandCards[0] = new Card(4, 3);
        strongerHandCards[1] = new Card(6, 0);

        Card[] weakerHandCards = new Card[2];
        weakerHandCards[0] = new Card(4, 1);
        weakerHandCards[1] = new Card(5, 0);

        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);

        assertTrue(stronger > weaker);
    }
}
