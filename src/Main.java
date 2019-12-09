import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("/Users/axel/Documents/Université/5A/S9/Analyse de données Hétérogènes/TPs/TP3/SortieXML.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("traceCMC");

        System.out.println("----------------------------");

        int i = 0;
        HashMap<Integer,HashMap<String,Integer[]>> results = new HashMap<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            Element eElement = (Element) nNode;

            Element action = (Element) eElement.getElementsByTagName("Action").item(0);
            Element user = (Element) eElement.getElementsByTagName("User").item(0);

            ConnexionInfos c = new ConnexionInfos(user.getTextContent());

            switch (action.getElementsByTagName("Type").item(0).getTextContent()){
                case "Connexion":
                    c = new ConnexionInfos(user.getTextContent());
                    break;
                case "Poster un nouveau message":
                    c.setWriteActivity(c.getWriteActivity()+1);
            }

            if (action.getElementsByTagName("Type").item(0).getTextContent().equals("Connexion")){
                System.out.println("CONNEXION !");
                i++;


            }

        }
    }

}
