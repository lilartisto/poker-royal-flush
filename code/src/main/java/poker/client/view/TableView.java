package poker.client.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import poker.client.Game;
import poker.client.data.Player;
import poker.client.data.cards.Card;

public class TableView {

	private Canvas canvas;
	private Image tableImage;

	public TableView(Canvas canvas){
		this.canvas = canvas;
		//tableImage = wczytanie stolu;
	}

	public void draw(){
		drawPlayerCards(Game.getPlayer());
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	public void drawEnding(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawEndingPlayersCards(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawEndingWinner(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawPlayerCards(Player player){
		Card[] cards = player.getHandCards();

		for(int i = 0; i < cards.length; i++){
			if(cards[i] != null){
				canvas.getGraphicsContext2D().drawImage(cards[i].image, i*150 + 50, 50);
			}
		}
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawPlayers(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawPot(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawTable(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawTableCards(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}