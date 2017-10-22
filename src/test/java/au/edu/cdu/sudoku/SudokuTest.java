package au.edu.cdu.sudoku;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.Assert;

public class SudokuTest {
	private Logger log = LogUtil.getLogger(SudokuTest.class);

	

	@Test
	public void testValidBoard() throws IOException {
		log.debug("testValidBoard");
		String fileName = TestUtil.getCurrentPath() + "/src/test/resources/game1ans.txt";
		int[][] board = FileOperation.readAnsFile(fileName);
		Util.printBoard(board);
		boolean output = Sudoku.validBoard(board);
		Assert.assertTrue(output);
	}

	@Test
	public void testReductionRule() throws IOException {
		log.debug("testReductionRule");
		String fileName = TestUtil.getCurrentPath() + "/src/test/resources/game1.txt";
		SudokuData data = FileOperation.readFile(fileName);
		Sudoku.reductionRule1(data);
		Sudoku.setBoard(data);
		Util.printBoard(data.board);

	}

	@Test
	public void testReductionRuleLoop() throws IOException {
		log.debug("testReductionRuleLoop");
		String fileName = TestUtil.getCurrentPath() + "/src/test/resources/game1.txt";
		SudokuData data = FileOperation.readFile(fileName);
		Sudoku.reductionRuleLoop(data);
		Util.printBoard(data.board);

	}
	
	
	
}
