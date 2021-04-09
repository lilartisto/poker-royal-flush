package poker.client.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poker.client.Game;
import poker.client.data.Player;
import poker.client.data.cards.Card;

public class TableView {

	private final Canvas canvas;
	private final Image tableImage;

	private final int cardsSpace = 10;

	public TableView(Canvas canvas){
		this.canvas = canvas;

		String path = getClass().getResource("/graphics/table.png").toExternalForm();
		tableImage = path != null ? new Image(path): null;
	}

	public void draw(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawTable(gc);
		drawPlayerCards(gc);

		//TODO
		// other draw functions
	}

	private void drawTable(GraphicsContext gc){
		if(tableImage != null){
			gc.drawImage(tableImage, 0, 0);
		}
	}

	private void drawPlayerCards(GraphicsContext gc){
		Player player = Game.getPlayer();
		Card[] cards = player.getHandCards();
		int x = 480;
		int y = 360;

		for(int i = 0; i < cards.length; i++){
			if(cards[i] != null){
				int cardX = x + i*((int)cards[i].image.getWidth() + cardsSpace);
				gc.drawImage(cards[i].image, cardX, y);
			}
		}
	}

	private void drawPlayers(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawPot(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawTableCards(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}



	public void drawEnding(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawEndingPlayersCards(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawEndingWinner(GraphicsContext gc){
		throw new UnsupportedOperationException("Not implemented yet");
	}
}