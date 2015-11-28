package data;

public class Checkpoint {
	
	private Tile tile;
	
	public Checkpoint(Tile tile) {
		this.tile = tile;
	}

	public float getX() {
		return tile.getX();
	}
	
	public float getY() {
		return tile.getY();
	}
	
	public Tile getTileOn() {
		return tile;
	}
	
	public boolean equals(Checkpoint other) {
		if (tile.equals(other.getTileOn())) {
			return true;
		}
		return false;
	}
}
