package smartbox;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Component implements Serializable {

    private Set<Class<?>> requiredInterfaces;
    private Set<Class<?>> providedInterfaces;
    private transient Map<Class<?>, Field> fields; // transient because Field not serializable
    protected Container container;
    protected String name;

    public Component() {
        fields = new HashMap<Class<?>, Field>();
        providedInterfaces = new HashSet<Class<?>>();
        requiredInterfaces = new HashSet<Class<?>>();
        computeRequiredInterfaces();
        computeProvidedInterfaces();
        container = null;
        name = this.getClass().getSimpleName();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Set<Class<?>> getProvidedInterfaces() {
        return providedInterfaces;
    }

    public Set<Class<?>> getRequiredInterfaces() {
        return requiredInterfaces;
    }

    public Map<Class<?>, Field> getFields() {
        return fields;
    }

    public String toString() { return name; }

    // initializes fields and requiredInterfaces
    public void computeRequiredInterfaces() {
        Field[] fieldArray = this.getClass().getDeclaredFields();
        for(int i = 0; i < fieldArray.length; i++) {
            Field field = fieldArray[i];
            Class<?> type = field.getType();
            if (type.isInterface()) {
                field.setAccessible(true); // Make field accessible even if private
                fields.put(type, field);
                requiredInterfaces.add(type);
            }
        }
    }

    // initializes provided interfaces
    public void computeProvidedInterfaces() {
        Class<?>[] interfaces = this.getClass().getInterfaces();
        for(int i = 0; i < interfaces.length; i++) {
            providedInterfaces.add(interfaces[i]);
        }
    }

    // set the field of this object to the provider
    public void setProvider(Class<?> intf, Component provider) throws Exception {
        Field field = fields.get(intf);
        if (field != null) {
            field.setAccessible(true); // Make sure the field is accessible
            field.set(this, provider);
        }
    }

    // needed by file/open
    public void initComponent() {
        fields = new HashMap<Class<?>, Field>();
        computeProvidedInterfaces();
        computeRequiredInterfaces();
    }
}