package br.edu.ifg.livroar;

import android.app.Application;
import android.content.Context;

/**
 * <p>Objeto que representa a Aplicação Android</p>
 * <p>Pode ser usado para ter uma instancia do contexto</p>
 * Created by leandro on 07/05/15.
 */
public class LivroApp extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }
}
