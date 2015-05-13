package br.edu.ifg.livroar.scenes;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import br.edu.ifg.livroar.App;
import br.edu.ifg.livroar.model.Material;
import br.edu.ifg.livroar.util.Utils;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;


/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class SceneLoader {

    public static final String TAG = "SceneLoader";

    public static Scene loadScene(String fileName, String patterName) {

        Context context = App.getContext();
        List<Geometry> geometries = new ArrayList<>();
        Map<String, Animation> animations = new HashMap<>();
        Map<String, Material>  materials = new HashMap<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document sceneDoc = dBuilder.parse(context.getAssets().open("scenes/" + fileName + ".dae"));
            sceneDoc.normalize();

            Element collada = (Element) sceneDoc.getElementsByTagName("COLLADA").item(0);

            //TODO: Avaliar elementos necessarios
            // Parse dos elementos
            materials = getMaterials(context,
                    (Element) collada.getElementsByTagName("library_images").item(0),
                    (Element) collada.getElementsByTagName("library_effects").item(0));

            Element library_animations = (Element) collada.getElementsByTagName("library_animations").item(0);
            if(library_animations != null) {
                animations = parseLibraryAnimations(library_animations);
            }

            Element library_geometries = (Element) collada.getElementsByTagName("library_geometries").item(0);
            if(library_geometries != null)
                geometries = parseLibraryGeometries(library_geometries,animations,materials,patterName);

        } catch (ParserConfigurationException |
                SAXException |
                IOException e) {
            e.printStackTrace();
        }

        return new Scene(geometries);
    }

    private static Map<String, Material> getMaterials(Context context,
                                                      Element library_images,
                                                      Element library_effects) {
        Map<String, Material>  materials = new HashMap<>();
        Map<String, String> textures = new HashMap<>();

        if(library_images != null){
            int imageCount = library_images.getElementsByTagName("image").getLength();
            for (int i = 0; i < imageCount; i++) {
                Element curImage = (Element) library_images.getElementsByTagName("image").item(i);
                String name = curImage.getAttribute("id");
                String[] initFrom = curImage.getElementsByTagName("init_from").item(0)
                        .getTextContent().split("[/]");
                String fileName = initFrom[initFrom.length-1];
                textures.put(name, fileName);
            }
        }
        if(library_effects != null){
            int effectCount = library_effects.getElementsByTagName("effect").getLength();
            for (int i = 0; i < effectCount; i++) {
                Element curEffect = (Element)((Element) library_effects.getElementsByTagName("effect").item(i))
                        .getElementsByTagName("profile_COMMON").item(0);
                String[] effectName = ((Element) library_effects.getElementsByTagName("effect").item(i)).getAttribute("id").split("[-]");
                String materialName = effectName[0]+"-material";
                Material material = new Material(materialName);

                int newparamCount = curEffect.getElementsByTagName("newparam").getLength();
                for (int j = 0; j < newparamCount; j++) {
                    Element curNewparam = (Element) curEffect.getElementsByTagName("newparam").item(j);
                    String[] sid = curNewparam.getAttribute("sid").split("[-]");
                    if(sid[sid.length-1].equals("surface")){
                        String initFrom = ((Element) curNewparam.getElementsByTagName("surface").item(0))
                                .getElementsByTagName("init_from").item(0).getTextContent();
                        material.setTexture(context, textures.get(initFrom));
                    }
                }

                Element technique = (Element) curEffect.getElementsByTagName("technique").item(0);
                // Possibilidade de escolher modelo de iluminacao com base no
                // nome da tag, ex.: phong
                Element colors = (Element) technique.getElementsByTagName("phong").item(0);

                String[] color = ((Element)colors.getElementsByTagName("ambient").item(0))
                        .getElementsByTagName("color").item(0).getTextContent().split("[ ]");
                float r = Float.parseFloat(color[0]);
                float g = Float.parseFloat(color[1]);
                float b = Float.parseFloat(color[2]);
                material.setAmbient(r, g, b);
                if(((Element)colors.getElementsByTagName("diffuse").item(0))
                        .getElementsByTagName("color").getLength()!=0){
                    color = ((Element)colors.getElementsByTagName("diffuse").item(0))
                            .getElementsByTagName("color").item(0).getTextContent().split("[ ]");
                    r = Float.parseFloat(color[0]);
                    g = Float.parseFloat(color[1]);
                    b = Float.parseFloat(color[2]);
                    float a = Float.parseFloat(color[3]);
                    material.setDiffuse(r, g, b);
                    material.setTransparency(a);
                }
                color = ((Element)colors.getElementsByTagName("specular").item(0))
                        .getElementsByTagName("color").item(0).getTextContent().split("[ ]");
                r = Float.parseFloat(color[0]);
                g = Float.parseFloat(color[1]);
                b = Float.parseFloat(color[2]);
                material.setSpecular(r, g, b);

                materials.put(material.getName(), material);
                Log.d(TAG, "Material " + material.toString());
            }
        }

        return materials;
    }

    private static Map<String, Animation> parseLibraryAnimations(Element library_animations) {
        Map<String, Animation> animations = new HashMap<>();
        int length = library_animations.getElementsByTagName("animation").getLength();
        for (int i = 0; i < length; i++) {

            Element animElement = (Element) library_animations.getElementsByTagName("animation").item(i);
            Log.d(TAG, "Animacao: " + animElement.getAttribute("id"));
            String[] id = animElement.getAttribute("id").split("[_]");
            List<Animation.Keyframe> keyframes = new ArrayList<>();

            int sourceCount = animElement.getElementsByTagName("source").getLength();
            Log.d(TAG, "Source Count: " + sourceCount);

            Element sourceInput = (Element)animElement.getElementsByTagName("source").item(0);
            String[] input = sourceInput.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
            List<Float> times = new ArrayList<>();
            for (String t : input){
                times.add(Float.parseFloat(t));
            }
            Element sourceOutput = ((Element)animElement.getElementsByTagName("source").item(1));
            String[] output = sourceOutput.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
            List<Float> pos = new ArrayList<>();
            for (String p : output){
                pos.add(Float.parseFloat(p));
            }
            Element interpolationSoure = ((Element)animElement.getElementsByTagName("source").item(2));
            String[] interpolation = interpolationSoure.getElementsByTagName("Name_array").item(0).getTextContent().split("[ ]");
            List<Animation.InterpolationMethod> methods = new ArrayList<>();
            for (String m : interpolation){
                switch (m){
                    default:
                    case "BEZIER":
                        methods.add(Animation.InterpolationMethod.BEZIER);
                        break;
                        // case "HERMITE":
                        //methods.add(Animation.InterpolationMethod.);
                        //  break;
                        // case "BSPLINE":
                        //   methods.add(Animation.InterpolationMethod.BEZIER);
                        //  break;
//                    case "LINEAR":
//                        methods.add(Animation.InterpolationMethod.LINEAR);
//                        break;
                }
            }
            Element intangentSource = ((Element)animElement.getElementsByTagName("source").item(3));
            String[] intangents = intangentSource.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
            List<Vec2> inTans = Utils.stringArrayToVec2List(intangents);
            Element outtangentSource = ((Element)animElement.getElementsByTagName("source").item(4));
            String[] outtangents = outtangentSource.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
            List<Vec2> outTans = Utils.stringArrayToVec2List(outtangents);

            for (int j = 0; j < input.length; j++) {
                keyframes.add(new Animation.Keyframe( methods.get(j), (long) (times.get(j)*1000), pos.get(j)));
            }

            Animation animation = null;
            if(animElement.getAttribute("id").endsWith("location_X")){
                animation = new Animation(Animation.Type.LOC_X, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("location_Y")){
                animation = new Animation(Animation.Type.LOC_Y, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("location_Z")){
                animation = new Animation(Animation.Type.LOC_Z, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("euler_X")){
                animation = new Animation(Animation.Type.ROT_X, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("euler_Y")){
                animation = new Animation(Animation.Type.ROT_Y, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("euler_Z")){
                animation = new Animation(Animation.Type.ROT_Z, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("scale_X")){
                animation = new Animation(Animation.Type.SCALE_X, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("scale_Y")){
                animation = new Animation(Animation.Type.SCALE_Y, keyframes, outTans, inTans);
            }else if(animElement.getAttribute("id").endsWith("scale_Z")){
                animation = new Animation(Animation.Type.SCALE_Z, keyframes, outTans, inTans);
            }
            assert animation != null;
            animations.put(animElement.getAttribute("id"), animation);
            Log.d(TAG, "Animation "+ animElement.getAttribute("id") + ": " + animation.toString());
        }

        return animations;
    }

    private static List<Geometry> parseLibraryGeometries(Element library_geometries,
                                                         Map<String, Animation> animations,
                                                         Map<String, Material>  materials,
                                                         String patterName) {
        List<Geometry> geometries = new ArrayList<>();
        int length = library_geometries.getElementsByTagName("geometry").getLength();

        for (int i = 0; i < length; i++) {
            Element geomElement = (Element) library_geometries.getElementsByTagName("geometry").item(i);
            String geomName = geomElement.getAttribute("name");
            int sourcesLength = ((Element)geomElement.getElementsByTagName("mesh").item(0))
                    .getElementsByTagName("source").getLength();
            List<GeometryPart> parts = new ArrayList<>();
            List<Animation> geomAnims = new ArrayList<>();
            List<Vec3> positions = new ArrayList<>();
            List<Vec3> partPositions;
            List<Vec3> normals = new ArrayList<>();
            List<Vec3> partNormals;
            List<Vec2> uvs = new ArrayList<>();
            List<Vec2> partUvs;
            boolean hasUvs = false;
            int vertexCount = 0;
            for (int j = 0; j < sourcesLength; j++) {
                Element curSource = (Element)((Element) geomElement.getElementsByTagName("mesh").item(0))
                        .getElementsByTagName("source").item(j);
                String[] id = curSource.getAttribute("id").split("[-]");
                if(id[id.length-1].equals("positions")){
                    String[] posS = curSource.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
//                    vertexCount = Integer.parseInt(((Element)((Element)curSource.getElementsByTagName("technique_common").item(0))
//                            .getElementsByTagName("accessor").item(0)).getAttribute("count"));
                    int posIndx = 0;
                    for (int k = 0; k < posS.length/3; k++) {
                        float x = Float.parseFloat(posS[posIndx++]);
                        float y = Float.parseFloat(posS[posIndx++]);
                        float z = Float.parseFloat(posS[posIndx++]);
                        positions.add(new Vec3(x,y,z));
                    }
                }else if(id[id.length-1].equals("normals")){
                    String[] normsS = curSource.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
                    int normsIndx = 0;
                    for (int k = 0; k < normsS.length/3; k++) {
                        float x = Float.parseFloat(normsS[normsIndx++]);
                        float y = Float.parseFloat(normsS[normsIndx++]);
                        float z = Float.parseFloat(normsS[normsIndx++]);
                        normals.add(new Vec3(x,y,z));
                    }
                }else if((id[id.length-2]+"-"+id[id.length-1]).equals("map-0")){
                    hasUvs = true;
                    String[] uvsS = curSource.getElementsByTagName("float_array").item(0).getTextContent().split("[ ]");
                    int uvsIndx = 0;
                    for (int k = 0; k < uvsS.length/2; k++) {
                        float x = Float.parseFloat(uvsS[uvsIndx++]);
                        float y = Float.parseFloat(uvsS[uvsIndx++]);
                        uvs.add(new Vec2(x,y));
                    }
                }
            }

            int partCount = ((Element)geomElement.getElementsByTagName("mesh").item(0))
                    .getElementsByTagName("polylist").getLength();
            for (int j = 0; j < partCount; j++) { // obtendo parts
                Element curPartElement = (Element)((Element) geomElement.getElementsByTagName("mesh").item(0))
                        .getElementsByTagName("polylist").item(j);
                vertexCount = Integer.parseInt(curPartElement.getAttribute("count")) * 3;

                partPositions = new ArrayList<>();
                partNormals = new ArrayList<>();
                partUvs = new ArrayList<>();

                String[] indicesS = curPartElement.getElementsByTagName("p").item(0)
                        .getTextContent().split("[ ]");
                int inputCount = curPartElement.getElementsByTagName("input").getLength();
                int index = 0;
                for (int k = 0; k < indicesS.length/inputCount; k++) {
                    partPositions.add(positions.get(Integer.parseInt(indicesS[index])));
                    partNormals.add(normals.get(Integer.parseInt(indicesS[index + 1])));
                    if(hasUvs){
                        partUvs.add(uvs.get(Integer.parseInt(indicesS[index + 2])));
                    }else{
//                        partUvs.add(new Vec2(0,0));
                    }
                    index += inputCount;
                }
                String materialName = curPartElement.getAttribute("material");
                Log.d(TAG, "Part: vertexCount: " + vertexCount +
                        "; material name: " + materialName +
                "; partPositionsSize: " + partPositions.size());
                parts.add(new GeometryPart( vertexCount,
                        partPositions,
                        partNormals,
                        partUvs,
                        materials.get(materialName)));
            }

            for (String animName : animations.keySet()){ // obtendo animacoes
                if(animName.startsWith(geomName))
                    geomAnims.add(animations.get(animName));
            }

            Geometry g = new Geometry(geomName, patterName, parts, geomAnims);
            Log.d(TAG, g.toString());
            geometries.add(g);

        }


        return geometries;
    }

}
