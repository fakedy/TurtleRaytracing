package TurtleMath;

public class Projection {



    // vertical fov
    public static Mat4 createProjection(float fov, float aspect, float near, float far){

        float tangent = (float) Math.tan(fov/2 * fov);
        float top = near * tangent;
        float right = top * aspect;

        Mat4 matrix = new Mat4();
        matrix.matrice[0][0] = near / right;
        matrix.matrice[0][1] = 0f;
        matrix.matrice[0][2] = 0f;
        matrix.matrice[0][3] = 0f;

        matrix.matrice[1][0] = 0f;
        matrix.matrice[1][1] = near/top;
        matrix.matrice[1][2] = 0f;
        matrix.matrice[1][3] = 0f;

        matrix.matrice[2][0] = 0f;
        matrix.matrice[2][1] = 0f;
        matrix.matrice[2][2] = -(far + near) / (far - near);
        matrix.matrice[2][3] = -1f;

        matrix.matrice[3][0] = 0f;
        matrix.matrice[3][1] = 0f;
        matrix.matrice[3][2] = -(2* far * near) / (far - near);
        matrix.matrice[3][3] = 0f;

        return new Mat4();
    }


}
