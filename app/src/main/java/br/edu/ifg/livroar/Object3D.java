package br.edu.ifg.livroar;

import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.ARObject;

/**
 * Objeto 3D para ser adicionado a cena de Realidade Aumentada
 * Ele faz a ligacao entre o arquivo .patt (marca) e o modelo 3d
 * .obj
 * Created by leandro on 28/04/15.
 */
public class Object3D extends ARObject {

    private static final String TAG = "Object3D";
    private final String objName;

    /**
     * Constroi um Objeto3D compatível com o AndAR.
     * @param name Nome para o objeto, pode ser qualquer nome
     * @param patternName Nome do arquivo da marca (.patt)
     *                    que está na pasta assets do projeto
     * @param objName Nome do arquivo do modelo 3D (.obj) e
     *                do arquivo de textura (.mtl).
     *                Eles devem ter o mesmo nome.
     */
    public Object3D(String name, String patternName, String objName) {
        //80.0 significa 80mm, o tamanho da marca matricial
        super(name, patternName, 80.0, new double[]{0,0});

        this.objName = objName;

        //TODO: fazer conversão do arquivo obj em modelo opengl
    }

    @Override
    public void init(GL10 gl10) {

    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);
    }
}
