package felyv527.tddc77.asciibikes.font;

/**
 * A font consisting of large, multiline ascii art letters.
 *
 */
public final class HeadlineFont extends Font {

	@Override
	protected String[] getGlyph(char chr) throws IllegalArgumentException {
		if (Character.isDigit(chr)) {
			return Glyph.valueOf("_" + Character.toString(chr)).glyph;
		} else if (Character.isSpaceChar(chr)) {
			return Glyph.SPACE.glyph;
		} else if (Character.isLetter(chr)) {
			return Glyph.valueOf(Character.toString(Character.toUpperCase(chr)) + "_"
					+ (Character.isUpperCase(chr) ? "UPPER" : "LOWER")).glyph;
		} else {
			// The glyph is probably some kind of special character. Listed are
			// the supported ones.
			switch (chr) {
				case ',':
					return Glyph.COMMA.glyph;
				case '.':
					return Glyph.PERIOD.glyph;
				case ':':
					return Glyph.COLON.glyph;
				case '-':
					return Glyph.DASH.glyph;
				case '+':
					return Glyph.PLUS.glyph;
				case '@':
					return Glyph.AT.glyph;
				case '_':
					return Glyph.UNDERLINE.glyph;
				case '?':
					return Glyph.QUESTION.glyph;
				case '!':
					return Glyph.EXCLAMATION.glyph;
				case '*':
					return Glyph.STAR.glyph;
				case '(':
					return Glyph.PARANTHESE_OPEN.glyph;
				case ')':
					return Glyph.PARANTHESE_CLOSE.glyph;
				case '[':
					return Glyph.BRACKET_OPEN.glyph;
				case ']':
					return Glyph.BRACKET_CLOSE.glyph;
				case '/':
					return Glyph.SLASH.glyph;
				case '\\':
					return Glyph.BACKSLASH.glyph;
				default:
					throw new IllegalArgumentException(
							"Character '" + Character.toString(chr) + "' not found in HeadlineFont");
			}
		}
	}

	private static enum Glyph {
		SPACE(new String[] { //
				"  ", //
				"  ", //
				"  ", //
				"  " //
		}),

		COMMA(new String[] { //
				" ", //
				" ", //
				"o", //
				"/", //
		}),

		PERIOD(new String[] { //
				" ", //
				" ", //
				"o", //
				" ", //
		}),

		COLON(new String[] { //
				" ", //
				"o", //
				"o", //
				" ", //
		}),

		DASH(new String[] { //
				"  ", //
				"__", //
				"  ", //
				"  ", //
		}),

		PLUS(new String[] { //
				"   ", //
				"_|_", //
				" | ", //
				"   ", //
		}),

		UNDERLINE(new String[] { //
				"  ", //
				"  ", //
				"  ", //
				"__", //
		}),

		AT(new String[] { //
				"  __ ", //
				" /  \\", //
				"| (|/", //
				" \\__ ", //
		}),

		QUESTION(new String[] { //
				"_ ", //
				" )", //
				"o ", //
				"  ", //
		}),

		EXCLAMATION(new String[] { //
				" ", //
				"|", //
				"o", //
				" ", //
		}),

		STAR(new String[] { //
				"     ", //
				" \\|/ ", //
				" /|\\ ", //
				"     " //
		}),

		PARANTHESE_OPEN(new String[] { //
				"    ", //
				"  / ", //
				" |  ", //
				"  \\ " //
		}),

		PARANTHESE_CLOSE(new String[] { //
				"    ", //
				" \\  ", //
				"  | ", //
				" /  " //
		}),

		BRACKET_OPEN(new String[] { //
				"  _ ", //
				" |  ", //
				" |_ ", //
				"    " //

		}),

		BRACKET_CLOSE(new String[] { //
				" _  ", //
				"  | ", //
				" _| ", //
				"    " //
		}),

		SLASH(new String[] { //
				"    ", //
				"  / ", //
				" /  ", //
				"    " //
		}),

