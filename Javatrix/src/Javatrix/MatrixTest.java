package Javatrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void testConstructorAndGetArray() {
		double[][] expected = {{0f, 1f, 2f},{3f, 4f, 5f},{6f, 7f, 8f}};
		Matrix m = new Matrix(expected);
		double[][] actual = m.getArray();
		assertArrayEquals(expected, actual);
	}
}
