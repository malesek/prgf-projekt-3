package objectdata;


import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

/**
 * Represents an object in a 3D space implemented as vertex and index buffers
 */
public abstract class Object3D {

    private final List<Point3D> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final Mat4 modelMatrix;

    private final int color;

    public Object3D(final List<Point3D> vertexBuffer,
                    final List<Integer> indexBuffer,
                    final Mat4 modelMatrix,
                    final int color) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMatrix = modelMatrix;
        this.color = color;
    }

    // getters
    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    public int getColor(){
        return color;
    }

    /**
     * Returns a new Object3D with its model matrix set to the given one
     * @param modelMatrix new model matrix
     * @return new Object3D with the new model matrix
     */
    abstract Object3D withModelMatrix(final Mat4 modelMatrix);
}

