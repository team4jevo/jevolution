package org.projectERv08;

import java.util.List;
import java.util.Random;

public class Spawn_Creature {

	
	/**
	 * Decides if the creature wants to spawn a new one, if it does, it spawns it in the
	 * previous cell, otherwise empties that cell
	 */
	public void spawn(int r1, int r2,  int change_v_time, List<Creature> new_list_of_creatures, Creature creature, Field field){
		Random rnd = new Random();
		if (rnd.nextInt(r1) >= r2) {
			// new_creature;
			Creature new_creature = (Creature) creature.clone();
			new_creature.newSpeed(change_v_time);
			field.cell[creature.cell_x][creature.cell_y].change(2, new_creature);
			new_list_of_creatures.add(new_creature);
		} 
		else 
			field.cell[creature.cell_x][creature.cell_y].change(0, null);
	}

}
