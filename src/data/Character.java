package data;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;
import static helpers.Clock.*;

public class Character {
	protected int x, y, width, height, maxHealth, speed, movement, damage = 10, range;
	protected Sprite sprite;
	protected Level level;
	protected Player player;
	protected String name = "Character";
	protected CharacterType type = null;
	protected ArrayList<String> deathScript = new ArrayList<String>();
	protected boolean playable, blind = false, visible = true;
	
	protected String animation = "";
	protected int animTimer;
	protected boolean inCombat = false, talking = false, selected = false, moving = false;
	protected ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
	protected float distance = 0;
	protected int health;
	protected Object interaction;
	
	public Character(int x, int y, int width, int height, int maxHealth, int movement, String sprite, int animLength, boolean playable, Level level) {
		this.x = x*64;
		this.y = y*64;
		this.width = width;
		this.height = height;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.movement = movement;
		this.speed = movement*10;
		this.sprite = new Sprite(sprite, animLength);
		this.playable = playable;
		this.level = level;
		deathScript.add(name + ": I couldn't make it, I'm sorry I let you all down.");
		deathScript.add("dead-character");
	}
	
	public Character(int x, int y, CharacterType type, Level level) {
		this.x = x*64;
		this.y = y*64;
		this.type = type;
		this.width = type.width;
		this.height = type.height;
		this.maxHealth = type.maxHealth;
		this.health = type.maxHealth;
		this.movement = type.movement;
		this.speed = type.movement*10;
		this.damage = type.damage;
		this.name = type.name;
		this.range = type.range;
		this.sprite = new Sprite(type.name.toLowerCase(), type.animLength);
		this.playable = type.playable;
		this.level = level;
		deathScript.add(name + ": I couldn't make it, I'm sorry I let you all down.");
		deathScript.add("dead-character");
	}

	public void update() {
		if (moving) {
			move();
		}
		if (selected == true && !moving) {
			makeCheckpoints();
		}
		Draw();
	}
		
	public void Draw() {
		if (visible) {
			if (selected || moving) {
				drawCheckpoints();
			}
			if ((playable || getTileOn().isVisible())) {
				updateSprite();
			}
		}
	}
	
