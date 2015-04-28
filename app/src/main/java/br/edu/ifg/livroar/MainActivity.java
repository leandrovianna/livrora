package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


public class MainActivity extends AndARActivity {

    private static final String TAG = "MainActivity";
    private CarObject carObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            carObject = new CarObject();
            getArtoolkit().registerARObject(carObject);
            startPreview();
        } catch (AndARException e) {
            e.printStackTrace();
            Log.e(TAG, "AndARException disparada");
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }
}
