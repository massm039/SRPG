package data;

public enum CharacterType {
	
	Daniel(64, 64, 50, 6, 20, "Daniel", 4, true, false, 1), Tali(64, 64, 50, 6, 20, "Tali", 4, false, false, 1), Erik(64, 64, 50, 6, 20, "Erik", 4, true, false, 1), Riken(64, 64, 50, 6, 20, "Riken", 4, true, false, 1), OldMan(64, 64, 50, 6, 20, "OldMan", 4, false, false, 1), Bandit(64, 64, 20, 6, 5, "Bandit", 4, false, true, 1), GreyBear(64, 64, 250, 2, 10, "GreyBear", 3, false, true, 1);
	
	int width, height, maxHealth, movement, animLength, damage, range;
	String name;
	public boolean playable, isEnemy;
	
	CharacterType (int width, int height, int maxHealth, int movement, int damage, String name, int animLength, boolean playable, boolean isEnemy, int range) {
		this.width = width;
		this.height = height;
		this.maxHealth = maxHealth;
		this.movement = movement;
		this.damage = damage;
		this.name = name;
		this.range = range;
		this.animLength = animLength;
		this.playable = playable;
		this.isEnemy = isEnemy;
	}
	
	public String toString() {
		return name;
	}
	
}