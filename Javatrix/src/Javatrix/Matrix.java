package Javatrix;
import java.io.*;
import java.util.Random;
public class Matrix 
{
	private double[][] matrix;
	private int m;
	private int n;
	private static Random r = new Random();
	
	/*
	 * Constructor that checks arguments on basis that all rows need to be
	 * 		the same length
	 * 
	 * @param	A - 2D array from which to construct the matrix
	 */
	public Matrix(double[][] A) throws java.lang.IllegalArgumentException
	{
		this.m = A.length;
		this.n = A[0].length;
		this.matrix = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = A[i][j];
			}
		}
		
	}
	// Not sure about this one
	public Matrix(double[][] A, int m, int n)
	{
		this.m = m;
		this.n =  n;
		this.matrix = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = A[i][j];
			}
		}
	}
	
	public Matrix(double[] vals, int m)
	{
		
	}
	
	public Matrix(int m, int n)
	{
		this.matrix = new double[m][n];
	}
	
	public Matrix(int m, int n, double s)
	{
		this.matrix = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = s;
			}
		}
	}
	/*
	 * copy - makes a deep copy of a matrix
	 */
	public Matrix copy() {
		int row = matrix.length;
		int col = matrix[0].length;
		Matrix M = new Matrix(row, col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				M.matrix[i][j] = matrix[i][j];
			}
		}
		return M;
	}
	/*
	 * get - returns a specific element from the matrix
	 * 
	 * @param i - row index
	 * @param j - column index
	 */
	public double get(int i, int j) {
		return matrix[i][j];
	}
	/*
	 * set - sets a single element of the matrix to a specific double value
	 * 
	 * @param i - row index
	 * @param j - column index
	 * @param s - double value to set the matrix element to.
	 */
	public void set(int i, int j, double s) throws java.lang.ArrayIndexOutOfBoundsException {
		matrix[i][j] = s;
	}
	/*
	 * random - generates a matrix with random elements
	 * 
	 * @param m - number of rows
	 * @param - n - number of columns
	 */
	public static Matrix random(int m, int n) {
		Matrix M = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				M.matrix[i][j] = r.nextInt();
			}
		}
		return M;	
	}
	/*
	 * trace - returns the sum of the diagonal of the matrix
	 * 
	 * !!!!Not sure about this one
	 */
	public double trace() {
		double sum = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i; j < matrix[0].length; j+=i) {
				sum += matrix[i][j];
			}
			
		}
		return sum;
	}
}
