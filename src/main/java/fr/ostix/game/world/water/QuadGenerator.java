package fr.ostix.game.world.water;


import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.model.MeshModel;

public class QuadGenerator {

	public static final int VERTEX_COUNT = 4;
	private static final float[] VERTICES = {0, 0, 1, 0, 1, 1, 0, 1};
	private static final int[] INDICES = {0, 3, 1, 1, 3, 2};

	public static MeshModel generateQuad() {
		return Loader.INSTANCE.loadToVAO(VERTICES, INDICES);
	}

}
