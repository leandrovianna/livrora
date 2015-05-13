package br.edu.ifg.livroar.scenes;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Material;
import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.exceptions.AndARException;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class Scene {

    private List<Geometry> geometries = new ArrayList<>();

    public Scene(List<Geometry> geometries) {
        this.geometries = geometries;
    }

    public void registerGeometries(ARToolkit arToolkit) throws AndARException {
        for (Geometry g : geometries){
            arToolkit.registerARObject(g);
        }
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }
}
