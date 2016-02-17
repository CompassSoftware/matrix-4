package Javatrix;

import static org.junit.Assert.*;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.PrintWriter;
import java.io.PrintStream;

import org.junit.Test;
public class MatrixTest {

	/*
	 * Test the basic constructor, and its exception case.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBasicConstructorAndGetArray() {
		//Test basic usage
		double[][] expected = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		double[][] expected2 = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 3.2}};
		Matrix m = new Matrix(expected);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
		
		//Test changing array from getArray changes the matrix
		actual[2][2] = 3.2;
		assertArrayEquals(expected2, m.getArray());
		
		//Test jagged arrays throw an exception
		double[][] jagged = {{0f},{1f, 2f},{3f, 4f, 5f}};
		m = new Matrix(jagged);
	}
	
	/*
	 * Tests the quick constructor
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testQuickConstructor() {
		//Test basic usage
		double[][] vals = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		double[][] expected = {{0f, 1f},{3f, 4f},{6f, 7f}};
		Matrix m = new Matrix(vals, 3, 2);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
		
		//Test bad parameters don't somehow work
		m = new Matrix(vals, 3, 4);
	}
	
	/*
	 * Test packed array constructor
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPackedConstructor() {
		//Test basic usage
		double[] vals = {0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};
		double[][] expected1 = {{0f, 1f, 2f, 3f, 4f}, {5f, 6f, 7f, 8f, 9f}};
		Matrix m = new Matrix(vals, 2);
		double[][] actual1 = m.getArray();
		assertArrayEquals(expected1, actual1);

		//And again, with different parameters
		m = new Matrix(vals, 5);
		double[][] expected2 = {{0f, 1f}, {2f, 3f}, {4f, 5f}, {6f, 7f}, {8f, 9f}};
		double[][] actual2 = m.getArray();
		assertArrayEquals(expected2, actual2);
		
		//Test bad row numbers don't work
		m = new Matrix(vals, 3);
	}
	
	/*
	 * Test the constructor that initializes a matrix of zeros.
	 */
	@Test
	public void testZeroConstructor() {
		double[][] expected = new double[3][4];
		Matrix m = new Matrix(3, 4);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
	}
	
	/*
	 * Test the constructor that initializes a matrix to a specified value.
	 */
	@Test
	public void testInitializeValueConstructor() {
		double[][] expected = {{1f, 1f, 1f, 1f}, {1f, 1f, 1f, 1f}, {1f, 1f, 1f, 1f}};
		Matrix m = new Matrix(3, 4, 1f);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
	}
	
	/*
	 * Test getColumnDimension
	 */
	@Test
	public void testGetColumnDimension() {
		Matrix m = new Matrix(3,4);
		int expected = 4;
		assertEquals(m.getColumnDimension(), expected);
	}
	
	/*
	 * Test getRowDimension
	 */
	@Test
	public void testGetRowDimension() {
		Matrix m = new Matrix(3,4);
		int expected = 3;
		assertEquals(m.getRowDimension(), expected);
	}
	
	/*
	 * Test copy, makes a deep copy of the matrix
	 */
	@Test
	public void testCopy() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4}};
		double[][] data2 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,3.2,2.4}};
		Matrix mat = new Matrix(data);
		Matrix t = mat.copy();
		
		int expectedRow = mat.getRowDimension();
		int expectedCol = mat.getColumnDimension();
		int actualRow = t.getRowDimension();
		int actualCol = t.getColumnDimension();
		assertEquals(expectedRow, actualRow);
		assertEquals(expectedCol, actualCol);
		
		double[][] expected = mat.getArray();
		double[][] actual = t.getArray();
		assertArrayEquals(expected, actual);

		//Test change to one matrix doesn't affect the other
		t.set(2, 2, 3.2);
		assertArrayEquals(data, mat.getArray());
		assertArrayEquals(data2, t.getArray());
	}
	
	/*
	 * Test getArrayCopy, returns a copy of the internal 2D array of the matrix
	 */
	@Test
	public void testgetArrayCopy() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix m = new Matrix(data);
		double[][] actual = m.getArrayCopy();
		assertArrayEquals(data, actual);
		
		//Test changing the array copy doesn't change the matrix
		actual[2][2] = 3.2;
		assertArrayEquals(data, m.getArray());
	}
	
	/*
	 * Test trace, returns sum of the diagonal of a matrix
	 * 
	 * ALSO tests random(), which returns a matrix of randomly generated doubles
	 */
	@Test
	public void testTrace() {
		Matrix t;
		t = Matrix.random(4,4);
		double expected = t.trace();
		double actual = 0;
		int m = t.getRowDimension();
		int n = t.getRowDimension();
		for (int i = 0; i < m && i < n; i++) {
			actual += t.get(i, i);
		}
		assertEquals(expected, actual, 0f);
	}
	
	/*
	 * Test print with number format
	 */
	@Test
	public void testPrintNumFormat() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix t = new Matrix(data);
		int m = t.getRowDimension();
		int n = t.getRowDimension();
		DecimalFormat format = new DecimalFormat();
		for (int i = 0; i < m && i < n; i++) {
			t.print(format, 1);
		}
		
	}

}
