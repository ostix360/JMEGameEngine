#version 330

in vec3 position;
in vec2 textureCoords;

out vec2 passTextureCoords;

uniform mat4 transformationMatrix;

void main(void){

    gl_Position = transformationMatrix * vec4(position.xy, 0.0, 1.0);
    passTextureCoords = textureCoords;
}