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

    Renderer(){


        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        aspectRatio = (float) this.getWidth() /this.getHeight();


        spheres.add(new Sphere(new Vector3(0,0,0),0.5f, new Vector3(255,0,255)));


    }

    public class Sphere{

        Vector3 position;
        float radius;
        Vector3 color;

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

        for(int y = 0; y < this.getSize().height; y++){
            for(int x = 0; x < this.getSize().width; x++){
                Vector3 cvec = traceRay(
                        ((float)x/(this.getWidth()) * 2f - 1f)*aspectRatio,
                           ((float)y/(this.getHeight()) * 2f - 1f));

                Color color = new Color(cvec.r.intValue(), cvec.g.intValue(), cvec.b.intValue());
                g.setColor(color);
                g.drawRect(x,y,1,1);

            }
        }
        finished = true;

    }

    private Vector3 traceRay(float x, float y){

        Sphere sphere = spheres.get(0);

        Vector3 rayDir = new Vector3(x,y, -1);

        float a = dot(rayDir, rayDir);
        float b = 2.0f * dot(cameraPos, rayDir);
        float c = dot(cameraPos, cameraPos) - sphere.radius*sphere.radius;

        float discriminant = b*b -4.0f * a * c;

        if(discriminant >= 0f)
            return sphere.color;

        return bgColor;

    }









}
