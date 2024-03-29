/*
 * Copyright (c) 2009-2021 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.bullet.collision.shapes;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.extras.gimpact.*;
import com.jme3.bullet.util.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

import java.nio.*;

/**
 * Basic mesh collision shape
 * @author normenhansen
 */
public class GImpactCollisionShape extends CollisionShape{

    protected Vector3f worldScale;
    protected int numVertices, numTriangles, vertexStride, triangleIndexStride;
    protected ByteBuffer triangleIndexBase, vertexBase;
    protected IndexedMesh bulletMesh;

    protected GImpactCollisionShape() {
    }

    /**
     * creates a collision shape from the given Mesh
     * @param mesh the Mesh to use
     */
    public GImpactCollisionShape(MeshModel mesh) {
        createCollisionMesh(mesh, new Vector3f(1,1,1));
    }


    private void createCollisionMesh(MeshModel mesh, Vector3f worldScale) {
        this.worldScale = worldScale;
        bulletMesh = Converter.convert(mesh);
        this.numVertices = bulletMesh.numVertices;
        this.numTriangles = bulletMesh.numTriangles;
        this.vertexStride = bulletMesh.vertexStride;
        this.triangleIndexStride = bulletMesh.triangleIndexStride;
        this.triangleIndexBase = bulletMesh.triangleIndexBase;
        this.vertexBase = bulletMesh.vertexBase;
        createShape();
    }

//    /**
//     * creates a jme mesh from the collision shape, only needed for debugging
//     *
//     * @return a new Mesh
//     */
//    public Mesh createJmeMesh(){
//        return Converter.convert(bulletMesh);
//    }

//    @Override
//    public void write(JmeExporter ex) throws IOException {
//        super.write(ex);
//        OutputCapsule capsule = ex.getCapsule(this);
//        capsule.write(worldScale, "worldScale", new Vector3f(1, 1, 1));
//        capsule.write(numVertices, "numVertices", 0);
//        capsule.write(numTriangles, "numTriangles", 0);
//        capsule.write(vertexStride, "vertexStride", 0);
//        capsule.write(triangleIndexStride, "triangleIndexStride", 0);
//
//        capsule.write(triangleIndexBase.array(), "triangleIndexBase", new byte[0]);
//        capsule.write(vertexBase.array(), "vertexBase", new byte[0]);
//    }
//
//    @Override
//    public void read(JmeImporter im) throws IOException {
//        super.read(im);
//        InputCapsule capsule = im.getCapsule(this);
//        worldScale = (Vector3f) capsule.readSavable("worldScale", new Vector3f(1, 1, 1));
//        numVertices = capsule.readInt("numVertices", 0);
//        numTriangles = capsule.readInt("numTriangles", 0);
//        vertexStride = capsule.readInt("vertexStride", 0);
//        triangleIndexStride = capsule.readInt("triangleIndexStride", 0);
//
//        triangleIndexBase = ByteBuffer.wrap(capsule.readByteArray("triangleIndexBase", new byte[0]));
//        vertexBase = ByteBuffer.wrap(capsule.readByteArray("vertexBase", new byte[0]));
//        createShape();
//    }

    protected void createShape() {
        bulletMesh = new IndexedMesh();
        bulletMesh.numVertices = numVertices;
        bulletMesh.numTriangles = numTriangles;
        bulletMesh.vertexStride = vertexStride;
        bulletMesh.triangleIndexStride = triangleIndexStride;
        bulletMesh.triangleIndexBase = triangleIndexBase;
        bulletMesh.vertexBase = vertexBase;
        bulletMesh.triangleIndexBase = triangleIndexBase;
        TriangleIndexVertexArray tiv = new TriangleIndexVertexArray(numTriangles, triangleIndexBase, triangleIndexStride, numVertices, vertexBase, vertexStride);
        cShape = new GImpactMeshShape(tiv);
        cShape.setLocalScaling(Converter.convert(worldScale));
        cShape.setLocalScaling(Converter.convert(getScale()));
        cShape.setMargin(margin);
        ((GImpactMeshShape) cShape).updateBound();
    }

    @Override
    public void setScale(Vector3f scale) {
        super.setScale(scale);
        ((GImpactMeshShape) cShape).updateBound();
    }
}
