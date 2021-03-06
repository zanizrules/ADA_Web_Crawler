import java.util.*;

/**
 * Vinicius Ferreira : 14868388
 * Shane Birdsall : 14870204
 *
 * The Graph Class is used to store vertices and edges, in other words it stores web-pages and their links to other
 * web-pages. A map is used to represent an adjacency list where the key is vertex type E and its value is a list of
 * vertices to which the key is linked to. An ArrayList is used to maintain an ordered list sequentially as vertices
 * are added.
 */
class Graph<E> {
    private ArrayList<E> vertexList; // ordered list of vertices based on the order they were added.
    private Map<E, List<E>> adjacency_list; // Maps each Vertex set to its adjacency list.

    Graph() {
        adjacency_list = new HashMap<>();
        vertexList = new ArrayList<>();
    }

    void addVertex(E vertex) {
        if (adjacency_list.containsKey(vertex)) {
            return;
        }
        adjacency_list.put(vertex, new LinkedList<>());
        vertexList.add(vertex);
    }

    void addEdge(E startVertex, E endVertex) {
        List<E> list = adjacency_list.get(startVertex);
        if (!list.contains(endVertex)) {
            list.add(endVertex);
        }
    }

    List<E> getEdge(E startVertex) {
        return adjacency_list.get(startVertex);
    }

    int getSize() {
        return this.adjacency_list.size();
    }

    Iterator<E> iterator() {
        return adjacency_list.keySet().iterator();
    }

    List<E> getVertexList() {
        return vertexList;
    }

    /**
     * Creates and returns an adjacency matrix representation of the Graph.
     * 0 represents a non-existent path and 1 represents a link between vertices.
     * A 2D array(matrix representation) is needed for calculating page rank easily in other methods.
     */
    Double[][] createAdjacencyMatrix() {
        int size = this.vertexList.size();
        Double[][] ajcMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ajcMatrix[i][j] = 0.0;
            }
        }
        int counter = 0;

        for (E key : vertexList) {
            List<E> listEdge = this.getEdge(key);
            for (E element : listEdge) {
                int i = vertexList.indexOf(element);
                ajcMatrix[counter][i] = 1.0;
            }
            counter++;
        }
        return ajcMatrix;
    }
}