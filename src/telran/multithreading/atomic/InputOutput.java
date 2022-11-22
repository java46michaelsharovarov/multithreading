package telran.multithreading.atomic;

import java.util.function.Function;

public interface InputOutput {
	
	String readString(String prompt);

	void writeObject(Object obj);
	
	default void close() {}

	default void writeLine(Object obj) {
		String str = obj + "\n";
		writeObject(str);		
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		R result = null;
		while (true) {
			String str = readString(prompt);
			try {
				result = mapper.apply(str);
				break;
			} catch (Exception e) {
				String message = e.getMessage();
				if (message == null) {
					message = "";
				}
				writeLine(errorPrompt + " " + message);
			}
		}
		return result;

	}
	
	default Integer readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}
	
	default Integer readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(prompt, errorPrompt, s -> {
			int num = Integer.parseInt(s);
			if (num < min) {
				throw new RuntimeException("less than " + min);
			}
			if (num > max) {
				throw new RuntimeException("greater than " + max);
			}
			return num;			
		});
	}

}