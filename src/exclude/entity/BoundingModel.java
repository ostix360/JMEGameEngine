package fr.ostix.game.entity;


import com.flowpowered.caustic.api.util.*;
import com.flowpowered.react.collision.shape.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.graphics.model.*;
import gnu.trove.list.*;
import gnu.trove.list.array.*;

public class BoundingModel {

    private final ModelData m;
    private Transform transform;
    private ConvexMeshShape convexMeshShape;

    public BoundingModel(ModelData m) {
        if (m != null){
            this.m = m;
            setConvexMesh();
        }else{
            this.m = null;
        }

    }

    public static BoundingModel load(String content) {
        String[] lines = content.split("\n");
        Transform transform = Transform.load(lines[0]);
        ModelData cm = loadModel(lines[1]);

        return new BoundingModel(cm).setTransform(transform);
    }

    private void setConvexMesh() {
        TFloatList meshPositions = new TFloatArrayList();
        TIntList meshIndices = new TIntArrayList();

        for (int i = 0; i < m.getVertices().length ; i++) {
            meshPositions.add(m.getVertices()[i]);
        }
        meshIndices.addAll(m.getIndices());
        TFloatList positions = new TFloatArrayList(meshPositions);
        TIntList indices = new TIntArrayList(meshIndices);
        MeshGenerator.toWireframe(positions, indices, true);
        final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
        for (int i = 0; i < indices.size(); i += 2) {
            meshShape.addEdge(indices.get(i), indices.get(i + 1));
        }
        meshShape.setIsEdgesInformationUsed(true);
        meshPositions.clear();
        meshIndices.clear();
        this.convexMeshShape = meshShape;

    }

    private static ModelData loadModel(String name) {
        return OBJFileLoader.loadModel(name);
    }

    public Transform getTransform() {
        return transform;
    }

    public BoundingModel setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public ConvexMeshShape getConvexMeshShape() {
        return convexMeshShape;
    }
}
