package com.mycompany.app;

import com.mycompany.app.math.Matrix;

/**
 * Hello world!
 */
public class App {
    
    private static final int MATRIX_DIMENSIONS = 3;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // mainSourceCodeTests();
        mainMatrixMultTests();
    }

    private static void mainMatrixMultTests() {

        // ARRANGE

        int rows = 3;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();

        // ACT

        Matrix matrixC = matrixA.mult(matrixB);
        matrixC.prettyPrintFormat("%4s");

        // ASSERT

        int[] intArray = new int[] { 30,  36,  42, 66,  81,  96, 102, 126, 150 };
        Matrix matrixExpected = new Matrix(intArray, 3, 3);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }
    }

    private static void mainSourceCodeTests() {

        int rows = MATRIX_DIMENSIONS;
        int columns = rows;

        Matrix matrix = new Matrix(rows, columns);
        matrix.prettyPrint();
        matrix.identityMatrix();
        matrix.upCountingMatrix();
        matrix.prettyPrint();
        matrix.prettyPrintFormat("%4s");

        Matrix subMatrix = matrix.getSubMatrix(0, 0, 3, 3);
        //Matrix subMatrix = matrix.getSubMatrix(1, 1, 3, 3);
        //Matrix subMatrix = matrix.getSubMatrix(6, 6, 3, 3);
        //Matrix subMatrix = matrix.getSubMatrix(0, 0, 5, 5);
        subMatrix.prettyPrintFormat("%4s");


    }
}
