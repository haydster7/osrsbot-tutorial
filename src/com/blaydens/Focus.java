package com.blaydens;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.HintArrow;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.SceneObjects;

import java.util.HashMap;
import java.util.Map;

public class Focus {

    public static enum ATTRIBUTE_TYPES {
        NAME,
        ID,
        TEXT,
        GROUP,
        SUB_INDEX
    }
    public static enum TYPES {
        NPC,
        SCENE_OBJECT,
        INTERFACE,
        DIALOG,
        HINT
    }

    private TYPES type;
    private Map<ATTRIBUTE_TYPES, String> attributes;
    private Position position;

    public Focus(TYPES type, Map<ATTRIBUTE_TYPES, String> attributes){
        this(type, attributes, null);
    }

    public Focus(TYPES type, Map<ATTRIBUTE_TYPES, String> attributes, Position position){
        this.type = type;
        this.attributes = attributes;
        this.position = position;
    }
    
    public Object getFocusObject(){
        Object focus = null;
        CLog.log("Getting matching entity of type: " + type.name());
        switch(type){
            case HINT:
                if(attributes.get(ATTRIBUTE_TYPES.TEXT).equals("POSITION")){
                    focus = HintArrow.getPosition();
                } else {
                    focus = HintArrow.getTarget();
                }
                break;
            case NPC:
                focus = Npcs.getNearest(npc -> matches(getEntityAttributes(npc)));
                break;
            case SCENE_OBJECT:
                focus = SceneObjects.getNearest(so -> matches(getEntityAttributes(so)));
                break;
            case INTERFACE:
                if(attributes.get(ATTRIBUTE_TYPES.TEXT) != null) {
                    InterfaceComponent[] ics = Interfaces.get(ic -> matches(getEntityAttributes(ic)));
                    if (ics.length > 0) {
                        focus = ics[0];
                    }
                } else {
                    focus = Interfaces.getComponent(
                            Integer.parseInt(attributes.get(ATTRIBUTE_TYPES.GROUP)),
                            Integer.parseInt(attributes.get(ATTRIBUTE_TYPES.ID))
                    );
                }
                break;
            case DIALOG:
                if(attributes.get(ATTRIBUTE_TYPES.TEXT).contains("ontinue") && Dialog.canContinue()){
                    focus = Dialog.getContinue();
                } else {
                    focus = Dialog.getChatOption(text -> matches(getEntityAttributes(text)));
                }
                break;
            default:
                CLog.log("Focus entity type " + type.name() + " not yet implemented");
                break;
        }
        return focus;
    }

    private boolean matches(Map<ATTRIBUTE_TYPES, String> entityAttributes){
        boolean match = false;
        for(Map.Entry<ATTRIBUTE_TYPES,String> focusAttribute : attributes.entrySet()){
            if(focusAttribute.getKey() == ATTRIBUTE_TYPES.TEXT){
                CLog.log("Checking if focus attribute key: " + focusAttribute.getKey() + " is equal to " + ATTRIBUTE_TYPES.TEXT);
                match = (entityAttributes.get(focusAttribute.getKey()).contains(focusAttribute.getValue()));
            } else {
                match = (entityAttributes.get(focusAttribute.getKey()).equals(focusAttribute.getValue()));
            }
        }
        return match;
    }

    private Map<ATTRIBUTE_TYPES, String> getEntityAttributes(Npc npc){
        return new HashMap<ATTRIBUTE_TYPES, String>(){{
            put(ATTRIBUTE_TYPES.NAME, npc.getName());
            put(ATTRIBUTE_TYPES.ID, String.valueOf(npc.getId()));
        }};
    }

    private Map<ATTRIBUTE_TYPES, String> getEntityAttributes(SceneObject sceneObject){
        return new HashMap<ATTRIBUTE_TYPES, String>(){{
            put(ATTRIBUTE_TYPES.NAME, sceneObject.getName());
            put(ATTRIBUTE_TYPES.ID, String.valueOf(sceneObject.getId()));
        }};
    }

    private Map<ATTRIBUTE_TYPES, String> getEntityAttributes(String text){
        return new HashMap<ATTRIBUTE_TYPES, String>(){{
            put(ATTRIBUTE_TYPES.TEXT, text);
        }};
    }

    private Map<ATTRIBUTE_TYPES, String> getEntityAttributes(InterfaceComponent ic) {
        return new HashMap<ATTRIBUTE_TYPES, String>(){{
            put(ATTRIBUTE_TYPES.TEXT, ic.getToolTip());
        }};
    }

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public Map<ATTRIBUTE_TYPES, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<ATTRIBUTE_TYPES, String> attributes) {
        this.attributes = attributes;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
