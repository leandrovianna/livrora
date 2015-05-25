package br.edu.ifg.livroar.scenes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import br.edu.ifg.livroar.App;
import br.edu.ifg.livroar.model.Material;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 20/05/2015.
 */
public class AutoLoadScene extends ARObject {

    public static final String TAG = "AutoLoadScene";

    private String fileName;
    private volatile List<AutoLoadGeometry> geometries;

    public AutoLoadScene(String fileName, String patternName) {
        super(fileName, patternName, 80.0, new double[]{0,0});
        this.fileName = fileName;
        geometries = new ArrayList<>(0);
    }

    @Override
    public void init(GL10 gl) {
        new SceneLoaderTask().execute(fileName);
    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);
        for (AutoLoadGeometry g : geometries)
        {
            if(g.wasInitialized())
            {
                g.draw(gl);
            }else
                g.init(gl);
        }
    }

    public synchronized List<AutoLoadGeometry> getGeometries() {
        return geometries;
    }

    public synchronized void setGeometries(List<AutoLoadGeometry> geometries) {
        this.geometries = geometries;
    }

    class SceneLoaderTask extends AsyncTask<String, Integer, List<AutoLoadGeometry>> {

        @Override
        protected List<AutoLoadGeometry> doInBackground(String... params) {
            if(params.length>0)
            {
                Context context = App.getContext();
                List<AutoLoadGeometry> geometries = new ArrayList<>();
                Map<String, Animation> animations = new HashMap<>();
                Map<String, Material>  materials = new HashMap<>();
                Map<String, SceneLoader.SceneNode> geomidNodeMap = new HashMap<>();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                Element collada = null;
                try {
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document sceneDoc = dBuilder.parse(context.getAssets().open("scenes/" + fileName + ".dae"));
                    sceneDoc.normalize();
                    Log.d(TAG, "Arquivo collada " + fileName + " aberto com sucesso");
                    collada = (Element) sceneDoc.getElementsByTagName("COLLADA").item(0);
                } catch (ParserConfigurationException |
                        SAXException |
                        IOException e) {
                    e.printStackTrace();
                }

                if(collada!=null)
                {
                    materials = SceneLoader.getMaterials(context,
                            (Element) collada.getElementsByTagName("library_images").item(0),
                            (Element) collada.getElementsByTagName("library_effects").item(0));

                    this.publishProgress(25);

                    Element library_animations = (Element) collada.getElementsByTagName("library_animations").item(0);
                    if(library_animations != null)
                        animations = SceneLoader.parseLibraryAnimations(library_animations);

                    this.publishProgress(50);

                    Element library_visual_scenes = (Element) collada.getElementsByTagName("library_visual_scenes").item(0);
                    if(library_visual_scenes != null)
                        geomidNodeMap = SceneLoader.parseLibraryVisualScenes(library_visual_scenes);

                    this.publishProgress(75);

                    Element library_geometries = (Element) collada.getElementsByTagName("library_geometries").item(0);
                    if(library_geometries != null)
                        geometries = SceneLoader.parseLibraryGeometriesAL(library_geometries, geomidNodeMap, animations, materials);

                    this.publishProgress(100);

                    return geometries;
                }
            }
            return new ArrayList<>(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "Parse do arquivo " + fileName + " " + values[0] + "% completo");
        }

        @Override
        protected void onPostExecute(List<AutoLoadGeometry> geometries) {
            setGeometries(geometries);
        }
    }
}
