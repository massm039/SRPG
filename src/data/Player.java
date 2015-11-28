package data;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;

import static helpers.Cartographer.*;

public class Player {
	
	private Game game;
	private Level level;
	private TextBox dialogBox;
	private CombatManager combatManager;
	private ArrayList<String> inventory = new ArrayList<String>();
	
	private ArrayList<Character> moved = new ArrayList<Character>();
	private Character selected;
	private boolean inCombat = false;
	
	Player(Level level, TextBox dialogBox, Game game) {
		this.level = level;
		this.dialogBox = dialogBox;
		this.game = game;
		this.combatManager = new CombatManager(this);
		
		for (int i=0; i<level.getCharacters().size(); i++) {
			level.getCharacters().get(i).setPlayer(this);
		}
	}
	
	public void update() {
		updateInput();
		if (!inCombat && moved.size() != 0) {
			moved = new ArrayList<Character>();
		}
		combatManager.update();
	}
	
	public void drawHealthBars() {
		if (inCombat) {
			for (Character i : level.getCharacters()) {
				if (i.isVisible()) {
					i.drawHealthBar();
				}
			}
		}
	}
	
	private void updateInput() {
		if (dialogBox.getMessage() == "") {
			while(Mouse.next()) {
				if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) { //left click
					if (selected != null) {
						if (!selected.getMoving()) {
							moveChar();
						}
					}
					for (int i=0; i<level.getCharacters().size(); i++) {
						if (level.getCharacters().get(i).mouseContact()) {
							if (selected == null && level.getCharacters().get(i).isPlayable()) {
								selectChar(level.getCharacters().get(i));
							}
						}
					}
				}
			}
		}
	}
	
	public void deselectChar() {
		selected.deselect();
		selected = null;
	}
	
	public void enterCombat() {
		level.getGoals().interaction("Enter", "Combat");
		inCombat = true;
		for (int i=0; i<level.getCharacters().size(); i++) {
			level.getCharacters().get(i).enterCombat();
		}
		for (Conversation i : level.getConversations()) {
			if (i.checkNames("Enter", "Combat")) {
				openDialog(i.getStatements());
			}
		}
	}
	
	public void exitCombat() {
		level.getGoals().interaction("Exit", "Combat");
		inCombat = false;
		for (int i=0; i<level.getCharacters().size(); i++) {
			level.getCharacters().get(i).exitCombat();
		}
		if (selected != null) {
			deselectChar();
		}
		for (Character i : level.getCharacters()) {
			i.setPlayable(getCharacterType(i.getName()).playable);
		}
		for (Conversation i : level.getConversations()) {
			if (i.checkNames("Exit", "Combat")) {
				openDialog(i.getStatements());
			}
		}
	}
	
	public void changeLevel(Door door) {
		game.changeLevel(door.getLevel());
		this.combatManager = new CombatManager(this);
	}
	
	public void setLevel(Level level) {
		this.level = level;
		for (int i=0; i<level.getCharacters().size(); i++) {
			level.getCharacters().get(i).setPlayer(this);
		}
	}
	
	public void openDialog(String dialog) {
		if (dialog.contains("I added a") && dialog.contains("to my inventory.")) {
			String key = dialog.substring(dialog.indexOf("I added a ") + 10, dialog.indexOf(" to my inventory."));
			if (!inventory.contains(key)) {
				inventory.add(key);
			}
		}
		dialogBox.setMessage(dialog);
	}
	
	public void openDialog(ArrayList<String> dialog) {
		for (String i : dialog) {
			if (i.contains("I added a ") && i.contains("to my inventory.")) {
				String key = i.substring(i.indexOf("I added a ") + 10, i.indexOf(" to my inventory."));
				if (!inventory.contains(key)) {
					inventory.add(key);
				}
			}
		}
		dialogBox.giveDialog(dialog);
	}
	
	public ArrayList<Character> getCharacters() {
		return level.getCharacters();
	}
	
	public ArrayList<Character> getMoved() {
		return moved;
	}
	
	public void resetMoved() {
		moved.clear();
	}
	
	public boolean inCombat() {
		return inCombat;
	}
	
	public CombatManager getCombatManager() {
		return combatManager;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean useKey(Door door) {
		if (inventory.contains(door.getKey())) {
			return true;
		}
		return false;
	}
	
	private void moveChar() {
		if (inCombat) {
			moved.add(selected);
		}
		selected.followCheckpoints(level.getGrid().getMouseTile());
		selected.queueInteract(selected.otherObjectOnTile(level.getGrid().getMouseTile()));
	}
	
	private void selectChar(Character newChar) {
		if (newChar.getName() == "Daniel" || (inCombat && !(newChar instanceof Enemy))) {
			if (selected == newChar) {
				deselectChar();
				return;
			}
			if (!moved.contains(newChar) && selected == null) {
				selected = newChar;
				newChar.select();
			}
		}
	}

}
