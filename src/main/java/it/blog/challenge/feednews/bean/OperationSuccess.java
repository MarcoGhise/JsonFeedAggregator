package it.blog.challenge.feednews.bean;

public class OperationSuccess extends Feed{

	private String message;
	
	public OperationSuccess(String message)
	{
		this.setTitle("Operation Success");
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
