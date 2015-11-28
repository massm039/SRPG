package data;

import java.util.ArrayList;

public class GoalManager {
	
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	
	public GoalManager() {
		
	}
	
	public GoalManager(String fileData) {
		try {
			String[] data = fileData.split("~");
			for (String i : data) {
				String [] subData = i.split("_");
				goals.add(new Goal(subData[0], subData[1]));
			}
		}
		catch(Exception e) {
			
		}
	}
	
	public ArrayList<Goal> getGoals() {
		return goals;
	}
	
	public void setGoals(ArrayList<Goal> goals) {
		this.goals = goals;
	}
	
	public void addGoals(GoalManager other) {
		for (Goal i : other.getGoals()) {
			boolean add = true;
			for (Goal j : goals) {
				if (j.equals(i)) {
					add = false;
					if (i.isComplete()) {
						j.setComplete(true);
					}
				}
			}
			if (add) {
				goals.add(i);
			}
		}
	}
	
	public void resetGoals(String fileData) {
		goals.clear();
		String[] data = fileData.split("~");
		for (String i : data) {
			String [] subData = i.split("_");
			goals.add(new Goal(subData[0], subData[1]));
		}
	}
	
	public void interaction(String name1, String name2) {
		for (Goal i : goals) {
			i.checkGoal(name1, name2);
		}
	}
	
	public boolean allGoalsComplete() {
		for (Goal i : goals) {
			if (!i.isComplete()) {
				return false;
			}
		}
		return true;
	}
	
}
