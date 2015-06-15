package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import br.edu.ifg.livroar.scenes.AutoLoadScene;
import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


public class MainActivity extends AndARActivity {

    private static final String TAG = "MainActivity";
    private ARToolkit arToolkit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            DefaultRenderer renderer = new DefaultRenderer();
            arToolkit = getArtoolkit();

            setNonARRenderer(renderer); //adicionando o renderer
//            Scene atomTestScene = SceneLoader.loadScene("cenario", "android.patt");
//            atomTestScene.registerGeometries(arToolkit);
//  arToolkit.registerARObject(a);
//            arToolkit.registerARObject(b);
            AutoLoadScene cenario = new AutoLoadScene("cenario", "android.patt");
            arToolkit.registerARObject(cenario);
            AutoLoadScene kenny = new AutoLoadScene("cena_01_kenny", "android.patt");
            arToolkit.registerARObject(kenny);
            AutoLoadScene tric = new AutoLoadScene("cena_01_tric", "android.patt");
            arToolkit.registerARObject(tric);

            startPreview();
        } catch (AndARException e) {
            e.printStackTrace();
            Log.e(TAG, "AndAR Exception: " + e.getMessage());
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        Log.e(TAG, "AndAR Uncaught Exception: " + ex.getMessage());
        Log.e(TAG, "AndAR Uncaught Exception: " + thread.toString());
        finish();
    }
}
