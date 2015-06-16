package br.edu.ifg.livroar.assets;

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
import br.edu.ifg.livroar.scenes.SceneObject;
import br.edu.ifg.livroar.scenes.animations.Animation;
import br.edu.ifg.livroar.scenes.animations.Keyframe;
import br.edu.ifg.livroar.scenes.models.Material;
import br.edu.ifg.livroar.scenes.models.ModelPart;
import br.edu.ifg.livroar.scenes.models.attributes.MaterialAttribute;
import br.edu.ifg.livroar.scenes.models.attributes.NormalAttribute;
import br.edu.ifg.livroar.scenes.models.attributes.PositionAttribute;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class ColladaLoader implements Asset3DLoader
{
    public static final String TAG = "ColladaLoader";
    private String filePath;
    private final Element mainElement;

    public ColladaLoader(String filePath) throws ParserConfigurationException, IOException, SAXException
    {
        this.filePath = filePath;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document sceneDoc = dBuilder.parse(App.getContext().getAssets().open(filePath));
        sceneDoc.normalize();

        mainElement = (Element) sceneDoc.getElementsByTagName("COLLADA").item(0);
    }

    @Override
    public List<SceneObject> loadObjects(String sceneName, String ... names)
    {
        Map<String, Element> libImages = getLibraryImagesMap();
        Map<String, Element> libEffects = getLibraryEffectsMap();
        Map<String, Element> libMaterials = getLibraryMaterialsMap();
        Map<String, Element> libGeoms = getLibraryGeometriesMap();
        Map<String, Element> libAnims = getLibraryAnimationsMap();
        Map<String, Element> sceneNodes = getSceneNodesMap(getLibraryVisualScenesMap().get(sceneName));

        List<SceneObject> objects = new ArrayList<>(names.length);

        if(names.length > 0) // Alguns objetos
        {
            for (String name : names)
            {
                Element curNode = sceneNodes.get(name);
                if (curNode != null)
                {
                    objects.add(getSceneObject(curNode,
                            libImages,
                            libEffects,
                            libMaterials,
                            libGeoms,
                            libAnims));
                }
            }
        }
        else // Todos os objetos
        {
            Log.d(TAG, "Carregando todos os objetos da cena");
            for (String id : sceneNodes.keySet())
            {
                SceneObject o = getSceneObject(sceneNodes.get(id),
                        libImages,
                        libEffects,
                        libMaterials,
                        libGeoms,
                        libAnims);
                objects.add(o);
                Log.d(TAG, "Objeto '" + id + "' carregado: " + o.toString());
            }
        }

        return objects;
    }

    private Map<String, Element> getLibraryImagesMap()
    {
        Element libImages = (Element) mainElement.getElementsByTagName("library_images").item(0);
        int imgCount = libImages.getElementsByTagName("image").getLength();
        Map<String, Element> map = new HashMap<>(imgCount);
        for (int i = 0; i < imgCount; i++)
        {
            String id = ((Element)libImages.getElementsByTagName("image").item(i)).getAttribute("id");
            map.put(id, (Element)libImages.getElementsByTagName("image").item(i));
        }
        return map;
    }

    private Map<String, Element> getLibraryEffectsMap()
    {
        Element libEffects = (Element) mainElement.getElementsByTagName("library_effects").item(0);
        int effCount = libEffects.getElementsByTagName("effect").getLength();
        Map<String, Element> map = new HashMap<>(effCount);
        for (int i = 0; i < effCount; i++)
        {
            String id = ((Element)libEffects.getElementsByTagName("effect").item(i)).getAttribute("id");
            map.put(id, (Element)libEffects.getElementsByTagName("effect").item(i));
        }
        return map;
    }

    private Map<String, Element> getLibraryMaterialsMap()
    {
        Element libMaterials = (Element) mainElement.getElementsByTagName("library_materials").item(0);
        int matCount = libMaterials.getElementsByTagName("material").getLength();
        Map<String, Element> map = new HashMap<>(matCount);
        for (int i = 0; i < matCount; i++)
        {
            String id = ((Element)libMaterials.getElementsByTagName("material").item(i)).getAttribute("id");
            map.put(id, (Element)libMaterials.getElementsByTagName("material").item(i));
        }
        return map;
    }

    private Map<String, Element> getLibraryGeometriesMap()
    {
        Element libGeometries = (Element) mainElement.getElementsByTagName("library_geometries").item(0);
        int geomCount = libGeometries.getElementsByTagName("geometry").getLength();
        Map<String, Element> map = new HashMap<>(geomCount);
        for (int i = 0; i < geomCount; i++)
        {
            String id = ((Element)libGeometries.getElementsByTagName("geometry").item(i)).getAttribute("id");
            map.put(id, (Element)libGeometries.getElementsByTagName("geometry").item(i));
        }
        return map;
    }

    private Map<String, Element> getLibraryAnimationsMap()
    {
        Element libAnims = (Element) mainElement.getElementsByTagName("library_animations").item(0);
        int animCount = libAnims.getElementsByTagName("animation").getLength();
        Map<String, Element> map = new HashMap<>(animCount);
        Element curElement;
        for (int i = 0; i < animCount; i++)
        {
            curElement = (Element) libAnims.getElementsByTagName("animation").item(i);
            String target = ((Element)curElement.getElementsByTagName("channel").item(0))
                    .getAttribute("target");
            map.put(target, curElement);
        }
        return map;
    }

    private Map<String, Element> getLibraryVisualScenesMap()
    {
        Element libVScenes = (Element) mainElement.getElementsByTagName("library_visual_scenes").item(0);
        int sceneCount = libVScenes.getElementsByTagName("visual_scene").getLength();
        Map<String, Element> map = new HashMap<>(sceneCount);
        for (int i = 0; i < sceneCount; i++)
        {
            String id = ((Element)libVScenes.getElementsByTagName("visual_scene").item(i)).getAttribute("id");
            map.put(id, (Element)libVScenes.getElementsByTagName("visual_scene").item(i));
        }
        return map;
    }

    private Map<String, Element> getSceneNodesMap(Element visualSceneElement)
    {
        int nodeCount = visualSceneElement.getElementsByTagName("node").getLength();
        Map<String, Element> map = new HashMap<>(nodeCount);
        for (int i = 0; i < nodeCount; i++)
        {
            String id = ((Element)visualSceneElement.getElementsByTagName("node").item(i)).getAttribute("id");
            map.put(id, (Element)visualSceneElement.getElementsByTagName("node").item(i));
        }
        return map;
    }

    private SceneObject getSceneObject(Element nodeElement,
                                       Map<String, Element> libraryImages,
                                       Map<String, Element> libraryEffects,
                                       Map<String, Element> libraryMaterials,
                                       Map<String, Element> libraryGeometries,
                                       Map<String, Element> libraryAnimations)
    {
        String nodeID = nodeElement.getAttribute("id");
        Vec3 loc = new Vec3(0,0,0);
        Vec3 rot = new Vec3(0,0,0);
        Vec3 scale = new Vec3(1,1,1);
        List<ModelPart> parts = new ArrayList<>();
        List<Animation> animations = new ArrayList<>();

        //Posicao
        nodeElement = (Element) nodeElement.getElementsByTagName("translate").item(0);
        if(nodeElement!=null)
        {
            String[] location = nodeElement.getTextContent().split("[ ]");
            loc = new Vec3(Float.parseFloat(location[0]),
                    Float.parseFloat(location[1]),
                    Float.parseFloat(location[2]));
        }
        nodeElement = (Element) nodeElement.getParentNode();
        assert nodeElement != null;
        //Rotacao
        int count = nodeElement.getElementsByTagName("rotate").getLength();
        for (int i = 0; i < count; i++)
        {
            nodeElement = (Element) nodeElement.getElementsByTagName("rotate").item(i);
            if(nodeElement.getAttribute("sid").endsWith("X"))
                rot.x = Float.parseFloat(nodeElement
                        .getTextContent().split("[ ]")[3]);
            else if(nodeElement.getAttribute("sid").endsWith("Y"))
                rot.y = Float.parseFloat(nodeElement
                        .getTextContent().split("[ ]")[3]);
            else if(nodeElement.getAttribute("sid").endsWith("Z"))
                rot.z = Float.parseFloat(nodeElement
                        .getTextContent().split("[ ]")[3]);
            nodeElement = (Element) nodeElement.getParentNode();
        }
        nodeElement = (Element) nodeElement.getParentNode();
        assert nodeElement != null;
        //Escala
        nodeElement = (Element) nodeElement.getElementsByTagName("scale").item(0);
        if(nodeElement!=null)
        {
            String[] scaleS = nodeElement.getTextContent().split("[ ]");
            scale = new Vec3(Float.parseFloat(scaleS[0]) * 20,
                    Float.parseFloat(scaleS[1]) * 20,
                    Float.parseFloat(scaleS[2]) * 20);
        }
        nodeElement = (Element) nodeElement.getParentNode();
        assert nodeElement != null;
        //Geometry
        nodeElement = (Element) nodeElement.getElementsByTagName("instance_geometry").item(0);
        if(nodeElement!=null)
        {
            String geomName = nodeElement.getAttribute("url").substring(1);
            Element geometry = libraryGeometries.get(geomName);
            geometry = (Element) geometry.getElementsByTagName("mesh").item(0);
            assert geometry != null;

            //Sources
            Map<String, float[]> sources = new HashMap<>();
            count = geometry.getElementsByTagName("source").getLength();
            Element curSrc;
            for (int i = 0; i < count; i++)
            {
                curSrc = (Element) geometry.getElementsByTagName("source").item(i);
                String srcId = curSrc.getAttribute("id");
                curSrc = (Element) geometry.getElementsByTagName("float_array").item(0);
                assert curSrc!= null;
                String[] srcContentS = curSrc.getTextContent().split("[ ]");
                float[] srcContent = new float[srcContentS.length];
                for (int k = 0; k < srcContentS.length; k++)
                    srcContent[k] = Float.parseFloat(srcContentS[k]);
                sources.put(srcId, srcContent);
            }
            geometry = (Element) geometry.getElementsByTagName("vertices").item(0);
            String vertsId = geometry.getAttribute("id");
            String vertsInputId = ((Element)geometry.getElementsByTagName("input").item(0)).getAttribute("source").substring(1);
            float[] vertsSrc = sources.get(vertsInputId);
            sources.remove(vertsInputId);
            sources.put(vertsId, vertsSrc);
            geometry = (Element) geometry.getParentNode();

            //Polylists/ModelParts
            count = geometry.getElementsByTagName("polylist").getLength();
            Element curPolylist;
            for (int j = 0; j < count; j++)
            {
                curPolylist = (Element) geometry.getElementsByTagName("polylist").item(j);

                int vertexCount = Integer.parseInt(curPolylist.getAttribute("count")) * 3;
                List<Vec3> positions = new ArrayList<>();
                String posSrcName = null;
                int positionOffset = -1;
                List<Vec3> normals = new ArrayList<>();
                String normsSrcName = null;
                int normalOffset = -1;
                List<Vec2> uvs = new ArrayList<>();
                String uvsSrcName = null;
                int uvOffset = -1;

                Material material = getMaterial(libraryMaterials.get(curPolylist.getAttribute("material")),
                        libraryImages, libraryEffects);

                String[] indicesS = (curPolylist.getElementsByTagName("p").item(0))
                        .getTextContent().split("[ ]");
                int inputCount = curPolylist.getElementsByTagName("input").getLength();
                Element curIn;
                for (int k = 0; k < inputCount; k++)
                {
                    curIn = (Element) curPolylist.getElementsByTagName("input").item(k);
                    String semantic = curIn.getAttribute("semantic");
                    switch (semantic)
                    {
                        case "VERTEX":
                            posSrcName = curIn.getAttribute("source").substring(1);
                            positionOffset = Integer.parseInt(curIn.getAttribute("offset"));
                            break;
                        case "NORMAL":
                            normsSrcName = curIn.getAttribute("source").substring(1);
                            normalOffset = Integer.parseInt(curIn.getAttribute("offset"));
                            break;
                        case "TEXCOORD":
                            uvsSrcName = curIn.getAttribute("source").substring(1);
                            uvOffset = Integer.parseInt(curIn.getAttribute("offset"));
                            break;
//                                case "COLOR": Cor por vertice, atualmente usando somente cores por material
//                                    break;
                    }
                }

                assert positionOffset>-1;
                for (int k = 0; k < indicesS.length; k+=inputCount)
                {
                    int index = Integer.parseInt(indicesS[k + positionOffset]);
                    positions.add(new Vec3(sources.get(posSrcName)[index],
                            sources.get(posSrcName)[index+1], sources.get(posSrcName)[index+2]));
                    if(normalOffset!=-1)
                    {
                        index = Integer.parseInt(indicesS[k+normalOffset]);
                        normals.add(new Vec3(sources.get(normsSrcName)[index],
                                sources.get(normsSrcName)[index+1], sources.get(normsSrcName)[index+2]));
                    }
                    if(uvOffset!=-1)
                    {
                        index = Integer.parseInt(indicesS[k+uvOffset]);
                        uvs.add(new Vec2(sources.get(uvsSrcName)[index],
                                sources.get(uvsSrcName)[index+1]));
                    }
                }

                ModelPart p = new ModelPart(vertexCount);
                p.addAttribute(new PositionAttribute(positions));
                if(normals.size()>0)
                    p.addAttribute(new NormalAttribute(normals));
                if(material!=null)
                    p.addAttribute(new MaterialAttribute(material, uvs));

                parts.add(p);
            }
            //Animations
            animations.addAll(getAnimations(nodeID, libraryAnimations));
        }

        return new SceneObject(loc, rot, scale, parts, animations);
    }

    //Suportando apenas uma animacao locXYZ/rotXYZ/scaleXYZ por objeto como imposto pelo exporter do blender
    private List<Animation> getAnimations(String targetName, Map<String, Element> libraryAnimations)
    {
        List<Element> animslocRotScaleXYZ = new ArrayList<>(9);
        for (int i = 0; i < 9; i++)
            animslocRotScaleXYZ.add(null);
//        Element locX = null, locY = null, locZ = null,
//                rotX = null, rotY = null, rotZ = null,
//                scaleX = null, scaleY = null, scaleZ = null;

        for (String t : libraryAnimations.keySet())
        {
            Element curAnim = libraryAnimations.get(t);
            String[] target = t.split("[/]");

            if(target[0].equals(targetName))
            {
                switch (target[1])
                {
                    case "location.X":
                        animslocRotScaleXYZ.set(0,curAnim);
                        break;
                    case "location.Y":
                        animslocRotScaleXYZ.set(1,curAnim);
                        break;
                    case "location.Z":
                        animslocRotScaleXYZ.set(2,curAnim);
                        break;
                    case "rotationX.ANGLE":
                        animslocRotScaleXYZ.set(3,curAnim);
                        break;
                    case "rotationY.ANGLE":
                        animslocRotScaleXYZ.set(4,curAnim);
                        break;
                    case "rotationZ.ANGLE":
                        animslocRotScaleXYZ.set(5,curAnim);
                        break;
                    case "scale.X":
                        animslocRotScaleXYZ.set(6,curAnim);
                        break;
                    case "scale.Y":
                        animslocRotScaleXYZ.set(7,curAnim);
                        break;
                    case "scale.Z":
                        animslocRotScaleXYZ.set(8,curAnim);
                }
            }
        }

        for (int i = 0; i < 3; i++) // Loc
        {
            Map<String, float[]> sourcesFloat = new HashMap<>();
            Map<String, String[]> sourcesString = new HashMap<>();

            Element curAnim = animslocRotScaleXYZ.get(i);
            if(curAnim!=null)
            {
                int sourceCount = curAnim.getElementsByTagName("source").getLength();
                for (int j = 0; j < sourceCount; j++)
                {
                    curAnim = (Element) curAnim.getElementsByTagName("source").item(j);
                    //Switch accessor
                    curAnim = (Element) curAnim.getParentNode();
                }
            }
        }

        List<Keyframe> keyframes = new ArrayList<>();
        List<Animation> animations = new ArrayList<>();

        //TODO: Parse dos keyframes

        animations.add(new Animation(keyframes));

        return animations;
    }

    private Material getMaterial(Element materialElement,
                                 Map<String, Element> libraryImages,
                                 Map<String, Element> libraryEffects)
    {
        String effectId = ((Element)materialElement.getElementsByTagName("instance_effect").item(0))
                .getAttribute("url").substring(1);
        Element effect = libraryEffects.get(effectId);
        if(effect!=null)
        {
            effect = (Element) effect.getElementsByTagName("profile_COMMON").item(0);
            Material m = new Material(materialElement.getAttribute("id"));
            m.setAmbient(0,0,0);
            m.setDiffuse(1, 0, 1);
            m.setTransparency(1);
            //TODO: Parse do material

            return m;
        }
        else
            return null;
    }

    @Override
    public String getFilePath()
    {
        return filePath;
    }
}
