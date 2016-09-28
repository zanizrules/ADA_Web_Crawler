
import java.util.*;

public class AdjList<E> {

    private ArrayList<E> orderedList;
    private Map<E, List<E>> adjacency_list;

    public AdjList() {
        adjacency_list = new HashMap<E, List<E>>();
        orderedList = new ArrayList();
    }

    public void addVertex(E vertex) {
        if (adjacency_list.containsKey(vertex)) {
            return;
        }
        List<E> list = new LinkedList();
        adjacency_list.put(vertex, list);
        orderedList.add(vertex);
    }

    public void removeVertex(E vertex) {
        if (!adjacency_list.containsKey(vertex)) {
            return;
        }
        adjacency_list.remove(vertex);
        orderedList.remove(vertex);
    }

    public void addEdge(E startVertex, E endVertex) {
        List<E> list = adjacency_list.get(startVertex);
        if (!list.contains(endVertex)) {
            list.add(endVertex);
        }
    }

    public void addUndirectedEdges(E startVertex, E endVertex) {
        this.addEdge(startVertex, endVertex);
        this.addEdge(endVertex, startVertex);
    }

    public void removeEdge(E startVertex, E endVertex) {
        if (!adjacency_list.containsKey(startVertex) || !adjacency_list.containsKey(endVertex)) {
            return;
        }

        List<E> list = adjacency_list.get(startVertex);

        if (!list.contains(endVertex)) {
            list.remove(endVertex);
        }
    }

    public List<E> getEdge(E startVertex) {
        return adjacency_list.get(startVertex);
    }

    public int getSize() {
        return this.adjacency_list.size();
    }

    public Iterator<E> iterator() {
        return adjacency_list.keySet().iterator();
    }

    public List getOrderedList(){
        return orderedList;
    }

    public Double[][] createAM() {
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

    public void print() {
        for (E key : orderedList) {
            System.out.println("Vertex: " + key);
            List<E> listEdge = this.getEdge(key);
            for (E element : listEdge) {
                System.out.println("    Edges: " + element);
            }
        }
    }
}
