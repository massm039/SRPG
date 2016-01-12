package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;
import static helpers.Cartographer.*;

public class Editor {
	
	private Level level;
	
	private TileType[] tileTypes = {TileType.Grass, TileType.Dirt, TileType.Rock, TileType.Sand, TileType.Obsidean, TileType.Cobblestone, 
			TileType.MossCobbs, TileType.StoneTile, TileType.WoodFloor, TileType.YellowRoof, TileType.DryWall, TileType.RedRoof, TileType.GreyBrickWall, 
			TileType.TanRoof, TileType.BrownRoof, TileType.BrownWall, TileType.GreenRoof, TileType.GreenWall, TileType.HorizontalBridge, TileType.VerticalBridge, 
			TileType.GlassBridge, TileType.Water, TileType.Black};
	
	private ItemType[] itemTypes = {ItemType.Door, ItemType.InDoor, ItemType.StairsDown, ItemType.StairsUp, ItemType.UpArrow, ItemType.DownArrow, ItemType.LeftArrow, ItemType.RightArrow, ItemType.Drawers, 
			ItemType.OldDrawers, ItemType.DrawersOther, ItemType.OldDrawersOther, ItemType.DrawersWhite, ItemType.OldDrawersWhite, ItemType.ShelfWhite, 
			ItemType.OldShelfWhite, ItemType.ShelfPurple, ItemType.OldShelfPurple, ItemType.Dresser, ItemType.OldDresser, ItemType.WoodShelf, ItemType.WoodShelfTwo, 
			ItemType.WoodShelfThree, ItemType.WoodGlassShelf, ItemType.WoodGlassShelfTwo, ItemType.WoodGlassShelfThree, ItemType.WoodFilledShelf, 
			ItemType.WoodFilledShelfTwo, ItemType.WoodFilledShelfThree, ItemType.RedWoodShelf, ItemType.RedWoodShelfTwo, ItemType.RedWoodShelfThree, 
			ItemType.RedWoodFilledShelf, ItemType.RedWoodFilledShelfTwo, ItemType.RedWoodFilledShelfThree, ItemType.FamilyPortrait, ItemType.OldPortrait,
			ItemType.Tree};
	
	private CharacterType[] characterTypes = {CharacterType.Daniel, CharacterType.Erik, CharacterType.Riken, CharacterType.Tali, CharacterType.OldMan, CharacterType.Bandit, CharacterType.GreyBear};
	
	private int tileIndex = 0, itemIndex = 0, characterIndex = 0;
	private String mode = "tiles", saveFile;
	
	public Editor(Level level, String name) {
		this.level = level;
		this.saveFile = name;
	}
	
