package felyv527.tddc77.asciibikes.io;

public class OutputManager {

	public static <T> void print(T out) {
		System.out.print(out);
	}

	public static void println() {
		System.out.print("\n\r");
	}

	@SuppressWarnings("unchecked")
	public static <T> void println(T out) {
		if(out instanceof CharSequence) {
			out = (T)(CharSequence)((CharSequence)out).toString().replace("\n", "\n\r");
		}
		System.out.print(out + "\n\r");
	}

	public static void returnCarriage() {
		System.out.print("\r");
	}

	public static void clear() {
		System.out.print("\033[H\033[J");
	}

	public static void clearCurrentLine() {
		System.out.print("\r\033[K");
	}

}
