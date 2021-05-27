package poker.client.data.cards;

import javafx.scene.image.Image;
import poker.properties.CardsProperties;

public class Deck {

    private static Deck instance;

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    private final Card[] cards;

    public Deck() {
        cards = new Card[CardsProperties.DECKSIZE + 1];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < cards.length - 1; i++) {
            int color = i / CardsProperties.COLORSIZE;
            int number = i % CardsProperties.COLORSIZE;

            String resourcesPath = "/graphics/cards/" + color + "/" + number + ".png";

            cards[i] = new Card(color, number, loadImage(resourcesPath));
        }
        cards[cards.length - 1] = new Card(-1, -1, loadImage("/graphics/cards/52.png"));
    }

    private Image loadImage(String resourcesPath) {
        try {
            String path = getClass().getResource(resourcesPath).toExternalForm();
            return new Image(path);
        } catch (Exception e) {
            return null;
        }
    }

    public Card getCard(int color, int number) {
        return cards[color * CardsProperties.COLORSIZE + number];
    }

    public Card getBackCard() {
        return cards[cards.length - 1];
    }

}