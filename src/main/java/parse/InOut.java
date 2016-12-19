package parse;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * <h1>parse ParseMapXML</h1>
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class InOut {

    /**
     * Load the XML file containing the maze and the objects in a DOM object
     *
     * @param filename String name of the file
     * @return A DOM object containing the content of the map, or null if a problem happened
     */
    public static Document loadMapXml(String filename) {
        Document doc;
        try {
            // Creation of a documents factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creation of a document builder
            DocumentBuilder parser = factory.newDocumentBuilder();
            // Loading of the XML file and converting in DOM
            File fdom = new File(filename);
            doc = parser.parse(fdom);
        } catch (Exception e) {
            System.out.println("Unable to load the " + filename + " file");
            return null;
        }
        System.out.println(filename + " has been loaded successfully");
        return doc;
    }

    /**
     * Save the XMl path in a file
     *
     * @param mazePath DOM object containing the path to pick every object needed
     * @param filename String name of the file
     * @return False if a problem happened, otherwise true
     */
    public static boolean saveOutputXML(Document mazePath, String filename) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            // Useful configuration of the transformer
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Saving of the DOM object in a XMl file
            File f = new File(filename);
            if (f.exists()) f.delete();
            StreamResult newFile = new StreamResult(f);
            transformer.transform(new DOMSource(mazePath), newFile);
        } catch (Exception e) {
            System.out.println("Unable to save the " + filename + " file");
            return false;
        }
        System.out.println("The path has been saved successfully in " + filename);
        return true;
    }

    /**
     * Read the config file and return the content in a List
     *
     * @param filename String name of the file
     * @return A List with the content of the file, or null if a problem happened
     */
    public static ArrayList<String> parseConfigTxt(String filename) {
        ArrayList<String> configContent = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename), Charset.forName("ISO-8859-1"))) {
            String line;
            while ((line = br.readLine()) != null)
                configContent.add(line);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find the " + filename + " file");
            return null;
        } catch (IOException e) {
            System.out.println("Error during the reading of the " + filename + " file");
            return null;
        }
        System.out.println(filename + " has been loaded successfully");
        return configContent;
    }
}
