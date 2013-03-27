package leo.tag;

public class ActionEventInteractiveObjectConditional extends ActionEvent {
	private InteractiveObject item;
	private ActionEvent onSuccess;
	private ActionEvent onFailure;
	
	public ActionEventInteractiveObjectConditional(World world, String description, InteractiveObject item,
			ActionEvent onSuccess, ActionEvent onFailure) {
		super(world, description);
		this.item = item;
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
	}
	
	public ActionEventInteractiveObjectConditional(World world, String description, InteractiveObject item,
			ActionEvent onSuccess) {
		this(world, description, item, onSuccess, null);
	}
	
	@Override
	public void update() {
		super.update();
		if(args != null) {
			InteractiveObject target = (InteractiveObject) args[0];
			if(item.getUniqueId() == target.getUniqueId()) {
				onSuccess.update();
				consumedAction = true;
			} else {
				//onFailure.update();
				consumedAction = false;
			}
		}
	}
}
