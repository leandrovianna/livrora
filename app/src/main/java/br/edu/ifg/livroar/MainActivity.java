package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

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

            CuboTest cuboVerde = new CuboTest("cuboVerde", "barcode.patt", new RGBColor(0,255,0));
            CuboTest cuboAzul = new CuboTest("cuboAzul", "patt.hiro", new RGBColor(0,0,255));
            CuboTest cuboVermelho = new CuboTest("cuboVermelho", "android.patt", new RGBColor(255,0,0));

            setNonARRenderer(renderer); //adicionando o renderer

            arToolkit.registerARObject(cuboAzul);
            arToolkit.registerARObject(cuboVerde);
            arToolkit.registerARObject(cuboVermelho);

            startPreview();
        } catch (AndARException e) {
            e.printStackTrace();
            Log.e(TAG, "AndAR Exception: " + e.getMessage());
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "AndAR Uncaught Exception: " + ex.getMessage());
        Log.e(TAG, "AndAR Uncaught Exception: Thread{" + thread.toString() + "}");
        finish();
    }
}
