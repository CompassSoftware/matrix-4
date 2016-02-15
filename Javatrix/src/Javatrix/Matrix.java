package Javatrix;

import java.io.*;
import java.util.Random;

public class Matrix 
{
	private double[][] matrix;
	private int m;
	private int n;
	
	/**
	 * Constructor that checks arguments on basis that all rows need to be
	 * 		the same length
	 * 
	 * @param	A - 2D array from which to construct the matrix
	 */
	public Matrix(double[][] A) throws java.lang.IllegalArgumentException
	{
		this.m = A.length;
		this.n = A[0].length;
		for (int i = 1; i < m; i++) {
			if(A[i].length != n) throw new java.lang.IllegalArgumentException();
		}
		this.matrix = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = A[i][j];
			}
		}
		
	}
	
	/**
	 * Attempts to create a new Matrix object quickly, without checking input parameters.
	 * 
	 * @param A - 2D array from which to construct the matrix
	 * @param m - Number of rows
	 * @param n - Number of columns
	 */
	public Matrix(double[][] A, int m, int n)
	{
		this.m = m;
		this.n = n;
		this.matrix = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = A[i][j];
			}
		}
	}
	
	/**
	 * Matrix - Construct a matrix from a one-dimensional packed array
	 * 
	 * @param vals - The packed array
	 * @param m - Number of rows to break the array into
	 * @throws IllegalArgumentException - If the length of vals isn't a multiple of m
	 */
	public Matrix(double[] vals, int m) throws IllegalArgumentException
	{
		if(vals.length%m != 0) throw new IllegalArgumentException();
		else {
			this.m = m;
			this.n = vals.length/m;
			this.matrix = new double[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					matrix[i][j] = vals[i*n+j];
				}
			}
		}
	}
	
	/**
	 * Creates a new Matrix object of the specified size, filled with zeros.
	 * 
	 * @param m - Number of rows
	 * @param n - Number of columns
	 */
	public Matrix(int m, int n)
	{
		this.matrix = new double[m][n];
		this.m = m;
		this.n = n;
	}
	
	/**
	 * Creates a new Matrix object of the specified size, filled with the specified value.
	 * 
	 * @param m - Number of rows
	 * @param n - Number of columns
	 * @param s - Value to fill the Matrix with
	 */
	public Matrix(int m, int n, double s)
	{
		this(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = s;
			}
		}
	}
	
	/**
	 * copy - makes a deep copy of a matrix
	 */
	public Matrix copy() {
		Matrix M = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				M.matrix[i][j] = matrix[i][j];
			}
		}
		return M;
	}
	
	/**
	 * getArrayCopy - returns a copy of the internal 2D array of the matrix.
	 * 
	 * @return double[][]
	 */
	public double[][] getArrayCopy() {
		double[][] copy = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				copy[i][j] = matrix[i][j];
			}
		}
		return copy;
	}
	/**
	 * set - sets a single element of the matrix to a specific double value
	 * 
	 * @param i - row index
	 * @param j - column index
	 * @param s - double value to set the matrix element to.
	 */
	public void set(int i, int j, double s) throws ArrayIndexOutOfBoundsException 
	{
		if(i >= 0 && i < m && j >= 0 && j < n)
	    {
			matrix[i][j] = s;
	    }
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	/**
	 * random - generates a matrix with random elements
	 * 
	 * @param m - number of rows
	 * @param - n - number of columns
	 */
	public static Matrix random(int m, int n) {
		Random r = new Random();
		Matrix M = new Matrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				M.matrix[i][j] = r.nextDouble();
			}
		}
		return M;	
	}
	
	/**
	 * trace - returns the sum of the diagonal of the matrix
	 */
	public double trace() {
		double sum = 0;
		for (int i = 0; i < m && i < n; i++) {
			sum += matrix[i][i];
		}
		return sum;
	}
	
	/**
	 * getArray - Accesses the internal two-dimensional array.
	 * 
	 * @return double[][]
	 */
	public double[][] getArray()
	{
		return matrix;
	}
	
	/**
	 * getColumnDimension - Gets the column dimension.
	 * 
	 * @return int 
	 */
	public int getColumnDimension()
	{
		return n;
	}
	
	/**
	 * getRowDimension - Gets the row dimension.
	 * 
	 * @return int 
	 */
	public int getRowDimension()
	{
		return m;
	}
	
	/**
	 * get - returns a specific element from the matrix
	 * 
	 * @param i - row index
	 * @param j - column index
	 */
	public double get(int i, int j) throws ArrayIndexOutOfBoundsException 
	{
		if(i >= 0 && i < m && j >= 0 && j < n) 
		{
			return matrix[i][j];
		}
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	/**
	 * print - Print the matrix to stdout.
	 * 
	 * @param format - java.text.NumberFormat
	 * @param width - represents how many columns to print. 
	 */
	public void print(java.text.NumberFormat format, int width)
	{
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < width; j++) 
			{
				System.out.print(format.format(matrix[i][j]));
			}
			System.out.println();
		}
	}
	
	/**
	 * print - Print the matrix to stdout.
	 * 
	 * @param format - java.text.NumberFormat
	 * @param width - represents how many columns to print. 
	 */
	public void print(java.io.PrintWriter output, java.text.NumberFormat format, int width)
	{
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < width; j++) 
			{
				output.print(format.format(matrix[i][j]));
			}
			output.println();
		}
	}
	
	/**
	 * print - Print the matrix to the output stream. 
	 * 
	 * @param output
	 * @param w
	 * @param d
	 */
	public void print(java.io.PrintWriter output, int w, int d)
	{
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				output.print(String.format("%.df", (matrix[i][j])));
			}
			output.println();
		}
	}
	
	
	
}
