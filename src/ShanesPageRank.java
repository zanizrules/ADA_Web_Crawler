import java.math.BigDecimal;

/**
 * Created by Shane Birdsall on 18/09/2016.
 * Lab 06, Question 4, part c.
 */
public class ShanesPageRank {

    private static BigDecimal[] pageRank(BigDecimal[][] graph) {
        double c = 0.15;
        BigDecimal[] pageRankVector = new BigDecimal[graph.length];
        for (int i = 0; i < pageRankVector.length; i++) {
            pageRankVector[i] = BigDecimal.ONE.divide(new BigDecimal(graph.length), 3, BigDecimal.ROUND_CEILING);
        }

        BigDecimal[][] pageRankMatrix = new BigDecimal[graph[0].length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            int outDegree = 0;
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j].compareTo(BigDecimal.ZERO) != 0) {
                    outDegree++;
                }
            }
            for (int j = 0; j < graph[0].length; j++) {
                if (outDegree == 0) {
                    pageRankMatrix[i][j] = BigDecimal.ONE.divide(new BigDecimal(graph.length), 3, BigDecimal.ROUND_CEILING);
                } else {
                    if (graph[i][j].equals(BigDecimal.ZERO)) {
                        pageRankMatrix[i][j] = new BigDecimal(((1 - c) / graph.length));
                    } else pageRankMatrix[i][j] = new BigDecimal(((1 - c) / graph.length) + c / outDegree);
                }
            }
        }

        pageRankMatrix = transposeMatrix(pageRankMatrix);

        BigDecimal difference = new BigDecimal(0.002);
        BigDecimal[] prevVec;
        boolean bigChange = true;

        while(bigChange){
            prevVec = pageRankVector;
            pageRankVector = PowerIteration.multiplySquareMatbyVec(pageRankMatrix, pageRankVector);
            bigChange = false;
            for(int i = 0; i < pageRankVector.length; i++) {
                if((prevVec[i].subtract(pageRankVector[i])).abs().compareTo(difference) == 1) {
                    bigChange = true;
                }
            }
        }
        for(int i = 0; i < pageRankVector.length; i++) { // round the decimal places
            pageRankVector[i] = pageRankVector[i].divide(BigDecimal.ONE, 5, BigDecimal.ROUND_CEILING);
        }
        return pageRankVector;
    }

    private static BigDecimal[][] transposeMatrix(BigDecimal[][] mat) {
        BigDecimal[][] newMat = new BigDecimal[mat.length][mat[0].length];

        for(int i = 0; i < mat.length; i++) {
            for(int j = 0; j < mat[0].length; j++) {
                newMat[j][i] = mat[i][j];
            }
        }
        return newMat;
    }

    public static void main(String[] args) {
        System.out.println("Page Rank Centrality on graph from lecture: ");

        BigDecimal[][] graph = new BigDecimal[4][4];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                graph[i][j] = BigDecimal.ZERO;
            }
        }
        graph[0][1] = BigDecimal.ONE;
        graph[1][2] = BigDecimal.ONE;
        graph[1][3] = BigDecimal.ONE;
        graph[2][3] = BigDecimal.ONE;
        graph[3][0] = BigDecimal.ONE;

        BigDecimal[] centrality = pageRank(graph);
        PowerIteration.printVector(centrality);

        // Lab Q3
        System.out.println("\nPage Rank Centrality on graph from lab: ");

        BigDecimal[][] graph2 = new BigDecimal[5][5];
        for (int i = 0; i < graph2.length; i++) {
            for (int j = 0; j < graph2[0].length; j++) {
                graph2[i][j] = BigDecimal.ZERO;
            }
        }
        graph2[0][1] = BigDecimal.ONE;
        graph2[2][0] = BigDecimal.ONE;
        graph2[2][1] = BigDecimal.ONE;
        graph2[2][3] = BigDecimal.ONE;
        graph2[2][4] = BigDecimal.ONE;
        graph2[3][2] = BigDecimal.ONE;
        graph2[3][4] = BigDecimal.ONE;
        graph2[4][3] = BigDecimal.ONE;

        centrality = pageRank(graph2);
        PowerIteration.printVector(centrality);

        // Lab 08 Q3
        System.out.println("\nPage Rank Centrality on graph from lab 8: ");

        BigDecimal[][] graph3 = new BigDecimal[3][3];
        for (int i = 0; i < graph3.length; i++) {
            for (int j = 0; j < graph3[0].length; j++) {
                graph3[i][j] = BigDecimal.ZERO;
            }
        }
        graph3[0][1] = BigDecimal.ONE;
        graph3[1][0] = BigDecimal.ONE;
        graph3[2][0] = BigDecimal.ONE;

        centrality = pageRank(graph3);
        PowerIteration.printVector(centrality);

    }
}
