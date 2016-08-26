package gl31;

import jevo.GraphicsEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import javafx.scene.paint.Color;

/**
 * This Class operates the simulation Currently this Class prepares a field of
 * cells (in the form of an array) that subsists of 2 values, one of them gives
 * a value that symbolizes what is in the cell: empty space (0), wall(1),
 * creature(2), food(3) and the other value gives a reference to the creature if
 * the cell is occupied by one. The field is an empty field with walls and
 * randomly placed creatures and pieces of food.
 * 
 * The cells have been given a physical size in the method and the creatures
 * have been placed in the middle of the cells and have been assigned speed
 * values. The process iterates over time, this makes it possible to give
 * creatures different speeds because not all of the creatures will always move
 * between different cells in a given time step. This allows to assign different
 * speed values to the creatures as long as the maximums value doesn't exceed
 * the cell size. Within a time step, the list of creatures is iterated over and
 * the creatures move procedurally. It checks whether the creature should move
 * cells. If it should move cells, it checks what is in the adjacent cell the
 * creature should move to and reacts accordingly: empty space - move, wall -
 * change directions, food - eats it, creature - interacts with it according to
 * the creatures.
 * 
 * Occasionally the speed and the direction of the creatures changes. When a
 * creature eats sufficient amount of food or creatures, it can evolve - improve
 * some of its abilities or gain new abilities like the ability to eat other
 * creatures. Food is randomly added to the field when sufficient time has
 * elapsed. If creatures do not get food often enough, they die of starvation.
 * Creatures can give birth to new generations of creatures when they eat. Each
 * creature has a unique ID that allows to see which creature is descendant from
 * which.
 * 
 * The program writes into an output file that can be used to start the program
 * from a different time step. It reads and writes initial parameters, the field
 * and the creature parameters. Can read the initial parameters from interface.
 * 
 * @author Edgars Rumkovskis
 */
public class GameLogic extends jevo.GameLogic {
	File inFile;
	Scanner scan;
	// File name that is used for input
	// File name that is used for input
	// Used for (X,Y) distance
	int x_0 = 0, y_0 = 0;
	int x_1 = 0, y_1 = 0;
	// Cell numbers (array numbers)
	int cx_0 = 0, cy_0 = 0;
	int cx_1 = 0, cy_1 = 0;
	// Amount of cells (array size)
	int cx_length = 20;
	int cy_length = 20;
	// Single cell size in (X,Y)
	int cell_size = 10;
	// Initial amount of creatures
	int number_of_creatures = 10;
	// Initial amount of food
	int nr_of_food = 30;
	// Start time
	int start_time = 0;
	// Total time
	int t = 0;
	// Used for determaning the time needed to change speed (t/2 + {0,t})
	int change_v_time = 50;
	// Used parametrs to determine when food will spawn
	int time_step = 100;
	int time_till_food;
	// How much food will spawn
	int random_food = cx_length;
	// How much time to starve
	int starve = 200;
	// How often are creatures birthed
	public int creature_spawn = 10;
	int cells_full, all_cells_full;

	List<Creature> list_of_alive_creatures;
	List<Creature> list_of_dead_creatures;
	List<Creature> list_of_all_creatures;

	Random rnd;

	int tmpX = 0;
	Cell[][] cell;

	public GameLogic(GraphicsEngine ge) {
		super(ge);
	}

