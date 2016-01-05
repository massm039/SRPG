package data;

import java.util.ArrayList;

public class Conversation {
	
	//

	private int priority = 0;
	private String nameOne, nameTwo;
	private ArrayList<Goal> requirements = new ArrayList<Goal>();;
	private ArrayList<String> statements = new ArrayList<String>();;
	
	public Conversation(String nameOne, String nameTwo) {
		this.nameOne = nameOne;
		this.nameTwo = nameTwo;
	}
	
	public Conversation(String toStringVal) {
		String[] data = toStringVal.split("_");
		if ("0123456789".contains(data[0])) {
			priority = Integer.parseInt(data[0]);
			int i=1;
			while (i<data.length && data[i].charAt(0) == '?') {
				data[i] = data[i].substring(1);
				String[] goalNames = data[i].split("/");
				requirements.add(new Goal(goalNames[0], goalNames[1]));
				i++;
			}
			nameOne = data[i];
			nameTwo = data[1+i];
			statements = new ArrayList<String>();
			for (int j=i+2; j<data.length; j++) {
				statements.add(data[j]);
			}
		}
		else {
			nameOne = data[0];
			nameTwo = data[1];
			statements = new ArrayList<String>();
			for (int i=2; i<data.length; i++) {
				statements.add(data[i]);
			}
		}
	}
	
	public void addStatement(String statement) {
		statements.add(statement);
	}
	
	public String getNameOne() {
		return nameOne;
	}

	public void setNameOne(String nameOne) {
		this.nameOne = nameOne;
	}

	public String getNameTwo() {
		return nameTwo;
	}

	public void setNameTwo(String nameTwo) {
		this.nameTwo = nameTwo;
	}

	public ArrayList<String> getStatements() {
		ArrayList<String> reStates = new ArrayList<String>();
		for (String i : statements) {
			reStates.add(i);
		}
		return reStates;
	}

	public void setStatements(ArrayList<String> statements) {
		this.statements = statements;
	}
	
	public boolean checkNames(String one, String two) {
		if ((nameOne.equals(one) && nameTwo.equals(two)) || 
				(nameOne.equals(two) && nameTwo.equals(one))) {
			return true;
		}
		return false;
	}
	
	public boolean checkRequirements(GoalManager g) {
		if (requirements.size() == 0) {
			return true;
		}
		ArrayList<Goal> goals = g.getGoals();
		ArrayList<Goal> tempReq = new ArrayList<Goal>();
		for (Goal i : requirements) {
			tempReq.add(i);
		}
		for (Goal i : goals) {
			if (i.isComplete()) {
				int j=0;
				int totalReqs = tempReq.size();
				while (j < totalReqs) {
					if (i.equals(tempReq.get(j))) {
						tempReq.remove(j);
						totalReqs--;
					}
					j++;
				}
			}
		}
		if (tempReq.size() == 0) {
			return true;
		}
		return false;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public String toString() {
		String val = nameOne + '_' + nameTwo + '_';
		for (int i=0; i<statements.size(); i++) {
			val += statements.get(i)+"_";
		}
		return val;
	}

}
