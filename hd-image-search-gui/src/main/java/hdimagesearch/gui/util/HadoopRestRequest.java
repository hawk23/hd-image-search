package hdimagesearch.gui.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by mario on 05.07.15.
 */
public class HadoopRestRequest
{
    public static String HOST           = "http://localhost:50070/";    // TODO: define in GUI
    public static String HADOOP_API     = "webhdfs/v1/";

    private String url;
    private String operation;

    public HadoopRestRequest(String url, String operation)
    {
        this.url            = url;
        this.operation      = operation;
    }

    public JSONObject send ()
    {
        JSONObject jsonResponse = null;

        try
        {
            HttpClient          httpClient      = HttpClientBuilder.create().build();
            HttpUriRequest      getRequest      = new HttpGet(HOST + HADOOP_API + this.url + this.operation);
            getRequest.addHeader("accept", "application/json");

            HttpResponse        response        = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String responseString = "";
            String output;
            while ((output = reader.readLine()) != null)
            {
                responseString += output;
            }

            jsonResponse = new JSONObject(responseString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonResponse;
    }
}
