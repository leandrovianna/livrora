package br.edu.ifg.livroar.model;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Objeto 3D para ser adicionado a cena de Realidade Aumentada
 * Ele faz a ligacao entre o arquivo .patt (marca) e o modelo 3d
 * .obj
 * Created by leandro on 28/04/15.
 */
public class Object3D extends ARObject {

    private static final String TAG = "Object3D";
    protected Model model;

    private Vec3 position = new Vec3(0,0,0);
    private Vec3 rotation = new Vec3(0,0,0);

    /**
     * Constroi um Objeto3D compatível com o AndAR.
     * @param name Nome para o objeto, pode ser qualquer nome
     * @param patternName Nome do arquivo da marca (.patt)
     *                    que está na pasta assets do projeto
     */
    public Object3D(String name, String patternName, Model model) {
        //80.0 significa 80mm, o tamanho da marca matricial
        super(name, patternName, 80.0, new double[]{0,0});
        this.model = model;
    }

    public Object3D(String name, String patternName) {
        super(name, patternName, 80.0, new double[]{0,0});
    }

    @Override
    public void init(GL10 gl) {
        model.init(gl);
    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);

//        gl.glScalef(20,20,20);

        model.draw(gl);
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float angleX, float angleY, float angleZ){
        rotation.x = angleX;
        rotation.y = angleY;
        rotation.z = angleZ;
    }
}