		BACKSLASH(new String[] { //
				"    ", //
				" \\  ", //
				"  \\ ", //
				"    " //
		}),

		A_UPPER(new String[] { //
				"    ", //
				" /\\ ", //
				"/--\\", //
				"    ", //
		}),

		B_UPPER(new String[] { //
				" _ ", //
				"|_)", //
				"|_)", //
				"   ", //
		}),

		C_UPPER(new String[] { //
				" _", //
				"/ ", //
				"\\_", //
				"  ", //
		}),

		D_UPPER(new String[] { //
				" _ ", //
				"| \\", //
				"|_/", //
				"   ", //
		}),

		E_UPPER(new String[] { //
				" _", //
				"|_", //
				"|_", //
				"  ", //
		}),

		F_UPPER(new String[] { //
				" _", //
				"|_", //
				"| ", //
				"  ", //
		}),

		G_UPPER(new String[] { //
				" __", //
				"/__", //
				"\\_|", //
				"   ", //
		}),

		H_UPPER(new String[] { //
				"   ", //
				"|_|", //
				"| |", //
				"   ", //
		}),

		I_UPPER(new String[] { //
				"___", //
				" | ", //
				"_|_", //
				"   ", //
		}),

		J_UPPER(new String[] { //
				"   ", //
				"  |", //
				"\\_|", //
				"   ", //
		}),

		K_UPPER(new String[] { //
				"  ", //
				"|/", //
				"|\\", //
				"  ", //
		}),

		L_UPPER(new String[] { //
				"  ", //
				"| ", //
				"|_", //
				"  ", //
		}),

		M_UPPER(new String[] { //
				"    ", //
				"|\\/|", //
				"|  |", //
				"    ", //
		}),

		N_UPPER(new String[] { //
				"    ", //
				"|\\ |", //
				"| \\|", //
				"    ", //
		}),

		O_UPPER(new String[] { //
				" _ ", //
				"/ \\", //
				"\\_/", //
				"   ", //
		}),

		P_UPPER(new String[] { //
				" _ ", //
				"|_)", //
				"|  ", //
				"   ", //
		}),

		Q_UPPER(new String[] { //
				" _ ", //
				"/ \\", //
				"\\_X", //
				"   ", //
		}),

		R_UPPER(new String[] { //
				" _ ", //
				"|_)", //
				"| \\", //
				"   ", //
		}),

		S_UPPER(new String[] { //
				" __", //
				"(_ ", //
				"__)", //
				"   ", //
		}),

		T_UPPER(new String[] { //
				"___", //
				" | ", //
				" | ", //
				"   ", //
		}),

		U_UPPER(new String[] { //
				"   ", //
				"| |", //
				"|_|", //
				"   ", //
		}),

		V_UPPER(new String[] { //
				"    ", //
				"\\  /", //
				" \\/ ", //
				"    ", //
		}),

		W_UPPER(new String[] { //
				"      ", //
				"\\    /", //
				" \\/\\/ ", //
				"      ", //
		}),

		X_UPPER(new String[] { //
				"  ", //
				"\\/", //
				"/\\", //
				"  ", //
		}),

		Y_UPPER(new String[] { //
				"   ", //
				"\\_/", //
				" | ", //
				"   ", //
		}),

		Z_UPPER(new String[] { //
				"__", //
				" /", //
				"/_", //
				"  ", //
		}),

		A_LOWER(new String[] { //
				"   ", //
				" _.", //
				"(_|", //
				"   ", //
		}),

		B_LOWER(new String[] { //
				"   ", //
				"|_ ", //
				"|_)", //
				"   ", //
		}),

		C_LOWER(new String[] { //
				"  ", //
				" _", //
				"(_", //
				"  ", //
		}),

		D_LOWER(new String[] { //
				"   ", //
				" _|", //
				"(_|", //
				"   ", //
		}),

