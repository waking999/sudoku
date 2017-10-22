package au.edu.cdu.sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuData {
	final static int IMPOSIBLE_VALUE=-1;
	int n; //the range of the values is [1,n] 
	int[] board;
	int[][] cellPossiblity;
	int[][] valExistence;
	boolean[][] valPossiblityBool;
	List<Integer>[] valPossiblityList;

	@SuppressWarnings("unchecked")
	SudokuData(int n) {
		board = new int[n*n];
		cellPossiblity = new int[n*n][n+1]; //the extra 1 for the real count of available values
		valExistence = new int[n][n+1];
		valPossiblityBool = new boolean[n][n*n];

		for (int i = 0; i < n; i++) {
			
			Arrays.fill(valExistence[i],IMPOSIBLE_VALUE);
			valExistence[i][0] = 0;
			Arrays.fill(valPossiblityBool[i], true);
		}

		for (int i = 0; i < n*n; i++) {
			Arrays.fill(board, 0);
			cellPossiblity[i] = new int[n+1];
			cellPossiblity[i][0]=n;
			for(int j=1;j<=n;j++){
				cellPossiblity[i][j]=j;
			}
			
		
		}

		valPossiblityList = new List[n];

	}
}
