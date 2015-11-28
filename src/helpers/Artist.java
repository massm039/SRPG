package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {
	
	public static final int WIDTH = 1280, HEIGHT = 960;
	
	public static void BeginSession() {
		Display.setTitle("SRPG");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		}
		catch (LWJGLException e){
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1); //Top Left is (0, 0)
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void DrawQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x+width, y);
		glVertex2f(x+width, y+height);
		glVertex2f(x, y+height);
		glEnd();
	}
	
	public static void DrawQuadTex(float x, float y, float width, float height, Texture tex) {
		tex.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	public static Texture LoadTexture(String path, String fileType) {
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture LoadPNG(String fileName) {
		Texture tex = null;
		//InputStream in = ResourceLoader.getResourceAsStream("res/" + fileName + ".png");
		InputStream in = ResourceLoader.getResourceAsStream("res/" + fileName + ".png");
		try {
			tex = TextureLoader.getTexture("PNG", in);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static void drawText(TrueTypeFont font, int x, int y, String message) {
		font.drawString(x,  y, message);
	}
}
