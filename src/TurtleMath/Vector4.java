package TurtleMath;

public class Vector4 {

    public Vector4(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        this.r = this.x;
        this.g = this.y;
        this.b = this.z;
        this.a = this.w;
    }
    public Vector4(float x){
        this.x = x;
        this.y = x;
        this.z = x;
        this.w = x;

        this.r = this.x;
        this.g = this.y;
        this.b = this.z;
        this.a = this.w;
    }

    public Vector4(Vector3 vec, float w){
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = w;

        this.r = this.x;
        this.g = this.y;
        this.b = this.z;
        this.a = this.w;
    }



    public Float x;
    public Float y;
    public Float z;
    public Float w;

    public Float r;
    public Float g;
    public Float b;
    public Float a;


    public Vector4 add(Vector4 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x + vec1.x;
        this.y = this.y + vec1.y;
        this.z = this.z + vec1.z;
        this.w = this.w + vec1.w;

        return this;
    }
    public Vector4 addSafe(Vector4 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x + vec1.x;
        Float y = this.y + vec1.y;
        Float z = this.z + vec1.z;
        Float w = this.w + vec1.w;

        return new Vector4(x,y,z,w);
    }

    public Vector4 sub(Vector4 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x - vec1.x;
        this.y = this.y - vec1.y;
        this.z = this.z - vec1.z;
        this.w = this.w - vec1.w;

        return this;
    }
    public Vector4 subSafe(Vector4 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x - vec1.x;
        Float y = this.y - vec1.y;
        Float z = this.z - vec1.z;
        Float w = this.w - vec1.w;

        return new Vector4(x,y,z,w);
    }

}
