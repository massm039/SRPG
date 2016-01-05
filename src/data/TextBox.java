package data;

import helpers.Clock;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import static helpers.StateManager.*;
import static helpers.Artist.*;

public class TextBox {
	
	private Player player;
	private String message = "";
	private ArrayList<String> dialog = new ArrayList<String>();
	private TrueTypeFont font;
	
	private final int x = 32, y = HEIGHT*2/3;
	private final Texture tex = LoadPNG("dialogBox");
	
	public TextBox() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 32);
		font = new TrueTypeFont(awtFont, false);
	}
	
	public TextBox(String font) {
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(font);
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(32f); //font size
			this.font = new TrueTypeFont(awtFont, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if (message != "") {
			Clock.setPause(true);
			while (Mouse.next()) {
				if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
					nextMessage();
				}
			}
			draw();
		}
		//MESSAGE FUNCTIONS
		if (message.equals("CHAPTERONE")) {
			nextMessage();
			setState(GameState.CHAPTERONE);
		}
		if (message.equals("death of character")) {
			nextMessage();
			player.getGame().reloadLevel();
		}
		if (message.contains("add-item-")) {
			String item = message.substring((message.indexOf("add-item-") + 9), message.length());
			player.addToInventory(item);
			nextMessage();
		}
	}
	
	public void setPlayer (Player player) {
		this.player = player;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void nextMessage() {
		if (dialog.size() != 0) {
			message = dialog.remove(0);
		}
		else {
			message = "";
			Clock.setPause(false);
			for (Character i : player.getCharacters()) {
				i.setTalking(false);
			}
		}
	}
	
	public void giveDialog(ArrayList<String> dialog) {
		this.message = dialog.remove(0);
		this.dialog = dialog;
	}
	
	public void changeFont(String font) {
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(font);
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(32f); //font size
			this.font = new TrueTypeFont(awtFont, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int findSpaceIndex(int start) {
		int index = start;
		while (message.charAt(index) != ' ') {
			index--;
		}
		return index;
	}
	
	private void draw() {
		DrawQuadTex(x, y, 1550, 420, tex);
		int maxLength = 59, indent = 8, margin = 32, yDist = 40;
		if (message.length() < 60) {
			drawText(font, x+margin+indent, y+yDist, message);
		}
		else if (message.length() < maxLength*2){
			drawText(font, x+margin+indent, y+yDist, message.substring(0, findSpaceIndex(findSpaceIndex(maxLength))));
			drawText(font, x+margin, y+yDist*2, message.substring(findSpaceIndex(maxLength)));
		}
		else if (message.length() < maxLength*3){
			drawText(font, x+margin+indent, y+yDist, message.substring(0, findSpaceIndex(maxLength)));
			drawText(font, x+margin, y+yDist*2, message.substring(findSpaceIndex(maxLength), findSpaceIndex(maxLength*2)));
			drawText(font, x+margin, y+yDist*3, message.substring(findSpaceIndex(maxLength*2)));
		}
		else {
			drawText(font, x+margin+indent, y+yDist, message.substring(0, findSpaceIndex(maxLength)));
			drawText(font, x+margin, y+yDist*2, message.substring(findSpaceIndex(maxLength), findSpaceIndex(maxLength*2)));
			drawText(font, x+margin, y+yDist*3, message.substring(findSpaceIndex(maxLength*2), findSpaceIndex(maxLength*3)));
			drawText(font, x+margin, y+yDist*4, message.substring(findSpaceIndex(maxLength*3)));
		}
		drawText(font, x+425, y+yDist*5, "- Left Click to Continue -"); //continue text
	}

}
