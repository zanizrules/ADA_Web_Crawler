
import java.util.*;

/**
 * Class Graph is used to store vertices and edges, in other words it stores the page and its links to other vertices.
 * A map is used to represent an adjacency list where the key is vertex type E and its value is
 * list of vertices to which the key is linked to. An ArrayList is used to maintain an ordered list sequentially as
 * vertices are added.
 * @param <E>
 */
class Graph<E> {
    /*
        TODO: Go through this class with Vini.
        I am wanting to:
            - change the name of this class to perhaps Graph.
            - Rename variables, edge set and node/vertex set?
            - Refactor through the code.
     */
    private ArrayList<E> orderedList; // maintain the vertices order
    private Map<E, List<E>> adjacency_list;//stores vertices and their edges

    Graph() {
        adjacency_list = new HashMap<>();
        orderedList = new ArrayList<>();
    }

    void addVertex(E vertex) {
        if (adjacency_list.containsKey(vertex)) {
            return;
        }
        List<E> list = new LinkedList<>();
        adjacency_list.put(vertex, list);
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

    List<E> getOrderedList(){
        return orderedList;
    }

    /**
     * Creates an adjacency matrix representation for the graph class.
     * 0 represents non-existent path and 1 if there is a link between vertices.
     * A 2 dimension array is used so further manipulation can be performed on the matrix's value
     * easily by other methods.
     * @return Double[][]
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
