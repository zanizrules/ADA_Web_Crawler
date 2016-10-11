import javax.swing.*;
import java.util.*;

/**
 * Vini :
 * Shane Birdsall : 14870204
 *
 * The SearchResults class stores the search results needing to be shown in the applications GUI and a
 * SearchResultListModel used for displaying the results.
 */
class SearchResults extends LinkedList<Page> {
    private SearchResultListModel listModel;
    private LinkedList<Page> searchResults;

    private SearchResults() {
        super();
        searchResults = new LinkedList<>();
        listModel = null;
    }

    SearchResults(Collection<Page> pages) {
        this();
        searchResults.addAll(pages);
    }

    @Override
    public Page get(int i) {
        return searchResults.get(i);
    }

    @Override
    public int size() {
        return searchResults.size();
    }


    @Override
    public boolean add(Page page) {  // add a Page to the searchResults.
        // If page is added to search results, then add page to list model.
        return searchResults.add(page) && listModel.addSearchResult(page);
    }

    SearchResultListModel getListModel() {
        if (listModel == null) { // Initialise only if not already initialised.
            listModel = new SearchResultListModel(searchResults);
        }
        return listModel;
    }

    /**
     * The SearchResultListModel inner class is a ListModel which is used to display the search results in a JList in
     * the applications GUI.
     */
    private class SearchResultListModel extends AbstractListModel<Page> {
        private ArrayList<Page> searchResults;

        // constructor initialises arrayList and sorts the collection in its natural order (Page Rank)
        SearchResultListModel(Collection<Page> searchData) {
            super();
            searchResults = new ArrayList<>();
            searchResults.addAll(searchData);
            Collections.sort(searchResults, Page.getComparator());
        }

        // gets the element at the specified index
        public Page getElementAt(int index) {
            if (index < searchResults.size())
                return searchResults.get(index);
            else return null;
        }

        // gets the size of the arrayList
        public int getSize() {
            return searchResults.size();
        }

        // adds a search result to the arrayList and notifies any listeners with fireIntervalAdded
        boolean addSearchResult(Page result) {
            int index = Collections.binarySearch(searchResults, result, Page.getComparator());
            index = (index * -1) - 1;
            searchResults.add(index, result);
            fireIntervalAdded(this, index, index);
            return true;
        }
    }
}