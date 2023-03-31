package fr.ostix.game.core.logics.ressourceProcessor;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.font.meshCreator.FontType;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.meshCreator.TextMeshData;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.model.MeshModel;

import java.util.HashMap;
import java.util.Map;

import static fr.ostix.game.graphics.font.meshCreator.GUIText.TextProperties;

public class FontRequest extends GLRequest {
    private static final Map<TextProperties, MeshModel> fontsModels = new HashMap<>();
    private final GUIText text;
    private final MasterFont masterFont;

    private final boolean isTemp;

    public FontRequest(GUIText text, boolean isTemp, MasterFont masterFont) {
        this.text = text;
        this.isTemp = isTemp;
        this.masterFont = masterFont;
        if (fontsModels.containsKey(text.getProp())){
            MeshModel vao = fontsModels.get(text.getProp());
            this.text.setMeshInfo(vao, vao.getVertexCount());
            masterFont.addText(text, isTemp);
            this.isExecuted = true;
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
        vao.getVAO().setVertexCount(data.getVertexCount());
        fontsModels.put(text.getProp(), vao);
        masterFont.addText(text, isTemp);
        super.execute();
    }
}