	/**
	 * This method prepares a new simulation field
	 */
	public void newGame() {
		t = 0;
		cx_length = super.width;
		cy_length = super.height;
		// Initialize field
		cell = new Cell[cx_length][cy_length];
		cells_full = 0;
		all_cells_full = cx_length * cy_length;

		// Initialize lists
		list_of_alive_creatures = new ArrayList<Creature>();
		list_of_dead_creatures = new ArrayList<Creature>();
		list_of_all_creatures = new ArrayList<Creature>();

		rnd = new Random();
		// Adding walls to the field
		for (int i = 0; i < cx_length; i++) {
			if (all_cells_full == cells_full)
				break;
			cell[i][0] = new Cell(1, "", i, 0);
			cell[i][0].setToken(super.getGe().createToken(cell[i][0]));
			cell[i][0].getToken().setColor(Color.GRAY);
			cells_full++;
			int yy = cy_length - 1;
			cell[i][yy] = new Cell(1, "", i, yy);
			cell[i][yy].setToken(super.getGe().createToken(cell[i][yy]));
			cell[i][yy].getToken().setColor(Color.GRAY);
			cells_full++;
		}
		for (int i = 1; i < cy_length - 1; i++) {
			if (all_cells_full == cells_full)
				break;
			cell[0][i] = new Cell(1, "", 0, i);
			cell[0][i].setToken(super.getGe().createToken(cell[0][i]));
			cell[0][i].getToken().setColor(Color.GRAY);
			cells_full++;
			int xx = cx_length - 1;
			cell[xx][i] = new Cell(1, "", xx, i);
			cell[xx][i].setToken(super.getGe().createToken(cell[xx][i]));
			cell[xx][i].getToken().setColor(Color.GRAY);
			cells_full++;
		}

		// Adding creatures to the field
		int nc_temp = number_of_creatures;
		int temp_i = 0;
		for (int i = 0; i < nc_temp; i++) {
			if (all_cells_full == cells_full)
				break;
			int xx = rnd.nextInt(cx_length);
			int yy = rnd.nextInt(cy_length);
			if (cell[xx][yy] == null) {
				// Made a new creature, added it and it's reference
				// into cell and added the creature to the list
				Creature creature = new Creature(xx, yy, cell_size, starve);
				creature.starvation_time_max = starve;
				creature.newSpeed(change_v_time);
				creature.id = Integer.toString(temp_i);
				temp_i++;
				cell[xx][yy] = new Cell(2, creature, "", xx, yy);
				cell[xx][yy].setToken(super.getGe().createToken(cell[xx][yy]));
				cell[xx][yy].getToken().setColor(Color.ORANGE);
				list_of_alive_creatures.add(creature);
				cells_full++;
			} else
				nc_temp++;
		}

		// Adding food to the field
		int temp_food = nr_of_food;
		for (int i = 0; i < temp_food; i++) {
			if (all_cells_full == cells_full)
				break;
			int xx = rnd.nextInt(cx_length);
			int yy = rnd.nextInt(cy_length);
			if (cell[xx][yy] == null) {
				cell[xx][yy] = new Cell(3, "", xx, yy);
				cell[xx][yy].setToken(super.getGe().createToken(cell[xx][yy]));
				cell[xx][yy].getToken().setColor(Color.GREEN);
				cells_full++;
			} else
				temp_food++;
		}

		time_till_food = rnd.nextInt(time_step) + time_step;
		// End
	}

