package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class SpriteAnim {
	
	private Texture[] frames;
	private String frameName;
	private int animLength, frameTime;
	private float frameIndex = 0;
	
	public SpriteAnim(String frameName, int animLength, int time) {
		this.animLength = animLength;
		this.frameName = frameName;
		this.frameTime = time;
		frames = new Texture[animLength];
		for (int i=0; i<frames.length; i++) {
			if (i == 0) {
				frames[i] = LoadPNG(frameName);
			}
			else {
				frames[i] = LoadPNG(frameName+Integer.toString(i));
			}
		}
	}
	
	public SpriteAnim(String frameName, int animLength) {
		this.animLength = animLength;
		this.frameName = frameName;
		this.frameTime = 1;
		frames = new Texture[animLength];
		for (int i=0; i<frames.length; i++) {
			if (i == 0) {
				frames[i] = LoadPNG(frameName);
			}
			else {
				frames[i] = LoadPNG(frameName+Integer.toString(i));
			}
		}
	}
	
	public String getName() {
		return frameName;
	}
	
	public void setName(String newName) {
		this.frameName = newName;
		for (int i=0; i<frames.length; i++) {
			if (i == 0) {
				frames[i] = LoadPNG(frameName);
			}
			else {
				frames[i] = LoadPNG(frameName+Integer.toString(i));
			}
		}
	}
	
	public Texture getFrame(float delta) {
		frameIndex += delta;
		frameIndex = frameIndex%animLength;
		if ((int)(frameIndex/frameTime) == 0) {
			return frames[(int)frameIndex];
		}
		return frames[(int)frameIndex];
	}
	
	public String toString() {
		return frameName + " " + animLength + " " + frameTime;
	}
	
}
