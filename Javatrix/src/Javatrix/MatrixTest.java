package Javatrix;

import static org.junit.Assert.*;

import java.text.NumberFormat;
import java.text.DecimalFormat;
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
		assertEquals(m.norm1(), 2.4, 0);
	}
	
	/*
	 * Tests the normNormF method.
	 */
	@Test
	public void testNormF()
	{
		double[][] data = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
		Matrix m = new Matrix(data);
		System.out.print(m.normF());
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
	
}
