/**
 * PageRank Class is responsible for calculating the Page Rank value for each page
 * it has private methods to multiply matrices, transpose matrices and calculate centrality values.
 */
class PageRank {

    /**
     * Calculates two matrices and return the results
     * @param A Double[][]
     * @param B Double[][]
     * @return Double[][]
     */
    private static Double[][] matrixMultiply(Double[][] A, Double[][] B) {

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

    /**
     * Calculates values for each edge and return a PageRank Matrix
     * @param aM Double[][]
     * @param df Double[][]
     * @return Double[][]
     */
    private static Double[][] pageRankMatrix(Double[][] aM, double df) {
        int size = aM.length;
        for (int i = 0; i < size; i++) {
            int outgoing = 0;
            for (int j = 0; j < size; j++) {
                if (aM[i][j] == 1) {
                    outgoing++;
                }
            }
            for (int k = 0; k < size; k++) {
                if(outgoing == 0){
                    aM[i][k] = (double) 1/ size;
                }else{
                    if (aM[i][k] == 1) {
                        aM[i][k] = ((1 - (df)) / size) + (df / outgoing);
                    } else {
                        aM[i][k] = (1 - (df)) / size;
                    }
                }
            }
        }
        return aM;
    }

    /**
     * Method transpose a matrix only
     * @param aM Double[][]
     * @return Double[][]
     */
    private static Double[][] transpose(Double[][] aM) {
        int size = aM.length;
        Double[][] transposedMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transposedMatrix[j][i] = aM[i][j];
            }
        }
        return transposedMatrix;
    }

    /**
     * Simulate the stochastic process by iteratively multiplying the stochastic matrix to the current vector.
     * Returns an array of vectors value corresponding to each page.
     * @param aM Double[][]
     * @param df Double[][]
     * @return Double[][]
     */
    static Double[][] pageRank(Double[][] aM, double df) {
        Double[][] pageRankMatrix = pageRankMatrix(aM, df);
        Double[][] transposedMatrix = transpose(pageRankMatrix);
        Double[][] vector = new Double[pageRankMatrix.length][1];
        for (int i = 0; i < vector.length; i++) {
            vector[i][0] = (double) 1 / pageRankMatrix.length;
        }
        boolean changing = true;
        while (changing) {
            Double[][] prevVector = vector;
            vector = matrixMultiply(transposedMatrix, vector);
            changing = false;
            for (int i = 0; i < vector.length; i++)
                if (vector[i][0] > (prevVector[i][0] + 0.0001)
                        || vector[i][0] < (prevVector[i][0] - 0.0001)) {
                    changing = true;
                    break;
                }
        }
        return vector;
    }

}
