package mvc;

import java.io.Serializable;

public abstract class Model extends Publisher implements Serializable {
    boolean unsavedChanges = false;
    String fileName = null;
    public void changed(){
        setUnsavedChanges(true);
        notifySubscribers();
    }
    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean b) {
        this.unsavedChanges = b;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fName) {
        this.fileName = fName;
    }
}
