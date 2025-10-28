package com.mycompany.app.math;

import java.util.Arrays;

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

    public Matrix getSubMatrix(int xPos, int yPos, int width, int height) {
        Matrix subMatrix = new Matrix(width, height);
        int innerI = 0;
        for (int i = xPos; i < xPos+width; i++) {
            int innerJ = 0;
            for (int j = yPos; j < yPos+height; j++) {
                subMatrix.data[innerI*width+innerJ] = this.data[i*this.rows + j];
                innerJ++;
            }
            innerI++;
        }
        return subMatrix;
    }

    public Matrix mult(final Matrix matB) {
        if (columns != matB.rows) {
            throw new RuntimeException("Does not match!");
        }
        Matrix matC = new Matrix(rows, matB.columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < matB.columns; j++) {

                for (int k = 0; k < columns; k++) {
                    matC.data[i*rows + j] += data[i*columns + k] * matB.data[k*columns + j];
                }

            }
        }
        return matC;
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
