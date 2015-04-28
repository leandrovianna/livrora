package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;
import edu.dhbw.andar.pub.CustomRenderer;


public class MainActivity extends AndARActivity {

    private static final String TAG = "MainActivity";
    private ARToolkit arToolkit;
    private CarObject carObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CustomRenderer renderer = new CustomRenderer();
            setNonARRenderer(renderer);

            carObject = new CarObject();
            arToolkit = getArtoolkit();

            arToolkit.registerARObject(carObject);
            startPreview();
        } catch (AndARException e) {
            Log.e(TAG, "AndAR Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "AndAR Exception: " + ex.getMessage());
        finish();
    }
}
