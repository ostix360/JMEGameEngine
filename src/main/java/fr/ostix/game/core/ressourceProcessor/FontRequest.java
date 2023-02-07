package fr.ostix.game.core.ressourceProcessor;

import fr.ostix.game.core.loader.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.graphics.model.*;

import java.util.*;

public class FontRequest extends GLRequest {
    private static final Map<String, MeshModel> fontsModels = new HashMap<>();
    private final GUIText text;
    private final MasterFont masterFont;

    private final boolean isTemp;

    public FontRequest(GUIText text, boolean isTemp, MasterFont masterFont) {
        this.text = text;
        this.isTemp = isTemp;
        this.masterFont = masterFont;
        if (fontsModels.containsKey(text.getTextString())){
            MeshModel vao = fontsModels.get(text.getTextString());
            text.setMeshInfo(vao, vao.getVertexCount());
            super.execute();
        }

    }

    @Override
    protected synchronized void execute() {
        if (isExecuted){
            return;
        }
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        MeshModel vao = Loader.INSTANCE.loadFontToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        masterFont.addText(text, isTemp);
        fontsModels.put(text.getTextString(), vao);
        super.execute();
    }
}
