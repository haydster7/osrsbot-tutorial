package com.blaydens;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.scene.HintArrow;

public class Util {
    public static String generateRandomString(int len){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++){
            sb.append((char)Random.nextInt(97,122));
        }
        return sb.toString();
    }

    public static void printDebugInfo(){
        //CLog.log("# of dialog items: " + Dialog.getChatOptions().length);
        //CLog.log("Dialog continue text: " + Dialog.getContinue().getText());
        /*CLog.log("Is inventory tab visible? " + Interfaces.getComponent(164, 55).isVisible());
        CLog.log("Is inventory tab hidden? " + Interfaces.getComponent(164, 55).isExplicitlyHidden());
        CLog.log("Is options tab visible? " + Interfaces.getComponent(164, 40).isVisible());
        CLog.log("Is options tab hidden? " + Interfaces.getComponent(164, 40).isExplicitlyHidden());

         */
        CLog.log("HintArrow position " + HintArrow.getPosition().getX() + "," + HintArrow.getPosition().getY());
        CLog.log("HintArrow target " + HintArrow.getTarget());
        if(HintArrow.getTarget() != null) {
            CLog.log("HintArrow target id " + HintArrow.getTarget().getId());
            CLog.log("HintArrow target name " + HintArrow.getTarget().getName());
            CLog.log("HintArrow target class " + HintArrow.getTarget().getClass().getSimpleName());
        }
    }
}
