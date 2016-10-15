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
               if(Math.abs(vector[i] - prevVector[i]) > changeFactor) { // Check if the page ranks are still changing.
                   changing = true;
                   i = vector.length; // Break out of loop
               }
        }
        return vector;
    }


    // test stuff -> Delete before submission

    private static void printVector(Double[] vector) {
        for (Double aVector : vector) {
            System.out.println(aVector);
        }
    }

    public static void main(String[] args) {
        System.out.println("Page Rank Centrality on graph from lecture: ");

        Double[][] graph = new Double[4][4];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                graph[i][j] = 0d;
            }
        }
        graph[0][1] = 1d;
        graph[1][2] = 1d;
        graph[1][3] = 1d;
        graph[2][3] = 1d;
        graph[3][0] = 1d;

        Double[] centrality = pageRank(graph, 0.15);
        printVector(centrality);

        // Lab Q3
        System.out.println("\nPage Rank Centrality on graph from lab: ");

        Double[][] graph2 = new Double[5][5];
        for (int i = 0; i < graph2.length; i++) {
            for (int j = 0; j < graph2[0].length; j++) {
                graph2[i][j] = 0d;
            }
        }
        graph2[0][1] = 1d;
        graph2[2][0] = 1d;
        graph2[2][1] = 1d;
        graph2[2][3] = 1d;
        graph2[2][4] = 1d;
        graph2[3][2] = 1d;
        graph2[3][4] = 1d;
        graph2[4][3] = 1d;

        centrality = pageRank(graph2, 0.15);
        printVector(centrality);

        // Lab 08 Q3
        System.out.println("\nPage Rank Centrality on graph from lab 8: ");

        Double[][] graph3 = new Double[3][3];
        for (int i = 0; i < graph3.length; i++) {
            for (int j = 0; j < graph3[0].length; j++) {
                graph3[i][j] = 0d;
            }
        }
        graph3[0][1] = 1d;
        graph3[1][0] = 1d;
        graph3[2][0] = 1d;

        centrality = pageRank(graph3, 0.15);
        printVector(centrality);
    }
}