#version 400 core

const int MAX_LIGHTS = 11;

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[MAX_LIGHTS];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;

uniform vec3 lightColor[MAX_LIGHTS];
uniform vec3 attenuation[MAX_LIGHTS];
uniform float lightPower[MAX_LIGHTS];
uniform float shine;
uniform float reflectivity;
uniform vec3 skyColor;

const int pcfCount = 2;
const float totalsTexels = (pcfCount * 2 - 1) * (pcfCount * 2 - 1);

void main() {

    //    float shadowMapSize = 8192;
    //    float texelSize = 1/shadowMapSize;
    //    float total = 0.0;
    //
    //    for (int x =-pcfCount; x <= pcfCount; x++){
    //        for (int y =-pcfCount; y <= pcfCount; y++){
    //            float objectNearstLght = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
    //            if (shadowCoords.z > objectNearstLght){
    //                total += 1.0;
    //            }
    //        }
    //    }
    //
    //    total /= totalsTexels;
    //
    //    float lightFactor = 0.7 -  (clamp(total, 0.0, 0.4) * shadowCoords.w);


    vec4 blendMapColour = texture(blendMap, pass_textureCoords);

    float backTextureAmount = 1 - (blendMapColour.r, blendMapColour.g, blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords *40;
    vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;

    vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;


    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.2);
    vec3 totalSpeculare= vec3(0.0);

    for (int i = 0;i<MAX_LIGHTS;i++){
        float lightBottomDirectionFactor = 1;
        float distance = length(toLightVector[i]);
        float attenuationFactor = max(attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance), 1.0);
        vec3 unitLightVector = normalize(toLightVector[i]);
        if (unitLightVector.y <= 0.99){
            lightBottomDirectionFactor = 0.01 + unitLightVector.y;
            if (lightBottomDirectionFactor <= 0){
                lightBottomDirectionFactor = 0;
            }
            if (lightBottomDirectionFactor > 1){
                lightBottomDirectionFactor = 1;
            }
        }
        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);

        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shine);
        totalDiffuse = totalDiffuse + ((brightness * lightColor[i] * lightPower[i])/attenuationFactor) * lightBottomDirectionFactor;
        totalSpeculare = totalSpeculare + (max(vec3(0.), (dampedFactor * lightColor[i] * reflectivity))/attenuationFactor) * lightBottomDirectionFactor;
    }
    //totalDiffuse = clamp(totalDiffuse,0.2,1.0);
    //totalDiffuse = max(totalDiffuse * lightFactor, 0.001);


    out_Color = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpeculare, 1.0);
    out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
    //out_BrightColor = vec4(0.0);
}
