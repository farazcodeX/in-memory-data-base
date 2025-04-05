package todo_service;

import todo_entity.Step;

public class StepService {
        public static void saveStep(int taskRef, String title) {

        Step newStep = new Step();
        newStep.title = title;
        newStep.taskRef = taskRef;
        newStep.status = newStep.status.NoStarted;   
    }
    
}
