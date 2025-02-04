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
import com.bulletphysics.linearmath.*;
import com.jme3.bullet.collision.shapes.infos.*;
import com.jme3.bullet.util.*;
import org.joml.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * A CompoundCollisionShape allows combining multiple base shapes
 * to generate a more sophisticated shape.
 * @author normenhansen
 */
public class CompoundCollisionShape extends CollisionShape {

    protected ArrayList<ChildCollisionShape> children = new ArrayList<>();

    public CompoundCollisionShape() {
        cShape = new CompoundShape();
    }

    /**
     * adds a child shape at the given local translation
     * @param shape the child shape to add
     * @param location the local location of the child shape
     */
    public void addChildShape(CollisionShape shape, Vector3f location) {
        Transform transA = new Transform(Converter.convert(new Matrix3f()));
        Converter.convert(location, transA.origin);
        try {
            children.add(new ChildCollisionShape((Vector3f) location.clone(), new Matrix3f(), shape));
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        ((CompoundShape) cShape).addChildShape(transA, shape.getCShape());
    }

    /**
     * adds a child shape at the given local translation
     * @param shape the child shape to add
     * @param location the local location of the child shape
     * @param rotation the local orientation of the child shape (not null,
     * unaffected)
     */
    public void addChildShape(CollisionShape shape, Vector3f location, Matrix3f rotation) {
        if(shape instanceof CompoundCollisionShape){
            throw new IllegalStateException("CompoundCollisionShapes cannot have CompoundCollisionShapes as children!");
        }
        Transform transA = new Transform(Converter.convert(rotation));
        Converter.convert(location, transA.origin);
        Converter.convert(rotation, transA.basis);
        try {
            children.add(new ChildCollisionShape((Vector3f) location.clone(), (Matrix3f) rotation.clone(), shape));
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        ((CompoundShape) cShape).addChildShape(transA, shape.getCShape());
    }

    private void addChildShapeDirect(CollisionShape shape, Vector3f location, Matrix3f rotation) {
        if(shape instanceof CompoundCollisionShape){
            throw new IllegalStateException("CompoundCollisionShapes cannot have CompoundCollisionShapes as children!");
        }
        Transform transA = new Transform(Converter.convert(rotation));
        Converter.convert(location, transA.origin);
        Converter.convert(rotation, transA.basis);
        ((CompoundShape) cShape).addChildShape(transA, shape.getCShape());
    }

    /**
     * removes a child shape
     * @param shape the child shape to remove
     */
    public void removeChildShape(CollisionShape shape) {
        ((CompoundShape) cShape).removeChildShape(shape.getCShape());
        for (Iterator<ChildCollisionShape> it = children.iterator(); it.hasNext();) {
            ChildCollisionShape childCollisionShape = it.next();
            if (childCollisionShape.shape == shape) {
                it.remove();
            }
        }
    }

    public List<ChildCollisionShape> getChildren() {
        return children;
    }

    /**
     * WARNING - CompoundCollisionShape scaling has no effect.
     */
    @Override
    public void setScale(Vector3f scale) {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "CompoundCollisionShape cannot be scaled");
    }

//    @Override
//    public void write(JmeExporter ex) throws IOException {
//        super.write(ex);
//        OutputCapsule capsule = ex.getCapsule(this);
//        capsule.writeSavableArrayList(children, "children", new ArrayList<ChildCollisionShape>());
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public void read(JmeImporter im) throws IOException {
//        super.read(im);
//        InputCapsule capsule = im.getCapsule(this);
//        children = capsule.readSavableArrayList("children", new ArrayList<ChildCollisionShape>());
//        cShape.setLocalScaling(Converter.convert(getScale()));
//        cShape.setMargin(margin);
//        loadChildren();
//    }

    private void loadChildren() {
        for (Iterator<ChildCollisionShape> it = children.iterator(); it.hasNext();) {
            ChildCollisionShape child = it.next();
            addChildShapeDirect(child.shape, child.location, child.rotation);
        }
    }

}
