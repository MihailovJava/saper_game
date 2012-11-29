package game.saperappgame;

import java.util.Random;

public class GameCore {	
	
	private int mine ;	
	private int row;	
	private int col;
	private int flagCount;

	private boolean loose;
	private boolean win;	
	private int[] data;
	

	
	private int getRow(int position){
		int j = 0;
		if (row <= col)
			j = position / col;
		if (row > col)
			j=(position-getCol(position))/ col;
		return j;
	}
	
	private int getCol(int position){
		int i = 0;
		if (row <= col)
			i = position - getRow(position) * col;
		if (row > col)
			i = position / row ;
		return i;
	}
	
	private boolean isBomb(int position){		
		if (data[position] == 9)
			return true;
		if (data[position] == 209)
			return true;
		return false;
	}
	
	public int getFlagCount() {
		return flagCount;
	}
	
	public int getRowCount() {
		return row;
	}	

	public int getColCount() {
		return col;
	}
	
	public int getLength(){
		return col*row;
	}


	public boolean isLoose() {
		return loose;
	}

	public boolean isWin() {
		return win;
	}	
	
	private void setElement(int position , int element){
		int r = getRow(position);
		int c = getCol(position);
		if (r > -1 & c > -1 & r < row & c < col)
			data[position] = element;
	}
	
	public int getElement(int position){
		int r = getRow(position);
		int c = getCol(position);
		
		if (r > -1 & c > -1 & r < row & c < col)
			return data[position];
		return -1;
	}
	
	private void chageData(int position){
		int r = getRow(position);
		int c = getCol(position);
		for (int i = c - 1 ; i < c + 2 ; i++)
			for (int j = r - 1 ; j < r + 2 ; j++)
				if (j > -1 & i > -1 & j < row & i < col)
					if (getElement(j*col+i) < 9 )
						setElement(j*col+i,getElement(j*col+i)+1);
	}
	
	public int[] initData(){
		data = new int[row*col];
		Random rnd = new Random();
		
		for (int mineCount = mine ; mineCount > 0 ;){			
			int position = rnd.nextInt((row-1)*(col-1));
			
			if (getElement(position) != 9){
				setElement(position,9);
				chageData(position);
				mineCount--;
			}			
		}
		loose = false;
		win = false;
		flagCount = 0;
		return data;					
	}
	
	public int getMine() {
		return mine;
	}

	public GameCore(int row, int col,int mine){		
			this.mine = mine;
			this.row = row;
			this.col = col;
			initData();		
	}
	
	private void borderNum(){
		 for (int position = 0 ; position < getLength() ; position++){		
				if (data[position] == 100){	
					int i = getCol(position);
					int j = getRow(position);
					for (int c = i - 1; c < i + 2 ; c++)
						for(int r = j - 1 ; r < j + 2 ; r++)
							if (r > -1 & c > -1 & r < row & c < col){
								int pos = r*col+c;
								if (data[pos] != 9 & data[pos] < 100)
									data[pos] += 100;	
							}
				}
		 }			
							
	}
	
	public void  action(int position){
		int r = getRow(position);
		int c = getCol(position);
		
		if (r > -1 & c > -1 & r < row & c < col){
			if (data[position]/100 == 0 ) 
				data[position] += 100;
			if (data[position] == 100){
				findOtherNull(r*row+c);
				borderNum();
			}
			if (data[position] - 100 == 9)
				loose = true;		
			if (checkEmptyCount() <= 10)
				checkWin();
		}
	}
		
	
	private void findOtherNull(int position){		
								
		if ( getElement(position-col) == 0){	
			action(position-col);	
		}						
						
		if ( getElement(position+col) == 0){			
			action(position+col);		
		}		
						
		if ( getElement(position-1) == 0){			
			if (getCol(position)-1 > -1)
				action(position-1);				
		}		
						
		if ( getElement(position+1) == 0 ){			
			if (getCol(position)+1 < col)
				action(position+1);	
		}	
	}
	
	private int checkEmptyCount(){
		int count = 0;
		 for (int i = 0; i < col ; i++)
			for(int j = 0; j < row ; j++){
				int position = j * col + i;
				if ( data[position]/100 == 0)
					count++;
			}
	 	return count;
	}
	
	private void checkWin(){
		boolean win = true;
		for (int i = 0; i < col ; i++)
			for(int j = 0; j < row ; j++){
				int position = j * col + i;
				if (data[position] / 100 == 0)
					if(! isBomb(position) )
						win = false;
				if (data[position] > 200 )
					if (! isBomb(position))
						win = false;
			}
		this.win = win;
	}	

	
	public void setFlag(int position){
		int r = getRow(position);
		int c = getCol(position);
		boolean flag = false;
		
		if (r > -1 & c > -1 & r < row & c < col)
			if (!flag)
				if (data[position]/100 == 2 ){
					data[position] -= 200;
					flagCount--;
					flag = true;
				}
			if (!flag)
				if (data[position]/100 == 0 ) {
					data[position] += 200;	
					flagCount++;
					flag = true;
				}
		if (checkEmptyCount() <= 10)
				checkWin();
	}
	
	
	
}

