package com.blaydens;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;

import java.util.HashMap;

public class TaskTemplate {
    public static Task click_dialog_containing(String containing){
        return new Task(
                "Click dialog containing: " + containing,
                Task.ACTIONS.CLICK,
                new Focus(
                        Focus.TYPES.DIALOG,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.TEXT, containing);
                        }}
                ),
                task -> {
                    return (
                            Dialog.getChatOptions().length == 3
                                    && Dialog.getChatOption(op -> op.contains(containing)) != null
                    );
                }
        );
    }

    public static Task click_tab_matching(Tab tab){
        return new Task(
                "Open tab: " + tab.name(),
                Task.ACTIONS.CLICK,
                new Focus(
                        Focus.TYPES.INTERFACE,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.GROUP, "164");
                            put(Focus.ATTRIBUTE_TYPES.ID, String.valueOf(tab.getComponentIndex()));
                        }}
                ),
                task -> {
                    InterfaceComponent tabIC = tab.getComponent();
                    return (
                            !Tabs.isOpen(tab)
                            && tabIC.isVisible()
                            && ((Task) task).getPerformedCount() == 0
                    );
                }
        );
    }
}
