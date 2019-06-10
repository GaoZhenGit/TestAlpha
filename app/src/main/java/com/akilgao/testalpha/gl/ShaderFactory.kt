package com.akilgao.testalpha.gl

import android.content.Context
import android.opengl.GLES20
import android.util.Log

/**
 * create by akilgao on 2019/5/29
 */
object ShaderFactory {
    val TAG = "ShaderFactory"

    fun getShader(context:Context, shaderName:String):String {
        val inputStream = context.assets.open(shaderName)
        var len: Int
        val bytes = ByteArray(4096)
        val sb = StringBuffer()
        while (inputStream.read(bytes).apply { len = this } > 0) {
            sb.append(String(bytes, 0, len))
        }
        return sb.toString()
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
        GLES20.glDeleteShader(vertexShader)
        GLES20.glDeleteShader(fragmentShader)
        return program
    }
}