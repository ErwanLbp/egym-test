package inout;

import log.Log;
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
 * The class that will manage all the in/out of the application, the parsing of the XMl files, the saving of the route, the parse of the config
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
            // Getting a parser for the Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            // Loading of the XML file and converting it to DOM
            File fdom = new File(filename);
            doc = parser.parse(fdom);
        } catch (Exception e) {
            Log.error("Unable to load the " + filename + " file");
            return null;
        }
        Log.success(filename + " has been loaded successfully");
        return doc;
    }

    /**
     * Save the XMl route in a file
     *
     * @param mazeRoute DOM object containing the route to pick every object needed
     * @param filename  String name of the file
     * @return False if a problem happened, otherwise true
     */
    public static boolean saveOutputXML(Document mazeRoute, String filename) {
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
            transformer.transform(new DOMSource(mazeRoute), newFile);
        } catch (Exception e) {
            Log.error("Unable to save the " + filename + " file");
            return false;
        }
        Log.success("The route has been saved successfully in " + filename);
        return true;
    }

    /**
     * Read the config file and return the content in a List of String
     *
     * @param filename String name of the file
     * @return A List with the content of the file, or null if a problem happened
     */
    public static ArrayList<String> parseConfigTxt(String filename) {
        ArrayList<String> configContent = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename), Charset.forName("ISO-8859-1"))) {
            String line;
            // We store each line of the file in the list
            while ((line = br.readLine()) != null)
                configContent.add(line);
        } catch (FileNotFoundException e) {
            Log.error("Unable to find the " + filename + " file");
            return null;
        } catch (IOException e) {
            Log.error("Error during the reading of the " + filename + " file");
            return null;
        }
        Log.success(filename + " has been loaded successfully");
        return configContent;
    }
}
