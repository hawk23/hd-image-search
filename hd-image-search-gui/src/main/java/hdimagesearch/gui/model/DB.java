package hdimagesearch.gui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 07.07.15.
 */
public class DB
{
    private static DB instance;
    private List<ClusterConfiguration> clusterConfigurations;

    private DB()
    {
        this.clusterConfigurations = new ArrayList<ClusterConfiguration>();
        this.clusterConfigurations.add(new ClusterConfiguration(
                "hdfs://localhost:54310",
                "localhost:54311",
                "/images/png2",
                "/index",
                "/searchResult"));
    }

    public static DB instance()
    {
        if (instance == null)
        {
            instance = new DB();
        }

        return instance;
    }

    public List<ClusterConfiguration> getClusterConfigurations() {
        return clusterConfigurations;
    }

    public void setClusterConfigurations(List<ClusterConfiguration> clusterConfigurations) {
        this.clusterConfigurations = clusterConfigurations;
    }
}
