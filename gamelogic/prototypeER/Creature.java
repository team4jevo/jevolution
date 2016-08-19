package org.projectERv10;

import java.util.Random;

public class Creature implements Cloneable {
	// Creature id
	public String id;
	// Creatures spawned
	public int creatures_spawned=0;
	// (X,Y) coordinates
	public int x;
	public int y;
	// (X,Y) coordinates by cell number
	public int cell_x;
	public int cell_y;
	// (X,Y) move direction
	public int direction_x;
	public int direction_y;
	// (X,Y) speed
	public int v_x;
	public int v_y;
	// Food eaten
	public int food_eaten;
	// Creatures eaten
	public int creatures_eaten;
	// Total eaten
	public int total_eaten;
	// Time till speed changes
	public int change_v;
	// Cell size
	public int cell_size;
	// Evolution times
	public int times_evolved = 0;
	// Is Alive?
	public boolean alive = true;
	// Time created
	public int time_created=0;
	// Died at time
	public int death_time;
	// Death cause
	public String death_cause = "";

	// Attributes that can evolve
	// Number of ways the creature can evolve
	public int evolution_paths = 4;

	// Maximum speed the creature can accelerate to
	public int v_max = 1;
	public int v_MAX; // Limited by cell size
	// Can the creature eat other creatures
	public boolean eatCreature = false;
	// Creatures ability to eat other creatures and protect itself from being
	// eaten
	public int strength = 1;
	// How long can creature survive starvation
	public int starvation_time_max = 200;
	public int starvation_time = starvation_time_max;

	Random rnd = new Random();

	public Creature() {
		cell_x = 0;
		cell_y = 0;
	}

	public Creature(int x, int y, int cell_size) {
		this.cell_x = x;
		this.cell_y = y;

		// is this needed?
		this.cell_size = cell_size;
		v_MAX = cell_size;
		//
		food_eaten = 0;

		this.x = cell_size * x + cell_size / 2;
		this.y = cell_size * y + cell_size / 2;
	}

	public void newSpeed(int t) {
		change_v = rnd.nextInt(t) + t / 2;
		int temp_x = rnd.nextInt(v_max * 2 + 1) - v_max;
		if (temp_x < 0)
			direction_x = -1;
		if (temp_x == 0)
			direction_x = 0;
		if (temp_x > 0)
			direction_x = 1;
		v_x = Math.abs(temp_x);

		int temp_y = rnd.nextInt(v_max * 2 + 1) - v_max;
		if (temp_y < 0)
			direction_y = -1;
		if (temp_y == 0)
			direction_y = 0;
		if (temp_y > 0)
			direction_y = 1;
		v_y = Math.abs(temp_y);

		// direction_x=(rnd.nextInt(3)-1);
		// speed_x=rnd.nextInt(maxspeed_x)+1;
		// direction_y=(rnd.nextInt(3)-1);
	}

	public void cellXY(int x, int y) {
		cell_x = x;
		cell_y = y;
	}

	public void locXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void eatAndEvolve(int eatfood, int eatcreature) {

		food_eaten += eatfood;
		creatures_eaten += eatcreature;

		int temp;
		if (eatfood == 1)
			temp = food_eaten;
		else
			temp = 3 * eatcreature;
		total_eaten = food_eaten + creatures_eaten * 3;
		if (temp % 3 == 0) {
			// System.out.println("I evolved!");
			int win = rnd.nextInt(evolution_paths);
			switch (win) {
			case 0: {
				// Shouldn't happen if initial parametrs have been chosen
				// correctly
				if (v_max < v_MAX)
					v_max++;
				break;
			}
			case 1: {
				strength++;
				break;
			}
			case 2: {
				starvation_time_max += 100;
				break;
			}
			case 3: {
				eatCreature = true;
				evolution_paths--;
				break;
			}
			}
			times_evolved++;
		}
		starvation_time = starvation_time_max;
	}

	public void kill(int t, String cause) {
		alive = false;
		death_time = t;
		death_cause = cause;
	}

	@Override
	protected Object clone() {
		
		Creature tmp = new Creature();
		try {
			tmp = (Creature) super.clone();
			tmp.creatures_spawned=0;
			tmp.food_eaten=0;
			tmp.creatures_eaten=0;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		tmp.id=tmp.id+"-"+creatures_spawned;
		creatures_spawned++;
		return tmp;
	}

}
