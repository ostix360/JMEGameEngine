package fr.ostix.game.core.collision;

public class CollisionSystem {

    public static boolean isColliding(AABoundingBox a, AABoundingBox b){
        return (a.minX <= b.maxX && a.maxX >= b.minX) &&
                (a.minY <= b.maxY && a.maxY >= b.minY) &&
                (a.minZ <= b.maxZ && a.maxZ >= b.minZ);
    }

    public static boolean isColliding(BoundingSphere s1,BoundingSphere s2){
        float d2 = (s1.x-s2.x)*(s1.x-s2.x) + (s1.y-s2.y)*(s1.y-s2.y) + (s1.z-s2.z)*(s1.z-s2.z);
        return !(d2 > (s1.radius + s2.radius) * (s1.radius + s2.radius));
    }

    public static boolean isColliding(AABoundingBox box,BoundingSphere sphere){
        float x = Math.max(box.minX, Math.min(sphere.x, box.maxX));
        float y = Math.max(box.minY, Math.min(sphere.y, box.maxY));
        float z = Math.max(box.minZ, Math.min(sphere.z, box.maxZ));

        // this is the same as isPointInsideSphere
        double distance = Math.sqrt((x - sphere.x) * (x - sphere.x) +
                (y - sphere.y) * (y - sphere.y) +
                (z - sphere.z) * (z - sphere.z));

        return distance < sphere.radius;
    }
}
