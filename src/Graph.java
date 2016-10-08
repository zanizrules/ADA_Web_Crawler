
import java.util.*;

class Graph<E> {
    /*
        TODO: Go through this class with Vini.
        I am wanting to:
            - change the name of this class to perhaps Graph.
            - Rename variables, edge set and node/vertex set?
            - Refactor through the code.
     */
    private ArrayList<E> orderedList;
    private Map<E, List<E>> adjacency_list;

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
