package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public class AxisZ extends Object3D{
    public AxisZ(final Mat4 modelMat, final int color){
        super(List.of(
                new Point3D(0, 0, -1.5),
                new Point3D(0, 0, 1)
        ), List.of(
                0, 1
        ), modelMat, color);
    }

    @Override
    Object3D withModelMatrix(final Mat4 modelMatrix) {
        return new AxisZ(modelMatrix, getColor());
    }
}
