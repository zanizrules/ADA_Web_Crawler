import java.util.*;

/**
 * The Graph Class is used to store vertices and edges, in other words it stores web-pages and their links to other
 * web-pages. A map is used to represent an adjacency list where the key is vertex type E and its value is a list of
 * vertices to which the key is linked to. An ArrayList is used to maintain an ordered list sequentially as vertices
 * are added.
 */
class Graph<E> {
    private ArrayList<E> orderedList; // Maintain the vertices order
    private Map<E, List<E>> adjacency_list; // Store vertices and their edges

    Graph() {
        adjacency_list = new HashMap<>();
        orderedList = new ArrayList<>();
    }

    void addVertex(E vertex) {
        if (adjacency_list.containsKey(vertex)) {
            return;
        }
        adjacency_list.put(vertex, new LinkedList<>());
        orderedList.add(vertex);
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

    List<E> getOrderedList() {
        return orderedList;
    }

    /**
     * Creates and returns an adjacency matrix representation of the Graph.
     * 0 represents a non-existent path and 1 represents a link between vertices.
     * A 2D array(matrix representation) is needed for calculating page rank easily in other methods.
     */
    Double[][] createAdjacencyMatrix() {
        int size = this.orderedList.size();
        Double[][] ajcMatrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ajcMatrix[i][j] = 0.0;
            }
        }
        int counter = 0;

        for (E key : orderedList) {
            List<E> listEdge = this.getEdge(key);
            for (E element : listEdge) {
                int i = orderedList.indexOf(element);
                ajcMatrix[counter][i] = 1.0;
            }
            counter++;
        }
        return ajcMatrix;
    }
}