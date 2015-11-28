package helpers;

import data.Level;
import data.MainMenu;
import data.Game;
import data.Edit;
import data.SpriteAnim;
import static helpers.Cartographer.*;
import static helpers.Artist.*;
import static helpers.Clock.*;

public class StateManager {
	
	public static enum GameState {
		MAINMENU, GAME, EDIT, CHAPTER;
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Edit edit;
	public static String startLevel = "startCityHouseOneBasement";
	public static String editorLevel = "startCity";
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
				game = new Game(level);
			}
			game.update();
			break;
		case EDIT:
			if (edit == null) {
				Level level = loadLevel(editorLevel);
				edit = new Edit(level, editorLevel);
			}
			edit.update();
			break;
		case CHAPTER:
			if (page == null) {
				page = new SpriteAnim("Prologue", 1);
			}
			DrawQuadTex(0, 0, WIDTH, HEIGHT, page.getFrame(Delta()));
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
