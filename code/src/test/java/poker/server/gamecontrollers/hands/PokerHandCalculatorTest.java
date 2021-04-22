package poker.server.gamecontrollers.hands;

import org.junit.jupiter.api.Test;
import poker.server.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PokerHandCalculatorTest {

    @Test
    public void shouldReturnHighCardWhenCardsAreHighCard(){
        Card[] tableCards = {
                new Card(10, 0),
                new Card(0, 1),
                new Card(11, 2),
                new Card(12, 3),
                new Card(7, 2)
        };

        Card[] strongerHandCards = {new Card(5, 3), new Card(1, 0)};
        Card[] weakerHandCards = {new Card(4, 1), new Card(2, 1)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 0;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnOnePairWhenCardsAreOnePair(){
        Card[] tableCards = {
                new Card(10, 0),
                new Card(0, 1),
                new Card(11, 2),
                new Card(12, 3),
                new Card(7, 2)
        };

        Card[] strongerHandCards = {new Card(11, 3), new Card(9, 0)};
        Card[] weakerHandCards = {new Card(8, 1), new Card(11, 1)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 1;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnTwoPairWhenCardsAreTwoPair(){
        Card[] tableCards = {
                new Card(6, 0),
                new Card(0, 1),
                new Card(11, 2),
                new Card(0, 3),
                new Card(9, 2)
        };

        Card[] strongerHandCards = {new Card(11, 3), new Card(3, 0)};
        Card[] weakerHandCards = {new Card(11, 1), new Card(2, 1)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 2;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnThreeOfKindWhenCardsAreThreeOfKind(){
        Card[] tableCards = {
                new Card(6, 0),
                new Card(0, 1),
                new Card(11, 2),
                new Card(0, 3),
                new Card(9, 2)
        };

        Card[] strongerHandCards = {new Card(0, 0), new Card(8, 0)};
        Card[] weakerHandCards = {new Card(0, 2), new Card(7, 1)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 3;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnStraightWhenCardsAreStraight(){
        Card[] tableCards = {
                new Card(6, 0),
                new Card(0, 1),
                new Card(8, 2),
                new Card(10, 3),
                new Card(9, 2)
        };

        Card[] strongerHandCards = {new Card(11, 1), new Card(12, 1)};
        Card[] weakerHandCards = {new Card(7, 3), new Card(3, 0)};


        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 4;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnFlushWhenCardsAreFlush() {
        Card[] tableCards = {
                new Card(6, 2),
                new Card(0, 1),
                new Card(8, 2),
                new Card(10, 3),
                new Card(9, 2)
        };

        Card[] strongerHandCards = {new Card(0, 2), new Card(3, 2)};
        Card[] weakerHandCards = {new Card(1, 2), new Card(2, 2)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 5;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnFullHouseWhenCardsAreFullHouse(){
        Card[] tableCards = {
                new Card(4, 0),
                new Card(11, 1),
                new Card(4, 2),
                new Card(6, 3),
                new Card(5, 2)
        };

        Card[] strongerHandCards = {new Card(4, 3), new Card(6, 0)};
        Card[] weakerHandCards = {new Card(4, 1), new Card(5, 0)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 6;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnFourOfKindWhenCardsAreFourOfKind() {
        Card[] tableCards = {
                new Card(6, 2),
                new Card(0, 1),
                new Card(6, 1),
                new Card(6, 3),
                new Card(9, 2)
        };

        Card[] strongerHandCards = {new Card(6, 0), new Card(10, 2)};
        Card[] weakerHandCards = {new Card(6, 0), new Card(2, 2)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 7;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

    @Test
    public void shouldReturnStraightFlushWhenCardsAreStraightFlush() {
        Card[] tableCards = {
                new Card(11, 1),
                new Card(10, 1),
                new Card(9, 1),
                new Card(8, 1),
                new Card(2, 2)
        };

        Card[] strongerHandCards = {new Card(12, 1), new Card(3, 3)};
        Card[] weakerHandCards = {new Card(7, 1), new Card(2, 0)};

        PokerHandCalculator phc = new PokerHandCalculator(tableCards);
        double stronger = phc.handStrength(strongerHandCards);
        double weaker = phc.handStrength(weakerHandCards);
        double expectedPoints = 8;

        assertTrue(stronger > expectedPoints && stronger < expectedPoints + 1);
        assertTrue(weaker > expectedPoints && weaker < expectedPoints + 1);
        assertTrue(stronger > weaker);
    }

}
