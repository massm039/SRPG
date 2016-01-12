package data;

import org.lwjgl.input.Mouse;

import userInterface.Button;
import userInterface.UI;

import java.util.ArrayList;

public class OptionsMenu {
	
	private TextBox parent;
	private Character actor;
	private ArrayList<Character> targets;
	private UI menuUI;
	private float x, y;
	
	public OptionsMenu(Character actr, TextBox parent) {
		menuUI = new UI();
		this.parent = parent;
		actor = actr;
		x = actor.getX();
		y = actor.getY();
		targets = new ArrayList<Character>();
		ArrayList<Character> chars = actor.player.getCharacters();
		for (Character i : chars) {
			int distance = i.getTileOn().gridDistanceFrom(actor.getTileOn());
			if ((distance > 0) && ((int)distance <= actr.range)) {
				targets.add(i);
			}
		}
		menuUI.addButton("Attack", "attackButton", (int)x+3, (int)y+4);
		menuUI.addButton("Wait", "waitButton", (int)x+3, (int)y+32);
	}
	
	public void update() {
		if (targets.isEmpty()) {
			closeMenu();
			return;
		}
		while(Mouse.next()) {
			updateButtons();
		}
		menuUI.draw();
	}
	
	private void updateButtons() {
		if (Mouse.isButtonDown(0) && Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
			Button clicked = menuUI.getButtonClicked();
			if (clicked != null) {
				if (clicked.getName() == "Attack") {
					for (Character i : targets) {
						menuUI.addButton(i.getName(), "attackpoint", (int)i.getX(), (int)i.getY());
					}
					menuUI.removeButton("Attack");
					menuUI.removeButton("Wait");
					if (menuUI.numButtons() == 0) {
						closeMenu();
						return;
					}
				}
				if (clicked.getName() == "Wait") {
					closeMenu();
					return;
				}
				if (clicked != null) {
					ArrayList<Character> chars = actor.player.getCharacters();
					for (Character i : chars) {
						if (i.getName() == clicked.getName() && i.getX() == clicked.getX() && i.getY() == clicked.getY()) {
							actor.attack(i);
							closeMenu();
							return;
						}
					}
				}
			}
		}
	}
	
	private void closeMenu() {
		parent.closeOptions();
	}

}
