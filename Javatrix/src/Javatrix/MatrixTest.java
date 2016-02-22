package Javatrix;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.PrintStream;

import org.junit.Test;
public class MatrixTest {
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
		assertArrayEquals(expected, actual);
		
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
		int m = 4;
		int n = 4;
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix mat = new Matrix(data, m, n);
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
	}
	
	/*
	 * Test getArrayCopy, returns a copy of the internal 2D array of the matrix
	 */
	@Test
	public void testgetArrayCopy() {
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix m = new Matrix(data);
		Matrix t = new Matrix(m.getArrayCopy());
		double[][] expected = m.getArray();
		double[][] actual = t.getArray();
		assertArrayEquals(expected, actual);
		
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
			actual += t.getArray()[i][i];
		}
		
		int x = (int)expected;
		int y = (int)actual;
		assertEquals(x,y);
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
	 * Test print with number format
	 */
	@Test
	public void testPrintNumFormat() {
		System.setOut(new PrintStream(outContent));
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix t = new Matrix(data);
		DecimalFormat format = new java.text.DecimalFormat("#.## ");
		t.print(format, 4);
		assertEquals(outContent.toString().substring(0, 16),
		"0.1 0.2 0.3 0.4 ");
		
	}
	
	/*
	 * Test print with number format with format and width
	 */
	@Test
	public void testPrintNumFormatWD() {
		System.setOut(new PrintStream(outContent));
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix t = new Matrix(data);
		t.print(new PrintWriter(System.out, true), new java.text.DecimalFormat("#.## "), 4);
		assertEquals(outContent.toString().substring(0, 16),
		"0.1 0.2 0.3 0.4 ");
		
	}
	
	/*
	 * Test print with number format with W and D parameters
	 */
	@Test
	public void testPrintNumFormatW() {
		System.setOut(new PrintStream(outContent));
		double[][] data = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		Matrix t = new Matrix(data);
		t.print(new PrintWriter(System.out, true), 4, 1);
		assertEquals(outContent.toString().substring(0, 16),
		"0.1 0.2 0.3 0.4 ");
		
	}
	
	
	/*
	 * Tests the get method.
	 */
	@Test
	public void testGet()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.get(1, 0), .4, 0);
		assertEquals(m.get(2, 0), .7, 0);
		
	}
	
	/*
	 * Tests the set method.
	 */
	@Test
	public void testSet()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		m.set(1, 1, 5);
		m.set(2, 0, .55);
		assertEquals(m.get(1, 1), 5, 0);
		assertEquals(m.get(2, 0), .55, 0);
		
	}
	
	/*
	 * Tests the get array method. 
	 */
	@Test
	public void testGetArray()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertArrayEquals(data, m.getArray());
	}
	
	/*
	 * Tests the norm1 method.
	 */
	@Test
	public void testNorm1()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.norm1(), 1.7999999999999998, 0);
	}
	
	/*
	 * Tests the normNormF method.
	 */
	@Test
	public void testNormF()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.normF(), 1.688, .001);
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
		int m = x.getRowDimension();
		int n = x.getColumnDimension();
		Matrix a = new Matrix(new double[m][n]);
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				a.getArray()[i][j] = x.getArray()[i][j] + y.getArray()[i][j];
			}
		}
		assertArrayEquals(z.getArray(), a.getArray());
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
		int m = x.getRowDimension();
		int n = x.getColumnDimension();
		Matrix a = new Matrix(new double[m][n]);
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				a.getArray()[i][j] = x.getArray()[i][j] - y.getArray()[i][j];
			}
		}
		assertArrayEquals(z.getArray(), a.getArray());
	}

	/*
	 * Test plusEquals, adds a matrix to the class matrix
	 */
	@Test
	public void testplusEquals() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix expected = new Matrix(data1);
		Matrix y = new Matrix(data2);
		expected.plusEquals(y);
		Matrix actual = new Matrix(data1);
		int m = expected.getRowDimension();
		int n = expected.getColumnDimension();
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				actual.getArray()[i][j] = actual.getArray()[i][j] + y.getArray()[i][j];
			}
		}
		assertArrayEquals(expected.getArray(), actual.getArray());	
	}
	
	/*
	 * Test minusEquals, subtracts a matrix from the class matrix
	 */
	@Test
	public void testminusEquals() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4},{4.2,4.2,4.3,4.4}};
		Matrix expected = new Matrix(data1);
		Matrix y = new Matrix(data2);
		expected.minusEquals(y);
		Matrix actual = new Matrix(data1);
		int m = expected.getRowDimension();
		int n = expected.getColumnDimension();
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				actual.getArray()[i][j] = actual.getArray()[i][j] - y.getArray()[i][j];
			}
		}
		assertArrayEquals(expected.getArray(), actual.getArray());	
	}
	
	/*
	 * Test identity, creates identity matrix with mxn dimensions
	 */
	@Test
	public void testidentity() {
		double[][] data1 = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
		
		Matrix expected = new Matrix(data1);
		Matrix actual = Matrix.identity(4, 4);
		assertArrayEquals(expected.getArray(), actual.getArray());	
	}
	
	/*
	 * Tests the normInF method.
	 */
	@Test
	public void testnormInF()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		assertEquals(m.normInF(), 2.4, 0);
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
		assertArrayEquals(actual, data2, 0);
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
		assertArrayEquals(actual, expected);
	}
	
	/*
	 * Tests the times equals method.
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
		assertArrayEquals(actual, expected);
	}
	
	/*
	 * Tests array times
	 */
	@Test
	public void testArrayTimes()
	{
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		Matrix test = m.arrayTimes(n);
		double [][] expected = new double[3][3];
		for (int i = 0; i < n.getRowDimension(); i++) {
			for (int j  = 0; j < n.getColumnDimension(); j++) {
				expected[i][j] = m.getArray()[i][j] * n.getArray()[i][j];
			}
		}
		assertArrayEquals(test.getArray(), expected);
	}
	
	
	/*
	 * Tests array times
	 */
	@Test
	public void testArrayTimesEquals()
	{
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		double [][] expected = new double[3][3];
		for (int i = 0; i < n.getRowDimension(); i++) {
			for (int j  = 0; j < n.getColumnDimension(); j++) {
				expected[i][j] = m.getArray()[i][j] * n.getArray()[i][j];
			}
		}
		assertArrayEquals(m.getArray(), expected);
	}
	

	/*
	 * Tests Transpose
	 */
	@Test
	public void testTranspose() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[][] data2 = {{0.1, 1.1, 2.1, 3.1},{0.2, 1.2, 2.2, 3.2},{0.3, 1.3, 2.3, 3.3},{0.4, 1.4, 2.4, 3.4}};
		Matrix x = new Matrix(data1);
		Matrix y = x.transpose();
		assertArrayEquals(new Matrix(data2).getArray(), y.getArray());
	}
	
	/*
	 * Tests getRowPackedCopy
	 */
	@Test
	public void testGetRowPackedCopy() {
		double[][] data1 = {{0.1,0.2,0.3,0.4},{1.1,1.2,1.3,1.4},{2.1,2.2,2.3,2.4},{3.1,3.2,3.3,3.4}};
		double[] data2 = {0.1, 0.2, 0.3, 0.4, 1.1, 1.2, 1.3, 1.4, 2.1, 2.2, 2.3, 2.4, 3.1, 3.2, 3.3, 3.4}; 
		Matrix x = new Matrix(data1);
		assertArrayEquals(x.getRowPackedCopy(), data2, 0);
	}
	
	/*
	 * Tests arrayLeftDivide
	 * 
	 */
	@Test
	public void testArrayLeftDivide() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		Matrix test = m.arrayLeftDivide(n);
		double [][] expected = new double[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				expected[i][j] = m.getArray()[i][j] / n.getArray()[i][j];
				
			}
		}
		assertArrayEquals(test.getArray(), expected);
	}
	
	/*
	 * Tests arrayLeftDivide
	 * 
	 */
	@Test
	public void testArrayLeftDivideEquals() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		Matrix test = m.arrayLeftDivideEquals(n);
		double [][] expected = new double[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				expected[i][j] = m.getArray()[i][j] / n.getArray()[i][j];
				
			}
		}
		assertArrayEquals(test.getArray(), expected);
	}
	
	/*
	 * Tests arrayLeftDivide
	 * 
	 */
	@Test
	public void testArrayRightDivideEquals() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		Matrix test = m.arrayRightDivideEquals(n);
		double [][] expected = new double[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				expected[i][j] = m.getArray()[i][j] / n.getArray()[i][j];
				
			}
		}
		assertArrayEquals(test.getArray(), expected);
	}
	/*
	 * Tests arrayLeftDivide
	 * 
	 */
	@Test
	public void testArrayRightDivide() {
		double[][] data = {{3.0,4.0,5.0},{4.0,5.0,6.0},{5.0,6.0,7.0}};
		double[][] data1 = {{1.0,1.0,1.0},{1.0,1.0,1.0},{1.0,1.0,1.0}};
		Matrix m = new Matrix(data);
		Matrix n = new Matrix(data1);
		Matrix test = m.arrayRightDivide(n);
		double [][] expected = new double[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				expected[i][j] = m.getArray()[i][j] / n.getArray()[i][j];
				
			}
		}
		assertArrayEquals(test.getArray(), expected);
	}
	
	/*
	 * Test getMatrix1
	 */
	@Test
	public void testGetMatrix1() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,2,4},{5,7,9},{15,17,19}};
		int[] r = {0,1,3};
		int[] c = {0,2,4};
		Matrix m = new Matrix(data);
		Matrix expected = m.getMatrix(r,c);
		assertArrayEquals(sub, expected.getArray());
	}
	
	/*
	 * Test getMatrix2
	 */
	@Test
	public void testGetMatrix2() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,1,2},{5,6,7},{15,16,17}};
		int[] r = {0,1,3};
		int j0 = 0;
		int j1 = 2;
		Matrix m = new Matrix(data);
		Matrix expected = m.getMatrix(r,j0,j1);
		assertArrayEquals(sub, expected.getArray());
	}
	

	/*
	 * Test getMatrix3
	 */
	@Test
	public void testGetMatrix3() {
		double[][] data = {{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19}};
		double[][] sub = {{0,1,3},{5,6,8},{10,11,13}};
		int[] c = {0,1,3};
		int i0 = 0;
		int i1 = 2;
		Matrix m = new Matrix(data);
		Matrix expected = m.getMatrix(i0,i1,c);
		assertArrayEquals(sub, expected.getArray());
	}
	
	/*
	 * Test getMatrix4
	 * 
	 */
	@Test
	public void testGetMatrix4() {
		double[][] data = new double[2][2];
		Matrix m = new Matrix(data);
		double[][] test = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] expected = {{1.0,2.0},{4.0,5.0}};
		Matrix k = new Matrix(test);
		int i0 = 0;
		int i1 = 1;
		int j0 = 0;
		int j1 = 1;
		m = k.getMatrix(i0,i1,j0,j1);
		assertArrayEquals(expected, m.getArray());
	}
	
    /*
	* Tests setMatrix(int[] r, int[] c, Matrix X) 
	*/
	@Test
	public void testSetMatrix1()
	{
		double[][] test = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] data1 = {{1.0,2.0}, {4.0,5.0}};
		Matrix a = new Matrix(test);
        Matrix b = new Matrix(2,2);
        a.setMatrix(new int[]{0,1}, new int[]{1,0}, b);
        assertArrayEquals(b.getArray(), data1);
	}
	
	/*
	* Tests setMatrix(int[] r, int j0, int j1, Matrix X)
	*/
	@Test
	public void testSetMatrix2()
	{
		double[][] test = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] data1 = {{0.0,2.0}, {0.0,5.0}};
		Matrix a = new Matrix(test);
        Matrix b = new Matrix(2,2);
        a.setMatrix(new int[]{0,1}, 1, 1, b);
        assertArrayEquals(b.getArray(), data1);
	}
	
	/*
	* Tests setMatrix(int i0, int i1, int[] c, Matrix X)
	*/
	@Test
	public void testSetMatrix3()
	{
		double[][] test = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] data1 = {{0.0,0.0}, {4.0,5.0}};
		Matrix a = new Matrix(test);
        Matrix b = new Matrix(2,2);
        a.setMatrix(1, 1, new int[]{0,1}, b);
        assertArrayEquals(b.getArray(), data1);
	}
	
	/*
	* Tests setMatrix(int i0, int i1, int j0, int j1, Matrix X)	
	* */
	@Test
	public void testSetMatrix4()
	{
		double[][] test = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		double[][] data1 = {{0.0,0.0}, {0.0,5.0}};
		Matrix a = new Matrix(test);
        Matrix b = new Matrix(2,2);
        a.setMatrix(1, 1, 1, 1, b);
        assertArrayEquals(b.getArray(), data1);
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
		double[][] actual = new double[m.getRowDimension()][m.getColumnDimension()];
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension(); j++) {
				actual[i][j] = data[i][j]*2.0;
			}
		}
		assertArrayEquals(test.getArray(), actual);
	}
	
	/*
	 * Tests times (linear)
	 * 
	 */
	@Test
	public void testTimes2() {
		double[][] data = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		Matrix m = new Matrix(data);
		double[][] test = {{2.0,3.0,4.0},{5.0,6.0,7.0},{8.0,9.0,10.0}};
		Matrix B = new Matrix(test);
		Matrix expected = m.times(B);
		double[][] prod = new double[m.getRowDimension()][B.getColumnDimension()];
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < B.getColumnDimension(); j++) {
				for (int k = 0; k < m.getColumnDimension(); k++) {
					prod[i][j] += m.getArray()[i][k] * B.getArray()[k][j];
				}
			}
		}
		assertArrayEquals(prod, expected.getArray());
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
		double[][] actual = new double[m.getRowDimension()][m.getColumnDimension()];
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension(); j++) {
				actual[i][j] = data[i][j]*2.0;
			}
		}
		assertArrayEquals(test.getArray(), actual);
	}
	
	/*
	 * Tests times (linear)
	 * 
	 */
	@Test
	public void testTimes2() {
		double[][] data = {{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
		Matrix m = new Matrix(data);
		double[][] test = {{2.0,3.0,4.0},{5.0,6.0,7.0},{8.0,9.0,10.0}};
		Matrix B = new Matrix(test);
		Matrix expected = m.times(B);
		double[][] prod = new double[m.getRowDimension()][B.getColumnDimension()];
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < B.getColumnDimension(); j++) {
				for (int k = 0; k < m.getColumnDimension(); k++) {
					prod[i][j] += m.getArray()[i][k] * B.getArray()[k][j];
				}
			}
		}
		assertArrayEquals(prod, expected.getArray());
	}
	
}
