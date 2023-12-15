package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;
import java.util.stream.Stream;


    public class Cube extends Object3D {
        public Cube(final Mat4 modelMat, final int color) {
            super(
                    List.of(
                            new Point3D(-1, -1, -1),
                            new Point3D(1, -1, -1),
                            new Point3D(1, 1, -1),
                            new Point3D(-1, 1, -1),

                            new Point3D(-1, -1, 1),
                            new Point3D(1, -1, 1),
                            new Point3D(1, 1, 1),
                            new Point3D(-1, 1, 1)
                    ), List.of(
                            0, 1,
                            1, 2,
                            2, 3,
                            3, 0,
                            4, 5,
                            5, 6,
                            6, 7,
                            7, 4,
                            0, 4,
                            1, 5,
                            2, 6,
                            3, 7
                    ), modelMat, color);
        }

        @Override
        Object3D withModelMatrix(final Mat4 modelMatrix) {
            return new Cube(modelMatrix, getColor());
        }

    }