	/**
	 * This method advances the simulation field a single turn
	 */
	public void nextTurn() {
		System.out.println("Turn number = " + t);
		t++;
		// Adding food randomly to the field, need to decide how to better
		// balance it
		time_till_food--;
		if (time_till_food == 0) {
			// random_food=rnd.nextInt(cx_length); //Need to choose food
			// amount better
			random_food = cx_length;
			for (int i = 0; i < random_food; i++) {
				int xx = rnd.nextInt(cx_length);
				int yy = rnd.nextInt(cy_length);
				if (cell[xx][yy] == null) {
					cell[xx][yy] = new Cell(3, "", xx, yy);
					cell[xx][yy].setToken(super.getGe().createToken(
							cell[xx][yy]));
					cell[xx][yy].getToken().setColor(Color.GREEN);
				}
			}
			time_till_food = rnd.nextInt(time_step) + time_step;
		}

		List<Creature> new_list_of_creatures = new ArrayList<Creature>();
		List<Creature> list_of_creatures_to_remove = new ArrayList<Creature>();
		for (Creature creature : list_of_alive_creatures) {
			Color c_color = Color.ORANGE;
			if (creature.eatCreature)
				c_color = Color.RED;

			if (list_of_creatures_to_remove.contains(creature))
				continue;
			// Looks at time when the creature has to randomly change speed
			if (creature.change_v == 0) {
				creature.newSpeed(change_v_time);
			}
			creature.change_v--;
			// movement direction
			// looks at creature's initial and intended position on a (x,y)
			// axis
			x_0 = creature.x;
			y_0 = creature.y;
			x_1 = x_0 + creature.direction_x * creature.v_x;
			y_1 = y_0 + creature.direction_y * creature.v_y;
			// initial and intended position in cells numbers
			cx_0 = creature.cell_x;
			cy_0 = creature.cell_y;
			cx_1 = x_1 / cell_size;
			cy_1 = y_1 / cell_size;
			// Sees if cell change is needed, if needed, see what's there
			// and determine what to do
			if (cx_1 != cx_0 || cy_1 != cy_0) {
				if (cell[cx_1][cy_1] == null) {
					cell[cx_1][cy_1] = new Cell(2, creature, "", cx_1, cy_1);
					cell[cx_1][cy_1].setToken(super.getGe().createToken(
							cell[cx_1][cy_1]));
					cell[cx_1][cy_1].getToken().setColor(c_color);
					cell[cx_0][cy_0].removeToken();
					cell[cx_0][cy_0] = null;
					creature.cellXY(cx_1, cy_1);
					creature.locXY(x_1, y_1);
				} else {
					switch (cell[cx_1][cy_1].whatIs) {
					case 1: {// wall
						if (cx_1 == cx_length - 1 || cx_1 == 0)
							creature.direction_x *= -1;
						if (cy_1 == cy_length - 1 || cy_1 == 0)
							creature.direction_y *= -1;
						break;
					}
					case 2: {// creature
						// If creature hasn't evolved ability to eat other
						// creatures
						if (creature.eatCreature == false) {
							if (cx_1 != cx_0)
								creature.direction_x *= -1;
							if (cy_1 != cy_0)
								creature.direction_y *= -1;
						}
						// If creature has evolved ability to eat other
						// creatures
						else {
							Creature temp_creature = cell[cx_1][cy_1].creature;
							if (temp_creature.strength < creature.strength) {
								temp_creature.kill(t, "Killed by creature");
								creature.eatAndEvolve(0, 1);
								cell[cx_1][cy_1].removeToken();
								cell[cx_1][cy_1] = null;
								cell[cx_1][cy_1] = new Cell(2, creature, "",
										cx_1, cy_1);
								cell[cx_1][cy_1].setToken(super.getGe()
										.createToken(cell[cx_1][cy_1]));
								cell[cx_1][cy_1].getToken().setColor(c_color);
								cell[cx_0][cy_0].removeToken();
								cell[cx_0][cy_0] = null;
								// trying to spawn a new creature
								if (rnd.nextInt(creature_spawn) >= creature_spawn - 3) {
									// new_creature;
									System.out.println("New creature");
									Creature new_creature = (Creature) creature
											.clone();
									new_creature.newSpeed(change_v_time);
									new_creature.time_created = t;
									cell[cx_0][cy_0] = new Cell(2,
											new_creature, "", cx_0, cy_0);

									cell[cx_0][cy_0].setToken(super.getGe()
											.createToken(cell[cx_0][cy_0]));
									cell[cx_0][cy_0].getToken().setColor(
											c_color);
									new_list_of_creatures.add(new_creature);
								}
								creature.cellXY(cx_1, cy_1);
								creature.locXY(x_1, y_1);
								list_of_creatures_to_remove.add(temp_creature);
								break;

							} else {
								creature.direction_x *= -1;
								creature.direction_y *= -1;
							}

						}
						break;
					}
					case 3: {// food
						creature.eatAndEvolve(1, 0);
						cell[cx_1][cy_1].removeToken();
						cell[cx_1][cy_1] = null;
						cell[cx_1][cy_1] = new Cell(2, creature, "", cx_1, cy_1);
						cell[cx_1][cy_1].setToken(super.getGe().createToken(
								cell[cx_1][cy_1]));
						cell[cx_1][cy_1].getToken().setColor(c_color);
						cell[cx_0][cy_0].removeToken();
						cell[cx_0][cy_0] = null;
						// trying to spawn a new creature
						if (rnd.nextInt(creature_spawn) >= creature_spawn - 1) {
							System.out.println("New creature");
							// new_creature;
							Creature new_creature = (Creature) creature.clone();
							new_creature.newSpeed(change_v_time);
							new_creature.time_created = t;
							cell[cx_0][cy_0] = new Cell(2, new_creature, "",
									cx_0, cy_0);
							cell[cx_0][cy_0].setToken(super.getGe()
									.createToken(cell[cx_0][cy_0]));
							cell[cx_0][cy_0].getToken().setColor(c_color);
							new_list_of_creatures.add(new_creature);
						}
						creature.cellXY(cx_1, cy_1);
						creature.locXY(x_1, y_1);
						break;
					}
					default:
						System.err.println("Wrong atribute in a field x="
								+ cx_1 + ", y=" + cy_1);
					}
				}
			}
			// if not needed, just update (X,Y)
			else {
				creature.x = x_1;
				creature.y = y_1;
			}
			creature.starvation_time--;
			if (creature.starvation_time == 0) {
				creature.kill(t, "Starved to death");
				cell[creature.cell_x][creature.cell_y].removeToken();
				cell[creature.cell_x][creature.cell_y] = null;
				list_of_creatures_to_remove.add(creature);
			}
		}
		// If some creatures have been eaten, remove unneeded (eaten)
		// creatures from the list
		// Adds dead creatures to full list
		if (new_list_of_creatures.isEmpty() == false)
			list_of_alive_creatures.addAll(new_list_of_creatures);
		if (list_of_creatures_to_remove.isEmpty() == false) {
			list_of_alive_creatures.removeAll(list_of_creatures_to_remove);
			list_of_dead_creatures.addAll(list_of_creatures_to_remove);
		}

	}

