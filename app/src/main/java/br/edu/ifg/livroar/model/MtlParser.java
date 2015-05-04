package br.edu.ifg.livroar.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aluno on 30/04/2015.
 */
public class MtlParser {
    public static final String TAG = "MtlParser";

    public static Map<String, MtlMaterial> loadMTL(Context context, String filePath) throws IOException {

        Map<String, MtlMaterial> materials = new HashMap<>();

        BufferedReader reader;
        String line;
        String[] lineParts;
        String curMaterial = null;

        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filePath)));
        while((line = reader.readLine())!=null) {
            lineParts = line.split("[ ]+");
            switch (lineParts[0]){
                case "newmtl":
                    curMaterial = lineParts[1];
                    materials.put(lineParts[1], new MtlMaterial(lineParts[1]));
                    break;
                case "Ns":
                    if(curMaterial != null) materials.get(curMaterial).setShininess(Float.parseFloat(lineParts[1]));
                    break;
                case "Ka":
                    if(curMaterial != null) materials.get(curMaterial).setAmbient(Float.parseFloat(lineParts[1]),
                            Float.parseFloat(lineParts[2]),
                            Float.parseFloat(lineParts[3]));
                    break;
                case "Kd":
                    if(curMaterial != null)
                        materials.get(curMaterial).setDiffuse(Float.parseFloat(lineParts[1]),
                                Float.parseFloat(lineParts[2]),
                                Float.parseFloat(lineParts[3]));
                    break;
                case "Ks":
                    if(curMaterial != null)
                        materials.get(curMaterial).setSpecular(Float.parseFloat(lineParts[1]),
                            Float.parseFloat(lineParts[2]),
                            Float.parseFloat(lineParts[3]));
                    break;
                case "d":
                    if(curMaterial != null) materials.get(curMaterial).setTransparency(Float.parseFloat(lineParts[1])); //Valor de alpha nao utilizado no model atual
                    break;
                case "illum":
                    if(curMaterial != null) materials.get(curMaterial).setIllum(Integer.parseInt(lineParts[1]));
                    break;
            }
        }

        for (MtlMaterial m : materials.values()){
            Log.i(TAG, m.toString());
        }

        return materials;

    }

}
