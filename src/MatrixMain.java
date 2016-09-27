
/**
 *
 * @author Everybody's
 */
public class MatrixMain {

    public Double[][] pageRank(Double[][] aM, double df) {
        Double[][] pageRankMatrix = pageRankMatrix(aM, df);
        Double[][] transposedMatrix = transpose(pageRankMatrix);
        Double[][] vector = new Double[pageRankMatrix.length][1];
        for (int i = 0; i < vector.length; i++) {
            vector[i][0] = (double) 1 / pageRankMatrix.length;
        }
        boolean changing = true;
//        int counter =0;
        while (changing) {
            Double[][] prevVector = vector;
            vector = matrixMultiply(transposedMatrix, vector);
            changing = false;
            for (int i = 0; i < vector.length; i++) {
                if (vector[i][0] > (prevVector[i][0] + 0.01)
                        || vector[i][0] < (prevVector[i][0] - 0.01)) {
                    changing = true;
//                    counter++;
                     break;
                }
            }
        }
//        System.out.println(counter);
        return vector;
    }

    public Double[][] powerInteration(Double[][] aM) {
        Double[][] B = matrixAddtion(aM, IdentityMatrix(aM));
        Double[][] vector = new Double[aM.length][1];
        for (int i = 0; i < vector.length; i++) {
            vector[i][0] = 1.0;
        }
        boolean changing = true;
        while (changing) {
            Double[][] u = matrixMultiply(B, vector);
            Double u0 = u[0][0];
            changing = false;
            for (int i = 0; i < vector.length; i++) {
                Double v = vector[i][0];
                vector[i][0] = u[i][0] / u0;
                if (vector[i][0] > (v + 0.02) || vector[i][0] < (v - 0.02)) {
                    changing = true;
                }

            }
        }
        double norm = norm(vector);
        for (int i = 0; i < vector.length; i++) {
            vector[i][0] = vector[i][0] / norm;
        }
        return vector;
    }

    public static Double[][] pageRankMatrix(Double[][] aM, double df) {
        int size = aM.length;
        Double[][] pageRankMatrix = aM;
        for (int i = 0; i < size; i++) {
            int outgoing = 0;
            for (int j = 0; j < size; j++) {
                if (pageRankMatrix[i][j] == 1) {
                    outgoing++;
                }
            }
            for (int k = 0; k < size; k++) {
                if (pageRankMatrix[i][k] == 1) {
                    pageRankMatrix[i][k] = (double) ((1 - (df)) / size) + (df / outgoing);
                } else {
                    pageRankMatrix[i][k] = (double) (1 - (df)) / size;
                }
            }
        }
        return pageRankMatrix;
    }
    
    public static Double[][] transpose(Double[][] aM) {
        int size = aM.length;
        Double[][] transposedMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transposedMatrix[j][i] = aM[i][j];
            }
        }
        return transposedMatrix;
    }
    
    public static double norm(Double[][] vector) {
        double norm = 0;
        for (int i = 0; i < vector.length; i++) {
            norm += Math.pow(vector[i][0], 2);
        }
        return Math.sqrt(norm);

    }

    public static Double[][] matrixAddtion(Double[][] first, Double[][] second) {

        int size = first.length;
        Double[][] adjMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (first[i][j] == 1 || second[i][j] == 1) {
                    adjMatrix[i][j] = 1.0;
                } else {
                    adjMatrix[i][j] = 0.0;
                }

            }
        }
        return adjMatrix;
    }

    public static Double[][] matrixMultiply(Double[][] A, Double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    public static Double[][] IdentityMatrix(Double[][] matrix) {
        int size = (int) (matrix.length);
        Double[][] adjMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    adjMatrix[i][j] = 1.0;
                } else {
                    adjMatrix[i][j] = 0.0;
                }

            }
        }
        return adjMatrix;
    }

    public static void printMatrix(Double[][] matrix, boolean isInt) {
        int rows = matrix.length;
        int colums = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                if (isInt) {
                    System.out.print(matrix[i][j].intValue() + " ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }
        
}
