package br.edu.ifg.livroar.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifg.livroar.util.RGBColor;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by leandro on 30/04/15.
 */
public class ObjParser {

    public static final String TAG = "ObjParser";

    public static ObjModel loadObj(Context context, String objPath) throws IOException
    {

        // Dados das vertices duplicadas, considerar usar multiplas partes por ObjModel
        List<Vec3> positions = new ArrayList<>();
        List<Vec3> positionsDupli = new ArrayList<>();

        List<Vec3> normals = new ArrayList<>();
        List<Vec3> normalsDupli = new ArrayList<>();

        List<Vec2> uvs = new ArrayList<>();
        List<Vec2> uvsDupli = new ArrayList<>();

        List<RGBColor> specularColorsDupli = new ArrayList<>();
        List<RGBColor> diffuseColorsDupli = new ArrayList<>();
        List<RGBColor> ambientColorsDupli = new ArrayList<>();

        Map<String, Material> materials = new HashMap<>();
        int vertsCount = 0;

        BufferedReader reader;
        String line;
        String[] lineParts;
        Material curMaterial = new Material();

        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(objPath+".obj")));
        while((line = reader.readLine())!=null){
//            Log.i(TAG, line);
            lineParts = line.split("[ ]+");
            switch (lineParts[0]){
                case "mtllib":
                    Log.d(TAG, "MTLLIB");
                    materials = MtlParser.loadMTL(context, lineParts[1]); //Nome do mtl == nome do obj
                    break;
                case "v":
                    parseV(positions, lineParts[1], lineParts[2], lineParts[3]);
                    break;
                case "vt":
                    parseVT(uvs, lineParts[1], lineParts[2]);
                    break;
                case "vn":
                    parseVN(normals, lineParts[1], lineParts[2], lineParts[3]);
                    break;
                case "usemtl":
                    Log.d(TAG, "usemtl");
                    if(!materials.isEmpty() && materials.get(lineParts[1])!=null){
                        curMaterial = materials.get(lineParts[1]);
                    }
                    else{
                        curMaterial = new Material("Null"); //Se material nao estiver carregado, setar cor para azul
                    }
                    break;
                case "f":
                    parseF(lineParts[1]+" "+lineParts[2]+" "+lineParts[3],
                            positions, positionsDupli,
                            normals, normalsDupli,
                            uvs, uvsDupli,
                            curMaterial, specularColorsDupli,
                            diffuseColorsDupli, ambientColorsDupli);
                    break;
            }
        }

        ObjModel objModel = new ObjModel();
        objModel.setPositionsBuffer(vec3fListToFloatBuffer(positionsDupli));
        objModel.setNormalsBuffer(vec3fListToFloatBuffer(normalsDupli));
        objModel.setUVsBuffer(vec2fListToFloatBuffer(uvsDupli));
        objModel.setSpecularColorsBuffer(rgbcolorfListToFloatBuffer(specularColorsDupli));
        objModel.setDiffuseColorsBuffer(rgbcolorfListToFloatBuffer(diffuseColorsDupli));
        objModel.setAmbientColorsBuffer(rgbcolorfListToFloatBuffer(ambientColorsDupli));
        objModel.setVertexCount(positionsDupli.size());

        return objModel;
    }

    private static void parseV(List<Vec3> positions, String x, String y, String z){
        positions.add(new Vec3(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(z)));
    }

    private static void parseVT(List<Vec2> uvs, String u, String v){
        uvs.add(new Vec2(Float.parseFloat(u),Float.parseFloat(v)));
    }

    private static void parseVN(List<Vec3> normals, String x, String y, String z){
        normals.add(new Vec3(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(z)));
    }

    private static void parseF(String lineParts1,
                               List<Vec3> positions, List<Vec3> positionsDupli,
                               List<Vec3> normals, List<Vec3> normalsDupli,
                               List<Vec2> uvs , List<Vec2> uvsDupli,
                               Material curMaterial, List<RGBColor> specularColorsDupli,
                               List<RGBColor> diffuseColorsDupli, List<RGBColor> ambientColorsDupli){

        //mudança: trocando indice 1 por 0
        String[] lineSubParts = lineParts1.split("[ ]+");
        if(lineSubParts[0].matches("[0-9]+")){ //Face composta de posicoes
            for (int i = 0; i < 3; i++) {
                short indx = Short.parseShort(lineSubParts[i]);
                indx--;
                positionsDupli.add(positions.get(indx));

                specularColorsDupli.add(curMaterial.getSpecular());
                diffuseColorsDupli.add(curMaterial.getDiffuse());
                ambientColorsDupli.add(curMaterial.getAmbient());
            }
        }else if(lineSubParts[0].matches("[0-9]+/[0-9]+")){//Face composta de posicoes e UVs
            for (int i = 0; i < 3; i++) {
                String[] l = lineSubParts[i].split("[/]+");
                short indx = Short.parseShort(l[0]);
                indx--;
                positionsDupli.add(positions.get(indx));

                indx = Short.parseShort(l[1]);
                indx--;
                uvsDupli.add(uvs.get(indx));

                specularColorsDupli.add(curMaterial.getSpecular());
                diffuseColorsDupli.add(curMaterial.getDiffuse());
                ambientColorsDupli.add(curMaterial.getAmbient());
            }
        }else if(lineSubParts[0].matches("[0-9]+//[0-9]+")){ //Face composta de posicoes e normais
            for (int i = 0; i < 3; i++) {
                String[] l = lineSubParts[i].split("[//]+");
                short indx = Short.parseShort(l[0]);
                indx--;
                positionsDupli.add(positions.get(indx));

                indx = Short.parseShort(l[1]);
                indx--;
                normalsDupli.add(normals.get(indx));

                specularColorsDupli.add(curMaterial.getSpecular());
                diffuseColorsDupli.add(curMaterial.getDiffuse());
                ambientColorsDupli.add(curMaterial.getAmbient());
            }
        }else if(lineSubParts[0].matches("[0-9]+/[0-9]+/[0-9]+")){//Face composta de posicoes, UVs e normais
            for (int i = 0; i < 3; i++) {
                String[] l = lineSubParts[i].split("[/]");
                short indx = Short.parseShort(l[0]);
                indx--;
                positionsDupli.add(positions.get(indx));

                indx = Short.parseShort(l[1]);
                indx--;
                uvsDupli.add(uvs.get(indx));

                indx = Short.parseShort(l[2]);
                indx--;
                normalsDupli.add(normals.get(indx));

                specularColorsDupli.add(curMaterial.getSpecular());
                diffuseColorsDupli.add(curMaterial.getDiffuse());
                ambientColorsDupli.add(curMaterial.getAmbient());
            }
        }

    }

    private static FloatBuffer vec3fListToFloatBuffer(List<Vec3> list){

        float[] floatArray = new float[list.size() * 3];

        int i = 0;
        for (Vec3 v : list){
            floatArray[i++] = v.x;
            floatArray[i++] = v.y;
            floatArray[i++] = v.z;
        }

        FloatBuffer floatBuffer = ByteBuffer
                .allocateDirect(floatArray.length * 4).
                        order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(floatArray);
        floatBuffer.position(0);
        floatBuffer.flip();

        return floatBuffer;
    }

    private static FloatBuffer vec2fListToFloatBuffer(List<Vec2> list){

        float[] floatArray = new float[list.size() * 2];

        int i = 0;
        for (Vec2 v : list){
            floatArray[i++] = v.x;
            floatArray[i++] = v.y;
        }

        FloatBuffer floatBuffer = ByteBuffer
                .allocateDirect(floatArray.length * 4).
                        order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(floatArray);
        floatBuffer.position(0);

        return floatBuffer;
    }

    private static FloatBuffer rgbcolorfListToFloatBuffer(List<RGBColor> list){

        float[] floatArray = new float[list.size() * 3];

        int i = 0;
        for (RGBColor c : list){
            floatArray[i++] = c.getRed();
            floatArray[i++] = c.getGreen();
            floatArray[i++] = c.getBlue();
        }

        FloatBuffer floatBuffer = ByteBuffer
                .allocateDirect(floatArray.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(floatArray);
        floatBuffer.position(0);

        return floatBuffer;
    }

    private static ShortBuffer shortListToShortBuffer(List<Short> list){

        short[] floatArray = new short[list.size()];

        for (int j = 0; j < list.size(); j++) {
            floatArray[j] = list.get(j);
        }

        ShortBuffer shortBuffer = ByteBuffer
                .allocateDirect(floatArray.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(floatArray);
        shortBuffer.position(0);

        return shortBuffer;
    }


}
