package Javatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.Test;
public class MatrixTest {
	private double delta = 1e-9;

	/*
	 * Test the basic constructor, and its exception case.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBasicConstructorAndGetArray() {
		//Test basic usage
		double[][] expected = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		Matrix m = new Matrix(expected);
		double[][] actual = m.getArray();
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test changing array from getArray changes the matrix
		double[][] expected2 = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 3.2}};
		actual[2][2] = 3.2;
		double[][] actual2 = m.getArray();
		for(int i = 0; i < 3; i++) assertArrayEquals(expected2[i], actual2[i], delta);
		
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
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
		
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
		for(int i = 0; i < 2; i++) assertArrayEquals(expected1[i], actual1[i], delta);

		//And again, with different parameters
		m = new Matrix(vals, 5);
		double[][] expected2 = {{0f, 1f}, {2f, 3f}, {4f, 5f}, {6f, 7f}, {8f, 9f}};
		double[][] actual2 = m.getArray();
		for(int i = 0; i < 5; i++) assertArrayEquals(expected2[i], actual2[i], delta);
		
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
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Test the constructor that initializes a matrix to a specified value.
	 */
	@Test
	public void testInitializeValueConstructor() {
		double[][] expected = {{1f, 1f, 1f, 1f}, {1f, 1f, 1f, 1f}, {1f, 1f, 1f, 1f}};
		Matrix m = new Matrix(3, 4, 1f);
		double[][] actual = m.getArray();
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
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
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);

		//Test change to one matrix doesn't affect the other
		t.set(2, 2, 3.2);
		for(int i = 0; i < 3; i++) assertArrayEquals(data[i], expected[i], delta);
		for(int i = 0; i < 3; i++) assertArrayEquals(data2[i], actual[i], delta);
	}
	
	/*
	 * Test getArrayCopy, returns a copy of the internal 2D array of the matrix
	 */
	@Test
	public void testgetArrayCopy() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix m = new Matrix(data);
		double[][] actual = m.getArrayCopy();
		for(int i = 0; i < 4; i++) assertArrayEquals(data[i], actual[i], delta);
		
		//Test changing the array copy doesn't change the matrix
		actual[2][2] = 3.2;
		double[][] actual2 = m.getArrayCopy();
		for(int i = 0; i < 3; i++) assertArrayEquals(data[i], actual2[i], delta);
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
		assertEquals(expected, actual, delta);
	}
	
	/*
	 * Test print with number format
	 */
	@Test
	public void testPrintNumFormat() {
		String expected = "-0  0  0  0 \n 1 -1  1  1 \n 2  2 -2  2 \n 3  3  3 -3 \n" +
			"-00  00  00  00 \n 01 -01  01  01 \n 02  02 -02  02 \n 03  03  03 -03 \n" +
			"-0.1  0.2  0.3  0.4 \n 1.1 -1.2  1.3  1.4 \n 2.1  2.2 -2.3  2.4 \n 3.1  3.2  3.3 -3.4 \n" +
			"-0.10  0.20  0.30  0.40 \n 1.10 -1.20  1.30  1.40 \n 2.10  2.20 -2.30  2.40 \n 3.10  3.20  3.30 -3.40 \n";
		try {
			PrintStream ps = new PrintStream("test");
			PrintStream oldOut = System.out;
			System.setOut(ps);
			
			double[][] data = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
			Matrix t = new Matrix(data);
			DecimalFormat format = new DecimalFormat();
			format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
			t.print(format, -1);
			t.print(format, 1);
			t.print(format, 2);
			t.print(format, 3);
			t.print(format, 4);

			ps.close();
			System.setOut(oldOut);
		} catch(FileNotFoundException e) {
			fail("Could not open file for stdout redirect:\n" + e);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("test"));
			String line, actual = "";
			while((line = br.readLine()) != null) {actual += line + "\n";}
			br.close();
			assertEquals(expected, actual);
		} catch(FileNotFoundException e) {
			fail("Could not open file to read output:\n" + e);
		} catch(IOException e) {
			fail("Could not read line:\n" + e);
		}
	}

	/*
	 * Tests the get method.
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testGet()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.get(1, 0), .4, delta);
		assertEquals(m.get(2, 0), .7, delta);
		
		//Test out of bounds exception is thrown
		m.get(0, -1);
	}
	
	/*
	 * Tests the set method.
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testSet()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		m.set(1, 1, 5);
		m.set(2, 0, .55);
		assertEquals(m.get(1, 1), 5, delta);
		assertEquals(m.get(2, 0), .55, delta);
		
		//Test out of bounds exception is thrown
		m.set(0, -1, 0.5);
		
	}
	
	/*
	 * Tests the norm1 method.
	 */
	@Test
	public void testNorm1()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(1.8, m.norm1(), delta);
	}
	
	/*
	 * Tests the normF method.
	 */
	@Test
	public void testNormF()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.normF(), 1.6881943016134132, delta);
	}
	
	/*
	 * Test plus, adds two matrices together
	 */
	@Test
	public void testPlus() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix x = new Matrix(data1);
		Matrix y = new Matrix(data2);
		Matrix z = x.plus(y);
		
		double[][] expected = {{1.2, 1.4, 1.6, 1.8}, {3.2, 3.4, 3.6, 3.8}, {5.2, 5.4, 5.6, 5.8}, {7.3, 7.4, 7.6, 7.8}};
		double[][] actual = z.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Check null is returned when trying to add matrices of different sizes
		Matrix f = new Matrix(2, 2);
		Matrix result = x.plus(f);
		assertEquals(null, result);
	}
	
	/*
	 * Test minus, subtracts two matrices 
	 */
	@Test
	public void testMinus() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix x = new Matrix(data1);
		Matrix y = new Matrix(data2);
		Matrix z = x.minus(y);

		double[][] expected = {{-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1.1, -1, -1, -1}};
		double[][] actual = z.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Check null is returned when trying to subtract matrices of different sizes
		Matrix f = new Matrix(2, 2);
		Matrix result = x.minus(f);
		assertEquals(null, result);
	}

	/*
	 * Test plusEquals, adds a matrix to the class matrix
	 */
	@Test
	public void testplusEquals() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix x = new Matrix(data1);
		Matrix y = new Matrix(data2);
		Matrix z = x.plusEquals(y);
		
		double[][] expected = {{1.2, 1.4, 1.6, 1.8}, {3.2, 3.4, 3.6, 3.8}, {5.2, 5.4, 5.6, 5.8}, {7.3, 7.4, 7.6, 7.8}};
		double[][] actual = x.getArray();
		double[][] actual2 = z.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual2[i], delta);
		
		//Check null is returned when trying to add matrices of different sizes
		Matrix f = new Matrix(2, 2);
		Matrix result = x.plusEquals(f);
		assertEquals(null, result);
	}
	
	/*
	 * Test minusEquals, subtracts a matrix from the class matrix
	 */
	@Test
	public void testminusEquals() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix x = new Matrix(data1);
		Matrix y = new Matrix(data2);
		Matrix z = x.minusEquals(y);

		double[][] expected = {{-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1.1, -1, -1, -1}};
		double[][] actual = x.getArray();
		double[][] actual2 = z.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual2[i], delta);

		//Check null is returned when trying to subtract matrices of different sizes
		Matrix f = new Matrix(2, 2);
		Matrix result = x.minusEquals(f);
		assertEquals(null, result);
	}
}
