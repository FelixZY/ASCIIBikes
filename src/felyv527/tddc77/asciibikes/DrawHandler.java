package felyv527.tddc77.asciibikes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.CaretMover;
import felyv527.tddc77.asciibikes.io.OutputManager;

/**
 * A class for drawing <code>Drawable</code>s to the terminal via
 * <code>OutputManager</code>.
 * 
 */
public class DrawHandler {

	private static Stack<DrawHandler> drawHandlerStack = new Stack<DrawHandler>();

	private List<Drawable> standardDrawItems = new ArrayList<>();
	private List<Drawable> overlayDrawItems = new ArrayList<>();
	private List<Drawable> frontDrawItems = new ArrayList<>();

	// Förhindrar extern instansiering.
	private DrawHandler() {

	}
	
	public static int debugGetStackSize() {
		return drawHandlerStack.size();
	}

	public static void registerForDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().standardDrawItems.add(drawable);
	}

	public static void unRegisterForDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().standardDrawItems.remove(drawable);
	}

	public static void registerForOverlayDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().overlayDrawItems.add(drawable);
	}

	public static void unRegisterForOverlayDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().overlayDrawItems.remove(drawable);
	}

	public static void registerForFrontDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().frontDrawItems.add(drawable);
	}

	public static void unRegisterForFrontDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().frontDrawItems.remove(drawable);
	}

	/**
	 * Starts a new DrawHandler session. A session is required to use
	 * DrawHandler.
	 * 
	 * When the session is complete, <code>endSession</code> should be called.
	 */
	public static void startSession() {
		/*
		 * Every session is a separate instance of DrawHandler on the stack. The
		 * top instance is considered the 'active' one which is why each session
		 * must be ended after use.
		 */
		drawHandlerStack.add(new DrawHandler());
	}

	public static void clearSession() {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.peek().standardDrawItems.clear();
		drawHandlerStack.peek().overlayDrawItems.clear();
		drawHandlerStack.peek().frontDrawItems.clear();
	}

	public static void endSession() {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawHandlerStack.pop();
	}

	private static void drawItems(List<Drawable> drawList) {
		for (Drawable drawable : drawList) {
			AsciiSprite sprite = drawable.getAsciiSprite();
			String[] stringRepresentation = sprite.getStringRepresentation();
			Point spriteLocation = sprite.getSpriteLocation();

			if (stringRepresentation.length > 0) {
				CaretMover.resetCaret();

				CaretMover.moveCaretDown(spriteLocation.getY());

				for (int i = 0; i < stringRepresentation.length - 1; i++) {
					CaretMover.moveCaretRight(spriteLocation.getX());

					OutputManager.println(stringRepresentation[i]);
				}

				CaretMover.moveCaretRight(spriteLocation.getX());

				OutputManager.print(stringRepresentation[stringRepresentation.length - 1]);
			}
		}
	}

	public static void drawFrame() {
		OutputManager.clear();

		if (drawHandlerStack.isEmpty())
			throw new NoActiveSessionException();

		drawItems(drawHandlerStack.peek().standardDrawItems);
		drawItems(drawHandlerStack.peek().overlayDrawItems);
		drawItems(drawHandlerStack.peek().frontDrawItems);

	}
}
