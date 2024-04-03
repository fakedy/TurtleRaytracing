import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import TurtleMath.*;

import static TurtleMath.Dot.dot;

public class Renderer extends JFrame{

    ArrayList<Sphere> spheres = new ArrayList<>();

    Vector3 bgColor = new Vector3(0,0,0); // 255
    Vector3 cameraPos = new Vector3(0,0, 2);

    Vector3 lightDir = new Vector3(-2, -1, -1);

    boolean finished = false;

    float aspectRatio;

    Camera camera;

    Renderer(){

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        aspectRatio = (float) this.getWidth() / (float)this.getHeight(); // sometimes height or width is 0...

        camera = new Camera();
        camera.updateCamera(aspectRatio);


        spheres.add(new Sphere(new Vector3(0,0,0),0.5f, new Vector3(255,0,255)));
        spheres.add(new Sphere(new Vector3(0f,-1,0),0.5f, new Vector3(255,80,20)));


    }

    public class Sphere{

        Vector3 position;
        float radius;
        Vector3 color;

        float roughness = 0.1f;
        float metallic = 0.0f;


        Sphere(Vector3 position, float radius, Vector3 color){
            this.position = position;
            this.radius = radius;
            this.color = color;
        }


    }
    @Override
    public void paint(Graphics g){

        if(finished)
            return;


        aspectRatio = (float) this.getWidth() / (float)this.getHeight(); // temp fix
        camera.updateCamera(aspectRatio);

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                Vector3 cvec = traceRay(toClipSpace(x,y));
                Color color = new Color(cvec.r.intValue(), cvec.g.intValue(), cvec.b.intValue());
                g.setColor(color);
                g.drawRect(x,y,1,1);

            }
        }
        //finished = true;

    }

    Vector2 toClipSpace(int x, int y){

        return new Vector2(((float)x/(this.getWidth()) * 2f - 1f),
                ((float)y/(this.getHeight()) * 2f - 1f) / aspectRatio);
    }

    private Vector3 traceRay(Vector2 cords){

        float x = cords.x;
        float y = cords.y;

        for(int i = 0; i < spheres.size(); i++){

            Vector3 rayDir = new Vector3(x,y, -1);

            Vector3 adjustedCameraPos = cameraPos.subSafe(spheres.get(i).position); // current way of moving spheres
            float a = dot(rayDir, rayDir);
            float b = 2.0f * dot(adjustedCameraPos, rayDir);
            float c = dot(adjustedCameraPos, adjustedCameraPos) - spheres.get(i).radius* spheres.get(i).radius;

            float discriminant = b*b -4.0f * a * c;

            float t1 = (float) ((-b - Math.sqrt(discriminant)) / (2.0f * a));

            Vector3 hitpos = new Vector3(adjustedCameraPos.x+rayDir.x*t1,
                    adjustedCameraPos.y+rayDir.y*t1,
                    adjustedCameraPos.z+rayDir.z*t1);

            if(discriminant >= 0)
                return getRGB(hitpos);

        }
        return bgColor;

    }



    private Vector3 HIT(){


        return null;
    }
    private Vector3 MISS(){
        return bgColor;
    }

    private Vector3 getRGB(Vector3 vec){

        vec.r = ((vec.r+1)/2)*255;
        vec.g = ((vec.g+1)/2);
        vec.b = ((vec.b+1)/2)*255;

        return vec;
    }









}
