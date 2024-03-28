package SlRenderer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//import static SlRenderer.slSingleBatchRenderer.OGL_MATRIX_SIZE;

import static csc133.spot.OGL_MATRIX_SIZE;
import static csc133.spot.shader_program;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class slShaderManager {
    private String vsData;
    private String fsData;
    public slShaderManager (String vs_filename, String fs_filename) throws Exception {
        //these try catches read the data from the files and print them to vsData and fsData
        try {
            vsData = new String(Files.readAllBytes(Paths.get(
                    System.getProperty("user.dir") + "\\assets\\shaders\\"
                            + vs_filename)));
        } catch(IOException e){
            e.printStackTrace();
        }
        try {
            fsData = new String(Files.readAllBytes(Paths.get(
                    System.getProperty("user.dir") + "\\assets\\shaders\\"
                            + fs_filename)));
        } catch(IOException e){
            e.printStackTrace();
        }
    }  // slShaderManager(String vs_filename, String fs_filename)

    public int compile_shader() {
        //make this do the shader stuff that slSingleBatchRenderer does for the shaders
        int csProgram = glCreateProgram();
        int VSID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(VSID, vsData);
        glCompileShader(VSID);
        glAttachShader(csProgram, VSID);
        int FSID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(FSID, fsData);
        glCompileShader(FSID);
        glAttachShader(csProgram, FSID);
        glLinkProgram(csProgram);
        glUseProgram(csProgram);

        return csProgram;

        //System.out.println(vsData);
        //System.out.println(fsData);
    }  // public int compile_shaders()

    public void set_shader_program() {
        glUseProgram(shader_program);
    }  // public void set_shader_program()

    public static void detach_shader() {
        glUseProgram(0);
    }  // public static void detach_shader()

    public void loadMatrix4f(String strMatrixName, Matrix4f my_mat4) {
        // send the data in my_mat4 to strMatrixName in the shader
        // 1. Get the uniform location of strMatrix in the shader program compiled
        // 2. Create a FloatBuffer
        // 3. Load the my_mat4 data to the FloatBuffer
        // 4. Send the FloatBuffer data to uniform location
        int var_location = glGetUniformLocation(shader_program, strMatrixName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        my_mat4.get(matrixBuffer);
        glUniformMatrix4fv(var_location, false, matrixBuffer);
    }  //  public void loadMatrix4f(String strMatrixName, Matrix4f my_mat4)



}
