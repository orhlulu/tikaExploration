package gem;


import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class tikaExtractor {

    public static void main(String[] args) {
        System.out.println("Hello, World !!!");

        String pathFolder = "C://Users//user//Documents//WFH//tika exploration//samples";
        try {
            walkPath(pathFolder);
        }catch (IOException ex) {

        }
        /*
        Path path = Paths.get(fileName);
        try (InputStream inputStream = Files.newInputStream(path)) {
            String result = detectDocTypeUsingFacade(inputStream);
            System.out.println(result);
        } catch (IOException io) {

        }

         */

    }

    public static void walkPath (String path) throws IOException{
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            List<Path> results = walk.filter(Files::isRegularFile)
                    .map(x -> x.toAbsolutePath()).collect(Collectors.toList());

            for (Path result : results) {
                System.out.println("each result is " + result.toAbsolutePath());
                try (InputStream inputStream = Files.newInputStream(result.toAbsolutePath())) {
                    String docType = detectDocTypeUsingFacade(inputStream);
                    System.out.println("filename is " + result.toString() + " doctype is : " + docType);
                } catch (IOException io) {
                    System.out.println(io.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detectDocTypeUsingDetector(InputStream stream)
            throws IOException {
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();

        MediaType mediaType = detector.detect(stream, metadata);
        return mediaType.toString();
    }

    public static String detectDocTypeUsingFacade(InputStream stream)
            throws IOException {

        Tika tika = new Tika();
        String mediaType = tika.detect(stream);
        return mediaType;
    }
}
