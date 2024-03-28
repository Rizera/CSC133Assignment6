package csc133;


import SlRenderer.slShaderManager;

import javax.swing.*;
import java.io.File;

import static SlRenderer.slSingleBatchRenderer.render;

public class Main {
    public static void main(String[] args) throws Exception {
        //slShaderManager newShader = new slShaderManager("vs_0.glsl", "fs_0.glsl");
        //newShader.compile_shader();
        render();
    }
}








