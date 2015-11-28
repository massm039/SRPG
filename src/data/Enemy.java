package data;

public class Enemy extends Character{
	
	boolean moved = false, inCombat = true;
	
	public Enemy(int x, int y, CharacterType type, Level level) {
		super(x, y, type, level);
	}
	
	public void update() {
		if (moving) {
			moved = true;
			move();
		}
		Draw();
	}
	
	public void drawCheckpoints() {};
	
	public boolean getMoved() {
		return moved;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public void deselect() {
		checkpoints.clear();
	}
	
	public void die() {
		player.getLevel().getCharacters().remove(this);
		player.getMoved().remove(this);
		player.getCombatManager().enemies.remove(this);
	}
	
	public void chase(Character target) {
		checkpoints = autoPath(target.getTileOn());
		if (checkpoints != null) {
			if (checkpoints.get(checkpoints.size()-1).getTileOn().gridDistanceFrom(target.getTileOn()) == 1) {
				queueInteract(target);
			}
			moving = true;
		}
		else {
			moved = true;
		}
	}
	
	protected void attack(Character other) {
		other.setHealth(other.getHealth()-this.damage);
		if (other.health <= 0) {
			other.die();
			for (Character i :player.getCharacters()) {
				if (i instanceof Enemy) {
					((Enemy) i).setMoved(true);
				}
			}
		}
	}
	
	protected void deselectSelf() {
		interact();
	}
	
}
