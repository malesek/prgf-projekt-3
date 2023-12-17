package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public class AxisY extends Object3D{
    public AxisY(final Mat4 modelMat, final int color){
        super(List.of(
                new Point3D(0, -1.5, 0),
                new Point3D(0, 1, 0)
        ), List.of(
                0, 1
        ), modelMat, color);
    }

    @Override
    Object3D withModelMatrix(final Mat4 modelMatrix) {
        return new AxisY(modelMatrix, getColor());
    }
}
