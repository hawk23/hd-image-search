package hdimagesearch.core;

import net.semanticmetadata.lire.imageanalysis.CEDD;
import net.semanticmetadata.lire.imageanalysis.ColorLayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hduser on 04.07.15.
 */
public class HistogramGenerator {

    public double[] generate(byte[] imageByte) {

        // convert byte array to BufferedImage
        InputStream in = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // extract histogram using LIRE
        double[] histogram =  extract_feature(bufferedImage);

        return histogram;
    }

    public double[] extract_feature(BufferedImage bimg) {

        CEDD feature = new CEDD ();
        feature.extract(bimg);

        return feature.getDoubleHistogram();
    }
}
