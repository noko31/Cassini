/*
* Copyright 2014 Abel Juste Ouedraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
package org.insa.core.roadnetwork;

import org.insa.core.enums.TrafficSignaling;
import org.insa.core.enums.TurnRestriction;
import org.insa.core.enums.TurningIndication;
import org.insa.view.graphicmodel.GraphicLane;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Juste Abel Ouedraogo & Guillaume Garzone & François Aïssaoui &  Thomas Thiebaud
 * Class NextLane
 * models movement possibility from current lane
 * section1         section2
 * --------------+------------
 * L1            +   L4
 * --------------+------------
 * +
 * L2            +   L5
 * +
 * --------------+------------
 * L3            +   L6
 * --------------+------------
 *
 * Transistions of L2 : L2->L1 (go to left lane)
 *                      L2->L3 (go to right lane)
 *                      L2->L5 (go straight)
 *
 * Uses Simple framework for xml serialization.
 * See http://simple.sourceforge.net/ for further details.
 */
@Root
public class NextLane {
    /**
     * target lane
     * This is a lane accessible from current lane
     */
    
    private GraphicLane targetLane;
    
    /**
     * turn restrictions (banned turns,...)
     */
    @Attribute
    private TurnRestriction restriction;
    
    /**
     * turning indication in lane
     */
    @Attribute
    private TurningIndication indication;
    
    /**
     * traffic signaling(traffic light, stop sign, ...)
     */
    @Attribute
    private TrafficSignaling signal;
    
    @Attribute
    private String ref;
    
    public NextLane(){
        super();
        this.restriction = TurnRestriction.NONE;
        this.indication = TurningIndication.NONE;
        this.signal = TrafficSignaling.NONE;
    }
    
    public void setTargetLane(GraphicLane targetLane) {
        this.targetLane = targetLane;
        if(ref==null) ref = targetLane.getId();
    }

    public GraphicLane getTargetLane() {
        return targetLane;
    }
    
    public TrafficSignaling getSignal() {
        return signal;
    }
    
    public void setSignal(TrafficSignaling signal) {
        this.signal = signal;
    }
    
    public TurningIndication getIndication() {
        return indication;
    }
    
    public void setIndication(TurningIndication indication) {
        this.indication = indication;
    }
    
    public TurnRestriction getRestriction() {
        return restriction;
    }
    
    public void setRestriction(TurnRestriction restriction) {
        this.restriction = restriction;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
    
}
