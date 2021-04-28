package poker.client.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import poker.client.Game;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;
import poker.properties.PlayerStateProperties;

import java.util.Collections;

public class TableView {

	private final Canvas canvas;
	private final Image tableImage;

	private final int cardsSpace = 10;
	private final int playerFrameHeight = 50;
	private final int playerFrameWidth = 200;

	public TableView(Canvas canvas){
		this.canvas = canvas;

		String path = getClass().getResource("/graphics/table.png").toExternalForm();
		tableImage = path != null ? new Image(path): null;
	}

	public void draw(){
		GraphicsContext gc = canvas.getGraphicsContext2D();

		clear(gc);
		drawTable(gc);
		drawPlayerCards(gc);
		drawPlayers(gc);

		//TODO
		// other draw functions
	}

	private void clear(GraphicsContext gc){
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
		Player[] players = Game.getGameTable().getPlayers();
		int x = 290;
		int y = 5;

		drawPlayer(gc, x, y, 0, 0, players[0]);
		drawPlayer(gc, (int)canvas.getWidth() - x - playerFrameWidth, y, 0, 0, players[1]);
	}

	private void drawPlayer(GraphicsContext gc, int frameX, int frameY, int playerPotX, int playerPotY, Player player){
		if(player == null){
			return;
		}

		drawPlayersFrame(gc, frameX, frameY, player);
		drawPlayerInfo(gc, frameX, frameY, player);
		//TODO
		//	drawing player's pot
	}

	private void drawPlayersFrame(GraphicsContext gc, int x, int y, Player player){
		int playerFrameBorderThickness = 5;

		gc.setFill(Color.ALICEBLUE);
		gc.setStroke(getBorderColor(player));
		gc.setLineWidth(playerFrameBorderThickness);

		gc.strokeRect(x,  y, playerFrameWidth, playerFrameHeight);
		gc.fillRect(x, y, playerFrameWidth, playerFrameHeight);
	}

	private void drawPlayerInfo(GraphicsContext gc, int frameX, int frameY, Player player){
		int fontSize = 15;
		int y = frameY + (playerFrameHeight - fontSize)/2;
		int x = frameX + 5;
		String text = String.format("%-10s %6d$", player.nickname, player.getPotValue());
		gc.setFont(Font.font("Verdana", FontWeight.LIGHT, fontSize));
		gc.setFill(Color.BLACK);

		gc.fillText(text, x, y);
	}

	private Color getBorderColor(Player player){
		int state = player.getState();

		if(state == PlayerStateProperties.INGAME){
			return Color.BLUEVIOLET;
		} else if(state == PlayerStateProperties.INMOVE){
			return Color.INDIANRED;
		} else {
			return Color.DARKGRAY;
		}
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