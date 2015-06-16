package br.edu.ifg.livroar.assets;

import java.util.List;

import br.edu.ifg.livroar.scenes.SceneObject;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public interface Asset3DLoader
{
    List<SceneObject> loadObjects(String sceneName, String ... names);
    String getFilePath();
}
