package TurtleMath;

public class Mat4 {

    public float matrice[][] = new float[4][4];

    public Mat4(){

    }


    public final float identity[][] =
            {{1,0,0,0},
            {0,1,0,0},
            {0,0,1,0},
            {0,0,0,1}};

    public Mat4 inverse() {
        // TODO: everything....
        return this;
    }

    public Vector4 safeMul(Vector4 vector4) {

        // TODO: Matrix multiplication with vec4...



        return new Vector4(0,0,0,0);
    }
}
