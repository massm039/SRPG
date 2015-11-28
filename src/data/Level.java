package data;

import java.util.ArrayList;

public class Level {
	
	private String levelName;
	private TileGrid grid;
	private ArrayList<Item> items;
	private ArrayList<Character> characters;
	private ArrayList<Conversation> conversations;
	private GoalManager goals;
	
	public Level() {
		this.grid = new TileGrid(this);
		this.items = new ArrayList<Item>();
		this.characters = new ArrayList<Character>();
		this.conversations = new ArrayList<Conversation>();
		this.goals = new GoalManager();
	}
	
	public Level(String name) {
		this.levelName = name;
	}
	
	public Level(TileGrid grid, ArrayList<Item> items, ArrayList<Character> characters) {
		this.grid = grid;
		this.items = items;
		this.characters = characters;
		this.conversations = new ArrayList<Conversation>();
		this.goals = new GoalManager();
	}
	
	public Level(TileGrid grid, ArrayList<Item> items, ArrayList<Character> characters, ArrayList<Conversation> conversations) {
		this.grid = grid;
		this.items = items;
		this.characters = characters;
		this.conversations = conversations;
		this.goals = new GoalManager();
	}
	
	public Level(TileGrid grid, ArrayList<Item> items, ArrayList<Character> characters, ArrayList<Conversation> conversations, GoalManager goals) {
		this.grid = grid;
		this.items = items;
		this.characters = characters;
		this.conversations = conversations;
		this.goals = goals;
	}
	
	public void updateVisibility() {
		grid.updateVisibility();
		for (Item i : items) {
			if (i.passable || grid.isSeen(i.getTileOn())) {
				i.setVisible(true);
			}
			else {
				i.setVisible(false);
			}
		}
		for (Character i : characters) {
			if (grid.isSeen(i.getTileOn())) {
				i.setVisible(true);
			}
			else {
				i.setVisible(false);
			}
		}
	}
		
	
	public TileGrid getGrid() {
		return grid;
	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}

	public ArrayList<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(ArrayList<Conversation> conversations) {
		this.conversations = conversations;
	}
	
	public GoalManager getGoals() {
		return goals;
	}
	
	public void setGoals(GoalManager goals) {
		this.goals = goals;
	}
	
	public String getName() {
		return levelName;
	}

}
