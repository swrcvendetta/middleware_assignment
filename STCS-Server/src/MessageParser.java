
import Primitives.MessageRecord;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * The MessageParser class provides functionality to read and write XML files containing MessageRecord objects.
 * It supports reading files in a specific XML schema, parsing XML content, and writing MessageRecord objects to XML files.
 */
public class MessageParser {
    private NodeList list;

    /**
     * Constructs a MessageParser object.
     */
    public MessageParser() {

    }

    /**
     * Reads an XML file with the specified source and validates it against a given XSD schema.
     *
     * @param source     The path to the XML file.
     * @param validation The path to the XSD schema for validation. Pass null or an empty string to skip validation.
     * @return True if the file is successfully read and validated, false otherwise.
     */
    public boolean readFileFormat(String source, String validation) {
        if(source == null || source.isEmpty())
            return false;

        if(validation != null || validation.length() > 4)
            if(!validateXMLSchema(source, validation))
                return false;

        File f = new File(source);
        if(!f.exists())
            return false;

        /*
            https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
         */

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(source));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            this.list = doc.getElementsByTagName("message-object");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Parses all MessageRecord objects from the previously read XML file.
     *
     * @return An ArrayList containing all parsed MessageRecord objects.
     */
    public ArrayList<MessageRecord> parseAllObjects() {
        ArrayList<MessageRecord> messages = new ArrayList<>();
        for (int temp = 0; temp < this.list.getLength(); temp++) {

            Node node = this.list.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                messages.add(parseAllData(element));
            }
        }
        return messages;
    }

    /**
     * Parses MessageRecord data from an XML element.
     *
     * @param elem The XML element containing message data.
     * @return A MessageRecord object with parsed data.
     */
    private MessageRecord parseAllData(Element elem) {
        String user = elem.getElementsByTagName("user").item(0).getTextContent();
        String message = elem.getElementsByTagName("message").item(0).getTextContent();
        if(message.equals("null"))
            message = null;
        String rawTimestamp = elem.getElementsByTagName("timestamp").item(0).getTextContent();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(rawTimestamp);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return new MessageRecord(
                    user,
                    message,
                    timestamp
            );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    // https://mkyong.com/java/how-to-create-xml-file-in-java-dom/

    /**
     * Writes MessageRecord objects to an XML file in a specific format.
     *
     * @param source   The path to the output XML file.
     * @param messages The list of MessageRecord objects to write to the file.
     * @return True if the writing process is successful, false otherwise.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     */
    public boolean writeFileFormat(String source, ArrayList<MessageRecord> messages) throws ParserConfigurationException {
        if(source.isEmpty() || messages.isEmpty())
            return false;

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("chatlog");
        doc.appendChild(rootElement);

        for(MessageRecord msg : messages) {

            // add xml elements
            Element metadata = doc.createElement("message-object");
            // add staff to root
            rootElement.appendChild(metadata);

            Element user = doc.createElement("user");
            user.setTextContent(String.valueOf(msg.user()));
            metadata.appendChild(user);

            Element message = doc.createElement("message");
            message.setTextContent(String.valueOf(msg.message()));
            metadata.appendChild(message);

            Element timestamp = doc.createElement("timestamp");
            timestamp.setTextContent(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(msg.timestamp()));
            metadata.appendChild(timestamp);
        }


        File file = new File(source);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            writeXml(doc, fos);
            fos.flush();
            fos.close();
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Writes an XML document to an output stream.
     *
     * @param doc    The Document object to write.
     * @param output The OutputStream where the XML document is written.
     * @throws TransformerException If an error occurs during the transformation process.
     */
    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    // https://www.digitalocean.com/community/tutorials/how-to-validate-xml-against-xsd-in-java
    /**
     * Validates an XML file against an XSD schema.
     *
     * @param xmlPath The path to the XML file for validation.
     * @param xsdPath The path to the XSD schema file.
     * @return True if the XML file is valid according to the schema, false otherwise.
     */
    public static boolean validateXMLSchema(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
