package data;

import static helpers.Clock.*;
import static helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	
	private int animTimer = 0;
	private String dir = "down", anim = "";
	private SpriteAnim up, right, down, left, upAttack, rightAttack, downAttack, leftAttack;
	private Texture idle;
	
	private Texture preFrame = idle;
	
	public Sprite(String spriteName, int animLength) {
		this.idle = LoadPNG(spriteName);
		
		this.up = new SpriteAnim(spriteName+"Up", animLength);
		this.right = new SpriteAnim(spriteName+"Right", animLength);
		this.down = new SpriteAnim(spriteName+"Down", animLength);
		this.left = new SpriteAnim(spriteName+"Left", animLength);
		//placeholder attack animations
		this.upAttack = up;
		this.rightAttack = right;
		this.downAttack = down;
		this.leftAttack = left;
		//placeholder attack animations
	}
	
	public Sprite(SpriteAnim up, SpriteAnim right, SpriteAnim down, SpriteAnim left) {
		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;
	}
	
	public void setAnimation(String animation, int animTimer) {
		anim = animation;
		this.animTimer = animTimer;
	}
	
	public void setAnimation(String animation) {
		anim = animation;
		animTimer = 0;
	}
	
	public String getAnimation() {
		return anim;
	}
	
	public void setDirection(String dir) {
		if (dir != "stop") {
			this.dir = dir;
		}
	}
	
	public String getDirection() {
		return dir;
	}
	
	public Texture updateTex() {
		Texture reTex;
		if (animTimer == 0) {
			anim = "";
		}
		float delta = Delta();
		switch(anim) {
		case "":
			switch(dir) {
			case "up":
				reTex = up.getFrame(delta);
				break;
			case "right":
				reTex = right.getFrame(delta);
				break;
			case "down":
				reTex = down.getFrame(delta);
				break;
			case "left":
				reTex = left.getFrame(delta);
				break;
			default:
				reTex = idle;
				break;
			}
			break;
		case "attack":
			switch(dir) {
			case "up":
				reTex = upAttack.getFrame(delta);
				break;
			case "right":
				reTex = rightAttack.getFrame(delta);
				break;
			case "down":
				reTex = downAttack.getFrame(delta);
				break;
			case "left":
				reTex = leftAttack.getFrame(delta);
				break;
			default:
				reTex = idle;
				break;
			}
			break;
		default:
			return idle;
		}
		if (reTex != preFrame) {
			animTimer--;
			if (animTimer < 0) {
				animTimer = 0;
			}
		}
		return reTex;
	}

}
