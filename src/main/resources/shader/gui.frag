#version 330 core

in vec2 passTextureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform vec4 layer;

void main(void){
    float alpha = layer.a;
    if (alpha > 0.1){
        out_Color = mix(texture(guiTexture, passTextureCoords), layer, 0.5);
    } else {
        out_Color = texture(guiTexture, passTextureCoords);
    }
}