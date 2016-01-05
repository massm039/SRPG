package helpers;

import data.Level;
import data.MainMenu;
import data.Game;
import data.Edit;
import data.SpriteAnim;
import static helpers.Cartographer.*;
import static helpers.Artist.*;
import static helpers.Clock.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class StateManager {
	
	public static enum GameState {
		MAINMENU, GAME, EDIT, CHAPTERONE;
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Edit edit;
	public static String startLevel = "startCityHouseOneBasement";
	public static String editorLevel = "startCityRoad";
	public static SpriteAnim page;
	
	public static void update() {
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null) {
				mainMenu = new MainMenu();
			}
			mainMenu.update();
			break;
		case GAME:
			if (game == null) {
				Level level = loadLevel(startLevel);
				edit = null;
				page = null;
				mainMenu = null;
				game = new Game(level);
			}
			game.update();
			break;
		case EDIT:
			if (edit == null) {
				Level level = loadLevel(editorLevel);
				game = null;
				mainMenu = null;
				edit = new Edit(level, editorLevel);
			}
			edit.update();
			break;
		case CHAPTERONE:
			if (page == null) {
				game = null;
				edit = null;
				mainMenu = null;
				page = new SpriteAnim("Prologue", 1);
			}
			DrawQuadTex(0, 0, WIDTH, HEIGHT, page.getFrame(Delta()));
			//go to chapter on keypress or mouseclick
			while(Mouse.next() || Keyboard.next()) {
				if (Mouse.getEventButtonState() || Keyboard.getEventKeyState()) {
					startLevel = "chpOneTrail";
					setState(GameState.GAME);
				}
			}
			break;
		}
	}
	
	public static void setState(GameState newState) {
		mainMenu = null;
		game = null;
		edit = null;
		page = null;
		gameState = newState;
	}
	
}
