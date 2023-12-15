package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public class Pyramid extends Object3D {
    public Pyramid(final Mat4 modelMat, final int color) {
        super(
                List.of(
                        new Point3D(1, 0, -1),  // Vrchol A
                        new Point3D(1, 0, 1),   // Vrchol B
                        new Point3D(-1, 0, 1),  // Vrchol C
                        new Point3D(-1, 0, -1), // Vrchol D
                        new Point3D(0, 1, 0)     // Vrchol E (Vrchol střechy)
                ), List.of(
                        0, 1,
                        1, 2,
                        2, 3,
                        3, 0,
                        0, 4,
                        1, 4,
                        2, 4,
                        3, 4,
                        0, 1,
                        1, 2,
                        2, 3,
                        3, 0
                ), modelMat, color);
    }

    @Override
    Object3D withModelMatrix(final Mat4 modelMatrix) {
        return new Pyramid(modelMatrix, getColor());
    }
}
