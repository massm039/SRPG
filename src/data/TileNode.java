package data;

import java.util.ArrayList;
import static helpers.Artist.*;

public class TileNode {
	
	private TileGrid grid;
	private TileNode from = null;
	private Tile tile, target;
	private float toStart, toEnd;
	
	public TileNode(Tile tile, Tile target, TileGrid grid) {
		this.tile = tile;
		toStart = 0;
		toEnd = tile.gridDistanceFrom(target);
		this.target = target;
		this.grid = grid;
	}
	
	public TileNode(Tile tile, TileNode from, Tile target, TileGrid grid) {
		this.tile = tile;
		this.from = from;
		this.target = target;
		toStart = from.toStart() + tile.getType().difficulty;
		toEnd = tile.gridDistanceFrom(target);
		this.grid = grid;
	}
	
	public ArrayList<TileNode> getNeighbours() {
		ArrayList<TileNode> neighbours = new ArrayList<TileNode>();
		int x = tile.getGridX();
		int y = tile.getGridY();
		if ((x+1)*64 < WIDTH) {
			neighbours.add(new TileNode(grid.getGridTile(x+1, y), this, target, grid));
		}
		if ((x-1)*64 >= 0) {
			neighbours.add(new TileNode(grid.getGridTile(x-1, y), this, target, grid));
		}
		if ((y+1)*64 < HEIGHT) {
			neighbours.add(new TileNode(grid.getGridTile(x, y+1), this, target, grid));
		}
		if ((y-1)*64 >= 0) {
			neighbours.add(new TileNode(grid.getGridTile(x, y-1), this, target, grid));
		}
		return neighbours;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public TileNode getFrom() {
		return from;
	}
	
	public void setFrom(TileNode from) {
		this.from = from;
	}
	
	public float toStart() {
		return toStart;
	}
	
	public float toEnd() {
		return toEnd;
	}
	
	public float totalVal() {
		return toStart + toEnd;
	}
	
	public boolean equals(TileNode other) {
		if (other.getTile().equals(tile)) {
			return true;
		}
		return false;
	}
}
