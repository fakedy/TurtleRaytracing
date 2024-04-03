import TurtleMath.*;

import java.util.ArrayList;

public class Camera {

    private Vector3 position = new Vector3(0,0,-2);
    private Vector3 forwardDirection = new Vector3(0,0,-1);
    private float fov = 45f;

    private float aspect = 21/9;

    private int viewPortX;
    private int viewPortY;
    private ArrayList<Vector3> directionVectors = new ArrayList<>(); // cache of vectors

    private boolean moved = true;

    private Mat4 projectionMatrix;
    private Mat4 projectionInverseMatrix;
    private Mat4 viewMatrix;
    private Mat4 viewInverseMatrix;

    Camera(){

    }

    Camera(Vector3 pos, float fov, float aspect){
        this.position = pos;
        this.fov = fov;
        this.aspect = aspect;
    }


    void updateCamera(float aspect){
        this.aspect = aspect;

        if(moved){
            calculateProjectionMatrices();
            calculateViewMatrices();
            calculateDirectionVectors();
            moved = false;
        }
    }

    private void calculateProjectionMatrices(){
        projectionMatrix = Projection.createProjection(fov, aspect,0.1f, 100f);
        projectionInverseMatrix = projectionMatrix.inverse();
    }

    private void calculateViewMatrices(){
        viewMatrix = LookAt.createLookAt(position, position.addSafe(forwardDirection), new Vector3(0,1,0));
        viewInverseMatrix = viewMatrix.inverse();
    }

    private void calculateDirectionVectors(){

        for(int y = 0; y < viewPortY; y++){
            for(int x = 0; x < viewPortX; x++){
                // [-1,1] space
                Vector2 test = new Vector2((float)x/(viewPortX * 2f - 1f), ((float)y/(viewPortY) * 2f - 1f));
                // TODO: stuff

            }
        }

    }


    public ArrayList<Vector3> getDirectionVectors(){
        return directionVectors;
    }


}
