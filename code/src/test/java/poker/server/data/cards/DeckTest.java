package poker.server.data.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp(){
        deck = new Deck();
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenAllCardsAreDrawn(){
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> {
                    for(int i = 0; i < 53; i++){
                        deck.getRandomCard();
                    }
                });
        assertEquals("All cards have been drawn", exception.getMessage());
    }

    @Test
    public void shouldReturnOtherCardWhenMethodIsCalledMultipleTimes(){
        Card card = deck.getRandomCard();

        for (int i = 0; i < 51; i++){
            if(card.equals(deck.getRandomCard())){
                assert false;
            }
        }
        assert true;
    }
}
