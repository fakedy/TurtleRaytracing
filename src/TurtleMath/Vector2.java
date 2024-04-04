package TurtleMath;

public class Vector2 {


    public Vector2(float x, float y){
        this.x = x;
        this.y = y;

        this.r = this.x;
        this.g = this.y;
    }

    public Float x;
    public Float y;

    public Float r;
    public Float g;


    public Vector2 add(Vector2 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x + vec1.x;
        this.y = this.y + vec1.y;

        return this;
    }
    public Vector2 addSafe(Vector2 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x + vec1.x;
        Float y = this.y + vec1.y;


        return new Vector2(x,y);
    }

    public Vector2 sub(Vector2 vec1){ // Modifies the original Vector3, not immutable

        this.x = this.x - vec1.x;
        this.y = this.y - vec1.y;

        return this;
    }
    public Vector2 subSafe(Vector2 vec1){ // Copy to new Vec3 to achieve immutability

        Float x = this.x - vec1.x;
        Float y = this.y - vec1.y;

        return new Vector2(x,y);
    }
}
