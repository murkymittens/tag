package leo.tag;

public class Command {
	private CommandInterpreter.Action action;
	private String[] arguments;
	
	public Command(CommandInterpreter.Action action, String... arguments) {
		this.action = action;
		this.arguments = arguments;
	}
	
	public CommandInterpreter.Action getAction() {
		return action;
	}
	
	public String getArgument(int index) {
		if(arguments != null && arguments.length > index) {
			return arguments[index];
		} else {
			return null;
		}
	}
	
	public int getArgumentCount() {
		if(arguments != null) {
			return arguments.length;
		} else {
			return 0;
		}
	}
}
