/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageCenter {
    /**
     * Send message to targeted message X/HTML tags
     * @param severity info/warning/error/fatal
     * @param message Message to be displayed
     * @param target Targeted tag, null if none
     */
    public static void SendPageMessage(FacesMessage.Severity severity, String message, String target){
        FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(target,fMessage);
    }
    
    /**
     * Send message to targeted message X/HTML tags with no target
     * @param severity info/warning/error/fatal
     * @param message Message to be displayed
     */
    public static void SendPageMessage(FacesMessage.Severity severity, String message){
        FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
}
