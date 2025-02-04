#version 330

in vec2 textureCoords1;
in vec2 textureCoords2;
in float blend;

out vec4 out_colour;

uniform sampler2D partcileTexture;

void main(void){

    vec4 color1 = texture(partcileTexture, textureCoords1);
    vec4 color2 = texture(partcileTexture, textureCoords2);

    out_colour = mix(color1, color2, blend);
}