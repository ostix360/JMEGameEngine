#version 330 core


out vec4 out_Color;

uniform vec4 layer;

void main(void){
    float alpha = layer.a;
    if (alpha > 0.1){
        out_Color = mix(vec4(0.0, 0.0, 0.0, 0.0), layer, 0.5);
    } else {
        out_Color = vec4(0.0, 0.0, 0.0, 0.0);
    }
}