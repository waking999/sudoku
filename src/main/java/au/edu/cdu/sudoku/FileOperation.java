package au.edu.cdu.sudoku;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileOperation {

	static SudokuData readFile(String filePath) throws IOException {
		int n=9;
		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		SudokuData data = new SudokuData(n);
	
		for (int i = 0; i < lines.size(); i++) {
			String[] vals = lines.get(i).split(",");
			int x = Integer.parseInt(vals[0]);
			int y = Integer.parseInt(vals[1]);
			int v = Integer.parseInt(vals[2]);
			data.board[x][y] = v;
			int cell = x * n + y;
			data.cellPossiblity[cell] = new int[] { 1, v };
			for (int j = 0; j < n; j++) {
				data.valPossiblityBool[j][cell] = false;
			}
	
			int vPossibleCount = data.valExistence[v - 1][0] + 1;
			data.valExistence[v - 1][0] = vPossibleCount;
			data.valExistence[v - 1][vPossibleCount] = cell;
	
		}
		return data;
	}

	static int[][] readAnsFile(String filePath) throws IOException {
		int n=9;
		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		int[][] board = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(board[i], -1);
		}
		for (int i = 0; i < lines.size(); i++) {
			String[] vals = lines.get(i).split(",");
			for (int j = 0; j < n; j++) {
				board[i][j] = Integer.parseInt(vals[j]);
			}
		}
		return board;
	}

}
