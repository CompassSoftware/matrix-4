package Javatrix;

import java.text.NumberFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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
	public Matrix(double[][] A) throws IllegalArgumentException
	{
		this.m = A.length;
		this.n = A[0].length;
		for (int i = 1; i < m; i++) {
			if(A[i].length != n) throw new IllegalArgumentException();
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
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					matrix[j][i] = vals[i*m+j];
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
	 * constructWithCopy - constructs matrix copy from 2D array
	 * 
	 * @return Matrix copy
	 */
	public static Matrix constructWithCopy(double[][] arr) throws IllegalArgumentException {
		int row = arr.length;
		int col = arr[0].length;
		for (int i = 1; i < row; i++) {
			if(arr[i].length != col) throw new IllegalArgumentException();
		}
		double[][] cp = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j  = 0; j < col; j++) {
				cp[i][j] = arr[i][j];
			}
		}
		return new Matrix(cp);
	}
	
	/**
	 * copy - makes a deep copy of a matrix
	 * 
	 * @return Matrix - deep copy of Matrix object
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
	 * clone - returns a clone of the matrix object
	 * 
	 * @return Object (clone of matrix)
	 */
	public Object clone() {
		return this.copy();
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
				M.matrix[i][j] = r.nextDouble() * 10;
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
	 * @param format - The NumberFormat object to use
	 * @param width - represents the field width
	 */
	public void print(NumberFormat format, int width)
	{
		print(new PrintWriter(System.out, true), format, width);
	}
	
	/**
	 * print - Print the matrix to output.
	 * 
	 * @param output - The PrintWriter to write to.
	 * @param format - The NumberFormat object to use.
	 * @param width - The column width. 
	 */
	public void print(java.io.PrintWriter output, java.text.NumberFormat format, int width)
	{
		if(width <= 0) return;
		
		DecimalFormat df = (DecimalFormat)format;
		if(width == 1) {
			df.setMaximumFractionDigits(0);
			df.setMaximumIntegerDigits(1);
		} else if(width == 2) {
			df.setMaximumFractionDigits(0);
			df.setMaximumIntegerDigits(2);
		} else if(width == 3) {
			df.setMaximumFractionDigits(1);
			df.setMaximumIntegerDigits(1);
		} else if(width >= 4) {
			int fractDigits = df.getMaximumFractionDigits();
			int intDigits = df.getMaximumIntegerDigits();
			if(fractDigits + intDigits > width-1) {
				df.setMaximumFractionDigits(width/2);
				df.setMaximumIntegerDigits(width/2);
			}
		}
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				if(matrix[i][j] < 0)
					output.format("%" + (width+1) + "s", format.format(matrix[i][j]));
				else
					output.format(" %" + width + "s", format.format(matrix[i][j]));
			}
			output.println();
		}
		output.println();
	}
	
	/**
	 * print - Print the matrix to the output stream. 
	 * 
	 * @param output - The output stream.
	 * @param w - Column width.
	 * @param d - Number of digits after the decimal.
	 */
	public void print(java.io.PrintWriter output, int w, int d)
	{
		if(w <= 0 || d < 0) return;
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				if(matrix[i][j] < 0)
					output.print(String.format("%" + (w+1) + "." + d + "f ", (matrix[i][j])));
				else
					output.print(String.format(" %" + w + "." + d + "f ", (matrix[i][j])));
			}
			output.println();
		}
		output.println();
	}
	
	/**
	 * print - Print the matrix to stdout, with width w and d decimal places.
	 * 
	 * @param w - column width
	 * @param d - Number of digits after the decimal.
	 */
	public void print(int w, int d) {
		print(new PrintWriter(System.out, true), w, d);
	}
	
	/**
	 * plus - Return the sum of the matrix and parameter matrix.
	 * @param B - Matrix to be added to current matrix. Must be same dimension.
	 * @return A + B
	 */
	public Matrix plus(Matrix B)
	{
		if(B.m == this.m && B.n == this.n)
		{
			Matrix C = new Matrix(new double[m][n]);
			for(int i = 0; i < m; i++)
			{
				for(int j = 0; j < n; j++)
				{
					C.set(i, j, matrix[i][j] + B.get(i, j));
				}
			}
			return C;
		}
		else
		{
			System.out.println("Parameter matrix must be same dimension.");
			return null;
		}
		
	}
	
	/**
	 * minus - Return the difference of the matrix and parameter matrix.
	 * @param B - Matrix to be subtracted from the current matrix. Must be same dimension.
	 * @return A - B
	 */
	public Matrix minus(Matrix B)
	{
		if(B.m == this.m && B.n == this.n)
		{
			Matrix C = new Matrix(new double[m][n]);
			for(int i = 0; i < m; i++)
			{
				for(int j = 0; j < n; j++)
				{
					C.set(i, j, matrix[i][j] - B.get(i, j));
				}
			}
			return C;
		}
		else
		{
			System.out.println("Parameter matrix must be same dimension.");
			return null;
		}
		
	}
	
	/**
	 * minusEquals - Return the difference of the matrix and parameter matrix.
	 * 				-Changes class matrix to returned value. 
	 * @param B - Matrix to be subtracted from the current matrix. Must be same dimension.
	 * @return A - B
	 */
	public Matrix minusEquals(Matrix B)
	{
		if(B.m == this.m && B.n == this.n)
		{
			for(int i = 0; i < m; i++)
			{
				for(int j = 0; j < n; j++)
				{
					matrix[i][j] -= B.get(i, j);
				}
			}
			return this;
		}
		else
		{
			System.out.println("Parameter matrix must be same dimension.");
			return null;
		}
		
	}
	
	/**
	 * plusEquals - Return the addition of the matrix and parameter matrix.
	 * 				-Changes class matrix to returned value. 
	 * @param B - Matrix to be added to the class Matrix. Must be same dimension.
	 * @return A + B
	 */
	public Matrix plusEquals(Matrix B)
	{
		
		if(B.m == this.m && B.n == this.n)
		{
			for(int i = 0; i < m; i++)
			{
				for(int j = 0; j < n; j++)
				{
					matrix[i][j] += B.get(i, j);
				}
			}
			return this;
		}
		else
		{
			System.out.println("Parameter matrix must be same dimension.");
			return null;
		}
		
	}
	
	/**
	 * identity-returns an mxn matrix with ones on the diagonal and zeroes elsewhere.
	 * @param m -number of rows
	 * @param n -number of columns
	 */
	public static Matrix identity(int m, int n)
	{
		double[][] data = new double[m][n];
		for(int i = 0; i < m && i < n; i++)
		{
			data[i][i] = 1;
		}
		return new Matrix(data);
		
	}
	
	/**
	 * timesEquals-Multiply a matrix by a scalar in place, A = s*A.
	 * @param s - scalar
	 * @return Scaled matrix, s*A.
	 */
	public Matrix timesEquals(double s)
	{
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				matrix[i][j] *= s;
			}
		}
		return this;
	}
	
	/**
	 * arrayTimes - multiplies the class matrix, and another matrix, returns the product matrix
	 * 
	 * @param Matrix B
	 * @return Product Matrix
	 */
	public Matrix arrayTimes(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		Matrix C = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C.set(i, j, matrix[i][j] * B.get(i, j));
			}
		}
		return C;
	}
	
	/**
	 * arrayTimes - multiplies the class matrix, and another matrix, returns the product matrix
	 * 
	 * @param Matrix B
	 * @return Product Matrix
	 */
	public Matrix arrayTimesEquals(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] *= B.get(i, j);
			}
		}
		return this;
	}
	
	/**
	 * times - Scalar
	 * 
	 * @param double s - the scalar to multiply the matrix by
	 * @return Matrix (product)
	 */
	public Matrix times(double s) {
		Matrix prod = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				prod.set(i, j, matrix[i][j]*s);
			}
		}
		return prod;
	}
	
	/**
	 * times - linear algebraic
	 * 
	 * @param Matrix B
	 * @return Matrix product
	 */
	public Matrix times(Matrix B) throws IllegalArgumentException {
		if(B.getRowDimension() != n) throw new IllegalArgumentException();

		Matrix prod = new Matrix(m, B.getColumnDimension());
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < B.getColumnDimension(); j++) {
				for (int k = 0; k < n; k++) {
					prod.set(i, j, matrix[i][k] * B.get(k, j) + prod.get(i, j));
				}
			}
		}
		return prod;
	}
	
	/**
	 * normF-returns Frobenius norm of a matrix.
	 * @return sqrt of sum of squares of all elements.
	 */
	public double normF()
	{
		double sum = 0;
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				sum += matrix[i][j]*matrix[i][j];
			}
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * normInF-returns infinity norm of a matrix.
	 * @return largest sum of absolute values from each row.
	 */
	public double normInF()
	{
		double sum = 0;
		double temp;
		for(int i = 0; i < m; i++)
		{
			temp = 0;
			for(int j = 0; j < n; j++)
			{
				temp += Math.abs(matrix[i][j]);
			}
			if(temp > sum)
			{
				sum = temp;
			}
		}
		return sum;
	}
	
	/**
	 * norm1-returns one norm of a matrix.
	 * @return largest sum of absolute values from each column.
	 */
	public double norm1()
	{
		double sum = 0;
		double temp;
		for(int i = 0; i < n; i++)
		{
			temp = 0;
			for(int j = 0; j < m; j++)
			{
				temp += Math.abs(matrix[j][i]);
			}
			if(temp > sum)
			{
				sum = temp;
			}
		}
		return sum;
	}
	
	/**
	 * getColumnPackedCopy - returns one dimensional column packed copy of internal array
	 * 
	 * @return double[] column packed array
	 */
	public double[] getColumnPackedCopy() {
		double[] copy = new double[m*n];
		for (int i = 0; i < getColumnDimension(); i++) {
			for (int j = 0; j < getRowDimension(); j++) {
				copy[i * m + j] = matrix[j][i];
			}
		}
		return copy;
	}
	
	/**
	 * getRowPackedCopy - returns one dimensional row packed copy of internal array
	 * 
	 * @return double[] row packed array
	 */
	public double[] getRowPackedCopy() {
		double[] copy = new double[m*n];
		for (int i = 0; i < getRowDimension(); i++) {
			for (int j = 0; j < getColumnDimension(); j++) {
				copy[i * n + j] = matrix[i][j]; 
			}
		}
		return copy;
	}
	
	/**
	 * uminus - performs a unary minus operation on a matrix
	 * 
	 * @return -A
	 */
	public Matrix uminus() {
		double[][] A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = -(matrix[i][j]);
			}
		}
		return new Matrix(A);
	}
	
	/**
	 * transpose - performs matrix transpose
	 * 
	 * @return A'
	 */
	public Matrix transpose() {
		double[][] A = new double[n][m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[j][i] = matrix[i][j];
			}
		}
		return new Matrix(A);
	}
	
	/**
	 * arrayLeftDivide - element by element left division (C = A.\B)
	 * 
	 * @return A.\B
	 */
	public Matrix arrayLeftDivide(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		Matrix C = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C.set(i, j, B.get(i, j) / matrix[i][j]);
			}
		}
		return C;
	}
	
	/**
	 * arrayLeftDivideEquals - element by element left division in place (A = A.\B)
	 * 
	 * @return A.\B
	 */
	public Matrix arrayLeftDivideEquals(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = B.get(i, j) / matrix[i][j];
			}
		}
		return this;
	}
	
	/**
	 * arrayRightDivide - element by element right division (C = A./B)
	 * 
	 * @return A./B
	 */
	public Matrix arrayRightDivide(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		Matrix C = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C.set(i, j, matrix[i][j] / B.get(i, j));
			}
		}
		return C;
	}
	
	/**
	 * arrayRightDivideEquals - element by element right division in place (A = A./B)
	 * 
	 * @return A./B
	 */
	public Matrix arrayRightDivideEquals(Matrix B) {
		if(m != B.getRowDimension() || n != B.getColumnDimension()) return null;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] /= B.get(i, j);
			}
		}
		return this;
	}
	
	/**
	 * getMatrix - returns a submatrix
	 * 
	 * @return Matrix (submatrix)
	 */
	public Matrix getMatrix(int[] r, int[] c) throws ArrayIndexOutOfBoundsException {
		for(int i = 0; i < r.length; i++)
			if(r[i] < 0 || r[i] >= m) throw new ArrayIndexOutOfBoundsException();
		for(int i = 0; i < c.length; i++)
			if(c[i] < 0 || c[i] >= n) throw new ArrayIndexOutOfBoundsException();

		double[][] sub = new double[r.length][c.length];
		for (int i = 0; i < r.length; i++) {
			for (int j = 0; j < c.length; j++) {
				sub[i][j] = matrix[r[i]][c[j]];
			}
		}
		return new Matrix(sub);
	}
	
	/**
	 * getMatrix - returns a submatrix
	 * 
	 * @return Matrix (submatrix)
	 */
	public Matrix getMatrix(int[] r, int j0, int j1) {
		for(int i = 0; i < r.length; i++)
			if(r[i] < 0 || r[i] >= m) throw new ArrayIndexOutOfBoundsException();
		if(j0 < 0 || j1 >= n) throw new ArrayIndexOutOfBoundsException();

		double[][] sub = new double[r.length][j1-j0+1];
		for (int i = 0; i < r.length; i++) {
			for (int j = j0; j <= j1; j++) {
				sub[i][j] = matrix[r[i]][j];
			}
		}
		return new Matrix(sub);
	}
	
	/**
	 * getMatrix - returns a submatrix
	 * 
	 * @return Matrix (submatrix)
	 */
	public Matrix getMatrix(int i0, int i1, int[] c) {
		for(int i = 0; i < c.length; i++)
			if(c[i] < 0 || c[i] >= n) throw new ArrayIndexOutOfBoundsException();
		if(i0 < 0 || i1 >= m) throw new ArrayIndexOutOfBoundsException();

		double[][] sub = new double[i1-i0+1][c.length];
		for (int i = i0; i <= i1; i++) {
			for (int j = 0; j < c.length; j++) {
				sub[i][j] = matrix[i][c[j]];
			}
		}
		return new Matrix(sub);
	}
	
	/**
	 * getMatrix - returns a submatrix
	 * 
	 * @return Matrix (submatrix)
	 */
	public Matrix getMatrix(int i0, int i1, int j0, int j1) {
		if(i0 < 0 || i1 >= m) throw new ArrayIndexOutOfBoundsException();
		if(j0 < 0 || j1 >= n) throw new ArrayIndexOutOfBoundsException();

		double[][] sub = new double[i1-i0+1][j1-j0+1];
		for (int i = i0; i <= i1; i++) {
			for (int j = j0; j <= j1; j++) {
				sub[i][j] = matrix[i][j];
			}
		}
		return new Matrix(sub);
	}
	
	/**
	 * setMatrix - sets a submatrix
	 */
	public void setMatrix(int[] r, int[] c, Matrix X) {
		for(int i = 0; i < r.length; i++)
			if(r[i] < 0 || r[i] >= m) throw new ArrayIndexOutOfBoundsException();
		for(int i = 0; i < c.length; i++)
			if(c[i] < 0 || c[i] >= n) throw new ArrayIndexOutOfBoundsException();

		for (int i = 0; i < r.length; i++) {
			for (int j = 0; j < c.length; j++) {
				matrix[r[i]][c[j]] = X.get(r[i], c[j]);
			}
		}
	}
	
	/**
	 * setMatrix - sets a submatrix
	 */
	public void setMatrix(int[] r, int j0, int j1, Matrix X) {
		for(int i = 0; i < r.length; i++)
			if(r[i] < 0 || r[i] >= m) throw new ArrayIndexOutOfBoundsException();
		if(j0 < 0 || j1 >= n) throw new ArrayIndexOutOfBoundsException();

		for (int i = 0; i < r.length; i++) {
			for (int j = j0; j <= j1; j++) {
				matrix[r[i]][j] = X.get(r[i], j);
			}
		}
	}
	
	/**
	 * setMatrix - sets a submatrix
	 */
	public void setMatrix(int i0, int i1, int[] c, Matrix X) {
		for(int i = 0; i < c.length; i++)
			if(c[i] < 0 || c[i] >= n) throw new ArrayIndexOutOfBoundsException();
		if(i0 < 0 || i1 >= m) throw new ArrayIndexOutOfBoundsException();

		for (int i = i0; i <= i1; i++) {
			for (int j = 0; j < c.length; j++) {
				matrix[i][c[j]] = X.get(i, c[j]);
			}
		}
	}
	
	/**
	 * setMatrix - sets a submatrix
	 */
	public void setMatrix(int i0, int i1, int j0, int j1, Matrix X) {
		if(i0 < 0 || i1 >= m) throw new ArrayIndexOutOfBoundsException();
		if(j0 < 0 || j1 >= n) throw new ArrayIndexOutOfBoundsException();

		for (int i = i0; i <= i1; i++) {
			for (int j = j0; j <= j1; j++) {
				matrix[i][j] = X.get(i, j);
			}
		}
	}
	
	/**
	 * read - Reads in a matrix from file
	 * 
	 * @param input - stream to be read from. Contains matrix rows on each line.
	 */
	public static Matrix read(BufferedReader input) throws IOException {
		int cols = 0, rows = 1;
		Matrix m = null;
		String data = "";

		//Determine number of columns
		String line = input.readLine();
		String[] split = line.split(" ");
		for(int i = 0; i < split.length; i++) {
			if(split[i].equals("")) continue;
			cols++;
		}
		data += line + "\n";
		
		//Determine number of rows
		while((line = input.readLine()) != null) {
			data += line + "\n";
			if(line.equals("")) break;
			rows++;
		}
		
		m = new Matrix(rows, cols);
		
		split = data.split("\n");
		if(split.length > rows) return null;
		for(int i = 0; i < split.length; i++) {
			String[] nums = split[i].split(" ");
			int k = 0;
			for(int j = 0; j < nums.length; j++) {
				if(nums[j].equals("")) continue;
				else if(k > cols) return null;
				m.set(i, k, Double.parseDouble(nums[j]));
				k++;
			}
		}
		
		return m;
	}
}
