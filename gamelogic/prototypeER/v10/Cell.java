package org.projectERv10;

public class Cell {

	public int whatIs;
	//if needed
	public Creature creature;

	public Cell() {
		whatIs=0;
		creature=null;
	}
	public Cell(int whatIs) {
		this.whatIs=whatIs;
	}
	public void change(int whatIs, Creature creature) {
		this.whatIs=whatIs;
		this.creature=creature;
	}
}
