package com.google.appinventor.components.runtime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class EventDispatcher {
    private static final boolean DEBUG = false;
    private static final java.util.Map<HandlesEventDispatching, EventRegistry> mapDispatchDelegateToEventRegistry = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class EventClosure {
        private final String componentId;
        private final String eventName;

        private EventClosure(String componentId, String eventName) {
            this.componentId = componentId;
            this.eventName = eventName;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventClosure that = (EventClosure) o;
            return this.componentId.equals(that.componentId) && this.eventName.equals(that.eventName);
        }

        public int hashCode() {
            return (this.eventName.hashCode() * 31) + this.componentId.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class EventRegistry {
        private final HandlesEventDispatching dispatchDelegate;
        private final HashMap<String, Set<EventClosure>> eventClosuresMap = new HashMap<>();

        EventRegistry(HandlesEventDispatching dispatchDelegate) {
            this.dispatchDelegate = dispatchDelegate;
        }
    }

    private EventDispatcher() {
    }

    private static EventRegistry getEventRegistry(HandlesEventDispatching dispatchDelegate) {
        EventRegistry er = mapDispatchDelegateToEventRegistry.get(dispatchDelegate);
        if (er == null) {
            EventRegistry er2 = new EventRegistry(dispatchDelegate);
            mapDispatchDelegateToEventRegistry.put(dispatchDelegate, er2);
            return er2;
        }
        return er;
    }

    private static EventRegistry removeEventRegistry(HandlesEventDispatching dispatchDelegate) {
        return mapDispatchDelegateToEventRegistry.remove(dispatchDelegate);
    }

    public static void registerEventForDelegation(HandlesEventDispatching dispatchDelegate, String componentId, String eventName) {
        EventRegistry er = getEventRegistry(dispatchDelegate);
        Set<EventClosure> eventClosures = (Set) er.eventClosuresMap.get(eventName);
        if (eventClosures == null) {
            eventClosures = new HashSet<>();
            er.eventClosuresMap.put(eventName, eventClosures);
        }
        eventClosures.add(new EventClosure(componentId, eventName));
    }

    public static void unregisterEventForDelegation(HandlesEventDispatching dispatchDelegate, String componentId, String eventName) {
        EventRegistry er = getEventRegistry(dispatchDelegate);
        Set<EventClosure> eventClosures = (Set) er.eventClosuresMap.get(eventName);
        if (eventClosures != null && !eventClosures.isEmpty()) {
            Set<EventClosure> toDelete = new HashSet<>();
            for (EventClosure eventClosure : eventClosures) {
                if (eventClosure.componentId.equals(componentId)) {
                    toDelete.add(eventClosure);
                }
            }
            for (EventClosure eventClosure2 : toDelete) {
                eventClosures.remove(eventClosure2);
            }
        }
    }

    public static void unregisterAllEventsForDelegation() {
        for (EventRegistry er : mapDispatchDelegateToEventRegistry.values()) {
            er.eventClosuresMap.clear();
        }
    }

    public static void removeDispatchDelegate(HandlesEventDispatching dispatchDelegate) {
        EventRegistry er = removeEventRegistry(dispatchDelegate);
        if (er != null) {
            er.eventClosuresMap.clear();
        }
    }

    public static boolean dispatchEvent(Component component, String eventName, Object... args) {
        Object[] args2 = OptionHelper.optionListsFromValues(component, eventName, args);
        boolean dispatched = false;
        HandlesEventDispatching dispatchDelegate = component.getDispatchDelegate();
        if (dispatchDelegate.canDispatchEvent(component, eventName)) {
            EventRegistry er = getEventRegistry(dispatchDelegate);
            Set<EventClosure> eventClosures = (Set) er.eventClosuresMap.get(eventName);
            if (eventClosures != null && eventClosures.size() > 0) {
                dispatched = delegateDispatchEvent(dispatchDelegate, eventClosures, component, args2);
            }
            dispatchDelegate.dispatchGenericEvent(component, eventName, !dispatched, args2);
        }
        return dispatched;
    }

    private static boolean delegateDispatchEvent(HandlesEventDispatching dispatchDelegate, Set<EventClosure> eventClosures, Component component, Object... args) {
        boolean dispatched = false;
        for (EventClosure eventClosure : eventClosures) {
            if (dispatchDelegate.dispatchEvent(component, eventClosure.componentId, eventClosure.eventName, args)) {
                dispatched = true;
            }
        }
        return dispatched;
    }

    public static String makeFullEventName(String componentId, String eventName) {
        return componentId + '$' + eventName;
    }
}
