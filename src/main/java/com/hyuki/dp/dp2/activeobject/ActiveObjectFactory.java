package com.hyuki.dp.dp2.activeobject;

public class ActiveObjectFactory {
    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread scheduler = new SchedulerThread(queue);//use for execute Request
        Proxy proxy = new Proxy(scheduler, servant);//wrapper future result and request queue
        scheduler.start();//
        return proxy;
    }
}
