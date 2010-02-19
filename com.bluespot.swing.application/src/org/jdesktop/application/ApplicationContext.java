/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

package org.jdesktop.application;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import javax.swing.JComponent;

/**
 * A singleton that manages shared objects, like actions, resources, and tasks,
 * for {@code Applications}.
 * <p>
 * {@link Application Applications} use the {@code ApplicationContext} singleton
 * to find global values and services. The majority of the Swing Application
 * Framework API can be accessed through {@code ApplicationContext}. The static
 * {@code getInstance} method returns the singleton Typically it's only called
 * after the application has been {@link Application#launch launched}, however
 * it is always safe to call {@code getInstance}.
 * 
 * @see Application
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class ApplicationContext extends AbstractBean {
    private static final Logger logger = Logger.getLogger(ApplicationContext.class.getName());
    private final List<TaskService> taskServices;
    private final List<TaskService> taskServicesReadOnly;
    private ResourceManager resourceManager;
    private ActionManager actionManager;
    private LocalStorage localStorage;
    private SessionStorage sessionStorage;
    private Application application = null;
    private Class applicationClass = null;
    private JComponent focusOwner = null;
    private Clipboard clipboard = null;
    private final Throwable uncaughtException = null;
    private TaskMonitor taskMonitor = null;

    protected ApplicationContext() {
        this.resourceManager = new ResourceManager(this);
        this.actionManager = new ActionManager(this);
        this.localStorage = new LocalStorage(this);
        this.sessionStorage = new SessionStorage(this);
        this.taskServices = new CopyOnWriteArrayList<TaskService>();
        this.taskServices.add(new TaskService("default"));
        this.taskServicesReadOnly = Collections.unmodifiableList(this.taskServices);
    }

    /**
     * Returns the application's class or null if the application hasn't been
     * launched and this property hasn't been set. Once the application has been
     * launched, the value returned by this method is the same as {@code
     * getApplication().getClass()}.
     * 
     * @return the application's class or null
     * @see #setApplicationClass
     * @see #getApplication
     */
    public final synchronized Class getApplicationClass() {
        return this.applicationClass;
    }

    /**
     * Called by {@link Application#launch Application.launch()} to record the
     * application's class.
     * <p>
     * This method is only intended for testing, or design time configuration.
     * Normal applications shouldn't need to call it directly.
     * 
     * @see #getApplicationClass
     */
    public final synchronized void setApplicationClass(final Class applicationClass) {
        if (this.application != null) {
            throw new IllegalStateException("application has been launched");
        }
        this.applicationClass = applicationClass;
    }

    /**
     * The {@code Application} singleton, or null if {@code launch} hasn't been
     * called yet.
     * 
     * @return the launched Application singleton.
     * @see Application#launch
     */
    public final synchronized Application getApplication() {
        return this.application;
    }

    /*
     * Called by Application.launch().
     */
    synchronized void setApplication(final Application application) {
        if (this.application != null) {
            throw new IllegalStateException("application has already been launched");
        }
        this.application = application;
    }

    /**
     * The application's {@code ResourceManager} provides read-only cached
     * access to resources in ResourceBundles via the {@link ResourceMap
     * ResourceMap} class.
     * 
     * @return this application's ResourceManager.
     * @see #getResourceMap(Class, Class)
     */
    public final ResourceManager getResourceManager() {
        return this.resourceManager;
    }

    /**
     * Change this application's {@code ResourceManager}. An {@code
     * ApplicationContext} subclass that wanted to fundamentally change the way
     * {@code ResourceMaps} were created and cached could replace this property
     * in its constructor.
     * <p>
     * Throws an IllegalArgumentException if resourceManager is null.
     * 
     * @param resourceManager
     *            the new value of the resourceManager property.
     * @see #getResourceMap(Class, Class)
     * @see #getResourceManager
     */
    protected void setResourceManager(final ResourceManager resourceManager) {
        if (resourceManager == null) {
            throw new IllegalArgumentException("null resourceManager");
        }
        final Object oldValue = this.resourceManager;
        this.resourceManager = resourceManager;
        this.firePropertyChange("resourceManager", oldValue, this.resourceManager);
    }

    /**
     * Returns a {@link ResourceMap#getParent chain} of two or more
     * ResourceMaps. The first encapsulates the ResourceBundles defined for the
     * specified class, and its parent encapsulates the ResourceBundles defined
     * for the entire application.
     * <p>
     * This is just a convenience method that calls
     * {@link ResourceManager#getResourceMap(Class, Class)
     * ResourceManager.getResourceMap()}. It's defined as:
     * 
     * <pre>
     * return getResourceManager().getResourceMap(cls, cls);
     * </pre>
     * 
     * @param cls
     *            the class that defines the location of ResourceBundles
     * @return a {@code ResourceMap} that contains resources loaded from {@code
     *         ResourceBundles} found in the resources subpackage of the
     *         specified class's package.
     * @see ResourceManager#getResourceMap(Class)
     */
    public final ResourceMap getResourceMap(final Class cls) {
        return this.getResourceManager().getResourceMap(cls, cls);
    }

    /**
     * Returns a {@link ResourceMap#getParent chain} of two or more
     * ResourceMaps. The first encapsulates the ResourceBundles defined for the
     * all of the classes between {@code startClass} and {@code stopClass}
     * inclusive. It's parent encapsulates the ResourceBundles defined for the
     * entire application.
     * <p>
     * This is just a convenience method that calls
     * {@link ResourceManager#getResourceMap(Class, Class)
     * ResourceManager.getResourceMap()}. It's defined as:
     * 
     * <pre>
     * return getResourceManager().getResourceMap(startClass, stopClass);
     * </pre>
     * 
     * @param startClass
     *            the first class whose ResourceBundles will be included
     * @param stopClass
     *            the last class whose ResourceBundles will be included
     * @return a {@code ResourceMap} that contains resources loaded from {@code
     *         ResourceBundles} found in the resources subpackage of the
     *         specified class's package.
     * @see ResourceManager#getResourceMap(Class, Class)
     */
    public final ResourceMap getResourceMap(final Class startClass, final Class stopClass) {
        return this.getResourceManager().getResourceMap(startClass, stopClass);
    }

    /**
     * Returns the {@link ResourceMap#getParent chain} of ResourceMaps that's
     * shared by the entire application, beginning with the one defined for the
     * Application class, i.e. the value of the {@code applicationClass}
     * property.
     * <p>
     * This is just a convenience method that calls
     * {@link ResourceManager#getResourceMap() ResourceManager.getResourceMap()}
     * . It's defined as:
     * 
     * <pre>
     * return getResourceManager().getResourceMap();
     * </pre>
     * 
     * @return the Application's ResourceMap
     * @see ResourceManager#getResourceMap()
     * @see #getApplicationClass
     */
    public final ResourceMap getResourceMap() {
        return this.getResourceManager().getResourceMap();
    }

    /**
     * 
     * @return this application's ActionManager.
     * @see #getActionMap(Object)
     */
    public final ActionManager getActionManager() {
        return this.actionManager;
    }

    /**
     * Change this application's {@code ActionManager}. An {@code
     * ApplicationContext} subclass that wanted to fundamentally change the way
     * {@code ActionManagers} were created and cached could replace this
     * property in its constructor.
     * <p>
     * Throws an IllegalArgumentException if actionManager is null.
     * 
     * @param actionManager
     *            the new value of the actionManager property.
     * @see #getActionManager
     * @see #getActionMap(Object)
     */
    protected void setActionManager(final ActionManager actionManager) {
        if (actionManager == null) {
            throw new IllegalArgumentException("null actionManager");
        }
        final Object oldValue = this.actionManager;
        this.actionManager = actionManager;
        this.firePropertyChange("actionManager", oldValue, this.actionManager);
    }

    /**
     * Returns the shared {@code ActionMap} chain for the entire {@code
     * Application}.
     * <p>
     * This is just a convenience method that calls
     * {@link ActionManager#getActionMap() ActionManager.getActionMap()}. It's
     * defined as:
     * 
     * <pre>
     * return getActionManager().getActionMap()
     * </pre>
     * 
     * @return the {@code ActionMap} chain for the entire {@code Application}.
     * @see ActionManager#getActionMap()
     */
    public final ApplicationActionMap getActionMap() {
        return this.getActionManager().getActionMap();
    }

    /**
     * Returns the {@code ApplicationActionMap} chain for the specified actions
     * class and target object.
     * <p>
     * This is just a convenience method that calls
     * {@link ActionManager#getActionMap() ActionManager.getActionMap(Class,
     * Object)}. It's defined as:
     * 
     * <pre>
     * return getActionManager().getActionMap(actionsClass, actionsObject)
     * </pre>
     * 
     * @return the {@code ActionMap} chain for the entire {@code Application}.
     * @see ActionManager#getActionMap(Class, Object)
     */
    public final ApplicationActionMap getActionMap(final Class actionsClass, final Object actionsObject) {
        return this.getActionManager().getActionMap(actionsClass, actionsObject);
    }

    /**
     * Defined as {@code getActionMap(actionsObject.getClass(), actionsObject)}.
     * 
     * @return the {@code ActionMap} for the specified object
     * @see #getActionMap(Class, Object)
     */
    public final ApplicationActionMap getActionMap(final Object actionsObject) {
        if (actionsObject == null) {
            throw new IllegalArgumentException("null actionsObject");
        }
        return this.getActionManager().getActionMap(actionsObject.getClass(), actionsObject);
    }

    /**
     * The shared {@link LocalStorage LocalStorage} object.
     * 
     * @return the shared {@link LocalStorage LocalStorage} object.
     */
    public final LocalStorage getLocalStorage() {
        return this.localStorage;
    }

    /**
     * The shared {@link LocalStorage LocalStorage} object.
     * 
     * @param localStorage
     *            the shared {@link LocalStorage LocalStorage} object.
     */
    protected void setLocalStorage(final LocalStorage localStorage) {
        if (localStorage == null) {
            throw new IllegalArgumentException("null localStorage");
        }
        final Object oldValue = this.localStorage;
        this.localStorage = localStorage;
        this.firePropertyChange("localStorage", oldValue, this.localStorage);
    }

    /**
     * The shared {@link SessionStorage SessionStorage} object.
     * 
     * @return the shared {@link SessionStorage SessionStorage} object.
     */
    public final SessionStorage getSessionStorage() {
        return this.sessionStorage;
    }

    /**
     * The shared {@link SessionStorage SessionStorage} object.
     * 
     * @param sessionStorage
     *            the shared {@link SessionStorage SessionStorage} object.
     */
    protected void setSessionStorage(final SessionStorage sessionStorage) {
        if (sessionStorage == null) {
            throw new IllegalArgumentException("null sessionStorage");
        }
        final Object oldValue = this.sessionStorage;
        this.sessionStorage = sessionStorage;
        this.firePropertyChange("sessionStorage", oldValue, this.sessionStorage);
    }

    /**
     * A shared {@code Clipboard}.
     */
    public Clipboard getClipboard() {
        if (this.clipboard == null) {
            try {
                this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            } catch (final SecurityException e) {
                this.clipboard = new Clipboard("sandbox");
            }
        }
        return this.clipboard;
    }

    /**
     * The application's focus owner.
     */
    public JComponent getFocusOwner() {
        return this.focusOwner;
    }

    void setFocusOwner(final JComponent focusOwner) {
        final Object oldValue = this.focusOwner;
        this.focusOwner = focusOwner;
        this.firePropertyChange("focusOwner", oldValue, this.focusOwner);
    }

    private List<TaskService> copyTaskServices() {
        return new ArrayList<TaskService>(this.taskServices);
    }

    public void addTaskService(final TaskService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("null taskService");
        }
        List<TaskService> oldValue = null, newValue = null;
        boolean changed = false;
        synchronized (this.taskServices) {
            if (!this.taskServices.contains(taskService)) {
                oldValue = this.copyTaskServices();
                this.taskServices.add(taskService);
                newValue = this.copyTaskServices();
                changed = true;
            }
        }
        if (changed) {
            this.firePropertyChange("taskServices", oldValue, newValue);
        }
    }

    public void removeTaskService(final TaskService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("null taskService");
        }
        List<TaskService> oldValue = null, newValue = null;
        boolean changed = false;
        synchronized (this.taskServices) {
            if (this.taskServices.contains(taskService)) {
                oldValue = this.copyTaskServices();
                this.taskServices.remove(taskService);
                newValue = this.copyTaskServices();
                changed = true;
            }
        }
        if (changed) {
            this.firePropertyChange("taskServices", oldValue, newValue);
        }
    }

    public TaskService getTaskService(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("null name");
        }
        for (final TaskService taskService : this.taskServices) {
            if (name.equals(taskService.getName())) {
                return taskService;
            }
        }
        return null;
    }

    /**
     * Returns the default TaskService, i.e. the one named "default":
     * <code>return getTaskService("default")</code>. The
     * {@link ApplicationAction#actionPerformed ApplicationAction
     * actionPerformed} method executes background <code>Tasks</code> on the
     * default TaskService. Application's can launch Tasks in the same way, e.g.
     * 
     * <pre>
     * ApplicationContext.getInstance().getTaskService().execute(myTask);
     * </pre>
     * 
     * @return the default TaskService.
     * @see #getTaskService(String)
     * 
     */
    public final TaskService getTaskService() {
        return this.getTaskService("default");
    }

    /**
     * Returns a read-only view of the complete list of TaskServices.
     * 
     * @return a list of all of the TaskServices.
     * @see #addTaskService
     * @see #removeTaskService
     */
    public List<TaskService> getTaskServices() {
        return this.taskServicesReadOnly;
    }

    /**
     * Returns a shared TaskMonitor object. Most applications only need one
     * TaskMonitor for the sake of status bars and other status indicators.
     * 
     * @return the shared TaskMonitor object.
     */
    public final TaskMonitor getTaskMonitor() {
        if (this.taskMonitor == null) {
            this.taskMonitor = new TaskMonitor(this);
        }
        return this.taskMonitor;
    }
}
