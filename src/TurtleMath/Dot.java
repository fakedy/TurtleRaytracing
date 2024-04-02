package TurtleMath;

public class Dot {


    public static float dot(Vector3 vec1, Vector3 vec2){

        float dot = vec1.x*vec2.x + vec1.y*vec2.y + vec1.z*vec2.z;

        return dot;
    }



}
