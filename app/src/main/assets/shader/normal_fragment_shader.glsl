#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vTexCoord;
uniform samplerExternalOES sTexture;

float dist(float x, float y, float cx, float cy) {
    return pow(x - cx, 2.0) + pow(y - cy, 2.0);
}

void main() {
    vec4 rgbaData;
    rgbaData = texture2D(sTexture, vTexCoord);
    if (dist(vTexCoord.x, vTexCoord.y, 0.5f, 0.5f) > 0.2f) {
        gl_FragColor = vec4(rgbaData.xyz, 0.4);
    } else {
        gl_FragColor = rgbaData;
    }
}