package hdimagesearch.gui.model;

/**
 * Created by mario on 07.07.15.
 */
public class ClusterConfiguration {

    private String fsDefaultName;
    private String mapredJobTracker;
    private String imageFolder;
    private String indexFolder;
    private String searchResultFolder;

    public ClusterConfiguration(String fsDefaultName, String mapredJobTracker, String imageFolder, String indexFolder, String searchResultFolder)
    {
        this.fsDefaultName = fsDefaultName;
        this.mapredJobTracker = mapredJobTracker;
        this.imageFolder = imageFolder;
        this.indexFolder = indexFolder;
        this.searchResultFolder = searchResultFolder;
    }

    public String getFsDefaultName() {
        return fsDefaultName;
    }

    public void setFsDefaultName(String fsDefaultName) {
        this.fsDefaultName = fsDefaultName;
    }

    public String getMapredJobTracker() {
        return mapredJobTracker;
    }

    public void setMapredJobTracker(String mapredJobTracker) {
        this.mapredJobTracker = mapredJobTracker;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public String getIndexFolder() {
        return indexFolder;
    }

    public void setIndexFolder(String indexFolder) {
        this.indexFolder = indexFolder;
    }

    public String getSearchResultFolder() {
        return searchResultFolder;
    }

    public void setSearchResultFolder(String searchResultFolder) {
        this.searchResultFolder = searchResultFolder;
    }

    @Override
    public String toString() {
        return this.fsDefaultName +", "+ this.mapredJobTracker;
    }
}
