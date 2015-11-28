package data;

import org.lwjgl.input.Mouse;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Item {
	
	protected int x, y, width, height, offsetY;
	protected SpriteAnim spriteAnim;
	protected TileGrid grid;
	protected String texFile, name;
	protected boolean passable = false, visible;
	
	public Item() {
		
	}
	
	public Item(int x, int y, int width, int height, String imageFile, int animLength, TileGrid grid) {
		this.x = x*64;
		this.y = y*64;
		this.width = width;
		this.height = height;
		this.texFile = imageFile;
		this.spriteAnim = new SpriteAnim(imageFile, animLength);
		this.grid = grid;
		this.offsetY = 0;
	}
	
	public Item(int x, int y, ItemType type, TileGrid grid) {
		this.x = x*64;
		this.y = y*64;
		this.width = type.width;
		this.height = type.height;
		this.texFile = type.tex;
		this.spriteAnim = new SpriteAnim(type.tex, type.animLength);
		this.name = type.name;
		this.offsetY = type.offsetY;
		this.grid = grid;
	}
	
	public Item(String[] newData, TileGrid grid) {
		x = Integer.parseInt(newData[1]);
		y = Integer.parseInt(newData[2]);
		width = Integer.parseInt(newData[3]);
		height = Integer.parseInt(newData[4]);
		texFile = newData[5];
		this.spriteAnim = new SpriteAnim(newData[5], Integer.parseInt(newData[6]));
		name = newData[7];
		this.offsetY = Integer.parseInt(newData[8]);
		this.grid = grid;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void Draw() {
		if (getTileOn().isVisible()) {
			DrawQuadTex(x, y + offsetY, width, height, spriteAnim.getFrame(Delta()));
		}
	}
	
	public Tile getTileOn() {
		return grid.getTile(x, y);
	}
	
	public boolean mouseContact() {
		if (Mouse.getX() >= x && Mouse.getX() <= x+width) {
			if (HEIGHT-1 - Mouse.getY() >= y && HEIGHT-1 - Mouse.getY() <= y+height) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Item " + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(width) + " " + Integer.toString(height) + " " + spriteAnim.toString() + " " + name + " " + Integer.toString(offsetY);
	}

}
