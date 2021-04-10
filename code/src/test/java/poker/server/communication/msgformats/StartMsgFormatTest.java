package poker.server.communication.msgformats;

import org.junit.jupiter.api.Test;
import poker.server.communication.msgformats.StartMsgFormat;
import poker.server.data.cards.Deck;
import poker.server.data.cards.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartMsgFormatTest {

    @Test
    public void shouldReturnCorrectStringWhenCardsAreNotNull(){
        Deck deck = new Deck();
        Card[] cards = {deck.getRandomCard(), deck.getRandomCard()};

        String expectedMsg = "{\"name\":\"start\",\"cards\":[" +
                "{\"color\":" + cards[0].color + ",\"number\":" + cards[0].number + "}," +
                "{\"color\":" + cards[1].color + ",\"number\":" + cards[1].number + "}]}";
        String actualMsg = StartMsgFormat.getMsg(cards);

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void shouldReturnCorrectStringWhenCardsAreNull(){
        Card[] cards = {null, null};

        String expectedMsg = "{\"name\":\"start\",\"cards\":[null,null]}";
        String actualMsg = StartMsgFormat.getMsg(cards);

        assertEquals(expectedMsg, actualMsg);
    }
}
