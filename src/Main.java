import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("D:\\Téléchargements\\SortieXML.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("traceCMC");

        System.out.println("----------------------------");

        int i = 0;
        HashMap<Integer,ConnexionInfos> results = new HashMap<>();
        ConnexionInfos c = new ConnexionInfos("","");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            Element eElement = (Element) nNode;

            Element action = (Element) eElement.getElementsByTagName("Action").item(0);
            Element user = (Element) eElement.getElementsByTagName("User").item(0);
            Element m = (Element) eElement.getElementsByTagName("Message").item(0);
            Element date = (Element) m.getElementsByTagName("Date").item(0);

            switch (action.getElementsByTagName("Type").item(0).getTextContent()){
                case "Connexion":
                    if (i > 0){
                        results.put(i,c);
                    }
                    c = new ConnexionInfos(user.getTextContent(),date.getTextContent());
                    i++;
                    break;
                case "Poster un nouveau message":
                case "Répondre à un message":
                case "Citer un message":
                    c.setWriteActivity(c.getWriteActivity()+1);
                    break;
                case "Afficher une structure (cours/forum)":
                case "Afficher le contenu d'un message":
                case "Afficher le fil de discussion":
                    c.setReadActivity(c.getReadActivity()+1);
                    break;
            }

        }
        results.put(i,c);
        System.out.println("Nb cos : "+results.size());
        File f = new File("results.csv");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
        bw.write("date;user;nbWrite;nbRead;txWrite;txRead");
        bw.newLine();
        for (int j = 1; j < results.size()+1; j++){
            ConnexionInfos ci = results.get(j);
            if (ci != null) {
                double txWrite = ci.getWriteActivity();
                txWrite /= ci.getTotalActivity();
                double txRead = ci.getReadActivity();
                txRead /= ci.getTotalActivity();
                if (ci.getTotalActivity() == 0) txWrite = txRead = 0;
                bw.write(ci.date+";"+ci.user+";"+ci.getWriteActivity()+";"+ci.getReadActivity()+";"+txWrite+";"+txRead);
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
    }

}
