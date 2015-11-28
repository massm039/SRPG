package data;

public enum ItemType {
	
	Door("Door", 64, 64, "door", 1, "startCity"), 
	InDoor("InDoor", 64, 64, "inDoor", 1, "InDoor"),
	StairsDown("StairsDown", 64, 64, "stairsDown", 1, "startCityHouseOne2"), 
	StairsUp("StairsUp", 64, 64, "stairsUp", 1, "startCityHouseOneBasement"), 
	UpArrow("UpArrow", 64, 64, "arrowUp", "startCity"), 
	DownArrow("DownArrow", 64, 64, "arrowDown", "startCity2"), 
	LeftArrow("LeftArrow", 64, 64, "arrowLeft", "startCity"), 
	RightArrow("RightArrow", 64, 64, "arrowRight", "startCity"), 
	Drawers("Drawers", 64, 64, "drawers", -32), 
	OldDrawers("OldDrawers", 64, 64, "oldDrawers", -32), 
	DrawersOther("DrawersOther", 64, 64, "drawersOther", -32), 
	OldDrawersOther("OldDrawersOther", 64, 64, "oldDrawersOther", -32), 
	DrawersWhite("DrawersWhite", 64, 64, "drawersWhite", -32), 
	OldDrawersWhite("OldDrawersWhite", 64, 64, "oldDrawersWhite", -32), 
	ShelfWhite("ShelfWhite", 64, 64, "shelfWhite", -32), 
	OldShelfWhite("OldShelfWhite", 64, 64, "oldShelfWhite", -32), 
	ShelfPurple("ShelfPurple", 64, 64, "shelfPurple", -32), 
	OldShelfPurple("OldShelfPurple", 64, 64, "oldShelfPurple", -32), 
	Dresser("Dresser", 64, 64, "dresser", -32), 
	OldDresser("OldDresser", 64, 64, "oldDresser", -32), 
	WoodShelf("WoodShelf", 64, 64, "woodShelf", -32), 
	WoodShelfTwo("WoodShelfTwo", 64, 64, "woodShelfTwo", -32), 
	WoodShelfThree("WoodShelfThree", 64, 64, "woodShelfThree", -32), 
	WoodGlassShelf("WoodGlassShelf", 64, 64, "woodGlassShelf", -32), 
	WoodGlassShelfTwo("WoodGlassShelfTwo", 64, 64, "woodGlassShelfTwo", -32), 
	WoodGlassShelfThree("woodGlassShelfThree", 64, 64, "woodGlassSHelfThree", -32), 
	WoodFilledShelf("WoodFilledShelf", 64, 64, "woodFilledShelf", -32), 
	WoodFilledShelfTwo("WoodFilledShelfTwo", 64, 64, "woodFilledShelfTwo", -32), 
	WoodFilledShelfThree("woodFilledShelfThree", 64, 64, "woodFilledShelfThree", -32), 
	RedWoodShelf("RedWoodShelf", 64, 64, "redWoodShelf", -32), 
	RedWoodShelfTwo("RedWoodShelfTwo", 64, 64, "redWoodShelfTwo", -32), 
	RedWoodShelfThree("RedWoodShelfThree", 64, 64, "redWoodShelfThree", -32), 
	RedWoodFilledShelf("RedWoodFilledShelf", 64, 64, "redWoodFilledShelf", -32), 
	RedWoodFilledShelfTwo("RedWoodFilledShelfTwo", 64, 64, "redWoodFilledShelfTwo", -32), 
	RedWoodFilledShelfThree("RedWoodFilledShelfThree", 64, 64, "redWoodFilledShelfThree", -32), 
	FamilyPortrait("FamilyPortrait", 64, 64, "familyPortrait"), 
	OldPortrait("OldPortrait", 64, 64, "oldPortrait"),
	Wagon("Wagon", 128, 256, "wagon");
	
	int width, height, animLength = 1, offsetY = 0;
	String name, tex, toLevel = "none";
	
	ItemType (String name, int width, int height, String texture) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tex = texture;
	}
	
	ItemType (String name, int width, int height, String texture, int offsetY) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tex = texture;
		this.offsetY = offsetY;
	}
	
	ItemType (String name, int width, int height, String texture, int animLength, int offsetY) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tex = texture;
		this.animLength = animLength;
		this.offsetY = offsetY;
	}
	
	ItemType (String name, int width, int height, String texture, String toLevel) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tex = texture;
		this.toLevel = toLevel;
	}
	
	ItemType (String name, int width, int height, String texture, int animLength, String toLevel) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tex = texture;
		this.animLength = animLength;
		this.toLevel = toLevel;
	}
	
	public String toString() {
		return name;
	}
	
}
