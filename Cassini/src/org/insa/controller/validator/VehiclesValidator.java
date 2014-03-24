/*
 * Copyright 2014 Abel Juste Oueadraogo & Guillaume Garzone & François Aïssaoui & Thomas Thiebaud
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
package org.insa.controller.validator;

import org.insa.view.form.VehiclesForm;

/**
 *
 * @author Thomas Thiebaud
 */
public class VehiclesValidator extends FormValidator {

    /**
     * Constructor
     * @param vehiclesForm Form which need to be validated
     */
    public VehiclesValidator(VehiclesForm vehiclesForm) {
        super(vehiclesForm);
    }
    
    @Override
    public boolean validate() {
        VehiclesForm vehiclesForm = (VehiclesForm) formPanel;
        
        try {
            Integer.valueOf(vehiclesForm.getMaxSpeed().getText());
        } catch(NumberFormatException e) {
            this.error = "Vitesse maximale incorrecte";
            return false;
        }
        
        try {
            Integer.valueOf(vehiclesForm.getMaxAcceleration().getText());
        } catch(NumberFormatException e) {
            this.error = "Accélération maximale incorrecte";
            return false;
        }
        
        try {
            Integer.valueOf(vehiclesForm.getMaxDeceleration().getText());
        } catch(NumberFormatException e) {
            this.error = "Décélération maximale incorrecte";
            return false;
        }
        
        try {
            Integer.valueOf(vehiclesForm.getLength().getText());
        } catch(NumberFormatException e) {
            this.error = "Longueur incorrecte";
            return false;
        }
        
        try {
            Integer.valueOf(vehiclesForm.getQuantity().getText());
        } catch(NumberFormatException e) {
            this.error = "Quantité incorrecte";
            return false;
        }
        
        this.success = "Véhicule(s) correctement ajouté";
        return true;
    }
    
}