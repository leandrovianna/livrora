package br.edu.ifg.livroar;

import android.os.Bundle;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import br.edu.ifg.livroar.scenes.Scene;
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

            Scene kenny = new Scene("android.patt", "scenes/triangle.dae");
            arToolkit.registerARObject(kenny);

            startPreview();
        } catch (AndARException e) {
            e.printStackTrace();
            Log.e(TAG, "AndAR Exception: " + e.getMessage());
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
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
