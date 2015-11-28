package data;

import java.util.ArrayList;

public class CombatManager {
	
	Player player;
	ArrayList<Character> allies = new ArrayList<Character>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	String turn = "allies";
	int turnNumber = 1;
	
	public CombatManager(Player player) {
		this.player = player;
		ArrayList<Character> characters = player.getCharacters();
		for (Character i : characters) {
			if (i instanceof Enemy) {
				enemies.add((Enemy)i);
			}
			else if (i.isPlayable()){
				allies.add(i);
			}
		}
	}
	
	public void update() {
		if (enemies.size() > 0 && !player.inCombat()) {
			player.enterCombat();
		}
		if (player.inCombat() && enemies.size() == 0){
			player.exitCombat();
		}
		if (player.inCombat()) {
			if (alliesMoved()) {
				moveEnemies();
			}
			enemiesMoved();
		}
	}
	
	public boolean alliesMoved() {
		boolean allMoved = true;
		if (player.getMoved().size() != allies.size()) {
			allMoved = false;
		}
		else {
			for (Character i : allies) {
				if (!player.getMoved().contains(i)) {
					allMoved = false;
				}
			}
		}
		if (allMoved) {
			if (turn == "allies") {
				turn = "enemies";
				for (Enemy i : enemies) {
					i.setMoved(false);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean enemiesMoved() {
		for (Enemy i : enemies) {
			if (!i.getMoved()) {
				return false;
			}
		}
		if (turn == "enemies") {
			turn = "allies";
			turnNumber++;
			player.resetMoved();
			runEvent(turnNumber);
		}
		return true;
	}
	
	public void moveEnemies() {
		if (noCharactersMoving()) {
			Enemy enemy = getNextEnemy();
			if (enemy == null) {
				return;
			}
			Character target = getClosestAlly(enemy);
			if (target == null) {
				enemy.setMoved(true);
				return;
			}
			enemy.chase(target);
		}
	}
	
	private void runEvent(int turnNum) {
	}
	
	private Enemy getNextEnemy() {
		for (Enemy i : enemies) {
			if (!i.getMoved()) {
				return i;
			}
		}
		return null;
	}
	
	private Character getClosestAlly(Character character) {
		int min = character.getMovSpeed()+1;
		Character target = null;
		for (Character i : allies) {
			if (character.getTileOn().gridDistanceFrom(i.getTileOn()) < min) {
				target = i;
				min = character.getTileOn().gridDistanceFrom(i.getTileOn());
			}
		}
		return target;
	}
	
	private boolean noCharactersMoving() {
		for (Character i :player.getCharacters()) {
			if (i.getMoving()) {
				return false;
			}
		}
		return true;
	}

}
