package poker.client.data.cards;

import javafx.scene.image.Image;

public class Card {

    public final int color;
    public final int number;
    public final Image image;

    public Card(int color, int number, Image image) {
        this.color = color;
        this.number = number;
        this.image = image;
    }

}