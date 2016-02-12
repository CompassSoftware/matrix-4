package Javatrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

	/*
	 * Test the basic constructor, and its exception case.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBasicConstructorAndGetArray() {
		double[][] expected = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		Matrix m = new Matrix(expected);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
		
		double[][] jagged = {{0f},{1f, 2f},{3f, 4f, 5f}};
		m = new Matrix(jagged);
	}
	
	/*
	 * Tests the quick constructor
	 */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testQuickConstructor() {
		double[][] vals = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		double[][] expected = {{0f, 1f},{3f, 4f},{6f, 7f}};
		Matrix m = new Matrix(vals, 3, 2);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
		
		m = new Matrix(vals, 3, 4);
	}
}
