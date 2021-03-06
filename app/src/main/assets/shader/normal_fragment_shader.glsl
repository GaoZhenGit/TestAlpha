#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vTexCoord;
uniform samplerExternalOES sTexture;

float dist(float x, float y, float cx, float cy) {
    return pow(x - cx, 2.0) + pow(y - cy, 2.0);
}

void main() {
    vec4 rgbaData;
    gl_FragColor = texture2D(sTexture, vTexCoord);
}