package br.edu.ifg.livroar.scenes;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.ParserConfigurationException;

import br.edu.ifg.livroar.assets.Asset3DLoader;
import br.edu.ifg.livroar.assets.ColladaLoader;
import edu.dhbw.andar.ARObject;

import static br.edu.ifg.livroar.util.Utils.DoubleArrayToFloatArray;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class Scene extends ARObject
{
    public static final String TAG = "TAG";
    public static final long NANOS_PER_SEC = 1000000000L;

    private AssetLoaderTask loaderTask;
    private volatile List<SceneObject> objects;

    private float[] sceneTransMatrix;

    public Scene()
    {
        super(null, null, 80.0, new double[]{0, 0});
    }

    public Scene(String patternName, String filePath, String... objectNames) throws ParserConfigurationException, SAXException, IOException
    {
        super(filePath, patternName, 80.0, new double[]{0, 0});
        loaderTask = new AssetLoaderTask(filePath);
        objects = new ArrayList<>();
    }

    @Override
    public void init(GL10 gl)
    {
        loaderTask.execute();
    }

    @Override
    public synchronized void draw(GL10 gl)
    {
        super.draw(gl);
        sceneTransMatrix = DoubleArrayToFloatArray(this.getTransMatrix());
        float curTime = (float) System.nanoTime()/ NANOS_PER_SEC;
        synchronized (objects)
        {
            for (SceneObject g : objects)
            {
                g.draw(gl, curTime);
                gl.glLoadMatrixf(sceneTransMatrix, 0);
            }
        }
    }

    public List<SceneObject> getObjects()
    {
        return objects;
    }

    public synchronized void setObjects(List<SceneObject> objects) {
        this.objects = objects;
    }

    class AssetLoaderTask extends AsyncTask<String, Integer, List<SceneObject>>
    {
        private Asset3DLoader loader;
        private String[] objectNames;

        public AssetLoaderTask(String filePath, String ... objectNames) throws IOException, SAXException, ParserConfigurationException
        {
            this.objectNames = objectNames;
            if(filePath.endsWith(".dae"))
                loader = new ColladaLoader(filePath);
            //TODO: load de outros tipos de arquivo?
//            else if(filePath.endsWith(".obj"));
        }

        @Override
        protected List<SceneObject> doInBackground(String... params)
        {
            //TODO: presumindo nome da cena, corrigir
            return loader.loadObjects("Scene", objectNames);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            Log.d(TAG, "Carregamento de '" + loader.getFilePath() + "' " + values[0] + "% completo");
        }

        @Override
        protected void onPostExecute(List<SceneObject> sceneObjects)
        {
            setObjects(sceneObjects);
        }
    }
}
