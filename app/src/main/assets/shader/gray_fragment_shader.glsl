#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vTexCoord;
uniform samplerExternalOES sTexture;
void main() {
    vec4 rgbaData;
    vec3 grayData;
    rgbaData = texture2D(sTexture, vTexCoord);
    grayData.x = (rgbaData.x + rgbaData.y + rgbaData.z) / 3.0;
    grayData.y = grayData.x;
    grayData.z = grayData.x;
    gl_FragColor = vec4(grayData, 1.0);
}