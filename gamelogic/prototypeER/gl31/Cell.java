package gl31;

import java.util.Hashtable;

/**
 * This class contains information about different field elements
 * 
 * @author Edgars Rumokovskis
 *
 */

public class Cell extends jevo.GameObject {

	public int whatIs;
	// if needed
	public Creature creature;

	public Cell() {
		super("What");
		whatIs = 0;
		creature = null;
	}

	public Cell(int whatIs, String text, int x, int y) {
		super(text, x, y);
		this.whatIs = whatIs;
	}

	public Cell(int whatIs, Creature creature, String text, int x, int y) {
		super(text, x, y);
		this.whatIs = whatIs;
		this.creature = creature;
	}

	/**
	 * This method displays information about the cell in the graphical display
	 */
	@Override
	public Hashtable<String, String> getRenderedStats() {
		Hashtable<String, String> ht = new Hashtable<>();
		if (whatIs == 1)
			ht.put("Object", "Wall");
		if (whatIs == 2) {
			String temp_text = "";
			ht.put("Object", "Creature");
			ht.put("ID", creature.id);
			if (creature.eatCreature)
				temp_text = "Yes";
			else
				temp_text = "No";
			ht.put("Eats creatures?", temp_text);
			ht.put("Food eaten", Integer.toString(creature.food_eaten));
			ht.put("Creatures eaten",
					Integer.toString(creature.creatures_eaten));
			ht.put("Times Evolved", Integer.toString(creature.times_evolved));
			ht.put("Creatures birthed",
					Integer.toString(creature.creatures_spawned));
			ht.put("Birth time", Integer.toString(creature.time_created));
			ht.put("Strength", Integer.toString(creature.strength));
			ht.put("Will survive w/o food",
					Integer.toString(creature.starvation_time));
			ht.put("Speed x",
					Integer.toString(creature.v_x * creature.direction_x));
			ht.put("Speed y",
					Integer.toString(creature.v_y * creature.direction_y));

		}
		if (whatIs == 3)
			ht.put("Object", "Food");
		return ht;
	}
}
