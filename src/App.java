import javax.swing.*;

public class App extends JFrame {

    Renderer renderer;


    App(){


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        renderer = new Renderer(640, 360);
        add(renderer);
        renderer.isOptimizedDrawingEnabled();
        this.setVisible(true);

        while(true){
            update();
        }
    }


    void update(){
        if(this.getWidth() == 0)
            return;
        renderer.newSizeX = this.getWidth();
        renderer.newSizeY = this.getHeight();

    }

}
