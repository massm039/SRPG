package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

public class InDoor extends Door{
		
	public InDoor(int x, int y, int width, int height, String imageFile, int animLength, TileGrid grid, String mapName) {
		super(x, y, width, height, imageFile, animLength, grid, mapName);
	}
	
	public InDoor(int x, int y, ItemType type, TileGrid grid) {
		super(x, y, type, grid);
	}
	
	public InDoor(String[] dataList, TileGrid grid) {
		super(dataList, grid);
		if (dataList.length > 10) {
			if (dataList[10].equals("true")) {
				passable = true;
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
			passable = false;
			spriteAnim.setName(spriteAnim.getName().substring(0, spriteAnim.getName().length()-4));
		}
	}
	
	public void Draw() {
		DrawQuadTex(x, y + offsetY, width, height, spriteAnim.getFrame(Delta()));
	}
	
	public String toString() {
		return "InDoor " + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(width) + " " + Integer.toString(height) + " " + spriteAnim.toString() + " " + key.replace(' ', '_') + " " + mapName + " It's_Locked. " + passable;
	}
	
}
