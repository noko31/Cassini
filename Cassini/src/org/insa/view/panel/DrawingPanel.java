/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.insa.view.panel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.insa.controller.MainController;
import org.insa.model.items.RoadsModel;
import org.insa.model.items.VehiclesModel;
import org.insa.view.utils.DrawingUtils;

/**
 *
 * @author Thomas Thiebaud
 */
public class DrawingPanel extends StackPane implements EventHandler<MouseEvent> {
    protected RoadsModel roads = MainController.getInstance().getModel().getRoadModel();
    protected VehiclesModel vehicles = MainController.getInstance().getModel().getVehiclesModel();
    
    protected RoadDrawingPanel roadDrawingPanel;
    protected VehicleDrawingPanel vehicleDrawingPanel;
    protected NodeDrawingPanel nodeDrawingPanel;
    
    protected DrawingUtils drawingUtils; 

    /**
     * Constructor
     * @param width Panel width
     * @param height Panel height
     */
    public DrawingPanel(int width, int height) {        
        drawingUtils = new DrawingUtils(height, width);
                
        roadDrawingPanel = new RoadDrawingPanel(drawingUtils);
        vehicleDrawingPanel = new VehicleDrawingPanel(drawingUtils);
        
        vehicleDrawingPanel.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                 vehicleDrawingPanel.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }

        });
        
        roadDrawingPanel.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                 roadDrawingPanel.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }

        });

        this.getChildren().add(roadDrawingPanel);
        this.getChildren().add(vehicleDrawingPanel);
        
        this.setOnMouseClicked(this);
        this.paint();
    }
    
    @Override
    public void handle(MouseEvent t) {  
        double x = t.getX();
        double y = t.getY();
        
        if(t.getButton() == MouseButton.PRIMARY)
            drawingUtils.zoom(x, y);
        else if(t.getButton() == MouseButton.SECONDARY)
            drawingUtils.dezoom(x, y); 
        
        this.repaint();
    }
    
    /**
     * Paint all components into panel
     */
    public void paint() {
        roadDrawingPanel.paint();
        vehicleDrawingPanel.paint();
        if(nodeDrawingPanel != null)
            roadDrawingPanel.paint();
    }
    
    /**
     * Repaint the panel by clearing it and calling the paint method
     */
    public void repaint() {
        this.repaintRoads();
        this.repaintVehicles();
        if(nodeDrawingPanel != null) {
            nodeDrawingPanel.repaint();
        }
            
    }
    
    /**
     * Repaint all vehicles
     */
    public void repaintVehicles() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vehicleDrawingPanel.repaint();
            }
        });
    }

    /**
     * Repaint all roads
     */
    public void repaintRoads() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                roadDrawingPanel.repaint();
            }
        });
    }

    /**
     * Add a layer to drawing panel in order to display nodes
     * @param isSourceNode true if display source node, false otherwise
     */
    public void performDisplayNode(boolean isSourceNode) {
        nodeDrawingPanel = new NodeDrawingPanel(drawingUtils);
        
        nodeDrawingPanel.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                if(nodeDrawingPanel != null) {
                    nodeDrawingPanel.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
                }
            }
        });
        
        this.getChildren().add(nodeDrawingPanel);
        
        if(isSourceNode)
            nodeDrawingPanel.paintSourceNode();
        else
            nodeDrawingPanel.paintTargetNode();
    }

    /**
     * Remove all the nodes into the layer and force it to null
     */
    public void performHideNode() {
        nodeDrawingPanel.getChildren().clear();
        nodeDrawingPanel = null;
    }
    
    /**
     * Get vehicle drawing panel
     * @return Vehicle drawing panel
     */
    public VehicleDrawingPanel getVehicleDrawingPanel() {
        return vehicleDrawingPanel;
    }

    /**
     * Get road drawing panel
     * @return Road drawing panel
     */
    public RoadDrawingPanel getRoadDrawingPanel() {
        return roadDrawingPanel;
    }
}

