package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import br.edu.ifg.livroar.model.ObjParser;
import br.edu.ifg.livroar.model.Object3D;
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

            Object3D icosphere = new Object3D("icosphere", "android.patt", ObjParser.loadObj(this, "icosphere"));

            setNonARRenderer(renderer); //adicionando o renderer

            arToolkit.registerARObject(icosphere);

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
