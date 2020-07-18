/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 *
 * @author lassulfi
 */
public class FacesMessageUtils {
    
    public static void setErrorMessage(FacesContext context, UIComponent toValidate, String errorMesage) {
        ((UIInput) toValidate).setValid(false);
        FacesMessage message = new FacesMessage(errorMesage);
        context.addMessage(toValidate.getClientId(), message);
    }
}
