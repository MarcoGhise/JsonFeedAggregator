package it.blog.challenge.feednews.bean;

public class GeneralError extends Feed{

	private String message;
	
	public GeneralError(String message)
	{
		this.setTitle("Oops, something goes wrong");
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
