package TurtleMath;

import java.util.Vector;

public class Vector3{

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

        this.r = x;
        this.g = y;
        this.b = z;
    }

    public Float x;
    public Float y;
    public Float z;

    public Float r;
    public Float g;
    public Float b;


    public Vector3 add(Vector3 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x + vec1.x;
        this.y = this.y + vec1.y;
        this.z = this.z + vec1.z;

        return this;
    }
    public Vector3 addSafe(Vector3 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x + vec1.x;
        Float y = this.y + vec1.y;
        Float z = this.z + vec1.z;

        return new Vector3(x,y,z);
    }

    public Vector3 sub(Vector3 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x - vec1.x;
        this.y = this.y - vec1.y;
        this.z = this.z - vec1.z;

        return this;
    }
    public Vector3 subSafe(Vector3 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x - vec1.x;
        Float y = this.y - vec1.y;
        Float z = this.z - vec1.z;

        return new Vector3(x,y,z);
    }



}