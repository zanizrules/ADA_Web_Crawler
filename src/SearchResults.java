import javax.swing.*;
import java.util.*;

/**
 * Created by Shane Birdsall on 8/10/2016.
 */
public class SearchResults extends LinkedList<Page> {
    private SearchResultListModel listModel;
    private LinkedList<Page> searchResults;

    SearchResults() {
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

    // This method adds a Page to the searchResults
    @Override
    public boolean add(Page page) {
        // If page is added to search results, then add page to list model.
        return searchResults.add(page) && listModel.addSearchResult(page);
    }

    // This method removes a Page from the searchResults
    public boolean remove(Page page) {
        // If page is removed from search results, then remove page from list model.
        return searchResults.remove(page) && listModel.removeSearchResult(page);
    }

    public SearchResultListModel getListModel() {
        if(listModel == null) {
            listModel = new SearchResultListModel(searchResults);
        }
        return listModel;
    }

    private class SearchResultListModel extends AbstractListModel {
        private ArrayList<Page> searchResults;

        // constructor initialises arrayList and sorts the collection in its natural order (Page Rank)
        public SearchResultListModel(Collection<Page> searchData) {
            super();
            searchResults = new ArrayList<>();
            searchResults.addAll(searchData);
            Collections.sort(searchResults);
        }

        // gets the element at the specified index
        public Page getElementAt(int index) {
            if (index < searchResults.size())
                return searchResults.get(index);
            else
                return null;
        }

        // gets the size of the arrayList
        public int getSize() {
            return searchResults.size();
        }

        // adds a search result to the arrayList and notifies any listeners with fireIntervalAdded
        public boolean addSearchResult(Page result) {
            int index = Collections.binarySearch(searchResults, result);
            index = (index * -1) - 1;
            searchResults.add(index, result);
            fireIntervalAdded(this, index, index);
            return true;
        }

        // removes a search result from the arrayList and notifies any listeners with fireIntervalRemoved
        public boolean removeSearchResult(Page result) {
            int index = searchResults.indexOf(result);
            searchResults.remove(result);
            fireIntervalRemoved(this, index, index);
            return true;
        }
    }
}