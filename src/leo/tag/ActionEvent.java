package leo.tag;

public class ActionEvent {
	protected String description;
	protected World world;
	protected Object[] args;
	protected boolean consumedAction = false;
	
	public ActionEvent(World world, String description) {
		this.description = description;
		this.world = world;
	}

	public void setArguments(Object... args) {
		this.args = args;
	}
	
	public void update() {
		if(description != null) {
			Main.printf(description);
		}
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isConsumedAction() {
		return consumedAction;
	}

	public void setConsumedAction(boolean consumedAction) {
		this.consumedAction = consumedAction;
	}
}
