package br.edu.ifg.livroar;

import android.app.Application;
import android.content.Context;

/**
 * <p>Objeto que representa a Aplica&ccedil;&atilde;o Android</p>
 * <p>Pode ser usado para ter uma inst&acirc;ncia do contexto</p>
 * Created by leandro on 07/05/15.
 */
public class App extends Application {

    private static App singleton;

    public static Context getContext() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
