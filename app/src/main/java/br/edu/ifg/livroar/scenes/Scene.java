package br.edu.ifg.livroar.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Material;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class Scene extends ARObject {

    private List<Geometry> geometries = new ArrayList<>();
    private Map<String, LRSAnimation> PRSAnimations = new HashMap<>();
    private List<Material> materials = new ArrayList<>();

    private double curTime = 0, lastTime = 0, deltaTime = 0;

    public Scene(String name, String patternName) {
        super(name, patternName, 80.0, new double[]{0,0});
    }

    @Override
    public void init(GL10 gl) {
        for(Material m : materials){
            if(m.getTexture()!=null){
                m.setTexureId(gl);
            }
        }
        for(Geometry g : geometries) {
            g.init(gl);
        }
    }

    @Override
    public void draw(GL10 gl) {

        curTime = System.currentTimeMillis();

        for(Geometry g : geometries){
            //TODO: Matriz de trans/rot/scale independente para cada geometry

            if(g.getAnimationName()=="null")
                g.draw(gl);
            else
                g.draw(gl, PRSAnimations.get(g.getAnimationName()), curTime, deltaTime);
        }

        deltaTime = curTime - lastTime;
        lastTime = curTime;
    }

}
