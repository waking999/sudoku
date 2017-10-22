package au.edu.cdu.sudoku;

import org.junit.Test;

import junit.framework.Assert;

public class UtilTest {
	@Test 
	public void testGetCells() {
		int cell;
		int[] expect;
		int[] output;
		
		cell=0;
		expect=new int[] {1,2,3,4,5,6,7,8};
		output=Util.getRowCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
		
		cell=0;
		expect=new int[] {9,18,27,36,45,54,63,72};
		output=Util.getColCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
		
		cell=0;
		expect=new int[] {1,2,9,10,11,18,19,20};
		output=Util.getPieceCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
		
		
		cell=16;
		expect=new int[] {9,10,11,12,13,14,15,17};
		output=Util.getRowCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
		
		cell=16;
		expect=new int[] {7,25,34,43,52,61,70,79};
		output=Util.getColCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
		
		cell=16;
		expect=new int[] {6,7,8,15,17,24,25,26};
		output=Util.getPieceCells(cell);
		Assert.assertTrue(Util.verifySort(expect, output));
	}

}
