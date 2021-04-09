package poker.client.data.cards;

import javafx.scene.image.Image;

public class Deck {

	private final Card[] cards;

	public Deck(){
		cards = new Card[53];
		initialize();
	}

	private void initialize(){
		for(int i = 0; i < cards.length-1; i++){
			int color = i / 13;
			int number = i % 13;
			Image image;

			try {
				String resourcesPath = "/graphics/cards/" + color + "/" + number + ".png";
				String path = getClass().getResource(resourcesPath).toExternalForm();
				image = new Image(path);
			} catch (Exception e){
				image = null;
			}

			cards[i] = new Card(color, number, image);
		}
	}

	public Card getCard(int color, int number){
		return cards[color*13 + number];
	}

	public Card getBackCard(){
		return cards[cards.length-1];
	}

}