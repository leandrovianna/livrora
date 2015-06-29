package br.edu.ifg.livroar;

import android.app.Application;
import android.content.Context;

/**
 * <p>Objeto que representa a Aplicacao Android</p>
 * <p>Pode ser usado para ter uma instancia do contexto</p>
 * Created by leandro on 07/05/15.
 */
public class App extends Application {

    private static App singleton;

    public App() {}

    public static Context getContext() {
        return singleton;
    }

    public static App getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
