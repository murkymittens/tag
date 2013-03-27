package leo.tag;

public class ActionEventChangeFiniteState extends ActionEvent {
	private InteractiveObject npc;
	private int state;
	
	public ActionEventChangeFiniteState(World world, InteractiveObject npc, int state) {
		super(world, null);
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void update() {
		super.update();
		npc.getState().setCurrentState(state);
	}
}
