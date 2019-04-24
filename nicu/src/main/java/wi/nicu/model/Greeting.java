package wi.nicu.model;

public class Greeting {
//	private final long id;
//	private final String content;
//
//	public Greeting(long id, String content) {
//		this.id = id;
//		this.content = content;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public String getContent() {
//		return content;
//	}
	private String content;
	
	public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
