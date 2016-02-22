package Javatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.junit.Test;
public class MatrixTest {
	private double delta = 1e-9;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
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
		double[][] expected1 = {{0f, 2f, 4f, 6f, 8f}, {1f, 3f, 5f, 7f, 9f}};
		Matrix m = new Matrix(vals, 2);
		double[][] actual1 = m.getArray();
		for(int i = 0; i < 2; i++) assertArrayEquals(expected1[i], actual1[i], delta);

		//And again, with different parameters
		m = new Matrix(vals, 5);
		double[][] expected2 = {{0f, 5f}, {1f, 6f}, {2f, 7f}, {3f, 8f}, {4f, 9f}};
		double[][] actual2 = m.getArray();
		for(int i = 0; i < 5; i++) assertArrayEquals(expected2[i], actual2[i], delta);
		
		//Test bad row numbers don't work
		m = new Matrix(vals, 3);
	}
	
	/*
	 * Tests the clone method. 
	 */
	@Test
	public void testClone()
	{
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		Matrix a = new Matrix(data);
		Object b = a.clone();
		Matrix a2 = (Matrix) b;
		assertTrue(Arrays.deepEquals(data, a.getArray()));
	    assertTrue(Arrays.deepEquals(data, a2.getArray()));
	    assertEquals(a.getRowDimension(), a2.getRowDimension());
	    assertEquals(a.getColumnDimension(), a2.getColumnDimension());
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
	 * Test constructWithCopy
	 */
	@Test
	public void testConstructWithCopy() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix mat = Matrix.constructWithCopy(data);
		assertTrue(Arrays.deepEquals(data, mat.getArray()));
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
	 * Test Random. 
	 */
	@Test
	public void TestRandom()
	{
		Matrix t;
		t = Matrix.random(4,4);
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				assertNotNull(t.get(i, j));
			}
		}
	}
	
	/*
	 * Test print with number format and width
	 */
	@Test
	public void testPrintNumFormat() {
		String expected = "-0  0  0  0 \r\n 1 -1  1  1 \r\n 2  2 -2  2 \r\n 3  3  3 -3 \r\n\r\n" +
			"-0.1  0.2  0.3  0.4 \r\n 1.1 -1.2  1.3  1.4 \r\n 2.1  2.2 -2.3  2.4 \r\n 3.1  3.2  3.3 -3.4 \r\n\r\n";
		PrintStream oldOut = System.out;
		System.setOut(new PrintStream(outContent));
		
		double[][] data = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
		Matrix t = new Matrix(data);
		DecimalFormat format = new DecimalFormat("#.## ");
		t.print(format, -1);
		t.print(format, 1);
		t.print(format, 3);

		System.setOut(oldOut);
		
		assertEquals(expected, outContent.toString());
	}

	/*
	 * Test print with number format, output, and width
	 */
	@Test
	public void testPrintNumFormatPrintWriter() {
		String expected = "-0  0  0  0 \r\n 1 -1  1  1 \r\n 2  2 -2  2 \r\n 3  3  3 -3 \r\n\r\n" +
			"-0.1  0.2  0.3  0.4 \r\n 1.1 -1.2  1.3  1.4 \r\n 2.1  2.2 -2.3  2.4 \r\n 3.1  3.2  3.3 -3.4 \r\n\r\n";
		PrintWriter pw = new PrintWriter(outContent, true);
		double[][] data = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
		Matrix t = new Matrix(data);
		DecimalFormat format = new DecimalFormat("#.## ");
		
		t.print(pw, format, -1);
		t.print(pw, format, 1);
		t.print(pw, format, 3);

		assertEquals(expected, outContent.toString());
	}

	/*
	 * Test print with output, W, and D parameters
	 */
	@Test
	public void testPrintWDPrintWriter() {
		String expected = "-0.10  0.20  0.30  0.40 \r\n 1.10 -1.20  1.30  1.40 \r\n" +
			" 2.10  2.20 -2.30  2.40 \r\n 3.10  3.20  3.30 -3.40 \r\n\r\n";
		PrintWriter pw = new PrintWriter(outContent, true);
		double[][] data = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
		Matrix t = new Matrix(data);
		t.print(pw, -1, 1);
		t.print(pw, 1, 2);
		
		assertEquals(expected, outContent.toString());
	}
	
	/*
	 * Test print with w and d
	 */
	@Test
	public void testPrintWD() {
		String expected = "-0.10  0.20  0.30  0.40 \r\n 1.10 -1.20  1.30  1.40 \r\n" +
			" 2.10  2.20 -2.30  2.40 \r\n 3.10  3.20  3.30 -3.40 \r\n\r\n";
		PrintStream oldOut = System.out;
		System.setOut(new PrintStream(outContent));

		double[][] data = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
		Matrix t = new Matrix(data);
		t.print(-1, 1);
		t.print(1, 2);

		System.setOut(oldOut);
		
		assertEquals(expected, outContent.toString());
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
	
	/*
	 * Test identity, creates identity matrix with mxn dimensions
	 */
	@Test
	public void testidentity() {
		double[][] data1 = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
		
		Matrix m = Matrix.identity(4, 4);
		double[][] actual = m.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(data1[i], actual[i], delta);
	}
	
	/*
	 * Tests the normInF method.
	 */
	@Test
	public void testnormInF()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.normInF(), 2.4, delta);
	}
	
	/*
	 * Tests the getColumnPackedCopy method.
	 */
	@Test
	public void testgetColumnPackedCopy()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		double [] actual = m.getColumnPackedCopy();
		double[] data2 = {.1,.4,.7,.2,.5,.8,.3,.6,.9};
		assertArrayEquals(actual, data2, delta);
	}
	
	/*
	 * Tests the uminus method.
	 */
	@Test
	public void testuminus()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		m = m.uminus();
		double[][] actual = m.getArray();
		double[][] expected = {{-.1,-.2,-.3}, {-.4,-.5,-.6}, {-.7,-.8,-.9}};
		for(int i = 0; i < 3; i++) assertArrayEquals(actual[i], expected[i], delta);
	}
	
	/*
	 * Tests the timesEquals method.
	 */
	@Test
	public void testTimesEquals()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		m = m.timesEquals(3.0);
		double[][] actual = m.getArray();
		double[][] expected = new double[3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				expected[i][j] = data[i][j] * 3.0;
			}
		}
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Tests array times
	 */
	@Test
	public void testArrayTimes()
	{
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{2.0,2.0,2.0},{2.0,2.0,2.0},{2.0,2.0,2.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		double[][] actual = m.arrayTimes(n).getArray();
		double [][] expected = {{6.0,8.0,10.0},{8.0,10.0,12.0},{10.0,12.0,14.0}};
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Tests array times equals
	 */
	@Test
	public void testArrayTimesEquals()
	{
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{2.0,2.0,2.0},{2.0,2.0,2.0},{2.0,2.0,2.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		m.arrayTimesEquals(n);
		double[][] actual = m.getArray();
		double [][] expected = {{6.0,8.0,10.0},{8.0,10.0,12.0},{10.0,12.0,14.0}};
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	

	/*
	 * Tests Transpose
	 */
	@Test
	public void testTranspose() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{0.1, 1.1, 2.1, 3.1},{0.2, 1.2, 2.2, 3.2},{0.3, 1.3, 2.3, 3.3},{0.4, 1.4, 2.4, 3.4}};
		Matrix m = new Matrix(data1);
		double[][] actual = m.transpose().getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(data2[i], actual[i], delta);
	}
	
	/*
	 * Tests getRowPackedCopy
	 */
	@Test
	public void testGetRowPackedCopy() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[] data2 = {0.1, 0.2, 0.3, 0.4, 1.1, 1.2, 1.3, 1.4, 2.1, 2.2, 2.3, 2.4, 3.1, 3.2, 3.3, 3.4}; 
		Matrix x = new Matrix(data1);
		double[] actual = x.getRowPackedCopy();
		assertArrayEquals(actual, data2, delta);
	}
	
	/*
	 * Tests arrayLeftDivide
	 */
	@Test
	public void testArrayLeftDivide() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		double[][] actual = m.arrayLeftDivide(n).getArray();
		double[][] expected = {{1d/3, 1d/4, 1d/5}, {1d/4, 1d/5, 1d/6}, {1d/5, 1d/6, 1d/7}}; 
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Tests arrayLeftDivideEquals
	 * 
	 */
	@Test
	public void testArrayLeftDivideEquals() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		m.arrayLeftDivideEquals(n);
		double[][] actual = m.getArray();
		double[][] expected = {{1d/3, 1d/4, 1d/5}, {1d/4, 1d/5, 1d/6}, {1d/5, 1d/6, 1d/7}}; 
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Tests arrayRightDivideEquals
	 * 
	 */
	@Test
	public void testArrayRightDivideEquals() {
		double[][] data1 = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		m.arrayRightDivideEquals(n);
		double[][] actual = m.getArray();
		double[][] expected = {{1d/3, 1d/4, 1d/5}, {1d/4, 1d/5, 1d/6}, {1d/5, 1d/6, 1d/7}}; 
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}

	/*
	 * Tests arrayRightDivide
	 */
	@Test
	public void testArrayRightDivide() {
		double[][] data1 = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		double[][] actual = m.arrayRightDivide(n).getArray();
		double[][] expected = {{1d/3, 1d/4, 1d/5}, {1d/4, 1d/5, 1d/6}, {1d/5, 1d/6, 1d/7}}; 
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Test getMatrix1
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMatrix1() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,2,4},{5,7,9},{15,17,19}};
		int[] r = {0,1,3};
		int[] c = {0,2,4};
		Matrix m = new Matrix(data);
		double[][] actual = m.getMatrix(r,c).getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(sub[i], actual[i], delta);
		
		//Test bounds
		c[0] = 6;
		m.getMatrix(r, c);
	}
	
	/*
	 * Test getMatrix2
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMatrix2() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,1,2},{5,6,7},{15,16,17}};
		int[] r = {0,1,3};
		int j0 = 0;
		int j1 = 2;
		Matrix m = new Matrix(data);
		double[][] actual = m.getMatrix(r,j0, j1).getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(sub[i], actual[i], delta);
		
		//Test bounds
		j0 = -1;
		m.getMatrix(r, j0, j1);
	}
	

	/*
	 * Test getMatrix3
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMatrix3() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,1,3},{5,6,8},{10,11,13}};
		int[] c = {0,1,3};
		int i0 = 0;
		int i1 = 2;
		Matrix m = new Matrix(data);
		double[][] actual = m.getMatrix(i0, i1, c).getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(sub[i], actual[i], delta);
		
		//Test bounds
		i0 = -1;
		m.getMatrix(i0, i1, c);
	}
	
	/*
	 * Test getMatrix4
	 * 
	 */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testGetMatrix4() {
		double[][] data = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] expected = {{1.0,2.0},{4.0,5.0}};
		Matrix m = new Matrix(data);
		int i0 = 0;
		int i1 = 1;
		int j0 = 0;
		int j1 = 1;
		double[][] actual = m.getMatrix(i0, i1, j0, j1).getArray();
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test bounds
		j0 = -1;
		m.getMatrix(i0, i1, j0, j1);
	}
	
    /*
	* Tests setMatrix(int[] r, int[] c, Matrix X) 
	*/
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testSetMatrix1()
	{
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] data2 = {{-1,0,-1,0,-1},{-1,0,-1,0,-1},{0,0,0,0,0},{-1,0,-1,0,-1}};
		double[][] expected = {{-1,1,-1,3,-1},{-1,6,-1,8,-1},{10,11,12,13,14},{-1,16,-1,18,-1}};
		int[] r = {0,1,3};
		int[] c = {0,2,4};
		Matrix m = new Matrix(data);
		Matrix x = new Matrix(data2);
		m.setMatrix(r,c,x);
		double[][] actual = m.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test bounds
		c[0] = 6;
		m.setMatrix(r, c, x);
	}
	
	/*
	* Tests setMatrix(int[] r, int j0, int j1, Matrix X)
	*/
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testSetMatrix2()
	{
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] data2 = {{-1,-1,-1,0,0},{-1,-1,-1,0,0},{0,0,0,0,0},{-1,-1,-1,0,0}};
		double[][] expected = {{-1,-1,-1,3,4},{-1,-1,-1,8,9},{10,11,12,13,14},{-1,-1,-1,18,19}};
		int[] r = {0,1,3};
		Matrix m = new Matrix(data);
        Matrix x = new Matrix(data2);
        m.setMatrix(r, 0, 2, x);
		double[][] actual = m.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test bounds
		m.setMatrix(r, -1, 2, x);
	}
	
	/*
	* Tests setMatrix(int i0, int i1, int[] c, Matrix X)
	*/
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testSetMatrix3()
	{
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] data2 = {{-1,-1,0,-1,0},{-1,-1,0,-1,0},{-1,-1,0,-1,0},{0,0,0,0,0}};
		double[][] expected = {{-1,-1,2,-1,4},{-1,-1,7,-1,9},{-1,-1,12,-1,14},{15,16,17,18,19}};
		int[] c = {0,1,3};
		Matrix m = new Matrix(data);
        Matrix x = new Matrix(data2);
        m.setMatrix(0, 2, c, x);
		double[][] actual = m.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test bounds
		m.setMatrix(-1, 6, c, x);
	}
	
	/*
	* Tests setMatrix(int i0, int i1, int j0, int j1, Matrix X)	
	* */
	@Test (expected=ArrayIndexOutOfBoundsException.class)
	public void testSetMatrix4()
	{
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] data2 = {{-1,-1,-1,0,0},{-1,-1,-1,0,0},{-1,-1,-1,0,0},{0,0,0,0,0}};
		double[][] expected = {{-1,-1,-1,3,4},{-1,-1,-1,8,9},{-1,-1,-1,13,14},{15,16,17,18,19}};
		Matrix m = new Matrix(data);
        Matrix x = new Matrix(data2);
        m.setMatrix(0, 2, 0, 2, x);
		double[][] actual = m.getArray();
		for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		
		//Test bounds
		m.setMatrix(-1, 6, -1, 6, x);
	}

	/*
	 * Tests times (scalar)
	 * 
	 */
	@Test
	public void testTimes1() {
		double[][] data = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		Matrix m = new Matrix(data);
		Matrix test = m.times(2.0);
		double[][] actual = test.getArray();
		double[][] expected = {{2,4,6},{8,10,12},{14,16,18}};
		for(int i = 0; i < 3; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Tests times (linear)
	 * 
	 */
	@Test
	public void testTimes2() {
		double[][] data = {{1.0,2.0,3.0},{4.0,5.0,6.0}};
		Matrix m = new Matrix(data);
		double[][] test = {{2.0,3.0},{4.0,5.0},{6.0,7.0}};
		Matrix B = new Matrix(test);
		double[][] actual = m.times(B).getArray();
		double[][] expected = {{28.0,34.0},{64.0,79.0}};
		for(int i = 0; i < 2; i++) assertArrayEquals(expected[i], actual[i], delta);
	}
	
	/*
	 * Test read
	 */
	@Test
	public void testRead() {
		String data = "-0.10  0.20  0.30  0.40 \r\n 1.10 -1.20  1.30  1.40 \r\n" +
			" 2.10  2.20 -2.30  2.40 \r\n 3.10  3.20  3.30 -3.40 \r\n\r\n";
		double[][] expected = {{-0.1,0.2,0.3,0.4},{1.1,-1.2,1.3,1.4},{2.1,2.2,-2.3,2.4},{3.1,3.2,3.3,-3.4}};
		
		BufferedReader input = new BufferedReader(new StringReader(data));
		try {
			Matrix m = Matrix.read(input);
			double[][] actual = m.getArray();
			for(int i = 0; i < 4; i++) assertArrayEquals(expected[i], actual[i], delta);
		} catch(IOException e) {fail(e.toString());}
	}
}
