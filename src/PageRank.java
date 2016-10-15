/**
 * Vinicius Ferreira : 14868388
 * Shane Birdsall : 14870204
 *
 * The PageRank Class is responsible for calculating the Page Rank value for a web-page.
 * The class has functionality to multiply matrices, transpose matrices and calculate centrality values.
 */
class PageRank {

    /**
     * Multiplies a given matrix by a vector and returns the resulting vector.
     */
    private static Double[] multiplySquareMatrixbyVec(Double[][] matrix, Double[] vec) {
        Double[] result = new Double[vec.length];
        Double sum;

        for (int i = 0; i < result.length; i++) {
            sum = 0d;
            for (int j = 0; j < matrix.length; j++) {
                sum += (matrix[i][j] * vec[j]);
            }
            result[i] = sum;
        }
        return result;
    }

    /**
     * Calculates the PageRank Matrix for a given graph aM, and dampening factor c.
     */
    private static Double[][] pageRankMatrix(Double[][] aM, double c) {
        int size = aM.length, outDegree;
        for (int i = 0; i < size; i++) {
            outDegree = 0;
            for (int j = 0; j < size; j++) {
                if (aM[i][j] != 0) {
                    outDegree++;
                }
            }
            for (int k = 0; k < size; k++) {
                if (outDegree == 0) {
                    aM[i][k] = 1d / size;
                } else {
                    if (aM[i][k] == 1) {
                        aM[i][k] = ((1 - c) / size) + (c / outDegree);
                    } else {
                        aM[i][k] = (1 - c) / size;
                    }
                }
            }
        }
        return transpose(aM);
    }

    /**
     * Returns the transpose of the given matrix aM
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
     * Calculate page rank by using power iteration.
     * Returns a vector containing the page rank values corresponding to each page.
     */
    static Double[] pageRank(Double[][] aM, double df) {
        Double[][] pageRankMatrix = pageRankMatrix(aM, df); // Calculate PageRankMatrix
        Double[] vector = new Double[pageRankMatrix.length]; // Initialise vector
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 1d / pageRankMatrix.length;
        }
        boolean changing = true;
        Double changeFactor = 0.0001; // Factor used in power iteration.
        Double[] prevVector;
        while (changing) {
            prevVector = vector;
            vector = multiplySquareMatrixbyVec(pageRankMatrix, vector); // Iteratively multiply.
            changing = false;
            for (int i = 0; i < vector.length; i++)
                if (Math.abs(vector[i] - prevVector[i]) > changeFactor) { // Check if the page ranks are still changing.
                    changing = true;
                    i = vector.length; // Break out of loop
                }
        }
        return vector;
    }
}