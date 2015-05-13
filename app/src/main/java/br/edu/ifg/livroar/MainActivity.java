package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import br.edu.ifg.livroar.animation.TestAnimation;
import br.edu.ifg.livroar.animation.TestAnimation1;
import br.edu.ifg.livroar.model.AnimatedObject3D;
import br.edu.ifg.livroar.model.ObjParser;
import br.edu.ifg.livroar.scenes.Scene;
import br.edu.ifg.livroar.scenes.SceneLoader;
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

//            AnimatedObject3D a = new AnimatedObject3D("icosphere", "android.patt",
//                    ObjParser.loadObj(this, "icospherehires"), new TestAnimation());
//            a.setPosition(100, 0, 0);
//            AnimatedObject3D b = new AnimatedObject3D("icosphere", "android.patt",
//                    ObjParser.loadObj(this, "icospherehires"), new TestAnimation1());
//            b.setPosition(20, 0, 0);
            setNonARRenderer(renderer); //adicionando o renderer

//            Scene triangleScene = SceneLoader.loadScene("triangle", "android.patt");
//            triangleScene.registerGeometries(arToolkit);
            Scene triangle1 = SceneLoader.loadScene("triangle1", "android.patt");
            triangle1.registerGeometries(arToolkit);
//  arToolkit.registerARObject(a);
//            arToolkit.registerARObject(b);

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
