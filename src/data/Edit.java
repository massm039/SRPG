package data;

public class Edit {
	
	private Editor editor;
	private Level level;
	
	public Edit(Level level, String name) {
		this.level = level;
		editor = new Editor(level, name);
	}
	
	public void update() {
		level.getGrid().Draw();
		editor.update();
		
		for (int i=0; i<level.getItems().size(); i++) {
			level.getItems().get(i).Draw();
		}
		
		for (int i=0; i<level.getCharacters().size(); i++) {
			level.getCharacters().get(i).Draw();
		}
	}
}
