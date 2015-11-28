package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class Tile {
	private float x, y, width, height;
	private Texture texture;
	private TileType type;
	private boolean visible = true;
	
	public Tile(float x, float y, float width, float height, TileType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.texture = LoadPNG(this.type.textureName);
	}
	
	public void Draw() {
		DrawQuadTex(this.x, this.y, this.width, this.height, this.texture);
	}
	
	public void DrawDark() {
		DrawQuadTex(this.x, this.y, this.width, this.height, this.texture);
		DrawQuadTex(this.x, this.y, this.width, this.height, LoadPNG("shade"));
	}

	public float getX() {
		return x;
	}
	
	public int getGridX() {
		return (int)x/64;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	
	public int getGridY() {
		return (int)y/64;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int gridDistanceFrom(Tile other) {
		int xDist = Math.abs(this.getGridX() - other.getGridX());
		int yDist = Math.abs(this.getGridY() - other.getGridY());
		return xDist + yDist;
	}
}
