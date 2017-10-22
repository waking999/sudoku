package au.edu.cdu.sudoku;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.Assert;

public class FileOperationTest {
	private Logger log = LogUtil.getLogger(FileOperationTest.class);

	@Test
	public void testReadFile() throws IOException {
		log.debug("testReadFile");
		String fileName = TestUtil.getCurrentPath() + "/src/test/resources/game1.txt";
		SudokuData data = FileOperation.readFile(fileName);

		int[][] board = data.board;
		Assert.assertTrue(board[0][0] == 1);
		Util.printBoard(board);
	}

}
