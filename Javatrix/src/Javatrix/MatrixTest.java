package Javatrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

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
}
