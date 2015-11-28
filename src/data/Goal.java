package data;

public class Goal {
	
	private String name1, name2;
	private boolean complete;
	
	public Goal(String name1, String name2) {
		this.name1 = name1;
		this.name2 = name2;
		complete = false;
	}
	
	public void completeGoal() {
		complete = true;
	}
	
	public void checkGoal(String name1, String name2) {
		if ((this.name1.equals(name1) && this.name2.equals(name2)) || (this.name1.equals(name2) && this.name2.equals(name1))) {
			complete = true;
		}
	}
	
	public boolean equals(Goal other) {
		if ((name1.equals(other.getNameOne()) && name2.equals(other.getNameTwo())) || (name1.equals(other.getNameTwo()) && name2.equals(other.getNameOne()))) {
			return true;
		}
		return false;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void setComplete(boolean val) {
		complete = val;
	}
	
	public String getNameOne() {
		return name1;
	}
	
	public String getNameTwo() {
		return name2;
	}

}
