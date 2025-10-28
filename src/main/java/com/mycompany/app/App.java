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
        // mainMatrixMultTests();

        mainMatrixMultSegmented();
    }

    private static void mainMatrixMultSegmented() {

        // ARRANGE

        int rows = 9;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        // matrixA.prettyPrint();
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();

        // ACT

        Matrix matrixC = matrixA.mult(matrixB);
        matrixC.prettyPrintFormat("%6s");

        // ASSERT

        int[] intArray = new int[] {  
            2205, 2250, 2295, 2340, 2385, 2430, 2475, 2520, 2565,
            5202, 5328, 5454, 5580, 5706, 5832, 5958, 6084, 6210,
            8199, 8406, 8613, 8820, 9027, 9234, 9441, 9648, 9855,
            11196, 11484, 11772, 12060, 12348, 12636, 12924, 13212, 13500,
            14193, 14562, 14931, 15300, 15669, 16038, 16407, 16776, 17145,
            17190, 17640, 18090, 18540, 18990, 19440, 19890, 20340, 20790,
            20187, 20718, 21249, 21780, 22311, 22842, 23373, 23904, 24435,
            23184, 23796, 24408, 25020, 25632, 26244, 26856, 27468, 28080,
            26181, 26874, 27567, 28260, 28953, 29646, 30339, 31032, 31725
        };

        Matrix matrixExpected = new Matrix(intArray, 9, 9);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
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

        System.out.println("Test OK");
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
