package br.edu.ifg.livroar.model;

import android.content.Context;

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

import br.edu.ifg.livroar.RGBColorF;
import br.edu.ifg.livroar.Vec2f;
import br.edu.ifg.livroar.Vec3f;

/**
 * Created by leandro on 30/04/15.
 */
public class ObjParser {

    public static ObjModel loadObj(Context context, String objPath) {

        // Dados das vertices duplicadas, considerar usar multiplas partes por ObjModel
        List<Vec3f> positions = new ArrayList<>();
        List<Vec3f> positionsDupli = new ArrayList<>();

        List<Vec3f> normals = new ArrayList<>();
        List<Vec3f> normalsDupli = new ArrayList<>();

        List<Vec2f> uvs = new ArrayList<>();
        List<Vec2f> uvsDupli = new ArrayList<>();

        List<RGBColorF> colors = new ArrayList<>();
        List<RGBColorF> colorsDupli = new ArrayList<>();

        Map<String, MtlMaterial> materials = new HashMap<>();
        int vertsCount = 0;

        BufferedReader reader;
        String line;
        String[] lineParts;
        RGBColorF curColor = new RGBColorF(0,0,1);

        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(objPath+".obj")));
            while((line = reader.readLine())!=null){
                lineParts = line.split("[ ]+");
                switch (lineParts[0]){
                    case "mtllib":
                        materials = MtlParser.loadMTL(context, objPath); //Nome do mtl == nome do obj
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
                        if(!materials.isEmpty() && materials.get(lineParts[1])!=null){
                            curColor = materials.get(lineParts[1]).getDiffuse();
                        }
                        else{
                            curColor = new RGBColorF(0,0,1); //Se material nao estiver carregado, setar cor para azul
                        }
                        break;
                    case "f":
                        parseF(lineParts[1],
                                positions, positionsDupli,
                                normals, normalsDupli,
                                uvs, uvsDupli,
                                curColor, colorsDupli);
                        break;
                }
            }
        }catch (IOException e){

        }

        ObjModel objModel = new ObjModel();
        objModel.setBufferPositions(vec3fListToFloatBuffer(positionsDupli));
        objModel.setBufferNormals(vec3fListToFloatBuffer(normalsDupli));
        objModel.setBufferUvs(vec2fListToFloatBuffer(uvsDupli));
        objModel.setBufferColors(rgbcolorfListToFloatBuffer(colorsDupli));
        objModel.setVertexCount(positionsDupli.size());

        return objModel;
    }

    private static void parseV(List<Vec3f> positions, String x, String y, String z){
        positions.add(new Vec3f(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(z)));
    }

    private static void parseVT(List<Vec2f> uvs, String u, String v){
        uvs.add(new Vec2f(Float.parseFloat(u),Float.parseFloat(v)));
    }

    private static void parseVN(List<Vec3f> normals, String x, String y, String z){
        normals.add(new Vec3f(Float.parseFloat(x),Float.parseFloat(y),Float.parseFloat(z)));
    }

    private static void parseF(String lineParts1,
                               List<Vec3f> positions, List<Vec3f> positionsDupli,
                               List<Vec3f> normals, List<Vec3f> normalsDupli,
                               List<Vec2f> uvs , List<Vec2f> uvsDupli,
                               RGBColorF curColor, List<RGBColorF> colorsDupli){

        String[] lineSubParts = lineParts1.split("[ ]+");
        if(lineSubParts[1].matches("[0-9]+")){ //Face composta de posicoes
            for (int i = 1; i < 4; i++) {
                short indx = Short.parseShort(lineSubParts[i]);
                indx--;
                positionsDupli.add(positions.get(indx));
                colorsDupli.add(curColor);
            }
        }else if(lineSubParts[1].matches("[0-9]+/[0-9]+")){//Face composta de posicoes e UVs
            for (int i = 1; i < 4; i++) {
                String[] l = lineSubParts[i].split("[/]");
                short indx = Short.parseShort(l[0]);
                indx--;
                positionsDupli.add(positions.get(indx));

                indx = Short.parseShort(l[1]);
                indx--;
                uvsDupli.add(uvs.get(indx));

                colorsDupli.add(curColor);
            }
        }else if(lineSubParts[1].matches("[0-9]+//[0-9]+")){ //Face composta de posicoes e normais
            for (int i = 1; i < 4; i++) {
                String[] l = lineSubParts[i].split("[//]");
                short indx = Short.parseShort(l[0]);
                indx--;
                positionsDupli.add(positions.get(indx));

                indx = Short.parseShort(l[1]);
                indx--;
                normalsDupli.add(normals.get(indx));

                colorsDupli.add(curColor);
            }
        }else if(lineSubParts[1].matches("[0-9]+/[0-9]+/[0-9]+")){//Face composta de posicoes, UVs e normais
            for (int i = 1; i < 4; i++) {
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

                colorsDupli.add(curColor);
            }
        }

    }

    private static FloatBuffer vec3fListToFloatBuffer(List<Vec3f> list){

        float[] floatArray = new float[list.size() * 3];

        int i = 0;
        for (Vec3f v : list){
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

        return floatBuffer;
    }

    private static FloatBuffer vec2fListToFloatBuffer(List<Vec2f> list){

        float[] floatArray = new float[list.size() * 2];

        int i = 0;
        for (Vec2f v : list){
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

    private static FloatBuffer rgbcolorfListToFloatBuffer(List<RGBColorF> list){

        float[] floatArray = new float[list.size() * 3];

        int i = 0;
        for (RGBColorF c : list){
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