	/**
	 * This method saves the initial parameters, field state and creature
	 * parameters
	 */
	@Override
	public void saveFile(File outFile) throws Exception {

		list_of_all_creatures = new ArrayList<Creature>();
		list_of_all_creatures.addAll(list_of_dead_creatures);
		list_of_all_creatures.addAll(list_of_alive_creatures);
		// Building the output file
		PrintWriter writer = null;
		writer = new PrintWriter(new FileWriter(outFile));
		writer.println("Initial parametrs:");
		writer.println("Number of cells at X axis:");
		writer.println(cx_length);
		writer.println("Number of cells at Y axis:");
		writer.println(cy_length);
		writer.println("Size of a single cell:");
		writer.println(cell_size);
		writer.println("Total elapsed time:");
		writer.println(t);
		writer.println("Time to change creatures speed:");
		writer.println(change_v_time);
		writer.println("Time to change creatures speed:");
		writer.println(time_step);
		writer.println("Creature spawn freqvency:");
		writer.println(creature_spawn);
		writer.println();

		writer.println("Field  coordinates:");
		int whatis = 0;
		for (int i = 0; i < cx_length; i++) {
			for (int j = 0; j < cy_length; j++) {
				if (cell[i][j] == null)
					whatis = 0;
				else
					whatis = cell[i][j].whatIs;
				writer.print(whatis + " ");
			}
			writer.println();
		}
		writer.println();
		writer.println("Number of Creatures:");
		writer.println(list_of_all_creatures.size());
		for (Creature c : list_of_all_creatures) {
			writer.println(c.id);
			writer.println(c.death_cause);
			writer.println(c.creatures_spawned + " " + c.x + " " + c.y + " "
					+ c.cell_x + " " + c.cell_y + " " + c.direction_x + " "
					+ c.direction_y + " " + c.v_x + " " + c.v_y + " "
					+ c.food_eaten + " " + c.creatures_eaten + " "
					+ c.total_eaten + " " + c.cell_size + " " + c.times_evolved
					+ " " + c.alive + " " + c.time_created + " " + c.death_time
					+ " " + c.evolution_paths + " " + c.v_max + " " + c.v_MAX
					+ " " + c.eatCreature + " " + c.strength + " "
					+ c.starvation_time + " " + c.starvation_time_max);
		}
		writer.close();
	}

	/**
	 * This method loads the initial parameters, field state and creature
	 * parameters
	 */
	@Override
	public void loadFile(File inFile) throws Exception {

		list_of_alive_creatures = new ArrayList<Creature>();
		list_of_dead_creatures = new ArrayList<Creature>();
		list_of_all_creatures = new ArrayList<Creature>();
		// Here goes an input file if start_time!=0
		// inFile = new File(input_file);
		scan = new Scanner(inFile);
		// Initial parameters
		scan.nextLine();
		scan.nextLine();
		cx_length = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		cy_length = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		cell_size = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		t = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		change_v_time = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		time_step = scan.nextInt();
		scan.nextLine();
		scan.nextLine();
		creature_spawn = scan.nextInt();
		scan.nextLine();
		scan.nextLine();

		// Field coordinates
		scan.nextLine();
		// Initializes a new array
		cell = new Cell[cx_length][cy_length];
		super.getGe().reset();
		for (int i = 0; i < cx_length; i++)
			for (int j = 0; j < cy_length; j++) {
				int whatis = scan.nextInt();
				if (whatis == 0)
					cell[i][j] = null;
				if (whatis == 1) {
					cell[i][j] = new Cell(whatis, "", i, j);
					cell[i][j].setToken(super.getGe().createToken(cell[i][j]));
					cell[i][j].getToken().setColor(Color.GRAY);
				}
				if (whatis == 2) {
					cell[i][j] = new Cell(whatis, "", i, j);
					cell[i][j].setToken(super.getGe().createToken(cell[i][j]));
				}
				if (whatis == 3) {
					cell[i][j] = new Cell(whatis, "", i, j);
					cell[i][j].setToken(super.getGe().createToken(cell[i][j]));
					cell[i][j].getToken().setColor(Color.GREEN);
				}
			}
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();
		// Creatures
		int temp_nr_creatures;
		temp_nr_creatures = scan.nextInt();
		scan.nextLine();
		for (int i = 0; i < temp_nr_creatures; i++) {
			Creature c = new Creature();
			c.id = scan.nextLine();
			c.death_cause = scan.nextLine();
			c.creatures_spawned = scan.nextInt();
			c.x = scan.nextInt();
			c.y = scan.nextInt();
			c.cell_x = scan.nextInt();
			c.cell_y = scan.nextInt();
			c.direction_x = scan.nextInt();
			c.direction_y = scan.nextInt();
			c.v_x = scan.nextInt();
			c.v_y = scan.nextInt();
			c.food_eaten = scan.nextInt();
			c.creatures_eaten = scan.nextInt();
			c.total_eaten = scan.nextInt();
			c.cell_size = scan.nextInt();
			c.times_evolved = scan.nextInt();
			c.alive = scan.nextBoolean();
			c.time_created = scan.nextInt();
			c.death_time = scan.nextInt();
			c.evolution_paths = scan.nextInt();
			c.v_max = scan.nextInt();
			c.v_MAX = scan.nextInt();
			c.eatCreature = scan.nextBoolean();
			c.strength = scan.nextInt();
			c.starvation_time = scan.nextInt();
			c.starvation_time_max = scan.nextInt();
			// adds the creature to the correct list
			if (c.alive == true) {
				list_of_alive_creatures.add(c);
				// adds a reference to the creature in the correct cell
				cell[c.cell_x][c.cell_y].creature = c;
				if (c.eatCreature)
					cell[c.cell_x][c.cell_y].getToken().setColor(Color.RED);
				else
					cell[c.cell_x][c.cell_y].getToken().setColor(Color.ORANGE);
			} else
				list_of_dead_creatures.add(c);
			scan.nextLine();
		}
	}

