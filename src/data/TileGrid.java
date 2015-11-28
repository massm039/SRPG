package data;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import static helpers.Artist.*;

public class TileGrid {
	
	public Tile[][] map;
	private int tilesWide;
	private int tilesHigh;
	private Level level;
	
	public TileGrid(Level level) {
		this.level = level;
		this.tilesWide = 20;
		this.tilesHigh = 15;
		map = new Tile[20][15];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Grass);
			}
		}
	}
	
	public TileGrid(int[][] newMap, Level level) {
		this.level = level;
		this.tilesWide = 20;
		this.tilesHigh = 15;
		map = new Tile[20][15];
		for (int x=0; x<map.length; x++) {
			for (int y=0; y<map[x].length; y++) {
				switch(newMap[y][x]) {
				case 0:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Grass);
					break;
				case 1:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Dirt);
					break;
				case 2:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Rock);
					break;
				case 3:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Sand);
					break;
				case 4:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Obsidean);
					break;
				case 5:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Cobblestone);
					break;
				case 6:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.MossCobbs);
					break;
				case 7:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.StoneTile);
					break;
				case 8:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.WoodFloor);
					break;
				case 9:
					map[x][y] = new Tile(x*64, y*64, 64, 64, TileType.Black);
					break;
				}
			}
		}
	}
	
	public int getTilesWide() {
		return tilesWide;
	}

	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
	
	public void setTile(int x, int y, TileType type) {
		map[x][y] = new Tile(x*64, y*64, 64, 64, type);
	}
	
	public Tile getGridTile(int x, int y) {
		if (x < 0 || x*64 > WIDTH || y < 0 || y*64 > HEIGHT) {
			return null;
		}
		return map[x][y];
	}
	
	public Tile getTile(int x, int y) {
		return map[(int)(x/64)] [(int)(y/64)];
	}

	public Tile getMouseTile() {
		int xPos = (int)(Mouse.getX()/64);
		xPos *= 64;
		int yPos = (int)((HEIGHT-1 - Mouse.getY())/64);
		yPos *= 64;
		return getTile(xPos, yPos);
	}
	
	public void drawCursor() {
		DrawQuadTex((int)getMouseTile().getX(), (int)getMouseTile().getY(), 64, 64, LoadPNG("cursor"));
	}
	
	public void Draw() {
		for (int x=0; x<map.length; x++) {
			for (int y=0; y<map[x].length; y++) {
				if (map[x][y].isVisible()) {
					map[x][y].Draw();
				}
				else {
					map[x][y].DrawDark();
				}
			}
		}
	}
	
	public void updateVisibility() {
		for (int x=0; x<map.length; x++) {
			for (int y=0; y<map[x].length; y++) {
				if (isSeen(map[x][y])) {
					map[x][y].setVisible(true);
				}
				else {
					map[x][y].setVisible(false);
				}
			}
		}
	}
	
	public boolean isSeen(Tile tile) {
		for (Character i : playableCharacters()) {
			if (i.canSee(tile)) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Character> playableCharacters() {
		ArrayList<Character> reList = new ArrayList<Character>();
		for (Character i : level.getCharacters()) {
			if (i.isPlayable()) {
				reList.add(i);
			}
		}
		return reList;
	}

}
