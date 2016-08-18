package org.projectERv07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	
/**
		Currently this program prepares a field of cells (in the form of an array) that subsists of 2 
	values, one of them gives a value that symbolizes what is in the cell: empty space (0), wall(1), 
	creature(2), food(3) and the other value gives a reference to the creature if the cell is occupied 
	by one. The field is an empty field with walls and randomly placed creatures and pieces of food. 
		The cells have been given a physical size externally in the main method and the creatures have 
	been placed in the middle of the cells and have been assigned speed values. The process iterates over 
	time, this makes it possible to give creatures different speeds because not all of the creatures will 
	always move between different cells in a given time step. This allows to assign different speed values 
	to the creatures as long as the maximums value doesn't exceed the cell size.
		Within a time step, the list of creatures is iterated over and the creatures move procedurally. 
	It checks whether the creature should move cells. If it should move cells, it checks what is in the 
	adjacent cell the creature should move to and reacts accordingly: empty space - move, wall - change 
	directions, food - eats it, creature - interacts with it according to the creatures. Occasionally 
	the speed and the direction of the creatures changes;
		When a creature eats sufficient amount of food or creatures, it can evolve - improve some of its 
	abilities or gain new abilities like the ability to eat other creatures.
	 	Food is randomly added to the field when sufficient time has elapsed. If creatures do not get food
	 often enough, they die of starvation.
*/
	
	public static void main(String[] args) {
		//Used for (X,Y) distance
		int x_0=0, y_0=0;
		int x_1=0, y_1=0;
		//Cell numbers (array numbers)
		int cx_0=0, cy_0=0;
		int cx_1=0, cy_1=0;
		//Amount of cells (array size)
		int cx_length=10;
		int cy_length=10;
		//Single cell size in (X,Y)
		int cell_size=10;
		//Initial amount of creatures
		int number_of_creatures=10;
		//Initial amount of food
		int nr_of_food=40;
		//Total time 
		int time=1000;
		//Used for determaning the time needed to change speed (t/2 + {0,t})
		int change_v_time=100;
		
		//Initialize field
		Field field = new Field(cx_length,cy_length);
		

		int time_step = time/10;
		int time_till_food;
		int random_food = 0;
		
		Random rnd = new Random();
		
		//Adding walls to the field
		for(int i=0; i<cx_length; i++){
			field.cell[i][0].whatIs=1;
			field.cell[i][cy_length-1].whatIs=1;
		}
		for(int i=0; i<cy_length; i++){
			field.cell[0][i].whatIs=1;
			field.cell[cx_length-1][i].whatIs=1;
		}
		
		//Adding creatures to the field
		int nc_temp=number_of_creatures;
		List<Creature> list_of_creatures = new ArrayList<Creature>();
		//Creature creature[]= new Creature[nc_temp];
		for(int i=0; i<nc_temp; i++){
			int xx=rnd.nextInt(cx_length);
			int yy=rnd.nextInt(cy_length);
			if(field.cell[xx][yy].whatIs==0){
				//Made a new creature, added it and it's reference
				//into cell and added the creature to the lsit
				Creature creature=new Creature(xx, yy, cell_size);
				creature.newSpeed(change_v_time);
				field.cell[xx][yy].change(2, creature);
				list_of_creatures.add(creature);
			}
			else nc_temp++;
		}
		
		//Adding food to the field
		for(int i=0; i<nr_of_food; i++){
			int xx=rnd.nextInt(cx_length);
			int yy=rnd.nextInt(cy_length);
			if(field.cell[xx][yy].whatIs==0) field.cell[xx][yy].whatIs=3;
			else nr_of_food++;
		}
		
		//Print out initial board
		char test=' ';
		for(int i=0; i<cx_length; i++){
			for(int j=0; j<cy_length; j++){
				if(field.cell[i][j].whatIs==0) test=' ';
				if(field.cell[i][j].whatIs==1) test='W';
				if(field.cell[i][j].whatIs==2) test='C';
				if(field.cell[i][j].whatIs==3) test='F';
				System.out.print(test+ " ");
			}
			System.out.println();
		}
		//Not necessary
		
		List<Creature> all_creatures = new ArrayList<Creature>();
		//Max speed shouldn't exceed cell size otherwise cells could be skipped,
		//instead one should just increase max time and cell size as needed.
		//This allows to increase creatures speed compared to other creatures
		
		time_till_food=rnd.nextInt(time_step)+time_step;
		
		for(int t=0; t<time; t++){			
			//Adding food randomly to the field, need to decide how to better balance it
			time_till_food--;
			if(time_till_food==0){
				//random_food=rnd.nextInt(cx_length); //Need to choose food amount better
				random_food=cx_length;
				System.out.println("Maybe adds "+random_food+" food");
				for(int i=0; i<random_food; i++){
					int xx=rnd.nextInt(cx_length);
					int yy=rnd.nextInt(cy_length);
					if(field.cell[xx][yy].whatIs==0) field.cell[xx][yy].whatIs=3;
				}
				time_till_food=rnd.nextInt(time_step)+time_step;
			}
			
			List<Creature> list_of_creatures_to_remove = new ArrayList<Creature>();
			for(Creature creature : list_of_creatures){
				if(list_of_creatures_to_remove.contains(creature)) continue;
				//Looks at time when the creature has to randomly change speed
				if(creature.change_v==0){
					creature.newSpeed(change_v_time);
				}
				creature.change_v--;
				//looks at creature's initial and intended position  on a (x,y) axis
				x_0=creature.x;
				y_0=creature.y;
				x_1=x_0+creature.direction_x*creature.v_x*1;
				y_1=y_0+creature.direction_y*creature.v_y*1;
				//initial and intended position in cells numbers
				cx_0=creature.cell_x;
				cy_0=creature.cell_y;
				cx_1=x_1/cell_size;
				cy_1=y_1/cell_size;
				//Sees if cell change is needed, if needed, see what's there
				//and determine what to do
				if(cx_1 != cx_0 || cy_1 != cy_0){
					switch(field.cell[cx_1][cy_1].whatIs){
					case 0:{//empty space
						creature.cellXY(cx_1, cy_1);
						field.cell[cx_1][cy_1].change(2, creature);
						field.cell[cx_0][cy_0].change(0, null);
						creature.locXY(x_1, y_1);
						break;
					}
					case 1:{//wall
						creature.direction_x*=-1;
						creature.direction_y*=-1;
						break;
					}
					case 2:{//creature
						//If creature hasn't evolved ability to eat other creatures
						if(creature.eatCreature==false){
							creature.direction_x*=-1;
							creature.direction_y*=-1;
						}
						//If creature has evolved ability to eat other creatures
						else{
							//System.out.println("Somehow a creature can eat other creatures");
							Creature temp_creature = field.cell[cx_1][cy_1].creature;
							if(temp_creature.strength<creature.strength){
								temp_creature.kill(t, "Killed by creature");
								list_of_creatures_to_remove.add(temp_creature);
								creature.cellXY(cx_1, cy_1);
								creature.eatAndEvolve(0,1);
								field.cell[cx_1][cy_1].change(2, creature);
								field.cell[cx_0][cy_0].change(0, null);
								creature.locXY(x_1, y_1);
							}
							else{
								creature.direction_x*=-1;
								creature.direction_y*=-1;
							}
							
						}
						break;
					}
					case 3:{//food
						creature.cellXY(cx_1, cy_1);
						creature.eatAndEvolve(1,0);
						field.cell[cx_1][cy_1].change(2, creature);
						field.cell[cx_0][cy_0].change(0, null);
						creature.locXY(x_1, y_1);
						break;
					}
					default: System.err.println("Wrong atribute in a field x="+cx_1 +", y="+cy_1);
					}
				}
				//if not needed, just update (X,Y)
				else{
					creature.x=x_1;
					creature.y=y_1;
				}
				creature.starvation_time--;
				if(creature.starvation_time==0){
					creature.kill(t, "Starved to death");
					field.cell[creature.cell_x][creature.cell_y].change(0, null);
					list_of_creatures_to_remove.add(creature);
				}
			}
			//If some creatures have been eaten, remove unneeded (eaten) creatures from the list
			//Adds dead creatures to full list
			if(list_of_creatures_to_remove.isEmpty()==false){
				list_of_creatures.removeAll(list_of_creatures_to_remove);
				all_creatures.addAll(list_of_creatures_to_remove);
				//Printing out how the board and creatures look, not necessary
				System.out.println("Time "+t+" ______________________________________________________________________________");
				for(int i=0; i<cx_length; i++){
					for(int j=0; j<cy_length; j++){
						if(field.cell[i][j].whatIs==0) test=' ';
						if(field.cell[i][j].whatIs==1) test='W';
						if(field.cell[i][j].whatIs==2) test='C';
						if(field.cell[i][j].whatIs==3) test='F';
						System.out.print(test+ " ");
					}
					System.out.println();
				}
				for(int i=0; i<list_of_creatures.size(); i++)
					System.out.println("Creature "+i+" has eaten="+ list_of_creatures.get(i).food_eaten+
						", can eat creature?="+ list_of_creatures.get(i).eatCreature +", creatures eaten="
							+ list_of_creatures.get(i).creatures_eaten + " and evolved "+ 
								list_of_creatures.get(i).times_evolved +" times.");
				//Not necessary
			}
		
		}
		//Full list of creatures, dead and alive
		all_creatures.addAll(list_of_creatures);
		//Print out resulting field and creatures, who have not been eaten
		test=' ';
		System.out.println("______________________________________________________________________________");
		for(int i=0; i<cx_length; i++){
			for(int j=0; j<cy_length; j++){
				if(field.cell[i][j].whatIs==0) test=' ';
				if(field.cell[i][j].whatIs==1) test='W';
				if(field.cell[i][j].whatIs==2) test='C';
				if(field.cell[i][j].whatIs==3) test='F';
				System.out.print(test+ " ");
			}
			System.out.println();
		}
		int a=0;
		for(Creature creature : all_creatures){
			System.out.print("Creature "+a+" has eaten "+ 
					creature.food_eaten+ " food, can eat creature = "+ creature.eatCreature +
						", creatures eaten=" + creature.creatures_eaten + ", evolved "+ 
							creature.times_evolved +" times. ");
			if(creature.alive==false)
				System.out.print("Died at "+ creature.death_time + ". "
									+ creature.death_cause+ ".");
			System.out.println();
			a++;
		}
		
//		for(int i=0; i<all_creatures.size(); i++)
//			System.out.println("Creature "+i+" is Alive?="+ all_creatures.get(i).alive +" has eaten="+ 
//				all_creatures.get(i).food_eaten+ ", can eat creature?="+ all_creatures.get(i).eatCreature +
//					", creatures eaten=" + all_creatures.get(i).creatures_eaten + " ,evolved "+ 
//						all_creatures.get(i).times_evolved +" times, died at"+ );
		//Not necessary
	}

}