	public void update() {
		drawCursor();
		switch(mode) {
		case "tiles":
			//space pressed
			while (Keyboard.next()) {
				//change mode
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
					mode = "characters";
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
					mode = "items";
				}
				//change tile
				if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
					moveTileIndexUp();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()) {
					moveTileIndexDown();
				}
				//save map
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState()) {
					saveLevel(saveFile, level, false);
				}
			}
			//left click = make tile
			if (Mouse.isButtonDown(0)) {
				this.setTile();
			}
			break;
		case "items":
			//space pressed
			while (Keyboard.next()) {
				//change mode
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
					mode = "tiles";
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
					mode = "characters";
				}
				//change item
				if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
					moveItemIndexUp();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()) {
					moveItemIndexDown();
				}
				//save map
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState()) {
					saveLevel(saveFile, level, false);
				}

			}
			//left click = make item
			if (Mouse.isButtonDown(0)) {
				if (!objectOnTile(level.getGrid().getMouseTile())) {
					placeItem();
				}
			}
			//right click = delete item
			if (Mouse.isButtonDown(1)) {
				deleteItem();
			}
			break;
		case "characters":
			//space pressed
			while (Keyboard.next()) {
				//next mode
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
					mode = "tiles";
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
					mode = "items";
				}
				//next char
				if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
					moveCharacterIndexUp();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()) {
					moveCharacterIndexDown();
				}
				//save map
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState()) {
					saveLevel(saveFile, level, false);
				}
			}
			//left click = make character
			if (Mouse.isButtonDown(0)) {
				if (!objectOnTile(level.getGrid().getMouseTile())) {
					placeCharacter();
				}
			}
			//right click = delete character
			if (Mouse.isButtonDown(1)) {
				deleteCharacter();
			}
		}

	}
	
	private void moveTileIndexUp() {
		tileIndex++;
		if (tileIndex > tileTypes.length-1) {
			tileIndex = 0;
		}
	}
	
	private void moveTileIndexDown() {
		tileIndex--;
		if (tileIndex < 0) {
			tileIndex = tileTypes.length-1;
		}
	}
	
	private void moveItemIndexUp() {
		itemIndex++;
		if (itemIndex > itemTypes.length-1) {
			itemIndex = 0;
		}
	}
	
	private void moveItemIndexDown() {
		itemIndex--;
		if (itemIndex < 0) {
			itemIndex = itemTypes.length-1;
		}
	}
	
	private void moveCharacterIndexUp() {
		characterIndex++;
		if (characterIndex > characterTypes.length-1) {
			characterIndex = 0;
		}
	}
	
	private void moveCharacterIndexDown() {
		characterIndex--;
		if (characterIndex < 0) {
			characterIndex = characterTypes.length-1;
		}
	}
	
	private void setTile() {
		level.getGrid().setTile((int)(Mouse.getX() / 64), (int)((HEIGHT - Mouse.getY() - 1)/64), tileTypes[tileIndex]);
	}
	
	private void placeItem() {
		if (itemTypes[itemIndex].toLevel.equals("none")) {
			level.getItems().add(new Item((int)(Mouse.getX() / 64), (int)((HEIGHT - Mouse.getY() - 1) / 64), itemTypes[itemIndex], level.getGrid()));
		}
		else if (itemTypes[itemIndex].toLevel.equals("InDoor")) {
			level.getItems().add(new InDoor((int)(Mouse.getX() / 64), (int)((HEIGHT - Mouse.getY() - 1) / 64), itemTypes[itemIndex], level.getGrid()));
		}
		else {
			level.getItems().add(new Door((int)(Mouse.getX() / 64), (int)((HEIGHT - Mouse.getY() - 1) / 64), itemTypes[itemIndex], level.getGrid()));
		}
	}
	
	private void placeCharacter() {
		level.getCharacters().add(new Character((int)(Mouse.getX() / 64), (int)((HEIGHT - Mouse.getY() - 1)/64), characterTypes[characterIndex], level));
	}
	
	private void deleteItem() {
		for (int i=0; i<level.getItems().size(); i++) {
			if (level.getItems().get(i).mouseContact()) {
				level.getItems().remove(i);
			}
		}
	}
	
	private void deleteCharacter() {
		for (int i=0; i<level.getCharacters().size(); i++) {
			if (level.getCharacters().get(i).mouseContact()) {
				level.getCharacters().remove(i);
			}
		}
	}
	
	private boolean objectOnTile(Tile tile) {
		for (int i=0; i<level.getCharacters().size(); i++) {
			if (level.getCharacters().get(i).getTileOn() == tile) {
				return true;
			}
		}
		for (int i=0; i<level.getItems().size(); i++) {
			if (level.getItems().get(i).getTileOn() == tile) {
				return true;
			}
		}
		return false;
	}
	
	private void drawCursor() {
		if (mode == "tiles") {
			DrawQuadTex(((int)(Mouse.getX()/64))*64, ((int)((HEIGHT-1 - Mouse.getY())/64))*64, 64, 64, LoadPNG(tileTypes[tileIndex].textureName));
		}
		if (mode == "items") {
			DrawQuadTex(((int)(Mouse.getX()/64))*64, ((int)((HEIGHT-1 - Mouse.getY())/64))*64 + itemTypes[itemIndex].offsetY, 64, 64, LoadPNG(itemTypes[itemIndex].tex));
		}
		if (mode == "characters") {
			DrawQuadTex(((int)(Mouse.getX()/64))*64, ((int)((HEIGHT-1 - Mouse.getY())/64))*64, 64, 64, LoadPNG(characterTypes[characterIndex].name));
		}
		DrawQuadTex(((int)(Mouse.getX()/64))*64, ((int)((HEIGHT-1 - Mouse.getY())/64))*64, 64, 64, LoadPNG("cursor"));
	}
}
