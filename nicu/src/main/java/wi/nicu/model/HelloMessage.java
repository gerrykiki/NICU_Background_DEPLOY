package wi.nicu.model;

public class HelloMessage {
	private String name;
	//Receive message model
	public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
