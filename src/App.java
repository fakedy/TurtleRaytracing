import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;

public class App extends JFrame {

    Renderer renderer;


    App(){


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        renderer = new Renderer(640, 360);
        add(renderer);
        renderer.isOptimizedDrawingEnabled();
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                update();
            }
        });
    }


    void update(){
        System.out.println("resize callback");
        renderer.newSizeX = this.getWidth();
        renderer.newSizeY = this.getHeight();
        renderer.shouldResize();

    }



}
