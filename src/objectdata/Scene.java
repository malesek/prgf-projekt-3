package objectdata;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<Object3D> objects;

    public Scene() {
        this.objects = new ArrayList<>();
    }

    public void addObject(final Object3D object) {
        objects.add(object);
    }

    public List<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(List<Object3D> objects){
        for(Object3D object : objects)
            addObject(object);
    }


    // set(i, object)
    // get(i)
    // remove(i)

}

