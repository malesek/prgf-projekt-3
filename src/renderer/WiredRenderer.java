package renderer;

import objectdata.Line;
import objectdata.Object3D;
import objectdata.Scene;
import rasterdata.Raster;
import rasterop.FilledLineRasterizer;
import rasterop.LineRasterizer;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

import java.util.List;

public class WiredRenderer {

    /**
     * Transforms the given scene into it's Raster representation
     *
     * @param scene
     */
    public void render(final Raster raster,
                       final Scene scene,
                       final FilledLineRasterizer liner,
                       final Mat4 viewMatrix,
                       final Mat4 projectionMatrix) {
        // for each object3D in the scene
        //      call renderObject with the appropriate transformation (model * view * projection)
        for (Object3D object3D : scene.getObjects()) {
            final Mat4 transformation = object3D.getModelMatrix().mul(viewMatrix).mul(projectionMatrix);
            renderObject(raster, object3D, liner, object3D.getColor(), transformation);
        }
    }

    private void renderObject(final Raster raster,
                              final Object3D object3D,
                              final FilledLineRasterizer liner,
                              final int color,
                              final Mat4 transformation) {
        // 1. compute the transformed points
        Point3D[] transformedPoints = new Point3D[object3D.getVertexBuffer().size()];
        for (int i = 0; i < object3D.getVertexBuffer().size(); i++) {
            transformedPoints[i] = object3D.getVertexBuffer().get(i).mul(transformation);
        }
        // for each line
        for (int i = 0; i < object3D.getIndexBuffer().size(); i += 2) {
            final int firstIndex = object3D.getIndexBuffer().get(i);
            final int secondIndex = object3D.getIndexBuffer().get(i + 1);
            drawLine(
                    raster,
                    transformedPoints[firstIndex],
                    transformedPoints[secondIndex],
                    liner,
                    color
            );
        }
    }

    void drawLine(
            final Raster raster,
            final Point3D first,
            final Point3D second,
            final FilledLineRasterizer liner,
            final int color
    ) {
        // 2. clip in the homogeneous coordinates (z < 0)
        if (first.getZ() > 0 || second.getZ() > 0 ||
                first.getX() > -first.getW() || first.getX() < first.getW() ||
                first.getY() > -first.getW() || first.getY() < first.getW() ||
                first.getZ() > first.getW() ||
                second.getX() > -second.getW() || second.getX() < second.getW() ||
                second.getY() > -second.getW() || second.getY() < second.getW() ||
                second.getZ() > second.getW()) {
            return;
        }
        // 3. dehomogenization (x, y, z)
        first.dehomog().ifPresent(p1 -> {
            second.dehomog().ifPresent(p2 -> {
                // 4. projection to 2d (Vec3D -> Vec2D) (ignoreZ)
                final Vec2D first2D = p1.ignoreZ();
                final Vec2D second2D = p2.ignoreZ();

                // 5. transformation to viewport <-1, 1> --> <0, 2> --> <0, width - 1> = (first, second)
                final Vec2D transformedFirst = transformToViewport(first2D, raster);
                final Vec2D transformedSecond = transformToViewport(second2D, raster);

                // 6. rasterization using the given liner (drawLine)
                liner.rasterize(transformedFirst.getX(), transformedFirst.getY(), transformedSecond.getX(), transformedSecond.getY(), color);
            });
        });

    }
    private Vec2D transformToViewport(Vec2D vec2D, Raster raster) {
        return new Vec2D((vec2D.getX() + 1) * 0.5 * (raster.getWidth() - 1), (1 - vec2D.getY()) * 0.5 * (raster.getHeight() - 1));
    }

}

