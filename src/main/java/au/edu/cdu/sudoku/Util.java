package au.edu.cdu.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
	static int n=9;
	/**
	 * verify if the output is the same as expect
	 * 
	 * @param expect
	 * @param output
	 * @return
	 */
	static boolean verifySort(int[] expect, int[] output) {
		int expectLen = expect.length;
		int outputSize = output.length;
		if (expectLen != outputSize) {
			return false;
		}
		String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		String outputStr = Arrays.stream(output).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		return expectStr.equals(outputStr);
	}

	/**
	 * print board
	 */
	static void printBoard(int[] board) {

		for (int i = 0; i < n; i++) {
			if (i == 0) {
				System.out.println("-------------------");
			} else {
				if (i % 3 == 0) {
					System.out.println("--+-+-#-+-+-#-+-+--");
				} else {
					System.out.println("--+-+-+-+-+-+-+-+--");
				}
			}

			for (int j = 0; j < 9; j++) {
				System.out.print("|");
				int cell=Util.getCellByXY(i, j, n);
				if (board[cell] > 0) {

					System.out.print(board[cell]);

				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println("-------------------");
	}

	/**
	 * get the cells in the same row
	 * 
	 * @param cell
	 * @return
	 */
	static int[] getRowCells(int cell) {
		int[] ret = new int[8];
		int count = 0;
		int x = cell / 9;
		int y = cell % 9;
		// x axis
		for (int col = 0; col < 9; col++) {
			if (col != y) {
				int newCell = x * 9 + col;
				ret[count++] = newCell;
			}
		}
		return ret;
	}

	/**
	 * get the cells in the same column
	 * 
	 * @param cell
	 * @return
	 */
	static int[] getColCells(int cell) {
		int[] ret = new int[8];
		int count = 0;
		int x = cell / 9;
		int y = cell % 9;
		// y axis
		for (int row = 0; row < 9; row++) {
			if (row != x) {
				int newCell = row * 9 + y;
				ret[count++] = newCell;
			}
		}
		return ret;
	}

	/**
	 * get the cells in the same piece
	 * 
	 * @param cell
	 * @return
	 */
	static int[] getPieceCells(int cell) {
		int[] ret = new int[8];
		int count = 0;
		int x = cell / 9;
		int y = cell % 9;
		int left = (x / 3) * 3;
		int up = (y / 3) * 3;
		for (int row = 0; row <= 2; row++) {
			for (int col = 0; col <= 2; col++) {
				int newCell = (left + row) * 9 + (up + col);
				if (newCell != cell) {
					ret[count++] = newCell;
				}
			}
		}
		return ret;
	}

	/**
	 * to check if a certain cell in the array of cells is the only possible cell
	 * for a certain value if the cell is the only cell on the row, column or piece
	 * 
	 * @param cells
	 * @param numPos
	 * @return
	 */
	static boolean isOnlyInCells(int[] cells, List<Integer> numPos) {
		boolean contains = false;
		for (int c : cells) {
			if (numPos.contains(c)) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			return true;
		}
		return false;
	}

	/**
	 * Given a particular cell, to check if it is the only in the possible cells of a value
	 * @param cell
	 * @param valPossiblityList
	 * @return
	 */
	static boolean isOnly(int cell, List<Integer> valPossiblityList) {

		boolean flag = false;

		int[] cells = getRowCells(cell);
		flag = isOnlyInCells(cells, valPossiblityList);
		if (flag) {
			return true;
		}

		cells = getColCells(cell);
		flag = isOnlyInCells(cells, valPossiblityList);
		if (flag) {
			return true;
		}

		cells = getPieceCells(cell);
		flag = isOnlyInCells(cells, valPossiblityList);
		if (flag) {
			return true;
		}

		return false;
	}

	static int[] getXYByCell(int cell, int n) {
		int[] ret = new int[2];
		ret[0] = cell / n;
		ret[1] = cell % n;
		return ret;
	}
	
	static int getCellByXY(int x,int y,int n) {
		int cell= x*n+y;
		return cell;
	}

	static int getValPos(int[] array, int val) {
		int count = array[0];
		for (int i = 1; i <= count; i++) {
			if (array[i] == val) {
				return i;
			}
		}

		return -1;
	}

}
