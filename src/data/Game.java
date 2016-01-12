package data;

import static helpers.Clock.*;
import static helpers.Cartographer.*;

public class Game {
	
	private Level level;
	private TextBox dialogBox;
	private Player player;
	
	public Game(Level level) {
		this.level = level;
		dialogBox = new TextBox();
		player = new Player(level, dialogBox, this);
		dialogBox.setPlayer(player);
		level.updateVisibility();
	}
	
	public void update() {
		level.getGrid().Draw();
		player.update();
		
		for (Item i : level.getItems()) { //Draw All level.getItems()
			i.Draw();
		}
		
		if (level.getCharacters().size() != 0) {
			Character c = level.getCharacters().get(0);
			c.update();
			while (level.getCharacters().indexOf(c)+1 < level.getCharacters().size()) { //Draw All level.getCharacters()
				c = level.getCharacters().get(level.getCharacters().indexOf(c)+1);
				c.update();
			}
		}
		
		if (!isPaused()) {
			level.getGrid().drawCursor();
		}
		
		player.drawHealthBars();
		
		dialogBox.update(); //draw dialog box
		
		if (!dialogBox.getMessage().equals("")) {
			setPause(true);
		}
		else {
			setPause(false);
		}
	}
	
	public void changeLevel(Level level) {
		saveLevel(this.level.getName(), this.level, true);
		if (level.getGoals() != null) {
			level.getGoals().addGoals(this.level.getGoals());
		}
		else {
			level.setGoals(this.level.getGoals());
		}
		this.level = level;
		player.setLevel(level);
		level.updateVisibility();
	}
	
	public void reloadLevel() {
		String name = level.getName();
		this.level = null;
		this.level = loadLevel(name);
		player = new Player(level, dialogBox, this);
		dialogBox.setPlayer(player);
		level.updateVisibility();
	}

}
