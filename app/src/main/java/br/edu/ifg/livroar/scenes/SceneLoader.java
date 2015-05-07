package br.edu.ifg.livroar.scenes;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import br.edu.ifg.livroar.App;
import br.edu.ifg.livroar.model.Material;


/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class SceneLoader {

    public static Scene loadScene(String filePath) {

        Context context = App.getContext();
        List<Geometry> geometries = new ArrayList<>();
        Map<String, LRSAnimation> PRSAnimations = new HashMap<>();
        List<Material> materials = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document sceneDoc = dBuilder.parse(context.getAssets().open("scenes/" + filePath + ".dae"));
            sceneDoc.normalize();

            Element collada = (Element) sceneDoc.getElementsByTagName("COLLADA").item(0);

            //TODO: Avaliar elementos necessarios
            // Parse dos elementos
            Element library_images = (Element) collada.getElementsByTagName("library_images").item(0);
            if(library_images != null)
                parseLibraryImages(library_images);

            Element library_effects = (Element) collada.getElementsByTagName("library_effects").item(0);
            if(library_effects != null)
                parseLibraryEffects(library_effects);

            Element library_materials = (Element) collada.getElementsByTagName("library_materials").item(0);
            if(library_materials != null)
                parseLibraryMaterials(library_materials);

            Element library_geometries = (Element) collada.getElementsByTagName("library_geometries").item(0);
            if(library_geometries != null)
                parseLibraryGeometries(library_geometries);

            Element library_animations = (Element) collada.getElementsByTagName("library_animations").item(0);
            if(library_animations != null)
                parseLibraryAnimations(library_animations);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Scene scene = new Scene();
        return null;
    }

    private static void parseLibraryAnimations(Element library_animations) {

    }

    private static void parseLibraryGeometries(Element library_geometries) {

    }

    private static void parseLibraryMaterials(Element library_materials) {

    }

    private static void parseLibraryEffects(Element library_effects) {

    }

    private static void parseLibraryImages(Element library_images){

    }

}
