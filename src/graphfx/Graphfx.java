/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfx;

import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author Kelly Jones
 */
public class Graphfx extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    StackPane root = new StackPane();
    @FXML
    private Slider alpha;
    @FXML
    private Slider beta;
    @FXML
    private Slider gamma;
    @FXML
    private Slider delta
    
        @FXML
    private HBox curveC;
    @FXML
    private TextField posStart;
    @FXML
    private TextField posEnd;
    @FXML
    private TextField stepStize;
    @FXML
    private TextField scale;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Graphfx.class.getResource("GraphController.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("didn't load");
        }
    }

    
    ab1test ab1Run = new ab1test();
    
    

    //GraphicsContext gc = curveC.getGraphicsContext2D();
    public void blacken(){
        curveC.setStyle("-fx-background-color: black;");
    }
    
    public void removeLast() {
        curveC.getChildren().remove(curveC.getChildren().size() - 1);
        
    }
    
    public void setValues(){
        if(posStart.getText()!=null){
            setstart();
        }
        if(posEnd.getText()!= null){
            setend();
        }
        
        
        
    }
    
    public void setstart(){
        ab1Run.setStart(Integer.valueOf(posStart.getText()));
        System.out.println(ab1Run.startTrim);
    }
    
    public void setend(){
        ab1Run.setEnd(Integer.valueOf(posEnd.getText()));
        System.out.println(ab1Run.endTrim);
    
    }
    
    public void genedraw() {
        Double graphheight = curveC.getHeight();
        Long runT = System.nanoTime();
        Group curveG = new Group();     
        ab1Run.readnprint(true);
        int interval = 1;
        Double factorx = 1.0;
        Double factory = 1.0;
        Double factorq = 5.0;
        Double push = 0.0;
        Double xcord = 0.0;
        Double xstep = 2.0;
        for (int i = 0; i < Math.max(ab1Run.read.size(),ab1Run.qual.size()) - 2*interval; i += interval) {
            Double xstart = xcord * factorx;
            Double ystart = Double.valueOf(ab1Run.read.get(i)) * factory + push;
            xcord += xstep;
            Double xend = xcord * factorx;

            Double yend = Double.valueOf(ab1Run.read.get(i + interval)) * factory + push;
            double baseline = 200.0;
            if (ystart > baseline) {
                ystart = baseline;
            }
            if (yend > baseline) {
                yend = baseline;
            }
            Double qstart = Double.valueOf(ab1Run.qual.get(i));
            Double qend = Double.valueOf(ab1Run.qual.get(i + interval));
            if (qstart > baseline) {
                qstart = 5.0;
            }
            if (qend > baseline) {
                qend = 5.0;
            }
            Line gene = new Line(xstart,
                    graphheight + ystart * factory + push,
                    xend,
                    graphheight + yend * factory + push);
            Line qual = new Line(
                    xstart,
                    graphheight + qstart * factorq,
                    xend,
                    graphheight + qend * factorq);
            gene.setStroke(Color.GREEN);
            qual.setStroke(Color.RED);
            curveG.getChildren().addAll(gene, qual);
            String out = i + ":" + xstart + "," + ystart + "\t" + qstart + "," + qend;
            System.out.println(out);
            //root.getChildren().add(line);
        }
        curveC.getChildren().addAll(curveG);
        System.out.println("Time to run: " + (System.nanoTime() - runT) / 100000);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void calcndraw() {
        Long runT = System.nanoTime();
        Group curveG = new Group();
        Group group1 = new Group();
        // Rectangle rect = new Rectangle(100,100);
        //curveG.getChildren().add(rect);

        LVRK run = new LVRK();
        run.RKcalc();
        ArrayList<Double> Xcords = run.getXarr();
        ArrayList<Double> Ycords = run.getYarr();
        ArrayList<Double> Tcords = run.getTarr();

        for (int i = 0; i < 600; i += 10) {
            Line line1 = new Line(i, 0, i, 600);
            line1.setStroke(Color.LIGHTGRAY);
            Line line2 = new Line(0, i, 600, i);
            line2.setStroke(Color.LIGHTGRAY);
            group1.getChildren().addAll(line1, line2);
        }
        int interval = 10;
        Double factor = 10.0;

        for (int i = 0; i < Tcords.size() - interval; i += interval) {
            Double xstart = Xcords.get(i);
            Double ystart = Ycords.get(i);
            Double tstart = Tcords.get(i);
            Double xend = Xcords.get(i + interval);
            Double yend = Ycords.get(i + interval);
            Double tend = Tcords.get(i + interval);
            Line linex = new Line(tstart * factor, 
                    curveC.getHeight() - xstart * factor, 
                    tend * factor, 
                    curveC.getHeight() - xend * factor);
            Line liney = new Line(tstart * factor, 
                    curveC.getHeight() - ystart * factor, 
                    tend * factor, 
                    curveC.getHeight() - yend * factor);
            liney.setStroke(Color.YELLOW);
            linex.setStroke(Color.RED);
            curveG.getChildren().addAll(linex, liney);
            //String out = i + ":" + tstart + "," + xstart + "," + ystart;
            //System.out.println(out);
            //root.getChildren().add(line);
        }
        curveC.getChildren().addAll(curveG);
        System.out.println("Time to run: " + (System.nanoTime() - runT) / 100000);

    }

    public void start2(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calcndraw();
            }
        }
        );

        root.getChildren().add(btn);

        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
