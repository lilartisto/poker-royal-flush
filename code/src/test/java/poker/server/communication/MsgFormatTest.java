package poker.server.communication;

import org.junit.jupiter.api.Test;
import poker.server.data.cards.Deck;
import poker.server.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MsgFormatTest {

    @Test
    public void shouldReturnCorrectStringWhenCardsAreNotNull(){
        Deck deck = new Deck();
        Card[] cards = {deck.getRandomCard(), deck.getRandomCard()};

        String expectedMsg = "{\"name\":\"start\",\"cards\":[" +
                "{\"color\":" + cards[0].color + ",\"number\":" + cards[0].number + "}," +
                "{\"color\":" + cards[1].color + ",\"number\":" + cards[1].number + "}]}";
        String actualMsg = MsgFormat.startMsg(cards);

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void shouldReturnCorrectStringWhenCardsAreNull(){
        Card[] cards = {null, null};

        String expectedMsg = "{\"name\":\"start\",\"cards\":[null,null]}";
        String actualMsg = MsgFormat.startMsg(cards);

        assertEquals(expectedMsg, actualMsg);
    }
}
