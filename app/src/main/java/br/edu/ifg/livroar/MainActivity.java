package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import br.edu.ifg.livroar.animation.TestAnimation;
import br.edu.ifg.livroar.model.AnimatedObject3D;
import br.edu.ifg.livroar.model.ObjParser;
import br.edu.ifg.livroar.model.Object3D;
import br.edu.ifg.livroar.util.RGBColor;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


public class MainActivity extends AndARActivity {

    private static final String TAG = "MainActivity";
    private ARToolkit arToolkit;
    private DefaultRenderer renderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            renderer = new DefaultRenderer();
            arToolkit = getArtoolkit();

//          Object3D monkey = new Object3D("monkey", "android.patt", ObjParser.loadObj(this, "monkey"));
            AnimatedObject3D monkey = new AnimatedObject3D("icosphere", "android.patt",
                    ObjParser.loadObj(this, "icosphere"), new TestAnimation());
            monkey.setPosition(50,0,0);

            setNonARRenderer(renderer); //adicionando o renderer

            arToolkit.registerARObject(monkey);

            startPreview();
        } catch (AndARException e) {
            e.printStackTrace();
            Log.e(TAG, "AndAR Exception: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IO Exception: "+e.getMessage());
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
