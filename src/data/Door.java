package data;

import java.util.ArrayList;

import static helpers.Artist.DrawQuadTex;
import static helpers.Cartographer.*;
import static helpers.Clock.Delta;

public class Door extends Item{
	
	String mapName, lockedMessage;
	String key = "none";
	
	public Door(int x, int y, int width, int height, String imageFile, int animLength, int frameTime, TileGrid grid, String mapName) {
		super(x, y, width, height, imageFile, animLength, grid);
		this.mapName = mapName;
	}
	
	public Door(int x, int y, ItemType type, TileGrid grid) {
		super(x, y, type, grid);
		this.mapName = type.toLevel;
	}
	
	public Door(String[] dataList, TileGrid grid) {
		this.x = Integer.parseInt(dataList[1]);
		this.y = Integer.parseInt(dataList[2]);
		this.width = Integer.parseInt(dataList[3]);
		this.height = Integer.parseInt(dataList[4]);
		this.texFile = dataList[5];
		spriteAnim = new SpriteAnim(texFile, Integer.parseInt(dataList[6]), Integer.parseInt(dataList[7]));
		this.grid = grid;
		if (!dataList[8].equals("none")) {
			key = dataList[8].replace("_", " ");
		}
		mapName = dataList[9];
		this.lockedMessage = dataList[10].replace('_', ' ');
	}
	
	public boolean isLocked() {
		if (mapName.equals("locked")) {
			return true;
		}
		return false;
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public ArrayList<String> examineBy(Character examiner) {
		ArrayList<String> list = new ArrayList<String>();
		if (isLocked()) {
			list.add("It's locked.");
		}
		list.add("Daniel: I can't just run away!");
		return list;
	}
	
	public Level getLevel() {
		return loadLevel(mapName);
	}
	
	public String getKey() {
		return key;
	}
	
	public boolean needsKey() {
		if (key == "none") {
			return false;
		}
		return true;
	}
	
	public String getMessage() {
		return lockedMessage;
	}
	
	public void Draw() {
		if (getTileOn().isVisible()) {
			DrawQuadTex(x, y + offsetY, width, height, spriteAnim.getFrame(Delta()));
		}
	}
	
	public String toString() {
		String lockMsg = lockedMessage.replace(' ', '_');
		return "Door " + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(width) + " " + Integer.toString(height) + " " + spriteAnim.toString() + " " + key.replace(' ', '_') + " " + mapName + " " + lockMsg;
	}
}