	/**
	 * This method displays the initial parameters in the graphical interface
	 */
	@Override
	public Hashtable<String, String> getRenderedStats() {
		Hashtable<String, String> ht = new Hashtable<>();
		ht.put("Width", Integer.toString(super.width)); //
		ht.put("Height", Integer.toString(super.height));
		ht.put("Initial creatures", Integer.toString(number_of_creatures));
		ht.put("Initial food", Integer.toString(nr_of_food));
		ht.put("Speed change freqvency", Integer.toString(change_v_time));
		ht.put("Food spawn freqvency", Integer.toString(time_step));
		ht.put("Time to starve", Integer.toString(starve));
		ht.put("Cell size", Integer.toString(cell_size));
		ht.put("Creature spawn freqvency", Integer.toString(creature_spawn));
		return ht;
	}

	/**
	 * This method changes the initial parameters in the graphical interface
	 * 
	 * @param parameter
	 *            - initial parameter name
	 * @param newValue
	 *            - the new value
	 */
	@Override
	public boolean modifyStat(String parameter, String newValue) {
	    if (!Pattern.matches("\\d*", newValue)) {
	        return false;
	    }
		boolean re = false;
		switch (parameter) {
		case "Width":
			super.width = Integer.parseInt(newValue);
			if (super.width < 8)
				super.width = 8;
			re = true;
			break;
		case "Height":
			super.height = Integer.parseInt(newValue);
			if (super.height < 8)
				super.height = 8;
			re = true;
			break;
		case "Initial creatures":
			number_of_creatures = Integer.parseInt(newValue);
			if (number_of_creatures < 0)
				number_of_creatures = 0;
			re = true;
			break;
		case "Initial food":
			nr_of_food = Integer.parseInt(newValue);
			if (nr_of_food < 0)
				nr_of_food = 0;
			re = true;
			break;
		case "Speed change freqvency":
			change_v_time = Integer.parseInt(newValue);
			if (change_v_time < 5)
				change_v_time = 5;
			re = true;
			break;
		case "Food spawn freqvency":
			time_step = Integer.parseInt(newValue);
			re = true;
			break;
		case "Time to starve":
			starve = Integer.parseInt(newValue);
			if (starve < 50)
				starve = 50;
			re = true;
			break;
		case "Cell size":
			cell_size = Integer.parseInt(newValue);
			if (cell_size < 5)
				cell_size = 5;
			re = true;
			break;
		case "Creature spawn freqvency":
			creature_spawn = Integer.parseInt(newValue);
			if (starve < 3)
				starve = 3;
			re = true;
			break;
		default:
			break;
		}
		return re;
	}

	
	@Override
    public int getTokenSize() {
        // token size in pixels;
        return 13;
    }
	
}
