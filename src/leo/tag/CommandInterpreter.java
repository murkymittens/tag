package leo.tag;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandInterpreter {
	public enum Action { GO, LOOK, EXAMINE, TAKE, USE, TALK, PLACE, CRAFT }
	private static Pattern argumentX = Pattern.compile("(.*)");
	private static Pattern argumentXoperandY = Pattern.compile("(.*)\\s(?:[oi]{1}n)?(?:with)?\\s(.*)");
	private static Pattern argumentOperandX = Pattern.compile("(?:[\\S]{2})\\s(.*)");
	private Map<Action, Pattern> argumentPatterns;
	
	public CommandInterpreter() {
		argumentPatterns = new HashMap<Action, Pattern>();
		argumentPatterns.put(Action.GO, argumentX);
		argumentPatterns.put(Action.EXAMINE, argumentX);
		argumentPatterns.put(Action.TAKE, argumentX);
		argumentPatterns.put(Action.USE, argumentXoperandY);
		argumentPatterns.put(Action.TALK, argumentOperandX);
		argumentPatterns.put(Action.PLACE, argumentX);
		argumentPatterns.put(Action.CRAFT, argumentX);
	}
	
	public Command parse(String input) {
		String[] capturedArguments = null;
		String[] commandString = input.trim().split(" ", 2);
		Action action;
		Matcher matcher;
		try {
			action = Action.valueOf(commandString[0].toUpperCase());
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(commandString.length > 1 && argumentPatterns.containsKey(action)) {
			matcher = argumentPatterns.get(action).matcher(commandString[1]);
			if(matcher.find()) {
				capturedArguments = new String[matcher.groupCount()];
				for(int i=1; i<=matcher.groupCount(); i++) {
					capturedArguments[i-1] = matcher.group(i);
				}
			}
		}
		return new Command(action, capturedArguments);
	}
}
