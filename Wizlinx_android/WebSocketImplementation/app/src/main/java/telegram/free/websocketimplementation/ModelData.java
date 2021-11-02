package telegram.free.websocketimplementation;

public class ModelData {
    String name;

    public ModelData(String name, String message) {
        this.name = name;
        this.message = message;
    }

    String message;

    public ModelData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
