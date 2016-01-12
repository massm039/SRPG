package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import data.Conversation;
import data.Door;
import data.InDoor;
import data.Enemy;
import data.GoalManager;
import data.ItemType;
import data.Level;
import data.TileGrid;
import data.TileType;
import data.Item;
import data.Character;
import data.CharacterType;

public class Cartographer {
	
	public static void saveLevel(String mapName, Level level, boolean inGame) {
		if (inGame) {
			mapName = "save" + mapName;
			try {
				File directory = new File("C:\\gamesaves"); //JAR
				directory.mkdir(); //JAR
				File file = new File(directory + "\\" + mapName); //JAR
				BufferedWriter bw = new BufferedWriter(new FileWriter(file)); //JAR
				
				saveGrid(bw, level.getGrid());
				saveItems(bw, level.getItems());
				saveCharacters(bw, level.getCharacters());
				bw.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				File file = new File(mapName);
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				
				saveGrid(bw, level.getGrid());
				saveItems(bw, level.getItems());
				saveCharacters(bw, level.getCharacters());
				bw.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Level loadLevel(String mapName) {
		Level level = new Level(mapName);
		BufferedReader br;
		try {
			File file = new File("C:\\gamesaves\\save" + mapName); //JAR
			br = new BufferedReader(new FileReader(file));
			level.setGrid(loadGrid(br, level));
			level.setItems(loadItems(br, level));
			level.setCharacters(loadCharacters(br, level));
			br.close();
		}
		catch (Exception e) {
			try {
				/*
				InputStream in = Cartographer.class.getClassLoader().getResourceAsStream(mapName); //for file IO in JAR file
				br = new BufferedReader(new InputStreamReader(in)); //for file IO in JAR file
				*/
				
				File file = new File(mapName); //for file IO in Eclipse
				br = new BufferedReader(new FileReader(file)); //for file IO in Eclipse
				
				level.setGrid(loadGrid(br, level));
				level.setItems(loadItems(br, level));
				level.setCharacters(loadCharacters(br, level));
				br.close();
			}
			catch (Exception err) {
				level = new Level();
			}
		}
		try {
			/*
			InputStream in = Cartographer.class.getClassLoader().getResourceAsStream(removeNums(mapName + "Dialog")); //for file IO in JAR file
			br = new BufferedReader(new InputStreamReader(in)); //for file IO in JAR file
			*/
			
			File file = new File(mapName + "Dialog"); //for file IO in Eclipse
			br = new BufferedReader(new FileReader(file)); //for file IO in Eclipse
			
			level.setConversations(loadConversations(br));
			level.setGoals(loadGoals(br));
			br.close();
			return level;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return level;
	}
	
	public static String getTileID(TileType type) {
		return type.getID();
	}
	
	public static TileType getTileType(String ID) {
		switch(ID) {
		case "0":
			return TileType.Dirt;			
		case "1":
			return TileType.Grass;			
		case "2":
			return TileType.Rock;			
		case "3":
			return TileType.Sand;			
		case "4":
			return TileType.Obsidean;			
		case "5":
			return TileType.Cobblestone;			
		case "6":
			return TileType.MossCobbs;			
		case "7":
			return TileType.StoneTile;			
		case "8":
			return TileType.WoodFloor;
		case "9":
			return TileType.YellowRoof;
		case "10":
			return TileType.DryWall;
		case "11":
			return TileType.RedRoof;
		case "12":
			return TileType.GreyBrickWall;
		case "13":
			return TileType.TanRoof;
		case "14":
			return TileType.BrownRoof;
		case "15":
			return TileType.BrownWall;
		case "16":
			return TileType.GreenRoof;
		case "17":
			return TileType.GreenWall;
		case "18":
			return TileType.HorizontalBridge;
		case "19":
			return TileType.GlassBridge;
		case "20":
			return TileType.VerticalBridge;
		case "21":
			return TileType.Water;
		case "22":
			return TileType.Black;
		}
		return null;
	}
	
	public static ItemType getItemType(String name) {
		switch(name) {
		case "Door":
			return ItemType.Door;
		case "InDoor":
			return ItemType.InDoor;
		case "StairsUp":
			return ItemType.StairsUp;
		case "StairsDown":
			return ItemType.StairsDown;
		case "UpArrow":
			return ItemType.UpArrow;
		case "DownArrow":
			return ItemType.DownArrow;
		case "LeftArrow":
			return ItemType.LeftArrow;
		case "RightArrow":
			return ItemType.RightArrow;
		case "Drawers":
			return ItemType.Drawers;
		case "oldDrawers":
			return ItemType.Drawers;
		case "DrawersOther":
			return ItemType.DrawersOther;
		case "OldDrawersOther":
			return ItemType.OldDrawersOther;
		case "DrawersWhite":
			return ItemType.DrawersWhite;
		case "OldDrawersWhite":
			return ItemType.OldDrawersWhite;
		case "ShelfWhite":
			return ItemType.ShelfWhite;
		case "OldShelfWhite":
			return ItemType.OldShelfWhite;
		case "ShelfPurple":
			return ItemType.ShelfPurple;
		case "OldShelfPurple":
			return ItemType.OldShelfPurple;
		case "Dresser":
			return ItemType.Dresser;
		case "OldDresser":
			return ItemType.OldDresser;
		case "WoodShelf":
			return ItemType.WoodShelf;
		case "WoodShelfTwo":
			return ItemType.WoodShelfTwo;
		case "WoodShelfThree":
			return ItemType.WoodShelfThree;
		case "WoodGlassShelf":
			return ItemType.WoodGlassShelf;
		case "WoodGlassShelfTwo":
			return ItemType.WoodGlassShelfTwo;
		case "WoodGlassShelfThree":
			return ItemType.WoodGlassShelfThree;
		case "WoodFilledShelf":
			return ItemType.WoodFilledShelf;
		case "WoodFilledShelfTwo":
			return ItemType.WoodFilledShelfTwo;
		case "WoodFilledShelftThree":
			return ItemType.WoodFilledShelfThree;
		case "RedWoodShelf":
			return ItemType.RedWoodShelf;
		case "RedWoodShelfTwo":
			return ItemType.RedWoodShelfTwo;
		case "RedWoodShelfThree":
			return ItemType.RedWoodShelfThree;
		case "RedWoodFilledShelf":
			return ItemType.RedWoodFilledShelf;
		case "RedWoodFilledShelfTwo":
			return ItemType.RedWoodFilledShelfTwo;
		case "RedWoodFilledShelfThree":
			return ItemType.RedWoodFilledShelfThree;
		case "FamilyPortrait":
			return ItemType.FamilyPortrait;
		case "OldPortrait":
			return ItemType.OldPortrait;
		case "Tree":
			return ItemType.Tree;
		default:
			return null;
		}
	}
	
	public static CharacterType getCharacterType(String name) {
		switch(name) {
		case "Daniel":
			return CharacterType.Daniel;
		case "Tali":
			return CharacterType.Tali;
		case "Erik":
			return CharacterType.Erik;
		case "Riken":
			return CharacterType.Riken;
		case "OldMan":
			return CharacterType.OldMan;
		case "Bandit":
			return CharacterType.Bandit;
		case "GreyBear":
			return CharacterType.GreyBear;
		default:
			return null;
		}
	}
	
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f: files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				}
				else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
	
	private static TileGrid loadGrid(BufferedReader br, Level level){
		TileGrid grid = new TileGrid(level);
		try {
			String data = br.readLine();
			String[] dataList = data.split(",");
			for (int i=0; i<grid.getTilesWide(); i++) {
				for (int j=0; j<grid.getTilesHigh(); j++) {
					grid.setTile(i, j, getTileType(dataList[i*grid.getTilesHigh() + j]));
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grid;
	}
	
	private static ArrayList<Item> loadItems(BufferedReader br, Level level){
		ArrayList<Item> items = new ArrayList<Item>();
		try {
			String[] dataList = br.readLine().split(",");
			for (String i : dataList) {
				String[] subDataList = i.split(" ");
				if (subDataList[0].equals("Door")) {
					items.add(new Door(subDataList, level.getGrid()));
				}
				else if (subDataList[0].equals("InDoor")) {
					items.add(new InDoor(subDataList, level.getGrid()));
				}
				else if (subDataList[0].equals("Item")) {
					items.add(new Item(subDataList, level.getGrid()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	private static ArrayList<Character> loadCharacters(BufferedReader br, Level level) {
		ArrayList<Character> characters = new ArrayList<Character>();
		try {
			String[] dataList = br.readLine().split(",");
			for (String i : dataList) {
				String[] newList = i.split(" ");
				int x = Integer.parseInt(newList[0]);
				int y = Integer.parseInt(newList[1]);
				CharacterType type = getCharacterType(newList[2]);
				if (type.isEnemy) {
					characters.add(new Enemy(x, y, type, level));
				}
				else {
					characters.add(new Character(x, y, type, level));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return characters;
	}
	
	private static ArrayList<Conversation> loadConversations(BufferedReader br) {
		ArrayList<Conversation> convos = new ArrayList<Conversation>();
		try {
			String[] dataList = br.readLine().split("~");
			for (String i : dataList) {
				convos.add(new Conversation(i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return convos;
	}
	
	private static GoalManager loadGoals(BufferedReader br) {
		try {
			return new GoalManager(br.readLine());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new GoalManager();
	}
	
	private static void saveGrid(BufferedWriter bw, TileGrid grid) {
		String mapData = "";
		for (int i=0; i<grid.getTilesWide(); i++) {
			for (int j=0; j<grid.getTilesHigh(); j++) {
				mapData += getTileID(grid.getTile(i*64,  j*64).getType())+',';
			}
		}
		mapData += '\n';
		try {
			bw.write(mapData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void saveItems(BufferedWriter bw, ArrayList<Item> items) {
		String mapData = "";
		for (int i=0; i<items.size(); i++) {
			if (items.get(i) instanceof InDoor) {
				mapData += ((InDoor)items.get(i)).toString() + ",";
			}
			else if (items.get(i) instanceof Door) {
				mapData += ((Door)items.get(i)).toString() + ",";
			}
			else {
				mapData += items.get(i).toString() + ",";
			}
		}
		mapData += '\n';
		try {
			bw.write(mapData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveCharacters(BufferedWriter bw, ArrayList<Character> characters) {
		String mapData = "";
		for (int i=0; i<characters.size(); i++) {
			mapData += characters.get(i).toString() + ",";
		}
		mapData += '\n';
		try {
			bw.write(mapData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String removeNums(String string) {
		for (int i=0; i<string.length(); i++) {
			if ("1234567890".contains("" + string.charAt(i))) {
				string = string.substring(0, i) + string.substring(i+1);
			}
		}
		return string;
	}
	
}
