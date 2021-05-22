package poker.client.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import poker.client.Game;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Card;
import poker.properties.PlayerStateProperties;

public class TableView {

	private final Canvas canvas;
	private final Image tableImage;
	private final GameTable gameTable;

	private final int cardsSpace = 10;
	private final int playersFrameHeight = 50;
	private final int playersFrameWidth = 200;

	private boolean drawingEnding;

	public TableView(Canvas canvas){
		this.canvas = canvas;
		drawingEnding = false;
		gameTable = GameTable.getInstance();

		String path = getClass().getResource("/graphics/table.png").toExternalForm();
		tableImage = path != null ? new Image(path): null;
	}

	public void draw(){
		GraphicsContext gc = canvas.getGraphicsContext2D();

		clear(gc);
		drawTable(gc);
		drawMainPlayerCards(gc);
		drawPlayers(gc);
		drawTableCards(gc);
		drawPot(gc);
	}

	private void clear(GraphicsContext gc){
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	private void drawTable(GraphicsContext gc){
		if(tableImage != null){
			gc.drawImage(tableImage, 0, 0);
		}
	}

	private void drawMainPlayerCards(GraphicsContext gc){
		Card[] cards = gameTable.getHandCards();
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
		Player[] players = gameTable.getPlayers();

		drawFirstRowOfPlayers(gc, players);
		drawSecondRowOfPlayers(gc, players);
		drawThirdRowOfPlayers(gc, players);
	}

	private void drawFirstRowOfPlayers(GraphicsContext gc, Player[] players){
		int x = 290;
		int y = 5;
		int playersPotX = x + playersFrameWidth/2;
		int playersPotY = y + playersFrameHeight + 25;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[0]);

		x = getSymmetricalX(x, playersFrameWidth);
		playersPotX = x + playersFrameWidth/2;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[1]);
	}

	private void drawSecondRowOfPlayers(GraphicsContext gc, Player[] players){
		int x = 5;
		int y = 200;
		int playersPotX = x + playersFrameWidth + 65;
		int playersPotY = y + playersFrameHeight/2;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[5]);

		x = getSymmetricalX(x, playersFrameWidth);
		playersPotX = x - 65;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[2]);
	}

	private void drawThirdRowOfPlayers(GraphicsContext gc, Player[] players){
		int x = 220;
		int y = 395;
		int playersPotX = x + playersFrameWidth/2;
		int playersPotY = y - 25;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[4]);

		x = getSymmetricalX(x, playersFrameWidth);
		playersPotX = x + playersFrameWidth/2;

		drawPlayer(gc, x, y, playersPotX, playersPotY, players[3]);
	}

	private void drawPlayer(GraphicsContext gc, int frameX, int frameY, int playerPotX, int playerPotY, Player player){
		if(player == null){
			return;
		}

		drawPlayersFrame(gc, frameX, frameY, player);

		if(drawingEnding) {
			drawPlayersCards(gc, frameX, frameY, player.getHandCards());
		} else {
			drawPlayersPot(gc, playerPotX, playerPotY, player);
		}
	}

	private void drawPlayersCards(GraphicsContext gc, int frameX, int frameY, Card[] handCards){
		if(handCards[0] == null || handCards[1] == null) {
			return;
		}

		int x = frameX + playersFrameWidth/2;
		int y = frameY + (playersFrameHeight - (int)handCards[0].image.getHeight()/2)/2;

		for (Card handCard : handCards) {
			if (handCard != null) {
				gc.drawImage(handCard.image, x, y, handCard.image.getWidth()/2, handCard.image.getHeight()/2);
				x += (handCard.image.getWidth() + cardsSpace)/2;
			}
		}
	}

	private void drawPlayersFrame(GraphicsContext gc, int x, int y, Player player){
		int playerFrameBorderThickness = 5;

		gc.setFill(Color.ALICEBLUE);
		gc.setStroke(getBorderColor(player));
		gc.setLineWidth(playerFrameBorderThickness);

		gc.strokeRect(x,  y, playersFrameWidth, playersFrameHeight);
		gc.fillRect(x, y, playersFrameWidth, playersFrameHeight);

		drawPlayerInfo(gc, x, y, player);
	}

	private void drawPlayerInfo(GraphicsContext gc, int frameX, int frameY, Player player){
		int fontSize = 20;
		int y = frameY + playersFrameHeight/2;
		int x = frameX + playersFrameWidth/2;
		String text = String.format("%-10s %6d$", player.nickname, player.getMoney());
		Font font = Font.font("Arial", FontWeight.LIGHT, fontSize);

		drawText(gc, text, font, x, y, Color.BLACK);
	}

	private void drawPlayersPot(GraphicsContext gc, int x, int y, Player player){
		int playersPot = player.getPotValue();

		if(playersPot > 0){
			int fontSize = 17;
			String text = playersPot + "$";
			Font font = Font.font("Arial", FontWeight.LIGHT, fontSize);

			drawText(gc, text, font, x, y, Color.BLACK);
		}
	}

	private Color getBorderColor(Player player){
		if(drawingEnding){
			return getBorderColorEnding(player.isWinner());
		} else {
			return getBorderColorNotEnding(player.getState());
		}
	}

	private Color getBorderColorEnding(boolean isWinner){
		if(isWinner){
			return Color.INDIANRED;
		} else {
			return Color.DARKGRAY;
		}
	}

	private Color getBorderColorNotEnding(int state){
		if(state == PlayerStateProperties.INGAME){
			return Color.BLUEVIOLET;
		} else if(state == PlayerStateProperties.INMOVE){
			return Color.INDIANRED;
		} else {
			return Color.DARKGRAY;
		}
	}

	private void drawPot(GraphicsContext gc){
		int framesWidth = 100;
		int framesHeight = 60;
		int x = getCenterX(framesWidth);
		int y = 275;

		gc.setStroke(Color.LIGHTGRAY);
		gc.strokeRect(x, y, framesWidth, framesHeight);

		int fontSize = 22;
		Font font = Font.font("Arial", FontWeight.LIGHT, fontSize);
		String text = gameTable.getPotValue() + "$";

		drawText(gc, text, font, x + framesWidth/2, y + framesHeight/2, Color.BLACK);
	}

	private void drawTableCards(GraphicsContext gc){
		Card[] tableCards = gameTable.getTableCards();
		int x = 330;
		int y = 125;

		for (Card tableCard : tableCards) {
			Image image;
			if(tableCard == null) {
				image = Game.getDeck().getBackCard().image;
			} else {
				image = tableCard.image;
			}

			gc.drawImage(image, x, y);
			x += image.getWidth() + cardsSpace;
		}
	}

	private void drawText(GraphicsContext gc, String s, Font font, int x, int y, Color color){
		Text text = new Text(s);
		text.setFont(font);

		x -= (int)(text.getLayoutBounds().getWidth()/2);
		y += (int)(text.getLayoutBounds().getHeight()*(0.66)/2.0);

		gc.setFont(font);
		gc.setFill(color);
		gc.fillText(s, x, y);
	}

	private int getCenterX(int width){
		return ((int)canvas.getWidth() - width)/2;
	}

	private int getSymmetricalX(int x, int width){
		return (int)canvas.getWidth() - (x + width);
	}


	public void drawEnding(){
		drawingEnding = true;
		draw();
		drawingEnding = false;
	}
}