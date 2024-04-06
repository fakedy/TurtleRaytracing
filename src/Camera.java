import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.Vector;

public class Camera {

    public Vector3d getPosition() {
        return position;
    }

    private Vector3d position = new Vector3d(0,0,2);
    private Vector3d forwardDirection = new Vector3d(0,0,-1);
    private float fov = 45f;

    private float aspect = 21/9;

    private int viewPortX;
    private int viewPortY;
    private Vector3d[] directionVectors;

    private boolean moved = true;

    private Matrix4d projectionMatrix;
    private Matrix4d projectionInverseMatrix;
    private Matrix4d viewMatrix;
    private Matrix4d viewInverseMatrix;

    Camera(){

    }

    Camera(Vector3d pos, float fov, float aspect){
        this.position = pos;
        this.fov = fov;
        this.aspect = aspect;

    }


    void updateCamera(float aspect, int width, int height){
        this.aspect = aspect;
        this.viewPortX = width;
        this.viewPortY = height;
        moved = true;
        if(moved){
            directionVectors = new Vector3d[viewPortX * viewPortY];
            calculateProjectionMatrices();
            calculateViewMatrices();
            calculateDirectionVectors();
            moved = false;
        }
    }

    private void calculateProjectionMatrices(){
        projectionMatrix = new Matrix4d().perspective(fov,aspect,0.1f,100f);
        projectionInverseMatrix = projectionMatrix.invert();
    }

    private void calculateViewMatrices(){
        viewMatrix = new Matrix4d().lookAt(position,position.add(forwardDirection, new Vector3d()), new Vector3d(0,1,0));
        viewInverseMatrix = viewMatrix.invert();
    }

    private void calculateDirectionVectors(){

        for(int y = 0; y < viewPortY; y++){
            for(int x = 0; x < viewPortX; x++){
                Vector2d coords = new Vector2d((float)x/viewPortX, (float)y/viewPortY);
                coords = coords.mul(2.0f, new Vector2d());
                coords = coords.sub(new Vector2d(1,1), new Vector2d());

                Vector4d target = new Vector4d(coords.x, coords.y, 1, 1).mul(projectionInverseMatrix, new Vector4d());
                Vector4d test = new Vector4d(new Vector3d(target.x,target.y,target.z).div(target.w, new Vector3d()).normalize(new Vector3d()),0);
                Vector4d test2 = test.mul(viewInverseMatrix, new Vector4d());
                Vector3d rayDirection = new Vector3d(test2.x, test2.y, test2.z);
                directionVectors[x + y * viewPortX] = rayDirection;

            }
        }
    }


    public Vector3d[] getDirectionVectors(){
        return directionVectors;
    }


}
