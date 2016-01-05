package data;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import static helpers.Artist.*;
import static helpers.Clock.*;
import static helpers.Cartographer.*;
import helpers.StateManager;

public class Boot {
	
	public static void main(String args[]) {
		new Boot();
	}
	
	public Boot() {
		
		BeginSession();
		
		while(!Display.isCloseRequested()) {
			
			update(); //Update Time
			
			StateManager.update();
			
			Display.update();
			Display.sync(60);
		}
		
		deleteData();
		
		Display.destroy();
	}
	
	private void deleteData() {
		File directory = new File("C:\\gamesaves");
		try {
			deleteFolder(directory);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
