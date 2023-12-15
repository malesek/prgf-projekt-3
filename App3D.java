import objectdata.*;
import rasterdata.RasterBufferedImage;
import rasterop.FilledLineRasterizer;
import renderer.WiredRenderer;
import transforms.Camera;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private Pyramid pyramid;
    private AxisX axisX;
    private AxisY axisY;
    private AxisZ axisZ;
    private transforms.Camera camera;
    private transforms.Mat4 proj;
    private float zoom = 4;

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
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    camera = camera.up(0.1);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    camera = camera.down(0.1);
                }
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
                drawScene();
            }
        });

        initScene();
    }

    public void initScene() {
        camera = new Camera(
                new Vec3D(0, 10, 0),
                Math.toRadians(80),
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

        pyramid = new Pyramid(new transforms.Mat4Transl(3,0,0), 0x00ffff);

        cube = new Cube(new transforms.Mat4Transl(0, 0, 0), 0xfdb975);

        axisX = new AxisX(new transforms.Mat4Transl(0,-1.5,-1.5), 0xff0000);
        axisY = new AxisY(new transforms.Mat4Transl(-1.5,0,-1.5), 0x00ff00);
        axisZ = new AxisZ(new transforms.Mat4Transl(-1.5,-1.5,0), 0x0000ff);

        scene.addObject(cube);
        scene.addObject(pyramid);
        scene.addObject(axisX);
        scene.addObject(axisY);
        scene.addObject(axisZ);
    }

    public void drawScene() {
        clear(0xaaaaaa);

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
