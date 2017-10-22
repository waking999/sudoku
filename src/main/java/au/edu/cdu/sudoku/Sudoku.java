package au.edu.cdu.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
	static int n = 9; // the range of the values is [1,n]
	static int pn = (int) Math.sqrt(n);
	static int sn = n * n;

	static boolean validBoard(int[] board) {
		int[] expect = new int[n];
		for (int i = 0; i < n; i++) {
			expect[i] = i + 1;
		}

		for (int i = 0; i < n; i++) {
			int[] row = new int[n];
			for (int j = 0; j < n; j++) {
				int cell = Util.getCellByXY(i, j, n);
				row[j] = board[cell];
			}

			Arrays.sort(row);
			if (!Util.verifySort(expect, row)) {
				return false;
			}
		}

		for (int j = 0; j < n; j++) {
			int[] col = new int[n];
			for (int i = 0; i < n; i++) {
				int cell = Util.getCellByXY(i, j, n);

				col[i] = board[cell];
			}
			Arrays.sort(col);
			if (!Util.verifySort(expect, col)) {
				return false;
			}
		}

		System.out.println("pn=" + pn);
		for (int k = 0; k < n; k++) {
			int[] piece = new int[n];

			for (int i = 0; i < pn; i++) {
				for (int j = 0; j < pn; j++) {
					int cell = Util.getCellByXY((k / pn) * pn + i, (k % pn) * pn + j, n);
					piece[i * pn + j] = board[cell];
				}
			}
			Arrays.sort(piece);
			if (!Util.verifySort(expect, piece)) {
				return false;
			}
		}
		return true;

	}

	/**
	 * reduction rule 1: the cell at the same row, column and piece can not own the
	 * same value;
	 * 
	 * @param data
	 */
	static void reductionRule1(SudokuData data) {
		for (int v = 0; v < n; v++) {
			int[] vPoss = data.valExistence[v];
			int count = vPoss[0];
			for (int i = 1; i <= count; i++) {
				int cell = vPoss[i];
				int[] xy = Util.getXYByCell(cell, n);
				int x = xy[0];
				int y = xy[1];
				// x axis
				for (int col = 0; col < n; col++) {
					if (col != y) {
						int newCell = x * n + col;
						if (data.cellPossiblity[newCell][0] > 1) {
							swap(data.cellPossiblity[newCell], v + 1);
							data.valPossiblityBool[v][newCell] = false;
						}

						if (data.cellPossiblity[newCell][0] == 1) {
							for (int k = 0; k < n; k++) {
								data.valPossiblityBool[k][newCell] = false;
							}
						}
					}
				}

				// y axis
				for (int row = 0; row < n; row++) {
					if (row != x) {
						int newCell = row * n + y;
						if (data.cellPossiblity[newCell][0] > 1) {
							swap(data.cellPossiblity[newCell], v + 1);
							data.valPossiblityBool[v][newCell] = false;
						}
						if (data.cellPossiblity[newCell][0] == 1) {
							for (int k = 0; k < n; k++) {
								data.valPossiblityBool[k][newCell] = false;
							}
						}
					}
				}

				// piece
				int left = (x / pn) * pn;
				int up = (y / pn) * pn;
				for (int row = 0; row <= pn - 1; row++) {
					for (int col = 0; col <= pn - 1; col++) {
						int newCell = (left + row) * n + (up + col);
						if (data.cellPossiblity[newCell][0] > 1) {
							swap(data.cellPossiblity[newCell], v + 1);
							data.valPossiblityBool[v][newCell] = false;
						}
						if (data.cellPossiblity[newCell][0] == 1) {
							for (int k = 0; k < n; k++) {
								data.valPossiblityBool[k][newCell] = false;
							}
						}
					}
				}

			}
		}
		setBoard(data);
	}

	static boolean swapFlag;

	static void swap(int[] array, int badVal) {
		int pos = Util.getValPos(array, badVal);
		if (pos != -1) {
			int count = array[0];
			int tmp = array[count];
			array[count] = array[pos];
			array[pos] = tmp;
			array[0] = count - 1;

			swapFlag = true;
		}

	}

	static void calNumPossiblity(SudokuData data) {
		@SuppressWarnings("unchecked")
		List<Integer>[] numPos = new List[n];
		for (int i = 0; i < n; i++) {
			List<Integer> tmpList = new ArrayList<>();
			for (int j = 0; j < sn; j++) {
				if (data.valPossiblityBool[i][j]) {
					tmpList.add(j);
				}
			}
			numPos[i] = tmpList;
		}
		data.valPossiblityList = numPos;
	}

	static void setBoard(SudokuData data) {
		for (int cell = 0; cell < sn; cell++) {
			if (data.cellPossiblity[cell][0] == 1) {
				int v = data.cellPossiblity[cell][1];
				// int[] xy=Util.getXYByCell( cell,n);
				// int x = xy[0];
				// int y = xy[1];
				data.board[cell] = v;

				int pos = Util.getValPos(data.valExistence[v - 1], cell);
				if (pos == -1) {
					int vPossibleCount = data.valExistence[v - 1][0] + 1;
					data.valExistence[v - 1][0] = vPossibleCount;
					data.valExistence[v - 1][vPossibleCount] = cell;
				}
			}
		}

	}

	/**
	 * reduction rule 2: if the number of possible cells of a number is the same as
	 * the remaining number of the number to be put, that's the only 1 solution to
	 * put all the remaining
	 * 
	 * @param data
	 */
	private static void reductionRule2(SudokuData data) {
		for (int v = 0; v < n; v++) {
			int[] exist = data.valExistence[v];
			int existCount = exist[0];
			List<Integer> numPos = data.valPossiblityList[v];
			if (numPos != null) {
				int numPosSize = numPos.size();
				if (numPosSize + existCount == n && numPosSize > 0) {
					for (int cell : numPos) {
						setCellVal(data, v, cell);

					}
				}
			}
		}
	}

	static void reductionRuleLoop(SudokuData data) {
		swapFlag = true;
		while (swapFlag) {
			while (swapFlag) {
				swapFlag = false;
				reductionRule1(data);
				calNumPossiblity(data);
				reductionRule2(data);
				calNumPossiblity(data);

			}
			reductionRule3(data);
			calNumPossiblity(data);
		}
		System.out.println();
		for (int cell = 0; cell < sn; cell++) {
			// int[] xy=Util.getXYByCell( cell,n);
			// int x = xy[0];
			// int y = xy[1];
			if (data.board[cell] == 0) {
				int count = data.cellPossiblity[cell][0];
				System.out.print(cell + ":");
				for (int i = 1; i <= count; i++) {
					System.out.print(data.cellPossiblity[cell][i] + ",");

				}
				System.out.println();
			}
		}

	}

	static void reductionRule3(SudokuData data) {
		for (int v = 0; v < n; v++) {
			List<Integer> listA = data.valPossiblityList[v];
			for (int cell : listA) {
				if (Util.isOnly(cell, listA)) {
					setCellVal(data, v, cell);
				}
			}
		}
	}

	private static void setCellVal(SudokuData data, int v, int cell) {
		swapFlag = true;
		// int[] xy=Util.getXYByCell( cell,n);
		// int x = xy[0];
		// int y = xy[1];
		data.board[cell] = v + 1;

		int vPossibleCount = data.valExistence[v][0] + 1;
		data.valExistence[v][0] = vPossibleCount;
		data.valExistence[v][vPossibleCount] = cell;

		data.valPossiblityBool[v][cell] = false;

		data.cellPossiblity[cell] = new int[] { 1, v + 1 };
	}
}
