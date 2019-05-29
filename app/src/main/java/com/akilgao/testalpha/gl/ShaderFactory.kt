package com.akilgao.testalpha.gl

import android.opengl.GLES20
import android.util.Log

/**
 * create by akilgao on 2019/5/29
 */
object ShaderFactory {
    val TAG = "ShaderFactory"
    fun getVertexShader(): String {
        return """
attribute vec4 aPosition;
attribute vec4 aTexCoord;
varying vec2 vTexCoord;
uniform mat4 uMatrix;
uniform mat4 uSTMatrix;
void main() {
    vTexCoord = (uSTMatrix * aTexCoord).xy;
    gl_Position = uMatrix*aPosition;
}"""
    }

    fun getFragmentShader(): String {
        return """
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
}"""
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        val shader = GLES20.glCreateShader(type)

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode)
        checkError()
        GLES20.glCompileShader(shader)
        checkError()

        return shader
    }

    fun checkError() {
        val error = GLES20.glGetError()
        if (error != 0) {
            val t = Throwable()
            Log.e(TAG, "GL error: $error", t)
        }
    }

    fun createProgram(vs: String, fs: String): Int {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs)
        val program = GLES20.glCreateProgram()
        checkError()
        if (program == 0) {
            throw RuntimeException("Cannot create GL program: " + GLES20.glGetError())
        }
        GLES20.glAttachShader(program, vertexShader)
        checkError()
        GLES20.glAttachShader(program, fragmentShader)
        checkError()
        GLES20.glLinkProgram(program)
        checkError()
        return program
    }
}