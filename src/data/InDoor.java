package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

public class InDoor extends Door{
		
	public InDoor(int x, int y, int width, int height, String imageFile, int animLength, int frameTime, TileGrid grid, String mapName) {
		super(x, y, width, height, imageFile, animLength, frameTime, grid, mapName);
	}
	
	public InDoor(int x, int y, ItemType type, TileGrid grid) {
		super(x, y, type, grid);
	}
	
	public InDoor(String[] dataList, TileGrid grid) {
		super(dataList, grid);
		if (dataList.length > 10) {
			if (dataList[10].equals("true")) {
				passable = true;
				spriteAnim.setName(spriteAnim.getName() + "Open");
			}
			else {
				passable = false;
			}
		}
	}
	
	public void open() {
		if (!passable) {
			passable = true;
			spriteAnim.setName(spriteAnim.getName() + "Open");
		}
		else {
			spriteAnim.setName(spriteAnim.getName().substring(0, spriteAnim.getName().length()-4));
		}
	}
	
	public void Draw() {
		DrawQuadTex(x, y + offsetY, width, height, spriteAnim.getFrame(Delta()));
	}
	
	public boolean isSeeThrough() {
		return passable;
	}
	
	public String toString() {
		String lockMsg = lockedMessage.replace(' ', '_');
		String spriteName = spriteAnim.toString();
		if (spriteAnim.toString().contains("Open")) {
			spriteName = spriteName.substring(0, spriteName.length()-4);
		}
		return "InDoor " + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(width) + " " + Integer.toString(height) + " " + spriteName + " " + key.replace(' ', '_') + " " + mapName + " " + lockMsg + " " + passable;
	}
	
}