		E_LOWER(new String[] { //
				"   ", //
				" _ ", //
				"(/_", //
				"   ", //
		}),

		F_LOWER(new String[] { //
				"  _", //
				"_|_", //
				" | ", //
				"   ", //
		}),

		G_LOWER(new String[] { //
				"   ", //
				" _ ", //
				"(_|", //
				" _|", //
		}),

		H_LOWER(new String[] { //
				"   ", //
				"|_ ", //
				"| |", //
				"   ", //
		}),

		I_LOWER(new String[] { //
				" ", //
				"o", //
				"|", //
				" ", //
		}),

		J_LOWER(new String[] { //
				"  ", //
				" o", //
				" |", //
				"_|", //
		}),

		K_LOWER(new String[] { //
				"  ", //
				"| ", //
				"|<", //
				"  ", //
		}),

		L_LOWER(new String[] { //
				" ", //
				"|", //
				"|", //
				" ", //
		}),

		M_LOWER(new String[] { //
				"     ", //
				"._ _ ", //
				"| | |", //
				"     ", //
		}),

		N_LOWER(new String[] { //
				"   ", //
				"._ ", //
				"| |", //
				"   ", //
		}),

		O_LOWER(new String[] { //
				"   ", //
				" _ ", //
				"(_)", //
				"   ", //
		}),

		P_LOWER(new String[] { //
				"   ", //
				"._ ", //
				"|_)", //
				"|  ", //
		}),

		Q_LOWER(new String[] { //
				"   ", //
				" _.", //
				"(_|", //
				"  |", //
		}),

		R_LOWER(new String[] { //
				"  ", //
				"._", //
				"| ", //
				"  ", //
		}),

		S_LOWER(new String[] { //
				"  ", //
				" _", //
				"_>", //
				"  ", //
		}),

		T_LOWER(new String[] { //
				"   ", //
				"_|_", //
				" |_", //
				"   ", //
		}),

		U_LOWER(new String[] { //
				"   ", //
				"   ", //
				"|_|", //
				"   ", //
		}),

		V_LOWER(new String[] { //
				"  ", //
				"  ", //
				"\\/", //
				"  ", //
		}),

		W_LOWER(new String[] { //
				"    ", //
				"    ", //
				"\\/\\/", //
				"    ", //
		}),

		X_LOWER(new String[] { //
				"  ", //
				"  ", //
				"><", //
				"  ", //
		}),

		Y_LOWER(new String[] { //
				"  ", //
				"  ", //
				"\\/", //
				"/ ", //
		}),

		Z_LOWER(new String[] { //
				"  ", //
				"_ ", //
				"/_", //
				"  ", //
		}),

		_1(new String[] { //
				"  ", //
				"/|", //
				" |", //
				"  ", //
		}),

		_2(new String[] { //
				"_ ", //
				" )", //
				"/_", //
				"  ", //
		}),

		_3(new String[] { //
				"_ ", //
				"_)", //
				"_)", //
				"  ", //
		}),

		_4(new String[] { //
				"    ", //
				"|_|_", //
				"  | ", //
				"    ", //
		}),

		_5(new String[] { //
				" _ ", //
				"|_ ", //
				" _)", //
				"   ", //
		}),

		_6(new String[] { //
				" _ ", //
				"|_ ", //
				"|_)", //
				"   ", //
		}),

		_7(new String[] { //
				"__", //
				" /", //
				"/ ", //
				"  ", //
		}),

		_8(new String[] { //
				" _ ", //
				"(_)", //
				"(_)", //
				"   ", //
		}),

		_9(new String[] { //
				" _ ", //
				"(_|", //
				"  |", //
				"   ", //
		}),

		_0(new String[] { //
				" _ ", //
				"/ \\", //
				"\\_/", //
				"   ", //
		});

		final String[] glyph;

		Glyph(String[] glyph) {
			this.glyph = glyph;
		}
	}
}