	public void drawCheckpoints() {
		Checkpoint curr;
		for (int i=0; i<checkpoints.size(); i++) {
			curr = checkpoints.get(i);
			DrawQuadTex(curr.getX(), curr.getY(), 64, 64, LoadPNG("checkpoint"));
		}
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void select() {
		selected = true;
		checkpoints.add(new Checkpoint(level.getGrid().getTile(x, y)));
	}
	
	public void deselect() {
		selected = false;
		checkpoints.clear();
	}
	
	public Tile getTileOn() {
		return level.getGrid().getTile(x, y);
	}
	
	public ArrayList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMovSpeed() {
		return speed;
	}
	
	public boolean getMoving() {
		return moving;
	}
	
	public boolean isPlayable() {
		return playable;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean mouseContact() {
		if (Mouse.getX() >= x && Mouse.getX() <= x+width) {
			if (HEIGHT-1 - Mouse.getY() >= y && HEIGHT-1 - Mouse.getY() <= y+height) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> examineBy(Character examiner) {
		ArrayList<Conversation> convos = level.getConversations();
		for (int i=0; i<convos.size(); i++) {
			if ((convos.get(i).getNameOne() == this.name && convos.get(i).getNameTwo() == examiner.getName())
				|| (convos.get(i).getNameOne() == examiner.getName() && convos.get(i).getNameTwo() == this.name)) {
				return convos.get(i).getStatements();
			}
		}
		convos = null;
		return new ArrayList<String>();
	}
	
	public void followCheckpoints(Tile tile) {
		moving = true;
	}
	
	public void enterCombat() {
		inCombat = true;
	}
	
	public void exitCombat() {
		inCombat = false;
	}
	
	public void drawHealthBar() {
		int baseX = (x+2);
		int baseY = y-8;
		DrawQuadTex(baseX, baseY, width-4, 4, LoadPNG("darkBar"));
		DrawQuadTex(baseX, baseY, ((float)health/(float)maxHealth)*(width-4), 4, LoadPNG("healthBar"));
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
	public String toString() {
		return Integer.toString(x/64) + " " + Integer.toString(y/64) + " " + type.toString();
	}
	
	public void queueInteract(Object other) {
		interaction = other;
	}
	
	public void die() {
		player.openDialog(deathScript);
	}
	
	public void setPlayable(boolean playable) {
		this.playable = playable;
	}
	
	public boolean canSee(Tile tile) {
		if (tile.getType().name().equals("Black")) {
			return true;
		}
		if (!tile.getType().passable) {
			TileNode node = new TileNode(tile, tile, level.getGrid());
			for (TileNode i : node.getNeighbours()) {
				if (i.getTile().getType().passable) {
					if (level.getGrid().isSeen(i.getTile())) {
						return true;
					}
				}
			}
			node = null;
			return false;
		}
		ArrayList<Checkpoint> checks = autoSee(tile);
		if (checks == null) {
			return false;
		}
		return true;
	}
	
	public void setBlind(boolean val) {
		blind = val;
	}
	
	public void setTalking(boolean talking) {
		this.talking = talking;
	}
	
	public void lookAt(Object other) {
		if (other instanceof Character) {
			if (((Character)other).getX() > this.getX()) {
				sprite.setDirection("right");
			}
			if (((Character)other).getX() < this.getX()) {
				sprite.setDirection("left");
			}
			if (((Character)other).getY() > this.getY()) {
				sprite.setDirection("down");
			}
			if (((Character)other).getY() < this.getY()) {
				sprite.setDirection("up");
			}
		}
		else if (other instanceof Item){
			if (((Item)other).getTileOn().getX() > this.getX()) {
				sprite.setDirection("right");
			}
			if (((Item)other).getTileOn().getX() < this.getX()) {
				sprite.setDirection("left");
			}
			if (((Item)other).getTileOn().getY() > this.getY()) {
				sprite.setDirection("down");
			}
			if (((Item)other).getTileOn().getY() < this.getY()) {
				sprite.setDirection("up");
			}
		}
		talking = true;
	}
	
	protected void interact() {
		if (interaction != null) {
			if (interaction instanceof Character) {
				if (inCombat) {
					attack((Character)interaction);
				}
				else {
					talk((Character)interaction);
				}
			}
			else if (interaction instanceof Item) {
				if (interaction instanceof Door && name.equals("Daniel")) {
					if (interaction instanceof InDoor) {
						if (player.useKey((Door)interaction) || !((Door)interaction).needsKey()) {
							((InDoor)interaction).open();
							level.updateVisibility();
						}
						else {
							player.openDialog(((Door)interaction).getMessage());
						}
					}
					else {
						if (inCombat) {
							player.openDialog(name + ": I can't run away now, everyone might need me.");
						}
						else if (!((Door)interaction).isLocked() && (player.useKey((Door)interaction) || !((Door)interaction).needsKey())) {
							if (interaction instanceof InDoor) {
								((InDoor)interaction).open();
								level.updateVisibility();
							}
							else {
								player.changeLevel((Door) interaction);
							}
						}
						else {
							player.openDialog(((Door)interaction).getMessage());
						}
					}	
				}
				else {
					examine((Item)interaction);
				}
			}
		}
		interaction = null;
	}
	
	protected void examine(Item item) {
		ArrayList<String> dialog = player.getBestDialog(name, item.getName());
		if (dialog != null) {
			lookAt(item);
			player.openDialog(dialog);
		}
		level.getGoals().interaction(name, item.getName());
	}
	
	protected void talk(Character other) {
		ArrayList<String> dialog = player.getBestDialog(name, other.getName());
		if (dialog != null) {
			lookAt(other);
			player.openDialog(dialog);
		}
		level.getGoals().interaction(name, other.getName());
	}
	
	protected void attack(Character other) {
		if (getTileOn().gridDistanceFrom(other.getTileOn()) <= 2) { 
			if (!player.getMoved().contains(this)) {
				player.getMoved().add(this);
			}
			other.setHealth(other.getHealth()-this.damage);
			if (other.health <= 0) {
				other.die();
			}
		}
	}
	
	protected void deselectSelf() {
		interact();
		player.deselectChar();
	}
	
	protected void move() { //make this next
		Tile tile = level.getGrid().getGridTile((int)((x+width/2)/64), (int)((y+height/2)/64));
		float difficulty = tile.getType().difficulty;
		switch(this.getDirection()) {
		case "right":
			x += Delta()*speed/difficulty;
			if (this.getDirection() == "left") {
				x = (int)checkpoints.get(0).getX();
				y = (int)checkpoints.get(0).getY();
				if (checkpoints.size() == 1) {
					moving = false;
					if (playable && inCombat && (interaction == null)) {
						player.openOptions(this);
					}
					deselectSelf();
					break;
				}
				checkpoints.remove(0);
			}
			break;
		case "left":
			x -= Delta()*speed/difficulty;
			if (this.getDirection() == "right") {
				x = (int)checkpoints.get(0).getX();
				y = (int)checkpoints.get(0).getY();
				if (checkpoints.size() == 1) {
					moving = false;
					if (playable && inCombat && (interaction == null)) {
						player.openOptions(this);
					}
					deselectSelf();
					break;
				}
				checkpoints.remove(0);
			}
			break;
		case "down":
			y += Delta()*speed/difficulty;
			if (this.getDirection() == "up") {
				x = (int)checkpoints.get(0).getX();
				y = (int)checkpoints.get(0).getY();
				if (checkpoints.size() == 1) {
					moving = false;
					if (playable && inCombat && (interaction == null)) {
						player.openOptions(this);
					}
					deselectSelf();
					break;
				}
				checkpoints.remove(0);
			}
			break;
		case "up":
			y -= Delta()*speed/difficulty;
			if (this.getDirection() == "down") {
				x = (int)checkpoints.get(0).getX();
				y = (int)checkpoints.get(0).getY();
				if (checkpoints.size() == 1) {
					moving = false;
					if (playable && inCombat && (interaction == null)) {
						player.openOptions(this);
					}
					deselectSelf();
					break;
				}
				checkpoints.remove(0);
			}
			break;
		case "stop":
			if (checkpoints.size() == 1) {
				moving = false;
				if (playable && inCombat && (interaction == null)) {
					player.openOptions(this);
				}
				deselectSelf();
				break;
			}
			checkpoints.remove(0);
			break;
		}
	}
	
	protected void makeCheckpoints() {
		Tile tile = level.getGrid().getMouseTile();
		if (!tile.equals(checkpoints.get(checkpoints.size()-1).getTileOn())) {
			if (tile.gridDistanceFrom(checkpoints.get(checkpoints.size()-1).getTileOn()) == 1 && otherObjectOnTile(tile) != null) {
				return;
			}
			ArrayList<Checkpoint> temp = autoPath(tile);
			if (temp != null) {
				checkpoints = temp;
			}
			else {
				checkpoints.clear();
				checkpoints.add(new Checkpoint(this.getTileOn()));
			}
		}
	}
	
	protected ArrayList<Checkpoint> autoSee(Tile target) {
		Tile start = getTileOn();
		ArrayList<TileNode> open = new ArrayList<TileNode>();
		ArrayList<Tile> closed = new ArrayList<Tile>();
		open.add(new TileNode(start, target, level.getGrid()));
		while (open.size() > 0) {
			TileNode currentNode = getLowestTotal(open);
			open.remove(currentNode);
			closed.add(currentNode.getTile());
			if (currentNode.getTile().equals(target) || (otherObjectOnTile(target) != null && currentNode.getTile().gridDistanceFrom(target) == 1)) {
				ArrayList<Checkpoint> reList = new ArrayList<Checkpoint>();
				ArrayList<Checkpoint> tempReList = new ArrayList<Checkpoint>();
				while(currentNode != null) {
					tempReList.add(new Checkpoint(currentNode.getTile()));
					currentNode = currentNode.getFrom();
				}
				float distance = 0;
				for (int i=tempReList.size()-1; i>=0; i--) {
					if (inCombat) {
						distance += tempReList.get(i).getTileOn().getType().difficulty;
						if (distance > movement) {
							open = null;
							closed = null;
							return reList;
						}
					}
					reList.add(tempReList.get(i));
				}
				open = null;
				closed = null;
				return reList;
			}
			for (TileNode i : currentNode.getNeighbours()) {
				if (tileIsSeeThrough(i.getTile()) && !closed.contains(i.getTile())) {
					boolean checked = false;
					for (int j=0; j<open.size(); j++) {
						if (open.get(j).equals(i)) {
							if (open.get(j).totalVal() > i.totalVal()) {
								open.set(j, i);
								open.get(j).setFrom(currentNode);
							}
							checked = true;
						}
					}
					if (!checked) {
						open.add(i);
					}
				}
			}
		}
		open = null;
		closed = null;
		return null;
	}
	
	protected ArrayList<Checkpoint> autoPath(Tile target) {
		Tile start = getTileOn();
		ArrayList<TileNode> open = new ArrayList<TileNode>();
		ArrayList<Tile> closed = new ArrayList<Tile>();
		open.add(new TileNode(start, target, level.getGrid()));
		while (open.size() > 0) {
			TileNode currentNode = getLowestTotal(open);
			open.remove(currentNode);
			closed.add(currentNode.getTile());
			if (currentNode.getTile().equals(target) || (otherObjectOnTile(target) != null && currentNode.getTile().gridDistanceFrom(target) == 1)) {
				ArrayList<Checkpoint> reList = new ArrayList<Checkpoint>();
				ArrayList<Checkpoint> tempReList = new ArrayList<Checkpoint>();
				while(currentNode != null) {
					tempReList.add(new Checkpoint(currentNode.getTile()));
					currentNode = currentNode.getFrom();
				}
				float distance = 0;
				for (int i=tempReList.size()-1; i>=0; i--) {
					if (inCombat) {
						distance += tempReList.get(i).getTileOn().getType().difficulty;
						if (distance > movement) {
							open = null;
							closed = null;
							return reList;
						}
					}
					reList.add(tempReList.get(i));
				}
				return reList;
			}
			for (TileNode i : currentNode.getNeighbours()) {
				if (tileIsPassable(i.getTile()) && !closed.contains(i.getTile())) {
					boolean checked = false;
					for (int j=0; j<open.size(); j++) {
						if (open.get(j).equals(i)) {
							if (open.get(j).totalVal() > i.totalVal()) {
								open.set(j, i);
								open.get(j).setFrom(currentNode);
							}
							checked = true;
						}
					}
					if (!checked) {
						open.add(i);
					}
				}
			}
		}
		return null;
	}
	
	protected boolean checkpointDuplicate(Checkpoint point) {
		if (checkpoints.size() == 0) {
			return false;
		}
		for (int i=0; i<checkpoints.size(); i++) {
			if (checkpoints.get(i).getX() == point.getX() && checkpoints.get(i).getY() == point.getY()) {
				return true;
			}
		}
		return false;
	}
	
	protected String getDirection() {
		if (talking == true) {
			return sprite.getDirection();
		}
		if (checkpoints.size() == 0) {
			return "stop";
		}
		if (checkpoints.get(0).getX() > x) {
			return "right";
		}
		if (checkpoints.get(0).getX() < x) {
			return "left";
		}
		if (checkpoints.get(0).getY() > y) {
			return "down";
		}
		if (checkpoints.get(0).getY() < y) {
			return "up";
		}
		else {
			return "stop";
		}
	}
	
	protected Object otherObjectOnTile(Tile tile) {
		for (int i=0; i<level.getCharacters().size(); i++) {
			if (level.getCharacters().get(i) != this && level.getCharacters().get(i).getTileOn() == tile) {
				return level.getCharacters().get(i);
			}
		}
		for (int i=0; i<level.getItems().size(); i++) {
			if (level.getItems().get(i).getTileOn() == tile) {
				return level.getItems().get(i);
			}
		}
		return null;
	}
	
	protected void updateSprite() {
		if (moving || animation != "") {
			sprite.setDirection(getDirection());
			sprite.setAnimation(animation, animTimer);
		}
		if (!moving && !talking) {
			sprite.setDirection("idle");
			sprite.setAnimation("");
		}
		DrawQuadTex(x, y, width, height, sprite.updateTex());
	}
	
	private boolean tileIsPassable(Tile tile) {
		Object block = otherObjectOnTile(tile);
		if (tile.getType().passable) {
			if (block == null) {
				return true;
			}
			if (block instanceof Item) {
				if (((Item)block).isPassable()) {
					return true;
				}
				else {
					return false;
				}
			}
			if (block instanceof Character) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean tileIsSeeThrough(Tile tile) {
		Object block = otherObjectOnTile(tile);
		if (blind) {
			if (block instanceof Character) {
				return true;
			}
			return false;
		}
		if (tile.getType().seeThrough) {
			if (block == null) {
				return true;
			}
			if (block instanceof Item) {
				if (((Item)block).isSeeThrough()) {
					return true;
				}
				else {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private TileNode getLowestTotal(ArrayList<TileNode> list) {
		float minTotal = list.get(0).totalVal();
		TileNode save = list.get(0);
		for (TileNode i : list) {
			if (i.totalVal() < minTotal) {
				save = i;
				minTotal = save.totalVal();
			}
		}
		return save;
	}
		
}
