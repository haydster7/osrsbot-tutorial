package com.blaydens;

import java.util.ListIterator;

public class Decider {
    public static Task chooseTask () {
        //CLog.log("Updating data in current context");
        Data.updateData();
        Task chosenTask = null;
        Task tempTask = null;

        //CLog.log("Choosing task to perform");
        //Iterate backwards through task list
        ListIterator li = Data.TASKS.listIterator(Data.TASKS.size());
        while(li.hasPrevious()){
            tempTask = (Task) li.previous();
            if(tempTask.shouldPerform()){
                chosenTask = Task.getTaskByName(tempTask.getName());
                break;
            }
        }

        if(chosenTask != null){
            CLog.log("Task chosen: " + chosenTask.getName());
        } else {
            CLog.log("No task to be performed");
        }

        return chosenTask;
    }
}
