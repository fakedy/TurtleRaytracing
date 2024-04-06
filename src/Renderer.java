import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;


public class Renderer extends JPanel{

    public int newSizeX;
    public int newSizeY;
    private ArrayList<Sphere> spheres = new ArrayList<>();

    private Vector3d bgColor = new Vector3d(0,0,0); // 255
    private Vector3d cameraPos = new Vector3d(0,0, 6);

    private Vector3d lightDir = new Vector3d(-1, 1, -1);

    private float aspectRatio;

    private Camera camera; // TODO: camera should not be in the renderer

    private int viewPortSizeX;
    private int viewPortSizeY;

    private Vector3f[] accumilatedImage;
    private int frameIndex = 1;

    Renderer(int width, int height){

        this.viewPortSizeX = width;
        this.viewPortSizeY = height;

        aspectRatio = (float)viewPortSizeX / (float)viewPortSizeY;
        camera = new Camera(cameraPos, 45, aspectRatio);
        camera.updateCamera(aspectRatio, viewPortSizeX, viewPortSizeY);
        accumilatedImage = new Vector3f[viewPortSizeX+viewPortSizeY*viewPortSizeX];
        for(int i = 0; i < viewPortSizeX+viewPortSizeY*viewPortSizeX; i++){
            accumilatedImage[i] = new Vector3f(0);
        }

        spheres.add(new Sphere(new Vector3d(-5,-5.0,0.0),4.6f, new Vector3d(255,77,0)));
        spheres.add(new Sphere(new Vector3d(0,5.0,0.0),4.6f, new Vector3d(51,77,255)));
        spheres.add(new Sphere(new Vector3d(0,0, 0.0),0.5f, new Vector3d(255,0,255)));
        spheres.add(new Sphere(new Vector3d(1.5,-0.5,0.2),0.5f, new Vector3d(255,140,0)));
        spheres.add(new Sphere(new Vector3d(-1.5,-0.5,0.2),0.5f, new Vector3d(255,140,0)));
        this.setVisible(true);
    }

    public class Sphere{

        Vector3d position;
        float radius;
        Vector3d color;
        float roughness = 0.7f;
        float metallic = 0.0f;

        Sphere(Vector3d position, float radius, Vector3d color){
            this.position = position;
            this.radius = radius;
            this.color = color;
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        long startTime = System.currentTimeMillis();

        for(int y = 0; y < viewPortSizeY; y++){
            for(int x = 0; x < viewPortSizeX; x++){
                Vector3f cvec = rayGen(x, y);
                accumilatedImage[x+y*viewPortSizeX].add(cvec);
                Vector3f stored = accumilatedImage[x+y*viewPortSizeX];
                stored = stored.div(frameIndex, new Vector3f());


                Color color = new Color((int)stored.x, (int)stored.y, (int)stored.z);
                g.setColor(color);
                g.fillRect(x,y,1,1);

            }
        }

        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        g.setColor(Color.GREEN);
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
        g.drawString(String.valueOf(elapsed) + " ms", 20, 20);
        repaint();
        frameIndex++;
    }

    public void shouldResize(){
        viewPortSizeX = newSizeX;
        viewPortSizeY = newSizeY;
        aspectRatio = (float)viewPortSizeX / (float)viewPortSizeY;
        camera.updateCamera(aspectRatio, viewPortSizeX, viewPortSizeY);
        accumilatedImage = new Vector3f[viewPortSizeX+viewPortSizeY*viewPortSizeX];
        for(int i = 0; i < viewPortSizeX+viewPortSizeY*viewPortSizeX; i++){
            accumilatedImage[i] = new Vector3f(0);
        }
        frameIndex = 1;
    }


    private Vector3f rayGen(int x, int y){

        Ray ray = new Ray();
        ray.origin = camera.getPosition();
        ray.direction = camera.getDirectionVectors()[x+y*viewPortSizeX];

        Vector3d finalColor = new Vector3d(0,0,0);
        float energy = 1.0f;

        for(int bounce = 0; bounce < 5; bounce++){
            HitPayload load = traceRay(ray);
            if(load.hitDistance < 0){
                finalColor.add(bgColor.mul(energy, new Vector3d()));
                break;
            }

            double lightStrength = Math.max(load.normal.dot(lightDir.mul(-1, new Vector3d())), 0.0);
            Vector3d sphereColor = load.sphere.color;
            sphereColor = sphereColor.mul(lightStrength, new Vector3d());
            finalColor.add(sphereColor.mul(energy,new Vector3d()));

            energy *= 0.7f;
            ray.origin = load.hitPosition.add(load.normal.mul(0.0001, new Vector3d()));
            Vector3d randomVec = new Vector3d(1 - Math.random()*load.sphere.roughness,1 - Math.random()*load.sphere.roughness,1 - Math.random()*load.sphere.roughness);
            ray.direction = ray.direction.reflect(randomVec.mul(load.normal), new Vector3d());
        }

        return getRGB(finalColor);
    }


    private HitPayload traceRay(Ray ray){

        Sphere closestSphere = null;
        float hitDistance = 1000000000; // float max is better

        Vector3d rayDir = ray.direction;
        for(int i = 0; i < spheres.size(); i++){


            Vector3d adjustedCameraPos = ray.origin.sub(spheres.get(i).position, new Vector3d()); // current way of moving spheres
            double a = rayDir.dot(rayDir);
            double b = 2.0f * adjustedCameraPos.dot(rayDir);
            double c = adjustedCameraPos.dot(adjustedCameraPos) - spheres.get(i).radius* spheres.get(i).radius;

            double discriminant = b*b -4.0f * a * c;

            if(discriminant < 0)
                continue;

            float t1 = (float) ((-b - Math.sqrt(discriminant)) / (2.0f * a));

            if(t1 > 0.0f && t1 < hitDistance){
                hitDistance = t1;
                closestSphere = spheres.get(i);
            }
        }

        if(closestSphere == null){
            return miss(ray);
        }

        return closestHit(ray, hitDistance, closestSphere);
    }

    private class HitPayload{
        float hitDistance;
        Vector3d normal;
        Vector3d hitPosition;
        Vector3d color;
        Sphere sphere;
    }



    private HitPayload closestHit(Ray ray, float hitDistance, Sphere closestSphere){

        Vector3d adjustedCameraPos = ray.origin.sub(closestSphere.position, new Vector3d()); // current way of moving spheres

        HitPayload load = new HitPayload();
        load.sphere = closestSphere;
        load.hitDistance = hitDistance;
        load.hitPosition = adjustedCameraPos.add(ray.direction.mul(hitDistance, new Vector3d()), new Vector3d());
        load.normal = load.hitPosition;
        load.normal = load.normal.normalize(new Vector3d());
        load.hitPosition = load.hitPosition.add(closestSphere.position, new Vector3d());
        return load;

    }
    private HitPayload miss(Ray ray){
        HitPayload load = new HitPayload();
        load.hitDistance = -1;
        return load;
    }

    private Vector3f getRGB(Vector3d vec){

        vec.x = Math.min(vec.x,255);
        vec.y = Math.min(vec.y,255);
        vec.z = Math.min(vec.z,255);

        return new Vector3f((float) vec.x, (float) vec.y, (float) vec.z);
    }
}
