package Javatrix;

public class Matrix 
{
	private double[][] matrix;

	public Matrix(double[][] A)
	{
		
	}
	
	public Matrix(double[][] A, int m, int n)
	{
		A = new double[m][n];
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
	

}
