import objectdata.*;
import rasterdata.RasterBufferedImage;
import rasterop.FilledLineRasterizer;
import renderer.WiredRenderer;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * @author PGRF FIM UHK
 * @version 2023.b
 */

public class App3D {

    private JPanel panel;
    private RasterBufferedImage raster;
    private Scene scene;
    private FilledLineRasterizer filledLineRasterizer;
    private WiredRenderer wiredRenderer;
    private Cube cube;
    private float translateXCube = 0;
    private float translateYCube = 0;
    private float translateZCube = 0;
    private int rotateXCube = 0;
    private int rotateYCube = 0;
    private int rotateZCube = 0;
    private Pyramid pyramid;
    private float translateXPyramid = 3;
    private float translateYPyramid = 0;
    private float translateZPyramid = 0;
    private int rotateXPyramid = 0;
    private int rotateYPyramid = 0;
    private int rotateZPyramid = 0;
    private AxisX axisX;
    private AxisY axisY;
    private AxisZ axisZ;
    private transforms.Camera camera;
    private transforms.Mat4 proj;


    private Point2D point2D;
    private boolean mode;

    public App3D(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        filledLineRasterizer = new FilledLineRasterizer(raster);
        wiredRenderer = new WiredRenderer();
        scene = new Scene();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocus();
        panel.requestFocusInWindow();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(0.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(0.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.forward(0.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.backward(0.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    mode = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    mode = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    if(mode)
                        rotatePyramid(0);
                    else
                        rotateCube(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    if(mode)
                        rotatePyramid(1);
                    else
                        rotateCube(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    if(mode)
                        rotatePyramid(2);
                    else
                        rotateCube(2);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if(mode){
                        translateZPyramid -= 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateZCube -= 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(mode){
                        translateZPyramid += 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateZCube += 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(mode){
                        translateXPyramid += 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateXCube += 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(mode){
                        translateXPyramid -= 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateXCube -= 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    if(mode){
                        translateYPyramid -= 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateYCube -= 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if(mode){
                        translateYPyramid += 0.1;
                        pyramid = new Pyramid(
                                new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                                        .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                                0x00ffff
                        );
                    }
                    else{
                        translateYCube += 0.1;
                        cube = new Cube(
                                new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                                        .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                                0xfdb975
                        );
                    }
                }
                drawScene();
            }
        });

        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                point2D = new Point2D(e.getX(), e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(point2D.getX() > e.getX()){
                    camera = camera.left(0.01);
                    point2D = new Point2D(e.getX(), e.getY());
                }
                if(point2D.getX() < e.getX()){
                    camera = camera.right(0.01);
                    point2D = new Point2D(e.getX(), e.getY());
                }
                if(point2D.getY() > e.getY()){
                    camera = camera.up(0.01);
                    point2D = new Point2D(e.getX(), e.getY());
                }
                if(point2D.getY() < e.getY()){
                    camera = camera.down(0.01);
                    point2D = new Point2D(e.getX(), e.getY());
                }
                drawScene();
            }
        });

        initScene();
    }

    private void rotatePyramid(int rotateDirection){
        if(rotateDirection == 0){
            rotateXPyramid += 1;
            pyramid = new Pyramid(
                    new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                            .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                    0x00ffff
            );
        } else if (rotateDirection == 1) {
            rotateYPyramid += 1;
            pyramid = new Pyramid(
                    new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                            .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                    0x00ffff
            );
        } else if (rotateDirection == 2) {
            rotateZPyramid += 1;
            pyramid = new Pyramid(
                    new Mat4RotXYZ(Math.toRadians(rotateXPyramid), Math.toRadians(rotateYPyramid), Math.toRadians(rotateZPyramid))
                            .mul(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid)),
                    0x00ffff
            );
        }
    }
    private void rotateCube(int rotateDirection){
        if(rotateDirection == 0){
            rotateXCube += 1;
            cube = new Cube(
                    new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                            .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                    0xfdb975
            );
        } else if (rotateDirection == 1) {
            rotateYCube += 1;
            cube = new Cube(
                    new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                            .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                    0xfdb975
            );
        } else if (rotateDirection == 2) {
            rotateZCube += 1;
            cube = new Cube(
                    new Mat4RotXYZ(Math.toRadians(rotateXCube), Math.toRadians(rotateYCube), Math.toRadians(rotateZCube))
                            .mul(new Mat4Transl(translateXCube,translateYCube,translateZCube)),
                    0xfdb975
            );
        }
    }

    public void initScene() {
        camera = new Camera(
                new Vec3D(0, 10, 0),
                Math.toRadians(90),
                Math.toRadians(0),
                1,
                true
        );

        proj = new transforms.Mat4PerspRH(
                Math.PI / 4,
                raster.getHeight() / (double)raster.getWidth(),
                0.1,
                20.
        );

        pyramid = new Pyramid(new Mat4Transl(translateXPyramid,translateYPyramid,translateZPyramid), 0x00ffff);

        cube = new Cube(new Mat4Transl(translateXCube,translateYCube,translateZCube), 0xfdb975);


        axisX = new AxisX(new transforms.Mat4Transl(0,-1.5,-1.5), 0xff0000);
        axisY = new AxisY(new transforms.Mat4Transl(-1.5,0,-1.5), 0x00ff00);
        axisZ = new AxisZ(new transforms.Mat4Transl(-1.5,-1.5,0), 0x0000ff);
    }

    public void drawScene() {
        clear(0xaaaaaa);

        scene = new Scene();
        scene.addObject(axisX);
        scene.addObject(axisY);
        scene.addObject(axisZ);
        scene.addObject(cube);
        scene.addObject(pyramid);
        wiredRenderer.render(raster, scene, filledLineRasterizer, camera.getViewMatrix(), proj);

        panel.repaint();
    }

    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();
    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        drawScene();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App3D(800, 600).start());
    }

}
