package fr.ostix.game.entity.animated.colladaParser.dataStructures;

import java.util.*;

public class KeyFrameData {

    public final float time;
    public final List<JointTransformData> jointTransforms = new ArrayList<JointTransformData>();

    public KeyFrameData(float time) {
        this.time = time;
    }

    public void addJointTransform(JointTransformData transform) {
        jointTransforms.add(transform);
    }

}
