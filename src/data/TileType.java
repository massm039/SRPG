package data;

public enum TileType {
	
	Dirt("0", "dirt", true, true, 1f), Grass("1", "grass", true, true, 1f), Rock("2", "rock", true, true, 1f), Sand("3", "sand", true, true, 1.5f), Obsidean("4", "obsidean", true, true, 2f), Cobblestone("5", "cobbs", true, true, 1f), MossCobbs("6", "mossCobbs", true, true, 1.2f), StoneTile("7", "stoneTiles", true, true, .8f), WoodFloor("8", "woodFloor", true, true, 1.0f), YellowRoof("9", "yellowRoof", false, true, 0.0f), DryWall("10", "dryWall", false, false, 0.0f), RedRoof("11", "redRoof", false, true, 0.0f), GreyBrickWall("12", "greyBrickWall", false, false, 0.0f), TanRoof("13", "tanRoof", false, true, 0.0f), BrownRoof("14", "brownRoof", false, false, 0.0f), BrownWall("15", "brownWall", false, false, 0.0f), GreenRoof("16", "greenRoof", false, true, 0.0f), GreenWall("17", "greenWall", false, false, 0.0f), HorizontalBridge("18", "horBridge", true, true, 1.0f), GlassBridge("19", "glassPane", true, true, 1.0f), VerticalBridge("20", "bridge", true, true, 1.0f), Water("21", "water", true, true, 3.0f), Black("22", "darkBar", false, false, 0.0f);
	
	String ID, textureName;
	boolean passable, seeThrough;
	float difficulty;
	
	TileType(String ID, String textureName, boolean passable, boolean seeThrough, float difficulty) {
		this.ID = ID;
		this.textureName = textureName;
		this.passable = passable;
		this.seeThrough = seeThrough;
		this.difficulty = difficulty;
	}
	
	public String getID() {
		return ID;
	}
}
