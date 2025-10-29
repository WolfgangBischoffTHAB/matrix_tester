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
        // mainMatrixMulTests();
        // mainMatrixMul4x4();
        // mainMatrixMul9x9();
        // mainMatrixAdd3x3();
        // mainMatrixSetSubMatrix();

        // mainMatrixMulSegmented();
        // mainMatrixMulSegmented2();

        // mainMatrixMulOuterProduct();
        mainMatrixMulOuterProductRandom();
    }

    private static void mainMatrixMulOuterProduct() {
        
        // ARRANGE

        int rows = 3;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        matrixA.prettyPrintFormat("%6s");

        System.out.println("----------------------");

        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();
        matrixB.prettyPrintFormat("%6s");

        System.out.println("----------------------");

         // ACT

        Matrix matrixC = matrixA.multOuterProduct(matrixB);
        matrixC.prettyPrintFormat("%6s");

        // ASSERT

        int[] intArray = new int[] { 30, 36, 42, 66, 81, 96, 102, 126, 150 };
        Matrix matrixExpected = new Matrix(intArray, 3, 3);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixMulOuterProductRandom() {
        
        // ARRANGE

        int rows = 3;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.randomMatrix(0, 100);
        matrixA.prettyPrintFormat("%6s");

        System.out.println("----------------------");

        Matrix matrixB = new Matrix(rows, columns);
        matrixB.randomMatrix(0, 100);
        matrixB.prettyPrintFormat("%6s");

        System.out.println("----------------------");

        // ACT

        // compute with outer product
        Matrix matrixCOuterProduct = matrixA.multOuterProduct(matrixB);
        matrixCOuterProduct.prettyPrintFormat("%6s");

        // compute normally
        Matrix matrixC = matrixA.mult(matrixB);
        matrixC.prettyPrintFormat("%6s");

        // ASSERT

        // compute normal result with outer product result
        if (!matrixC.equals(matrixCOuterProduct)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixSetSubMatrix() {
        
        // ARRANGE

        int rows = 3;
        int columns = rows;

        Matrix matrixA = new Matrix(9, 9);
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();
        matrixB.prettyPrintFormat("%6s");

        System.out.println("----------------------");

        // ACT

        matrixA.setSubMatrix(3, 3, 3, 3, matrixB);
        matrixA.prettyPrintFormat("%6s");

        // ASSERT

        int[] intArray = new int[] {  
            0,     0,     0,     0,     0,     0,     0,     0,     0,
            0,     0,     0,     0,     0,     0,     0,     0,     0,
            0,     0,     0,     0,     0,     0,     0,     0,     0,
            0,     0,     0,     1,     2,     3,     0,     0,     0,
            0,     0,     0,     4,     5,     6,     0,     0,     0,
            0,     0,     0,     7,     8,     9,     0,     0,     0,
            0,     0,     0,     0,     0,     0,     0,     0,     0,
            0,     0,     0,     0,     0,     0,     0,     0,     0,
            0,     0,     0,     0,     0,     0,     0,     0,     0
        };

        Matrix matrixExpected = new Matrix(intArray, 9, 9);

        if (!matrixA.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixAdd3x3() {

        // ARRANGE

        int rows = 3;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();

        // ACT

        matrixA.add(matrixB);
        matrixA.prettyPrintFormat("%6s");

        // ASSERT

        int[] intArray = new int[] {  
            2, 4, 6,
            8, 10, 12,
            14, 16, 18
        };

        Matrix matrixExpected = new Matrix(intArray, 3, 3);

        if (!matrixA.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    // https://onlinetools.com/math/generate-random-matrix
    // https://matrix.reshish.com/de/matrix-multiplication/    

    private static void mainMatrixMulSegmented2() {
        
        // ARRANGE

        int rows = 4;
        int columns = rows;

        System.out.println("A");
        /*
        9 0 9 4
        2 6 6 7
        9 3 8 1
        6 9 7 1
        */
        int[] intArrayA = new int[] {  
            9, 0, 9, 4,
            2, 6, 6, 7,
            9, 3, 8, 1,
            6, 9, 7, 1
        };
        Matrix matrixA = new Matrix(intArrayA, rows, columns);
        matrixA.prettyPrintFormat("%6s");

        System.out.println("B");
        /*
        1 2 4 2
        8 6 0 0
        7 6 8 5
        8 4 7 5
        */
        int[] intArrayB = new int[] {  
            1, 2, 4, 2,
            8, 6, 0, 0,
            7, 6, 8, 5,
            8, 4, 7, 5
        };
        Matrix matrixB = new Matrix(intArrayB, rows, columns);
        matrixB.prettyPrintFormat("%6s");

        Matrix matrixC = new Matrix(rows, columns);

        int nc = 2; // subset size
        int rowSteps = rows / nc;

        int kc = 2; // subset size
        int columnsSteps = columns / kc;

        int mc = 2; // subset size
        int innerSteps = rows / nc;

        //
        // ACT
        //

        // DEBUG
        int iterationCounter = 0;

        // for jc = 0 to n-1 step nc // Loop 1

        // Loop 1
        for (int jc = 0; jc < rowSteps; jc++) {

            // for pc = 0 to k-1 step kc // Loop 2
            // Loop 2
            for (int pc = 0; pc < columnsSteps; pc++) {

                Matrix subMatrixB = matrixB.getSubMatrix(pc*kc, jc*nc, kc, nc);

                // for ic = 0 to m-1 step mc // Loop 3
                for (int ic = 0; ic < innerSteps; ic++) {

                    Matrix subMatrixA = matrixA.getSubMatrix(ic*mc, pc*kc, mc, kc);

                    System.out.println("[");
                    subMatrixA.prettyPrintFormat("%6s");
                    System.out.println("------------------------");
                    subMatrixB.prettyPrintFormat("%6s");
                    System.out.println("]");

                    iterationCounter++;

                    //Matrix accumulatorSubMatrixC = matrixC.getSubMatrix(jc*nc, ic*kc, nc, kc);
                    Matrix accumulatorSubMatrixC = matrixC.getSubMatrix(ic*kc, jc*nc, nc, kc);

                    Matrix temp = subMatrixA.mult(subMatrixB);

                    accumulatorSubMatrixC.add(temp);

                    matrixC.setSubMatrix(jc*nc, ic*kc, nc, kc, accumulatorSubMatrixC);

                }

            }
        }

        System.out.println(iterationCounter);
        matrixC.prettyPrintFormat("%4s");
        
        // ASSERT

        int[] intArray = new int[] {  
            104,  88, 136,  83,
            148, 104, 105,  69,
            97,  88, 107,  63,
            135, 112,  87,  52
        };
        
        Matrix matrixExpected = new Matrix(intArray, 4, 4);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixMulSegmented() {
        
        // ARRANGE

        int rows = 4;
        int columns = rows;

        System.out.println("A");
        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        matrixA.prettyPrintFormat("%6s");

        System.out.println("B");
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();
        matrixB.prettyPrintFormat("%6s");

        Matrix matrixC = new Matrix(rows, columns);

        int nc = 2; // subset size
        int rowSteps = rows / nc;

        int kc = 2; // subset size
        int columnsSteps = columns / kc;

        int mc = 2; // subset size
        int innerSteps = 2;

        //
        // ACT
        //

        // DEBUG
        int iterationCounter = 0;

        // for jc = 0 to n-1 step nc // Loop 1

        // Loop 1
        for (int jc = 0; jc < rowSteps; jc++) {

            // for pc = 0 to k-1 step kc // Loop 2
            // Loop 2
            for (int pc = 0; pc < columnsSteps; pc++) {

                Matrix subMatrixB = matrixB.getSubMatrix(pc*kc, jc*nc, kc, nc);

                // for ic = 0 to m-1 step mc // Loop 3
                for (int ic = 0; ic < innerSteps; ic++) {

                    Matrix subMatrixA = matrixA.getSubMatrix(ic*mc, pc*kc, mc, kc);

                    System.out.println("[");
                    subMatrixA.prettyPrintFormat("%6s");
                    System.out.println("------------------------");
                    subMatrixB.prettyPrintFormat("%6s");
                    System.out.println("]");

                    iterationCounter++;

                    //Matrix accumulatorSubMatrixC = matrixC.getSubMatrix(jc*nc, ic*kc, nc, kc);
                    Matrix accumulatorSubMatrixC = matrixC.getSubMatrix(ic*kc, jc*nc, nc, kc);

                    Matrix temp = subMatrixA.mult(subMatrixB);

                    accumulatorSubMatrixC.add(temp);

                    matrixC.setSubMatrix(jc*nc, ic*kc, nc, kc, accumulatorSubMatrixC);

                }

            }
        }

        System.out.println(iterationCounter);
        matrixC.prettyPrintFormat("%4s");
        
        // ASSERT

        int[] intArray = new int[] {  
            90, 100, 110, 120,
            202, 228, 254, 280,
            314, 356, 398, 440,
            426, 484, 542, 600
        };
        
        Matrix matrixExpected = new Matrix(intArray, 4, 4);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixMul4x4() {

        // ARRANGE

        int rows = 4;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
        matrixA.prettyPrintFormat("%4s");
        Matrix matrixB = new Matrix(rows, columns);
        matrixB.upCountingMatrix();
        matrixB.prettyPrintFormat("%4s");

        // ACT

        Matrix matrixC = matrixA.mult(matrixB);
        matrixC.prettyPrintFormat("%4s");

        // ASSERT

        int[] intArray = new int[] {  
            90, 100, 110, 120,
            202, 228, 254, 280,
            314, 356, 398, 440,
            426, 484, 542, 600
        };

        Matrix matrixExpected = new Matrix(intArray, 4, 4);

        if (!matrixC.equals(matrixExpected)) {
            throw new RuntimeException("No match!");
        }

        System.out.println("Test OK");
    }

    private static void mainMatrixMul9x9() {

        // ARRANGE

        int rows = 9;
        int columns = rows;

        Matrix matrixA = new Matrix(rows, columns);
        matrixA.upCountingMatrix();
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

    private static void mainMatrixMulTests() {

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

        int[] intArray = new int[] { 30, 36, 42, 66, 81, 96, 102, 126, 150 };
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
