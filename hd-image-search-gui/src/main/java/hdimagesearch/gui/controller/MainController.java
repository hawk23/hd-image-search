package hdimagesearch.gui.controller;

import hdimagesearch.core.FeatureExtract;
import hdimagesearch.core.HistogramGenerator;
import hdimagesearch.core.ImageFeature;
import hdimagesearch.core.ImageSearch;
import hdimagesearch.gui.events.Event;
import hdimagesearch.gui.events.EventTypes;
import hdimagesearch.gui.forms.MainForm;
import hdimagesearch.gui.model.ClusterConfiguration;
import hdimagesearch.gui.model.DB;
import hdimagesearch.gui.model.ImageListItem;
import hdimagesearch.gui.threading.JobWatcher;
import hdimagesearch.gui.util.ImageListRenderer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import sun.awt.windows.ThemeReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by mario on 05.07.15.
 */
public class MainController extends Controller
{
    private MainForm mainForm;
    private ImageFeature sampleFeatures;
    private HistogramGenerator histogramGenerator;
    private LogController logController;

    public MainController(MainForm mainForm)
    {
        super(mainForm);

        this.mainForm = mainForm;

        this.init();
    }

    private void init ()
    {
        this.histogramGenerator = new HistogramGenerator();

        for (ClusterConfiguration clusterConfiguration : DB.instance().getClusterConfigurations()) {
            this.mainForm.getCbCluster().addItem(clusterConfiguration);
        }

        this.mainForm.getLstResult().setCellRenderer(new ImageListRenderer());
        this.mainForm.getSpinner1().setValue(10);

        this.logController = new LogController(this.mainForm.getTxtLog());
    }

    private void startSearch ()
    {
        ClusterConfiguration selectedConfig = (ClusterConfiguration) this.mainForm.getCbCluster().getSelectedItem();

        if (selectedConfig != null && this.sampleFeatures != null)
        {
            Integer numResults = (Integer) this.mainForm.getSpinner1().getValue();

            // TODO: pass values (fsDefaultName, mapredJobTracker) from selectedConfig as parameters to ImageSearch.main
            // TODO: Start search like ExtractFeature task using JobWatcher.

            try {
                logController.log("Start image search!");

                final Job job = ImageSearch.imageSearch(selectedConfig.getIndexFolder(), selectedConfig.getSearchResultFolder(), numResults, this.sampleFeatures.getHistogramString(), selectedConfig.getHadoopConfig());

                JobWatcher watcher = new JobWatcher(job, logController);
                watcher.start();
                watcher.join();

                if (job.isComplete()) {
                    this.showSearchResults();
                }
            }
            catch (Exception ex) {
                logController.error(ex.getMessage());
                System.err.print(ex);
            }
        }
    }

    private void showSearchResults ()
    {
        logController.log("displaying search results.");

        ClusterConfiguration selectedConfig = (ClusterConfiguration) this.mainForm.getCbCluster().getSelectedItem();

        if (selectedConfig != null) {
            Configuration conf    = selectedConfig.getHadoopConfig();

            try {
                FileSystem fs= FileSystem.get(conf);
                ArrayList<String> results = new ArrayList<String>();

                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(new Path(selectedConfig.getSearchResultFolder() + "/part-r-00000"))));
                String line;
                line=br.readLine();
                while (line != null){
                    logController.log("result image: " + line);
                    System.out.println(line);

                    String searchResultImagePath = line.split("\t")[1];
                    results.add(searchResultImagePath);
                    line=br.readLine();
                }

                br.close();
                fs.close();

                // TODO: display search results more beautiful: e.g. text below images
                DefaultListModel listModel = new DefaultListModel();

                for (String result : results) {
                    byte[] searchResultImageBytes = ImageSearch.loadImage(new Path(result), conf);

                    ImageListItem imageListItem = new ImageListItem(result, searchResultImageBytes);
                    listModel.addElement(imageListItem);
                }

                this.mainForm.getLstResult().setModel(listModel);
            }
            catch (Exception ex) {
                logController.error(ex.getMessage());
                System.err.print(ex);
            }
        }
    }

    private void selectInputImage ()
    {
        int returnVal = this.mainForm.getFileChooser().showOpenDialog(this.mainForm.getPnlMain());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = this.mainForm.getFileChooser().getSelectedFile();

            try
            {
                byte[] imageByte = Files.readAllBytes(file.toPath());
                double[] features = histogramGenerator.generate(imageByte);
                this.sampleFeatures = new ImageFeature(file.getPath(), features);
                this.mainForm.getImgSample().setIcon(new ImageIcon(imageByte));
                this.mainForm.getTxtInputImage().setText(file.getPath());
            }
            catch (Exception ex) {
                logController.error(ex.getMessage());
                System.err.print(ex);
            }
        }
    }

    private void extractFeatures ()
    {
        final ClusterConfiguration selectedConfig = (ClusterConfiguration) this.mainForm.getCbCluster().getSelectedItem();

        if (selectedConfig != null)
        {
            try {
                logController.log("Start extracting features!");
                final Job job = FeatureExtract.featureExtract(selectedConfig.getImageFolder(), selectedConfig.getIndexFolder(), selectedConfig.getHadoopConfig());

                JobWatcher watcher = new JobWatcher(job, logController);
                watcher.start();
            }
            catch (Exception ex) {
                logController.error(ex.getMessage());
                System.err.print(ex);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Event) {
            Event event = (Event) o;

            switch (event.getType())
            {
                case START_SEARCH:
                    this.startSearch();
                    break;
                case SELECT_INPUT_IMAGE:
                    this.selectInputImage();
                    break;
                case EXTRACT_FEATURES:
                    this.extractFeatures();
                    break;
            }
        }
    }
}
