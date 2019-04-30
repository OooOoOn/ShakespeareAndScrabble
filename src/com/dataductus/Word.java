package com.dataductus;

public class Word {
    private int points;
    private boolean valid;
    private String name;

    public Word(int points, boolean valid, String name) {
        this.setPoints(points);
        this.setValid(valid);
        this.setName(name);
    }

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		  return "Word: " + name + " Points: " + points + " Valid: " + valid;  
		 }  

}
