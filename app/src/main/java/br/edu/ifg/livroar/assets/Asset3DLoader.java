package br.edu.ifg.livroar.assets;

import br.edu.ifg.livroar.scenes.Scene;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public interface Asset3DLoader
{
    void loadObjects(Scene scene, String sceneName, String ... names);
    String getFilePath();
}
