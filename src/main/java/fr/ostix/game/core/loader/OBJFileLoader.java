package fr.ostix.game.core.loader;

import fr.ostix.game.graphics.model.*;
import fr.ostix.game.toolBox.*;

import java.io.*;
import java.util.*;

public class OBJFileLoader {
//    public static MeshModel loadModel(String objFileName, Loader loader) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(ToolDirectory.RES_FOLDER + "/models/" + objFileName + ".obj"));
//        } catch (FileNotFoundException e) {
//            System.err.println("OBJ file not found at: " + ToolDirectory.RES_FOLDER + objFileName + ".obj");
//            return null;
//        }
//
//        // input data lists
//        List<Float> vertices = new ArrayList<Float>();
//        List<Float> texCoords = new ArrayList<Float>();
//        List<Float> normals = new ArrayList<Float>();
//
//        // output data lists
//        List<Float> v = new ArrayList<Float>();
//        List<Float> t = new ArrayList<Float>();
//        List<Float> n = new ArrayList<Float>();
//        List<Integer> i = new ArrayList<Integer>();
//
//        try {
//            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//                String[] parts = line.split(" ");
//                if (parts[0].equalsIgnoreCase("v")) { // vertex position
//                    vertices.add(Float.parseFloat(parts[1])); // x
//                    vertices.add(Float.parseFloat(parts[2])); // y
//                    vertices.add(Float.parseFloat(parts[3])); // z
//                } else if (parts[0].equalsIgnoreCase("vt")) { // texture coordinate
//                    texCoords.add(Float.parseFloat(parts[1])); // u
//                    texCoords.add(1f - Float.parseFloat(parts[2])); // v
//                } else if (parts[0].equalsIgnoreCase("vn")) { // normal vector
//                    normals.add(Float.parseFloat(parts[1])); // x
//                    normals.add(Float.parseFloat(parts[2])); // y
//                    normals.add(Float.parseFloat(parts[3])); // z
//                } else if (parts[0].equalsIgnoreCase("f")) {
//                    int v0, v1, v2, v3, idx = v.size() / 3;
//                    switch (parts.length) {
//                        case 4: // triangle
//                            v0 = appendVertex(parts[1].split("/"), vertices, texCoords, normals, v, t, n, idx);
//                            v1 = appendVertex(parts[2].split("/"), vertices, texCoords, normals, v, t, n, idx + 1);
//                            v2 = appendVertex(parts[3].split("/"), vertices, texCoords, normals, v, t, n, idx + 2);
//                            i.add(v0);
//                            i.add(v1);
//                            i.add(v2);
//                            break;
//                        case 5: // quad
//                            v0 = appendVertex(parts[1].split("/"), vertices, texCoords, normals, v, t, n, idx);
//                            v1 = appendVertex(parts[2].split("/"), vertices, texCoords, normals, v, t, n, idx + 1);
//                            v2 = appendVertex(parts[3].split("/"), vertices, texCoords, normals, v, t, n, idx + 2);
//                            v3 = appendVertex(parts[4].split("/"), vertices, texCoords, normals, v, t, n, idx + 3);
//                            // triangle 1
//                            i.add(v0);
//                            i.add(v1);
//                            i.add(v2);
//                            // triangle 2
//                            i.add(v2);
//                            i.add(v3);
//                            i.add(v0);
//                            break;
//                        default: // unrecognized n-gon
//                            System.out.println("Unrecognized vertex size: " + (parts.length - 1));
//                            break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to read OBJ model!");
//            e.printStackTrace(System.err);
//            return null;
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                System.err.println("Failed to close reader!");
//                e.printStackTrace();
//            }
//        }
//
//        float[] vertexData = floatListToArray(v);
//        float[] textureData = floatListToArray(t);
//        float[] normalData = floatListToArray(n);
//        int[] indexData = intListToArray(i);
//        return loader.loadToVAO(vertexData, textureData, normalData, indexData);
//    }
//
//    public static ModelData loadModel(String objFileName) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(ToolDirectory.RES_FOLDER + "/models/entities/collision/" + objFileName));
//        } catch (FileNotFoundException e) {
//            System.err.println("OBJ file not found at: " + ToolDirectory.RES_FOLDER+ "/models/entities/collision/" + objFileName);
//            return null;
//        }
//
//        // input data lists
//        List<Float> vertices = new ArrayList<Float>();
//        List<Float> texCoords = new ArrayList<Float>();
//        List<Float> normals = new ArrayList<Float>();
//
//        // output data lists
//        List<Float> v = new ArrayList<Float>();
//        List<Float> t = new ArrayList<Float>();
//        List<Float> n = new ArrayList<Float>();
//        List<Integer> i = new ArrayList<Integer>();
//
//        try {
//            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//                String[] parts = line.split(" ");
//                if (parts[0].equalsIgnoreCase("v")) { // vertex position
//                    vertices.add(Float.parseFloat(parts[1])); // x
//                    vertices.add(Float.parseFloat(parts[2])); // y
//                    vertices.add(Float.parseFloat(parts[3])); // z
//                } else if (parts[0].equalsIgnoreCase("vt")) { // texture coordinate
//                    texCoords.add(Float.parseFloat(parts[1])); // u
//                    texCoords.add(1f - Float.parseFloat(parts[2])); // v
//                } else if (parts[0].equalsIgnoreCase("vn")) { // normal vector
//                    normals.add(Float.parseFloat(parts[1])); // x
//                    normals.add(Float.parseFloat(parts[2])); // y
//                    normals.add(Float.parseFloat(parts[3])); // z
//                } else if (parts[0].equalsIgnoreCase("f")) {
//                    int v0, v1, v2, v3, idx = v.size() / 3;
//                    switch (parts.length) {
//                        case 4: // triangle
//                            v0 = appendVertex(parts[1].split("/"), vertices, texCoords, normals, v, t, n, idx);
//                            v1 = appendVertex(parts[2].split("/"), vertices, texCoords, normals, v, t, n, idx + 1);
//                            v2 = appendVertex(parts[3].split("/"), vertices, texCoords, normals, v, t, n, idx + 2);
//                            i.add(v0);
//                            i.add(v1);
//                            i.add(v2);
//                            break;
//                        case 5: // quad
//                            v0 = appendVertex(parts[1].split("/"), vertices, texCoords, normals, v, t, n, idx);
//                            v1 = appendVertex(parts[2].split("/"), vertices, texCoords, normals, v, t, n, idx + 1);
//                            v2 = appendVertex(parts[3].split("/"), vertices, texCoords, normals, v, t, n, idx + 2);
//                            v3 = appendVertex(parts[4].split("/"), vertices, texCoords, normals, v, t, n, idx + 3);
//                            // triangle 1
//                            i.add(v0);
//                            i.add(v1);
//                            i.add(v2);
//                            // triangle 2
//                            i.add(v2);
//                            i.add(v3);
//                            i.add(v0);
//                            break;
//                        default: // unrecognized n-gon
//                            System.out.println("Unrecognized vertex size: " + (parts.length - 1));
//                            break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to read OBJ model!");
//            e.printStackTrace(System.err);
//            return null;
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                System.err.println("Failed to close reader!");
//                e.printStackTrace();
//            }
//        }
//
//        float[] vertexData = floatListToArray(v);
//        int[] indexData = intListToArray(i);
//        return new ModelData(vertexData,null,null,indexData);
//    }


    private static int appendVertex(String[] vertex, List<Float> v, List<Float> t, List<Float> n, List<Float> ov, List<Float> ot, List<Float> on, int i) {
        int vi = Integer.parseInt(vertex[0]) - 1;
        int ti = Integer.parseInt(vertex[1]) - 1;
        int ni = Integer.parseInt(vertex[2]) - 1;
        // append vertex position
        ov.add(v.get(vi * 3));
        ov.add(v.get(vi * 3 + 1));
        ov.add(v.get(vi * 3 + 2));
        // append texture coordinate
        ot.add(t.get(ti * 2));
        ot.add(t.get(ti * 2 + 1));
        // append normal vector
        on.add(n.get(ni * 3));
        on.add(n.get(ni * 3 + 1));
        on.add(n.get(ni * 3 + 2));
        // return index
        return i;
    }

    private static float[] floatListToArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private static int[] intListToArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }


}