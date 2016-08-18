package org.projectERv06;

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
	directions, food - eats it, creature - interacts with it according to the creatures.
		When a creature eats sufficient amount of food or creatures, it can evolve - improve some of its 
	abilities or gain new abilities like the ability to eat other creatures.	
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
		//Initial amount of creatures
		int number_of_creatures=10;
		//Initial amount of food
		int nr_of_food=40;
		//Total time 
		int time=1000;
		//Used for determaning the time needed to change speed
		int chv_time=100;
		//Single cell size in (X,Y)
		int cell_size=10;
		//Initialize field
		Field field = new Field(cx_length,cy_length);
		
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
				//Made a new creature, added it and it's copy
				//into cell and added the creature to the lsit
				Creature creature=new Creature(xx, yy, cell_size);
				creature.NewSpeed(chv_time);
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
		//Uses time instead of turns as was in v1
		//Max speed shouldn't exceed cell size otherwise cells could be skipped,
		//instead one should just increase max time and cell size as needed.
		//This allows to increase creatures speed compared to other creatures
		for(int t=0; t<time; t++){
			List<Creature> list_of_creatures_to_remove = new ArrayList<Creature>();
			for(Creature creature : list_of_creatures){
				if(list_of_creatures_to_remove.contains(creature)) continue;
				//Looks at time when the creature has to randomly change speed
				if(creature.change_v==0){
					creature.NewSpeed(chv_time);
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
						creature.CellXY(cx_1, cy_1);
						field.cell[cx_1][cy_1].change(2, creature);
						field.cell[cx_0][cy_0].change(0, null);
						creature.XY(x_1, y_1);
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
							temp_creature.alive=false;
							list_of_creatures_to_remove.add(temp_creature);
							creature.CellXY(cx_1, cy_1);
							creature.Evolve(0,1);
							field.cell[cx_1][cy_1].change(2, creature);
							field.cell[cx_0][cy_0].change(0, null);
							creature.XY(x_1, y_1);
						}
						break;
					}
					case 3:{//food
						creature.CellXY(cx_1, cy_1);
						creature.Evolve(1,0);
						field.cell[cx_1][cy_1].change(2, creature);
						field.cell[cx_0][cy_0].change(0, null);
						creature.XY(x_1, y_1);
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
		for(int i=0; i<all_creatures.size(); i++)
			System.out.println("Creature "+i+" is Alive?="+ all_creatures.get(i).alive +" has eaten="+ 
					all_creatures.get(i).food_eaten+ ", can eat creature?="+ all_creatures.get(i).eatCreature +
					", creatures eaten=" + all_creatures.get(i).creatures_eaten + " and evolved "+ 
						all_creatures.get(i).times_evolved +" times.");
		//Not necessary
	}

}
