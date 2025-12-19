package com.mycompany.app.math;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Matrix {

    public int data[];

    public int rows;

    public int columns;

    /**
     * ctor
     *
     * @param rows
     * @param columns
     */
    public Matrix(int rows, int columns) {

        this.rows = rows;
        this.columns = columns;

        int elemCount = rows * columns;
        data = new int[elemCount];
        for (int i = 0; i < elemCount; i++) {
            data[i] = 0;
        }
    }

    /**
     * ctor
     *
     * @param intArray
     * @param rows
     * @param columns
     */
    public Matrix(int[] intArray, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = intArray;
    }

    public void identityMatrix() {
        if (rows != columns) {
            return;
        }
        for (int i = 0; i < rows; i++) {
            data[i * columns + i] = 1;
        }
    }

    public void upCountingMatrix() {
        int upCounter = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i * columns + j] = upCounter;
                upCounter++;
            }
        }
    }

    public void randomMatrix(int min, int max) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i * columns + j] = ThreadLocalRandom.current().nextInt(min, max + 1);
            }
        }
    }

    public Matrix getSubMatrix(int xPos, int yPos, int width, int height) {
        Matrix subMatrix = new Matrix(width, height);
        int innerI = 0;
        for (int i = xPos; i < xPos + width; i++) {
            int innerJ = 0;
            for (int j = yPos; j < yPos + height; j++) {
                subMatrix.data[innerI * width + innerJ] = data[i * rows + j];
                innerJ++;
            }
            innerI++;
        }
        return subMatrix;
    }

    public void setSubMatrix(int xPos, int yPos, int width, int height, Matrix accumulatorSubMatrixC) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tempData = accumulatorSubMatrixC.data[i * width + j];
                data[(yPos + i) * rows + (xPos + j)] = tempData;
            }
        }
    }

    public void add(Matrix rhs) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i * rows + j] += rhs.data[i * rows + j];
            }
        }
    }

    /** This is the basic algorithm for matrix multiplication */
    public Matrix mult(final Matrix matB) {
        if (columns != matB.rows) {
            throw new RuntimeException("Does not match!");
        }
        Matrix matC = new Matrix(rows, matB.columns);
        // over row of matrix B
        for (int i = 0; i < rows; i++) {
            // over column of matrix A
            for (int j = 0; j < matB.columns; j++) {
                // fuse row and column together into a single cell of matrix C
                for (int k = 0; k < columns; k++) {
                    matC.data[i * rows + j] += data[i * columns + k] * matB.data[k * columns + j];
                }
            }
        }
        return matC;
    }

    /**
     * The idea is that the outer product lends itself to be implemented in
     * hardware. The result is created in steps and the steps are combined using the
     * accumulator memory. Accumulation literally means that the intermediate result
     * are accumulated (add operation)
     *
     * @param matrixB
     */
    public Matrix multOuterProduct(Matrix matrixB) {

        if ((rows != matrixB.rows) && (columns != matrixB.columns) && (columns != rows)) {
            throw new RuntimeException("Need square, same dimensions for both matrixes!");
        }

        // gradually build up the result by buffering intermediate results in the
        // accumulator
        // int accumulator[] = new int[rows*rows];

        Matrix matrixC = new Matrix(rows, columns);

        // over all columns in matrix A
        for (int pivot = 0; pivot < columns; pivot++) {

            // over all rows in matrix A
            for (int rowA = 0; rowA < rows; rowA++) {

                // use the same row in matrix A and B

                // over all columns in matrix B
                for (int colB = 0; colB < columns; colB++) {

                    int a = data[rowA * rows + pivot];
                    int b = matrixB.data[pivot * rows + colB];

                    // DEBUG
                    // accumulator[rowA*rows + colB] += a * b;

                    matrixC.data[rowA * rows + colB] += a * b;

                }

            }

            // // DEBUG
            // for (int i = 0; i < rows*columns; i++) {
            // System.out.println(accumulator[i]);
            // }

        }

        // // 30, 36, 42, 66, 81, 96, 102, 126, 150
        // for (int i = 0; i < rows*columns; i++) {
        // System.out.println(accumulator[i]);
        // }

        return matrixC;
    }

    /**
     * Segmented matrix mult wraps a kernel for multiplying smaller matrices into a
     * loop structure so that a large matrix is computed in iterations. As a kernel
     * you can use different ways to multiply matrixes. For example standard matrix
     * multiplication or the outer product approach.
     *
     * @param rows
     * @param columns
     * @param matrixA
     * @param matrixB
     * @param matrixC
     */
    public static void segmentedMatrixMult(int rows, int columns,
            Matrix matrixA, Matrix matrixB, Matrix matrixC) {

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

        // for jc = 0 to n-1 step nc
        // Loop 1
        for (int jc = 0; jc < rowSteps; jc++) {

            // for pc = 0 to k-1 step kc
            // Loop 2
            for (int pc = 0; pc < columnsSteps; pc++) {

                Matrix subMatrixB = matrixB.getSubMatrix(pc * kc, jc * nc, kc, nc);

                // for ic = 0 to m-1 step mc
                // Loop 3
                for (int ic = 0; ic < innerSteps; ic++) {

                    Matrix subMatrixA = matrixA.getSubMatrix(ic * mc, pc * kc, mc, kc);

                    System.out.println("[");
                    subMatrixA.prettyPrintFormat("%6s");
                    System.out.println("------------------------");
                    subMatrixB.prettyPrintFormat("%6s");
                    System.out.println("]");

                    iterationCounter++;

                    Matrix accumulatorSubMatrixC = matrixC.getSubMatrix(ic * kc, jc * nc, nc, kc);

                    //
                    // kernel of sub matrix multiplication
                    //

                    // perform a matrix mult of the sub matrices using standard matrix
                    // multiplication
                    // Matrix multResult = subMatrixA.mult(subMatrixB);

                    // perform matrix multiplication based on output product multiplication
                    Matrix multResult = subMatrixA.multOuterProduct(subMatrixB);

                    // accumulate the result
                    accumulatorSubMatrixC.add(multResult);

                    // place the accumulator back into the large result matrix
                    matrixC.setSubMatrix(jc * nc, ic * kc, nc, kc, accumulatorSubMatrixC);

                }

            }
        }

        System.out.println(iterationCounter);
    }

    public void prettyPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stringBuilder.append(data[i * columns + j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * https://stackoverflow.com/questions/2635076/convert-integer-to-equivalent-number-of-blank-spaces
     * <br/>
     * <br/>
     * Usage:
     *
     * <pre>
     * int rows = MATRIX_DIMENSIONS;
     * int columns = rows;
     * Matrix matrix = new Matrix(rows, columns);
     * matrix.resetToUpCountingMatrix();
     * matrix.prettyPrintFormat("%4s");
     * </pre>
     *
     * @param format
     */
    public void prettyPrintFormat(final String format) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.format(format, data[i * columns + j]);
            }
            System.out.println("");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(data);
        result = prime * result + rows;
        result = prime * result + columns;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix other = (Matrix) obj;
        if (!Arrays.equals(data, other.data))
            return false;
        if (rows != other.rows)
            return false;
        if (columns != other.columns)
            return false;
        return true;
    }

}
