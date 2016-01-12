package userInterface;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import static helpers.Artist.*;

public class UI {
	
	private ArrayList<Button> buttonList;
	
	public UI() {
		buttonList = new ArrayList<Button>();
	}
	
	public void addButton(String name, String textureName, int x, int y) {
		buttonList.add(new Button(name, LoadPNG(textureName), x, y));
	}
	
	public void addButton(String name, String textureName, int x, int y, int width, int height) {
		buttonList.add(new Button(name, LoadPNG(textureName), x, y, width, height));
	}
	
	public void draw() {
		for (Button b: buttonList) {
			DrawQuadTex(b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getTexture());
		}
	}
	
	public Button getButtonClicked() {
		for (Button b : buttonList) {
			if (isButtonClicked(b.getName())) {
				return b;
			}
		}
		return null;
	}
	
	public boolean isButtonClicked(String buttonName) {
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() -1;
		if (Mouse.getX() > b.getX() 
				&& Mouse.getX() < b.getX() + b.getWidth() 
				&& mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
			return true;
		}
		return false;
	}
	
	public Button getButton(String buttonName) {
		for (Button b: buttonList) {
			if (b.getName() == buttonName) {
				return b;
			}
		}
		return null;
	}
	
	public Button removeButton(String buttonName) {
		for (int i=0; i<buttonList.size(); i++) {
			if (buttonList.get(i).getName() == buttonName) {
				buttonList.remove(i);
			}
		}
		return null;
	}
	
	public int numButtons() {
		return buttonList.size();
	}

}
