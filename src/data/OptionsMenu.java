package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import userInterface.UI;
import static helpers.StateManager.*;
import static helpers.Artist.*;

public class OptionsMenu {
	
	private Character actor;
	private Texture background;
	private UI menuUI;
	
	public OptionsMenu() {
		background = LoadPNG("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Attack", "playButton", WIDTH/2 - 128*3, (int)(HEIGHT*0.45f));
		menuUI.addButton("Editor", "editorButton", WIDTH/2 + 128, (int)(HEIGHT*0.45f));
		menuUI.addButton("Quit", "quitButton", WIDTH/2 - 128, (int)(HEIGHT*0.65f));
	}
	
	public void update() {
		DrawQuadTex(actor.getX(), actor.getY(), 128, 384, background);
		updateButtons();
		menuUI.draw();
	}
	
	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Play")) {
				setState(GameState.GAME);
			}
			if (menuUI.isButtonClicked("Editor")) {
				setState(GameState.EDIT);
			}
			if (menuUI.isButtonClicked("Quit")) {
				System.exit(0);
			}
		}
	}

}
