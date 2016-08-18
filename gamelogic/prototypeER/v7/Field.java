package org.projectERv07;






public class Field {

	
	public int cx_lenght,cy_lenght;
	public Cell cell[][];
	
	public Field(int x, int y) {
		this.cx_lenght=x;
		this.cy_lenght=y;
		cell = new Cell[x][y];
		
		//Initialized each cell
		for(int i=0; i<x; i++)
			for(int j=0; j<y; j++)
				cell[i][j] = new Cell();
		
	}
	
//	public List checknbr(int x, int y){
//		List<Integer> neighbor = new ArrayList<Integer>();
//		for(int i=x-1; i<=x+1; i++){
//			for(int j=y-1; j<=y+1; j++){
//				neighbor.add(cell[i][j].isWhat);
//				//if(cell[i][j].isFull==true){	
//					
//					
//					
//					
//					//Do something with what is here
//					//cell[i][y].isWhat;
//				//}
//			}
//		
//		}
//	return neighbor;
//	}
	
	
	//Convers (x,y) to cell form and gives what is in it
//	public int GiveCell(int x, int y){
//		int cx=x/cell_size;
//		int cy=y/cell_size;
//		return cell[cx][cy];
//	}
//	
//	
//	
//	public int checknbr(int x, int y){
//		return cell[x][y];
//	}
}
