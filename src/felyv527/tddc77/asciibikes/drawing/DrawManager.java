package felyv527.tddc77.asciibikes.drawing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.io.CaretMover;
import felyv527.tddc77.asciibikes.io.OutputManager;

/**
 * {@link DrawManager} manages the drawing of {@link Drawable}s to the terminal
 * via {@link OutputManager} in a session based way.
 * 
 * <p>
 * A session is required to use {@link DrawManager} and can be acquired by
 * calling {@link DrawManager#startSession()}.
 * 
 * <p>
 * By starting a new session the previously active session will be saved and any
 * subsequent calls for draw /un/registration will go to the newly started
 * session. For this reason it is also important that sessions are ended.
 * 
 * <p>
 * Sessions can be ended with {@link DrawManager#endSession()}.
 */
public class DrawManager {

	private static Stack<DrawManager> drawHandlerStack = new Stack<DrawManager>();

	private List<Drawable> standardDrawItems = new ArrayList<>();
	private List<Drawable> overlayDrawItems = new ArrayList<>();
	private List<Drawable> frontDrawItems = new ArrayList<>();

	private DrawManager() {
		/*
		 * There is reasoning to be done over whether this class should be
		 * static or not.
		 * 
		 * In this case I have chosen a semi-static approach with sessions to
		 * combine the ease of access of static methods, allowing {@link
		 * Drawable} objects to tend to themselves, with the clean-slate and
		 * garbage-collected nature of instances.
		 */
	}

	/**
	 * Starts a new DrawHandler session.
	 * 
	 * <p>
	 * Note that a session is required to use {@link DrawManager} and that
	 * {@link DrawManager#endSession()} should be called when the session is
	 * completed.
	 */
	public static void startSession() {
		/*
		 * Every session is a separate instance of DrawHandler on the stack. The
		 * top instance is considered the 'active' one which is why each session
		 * must be ended after use. Otherwise one may end up with the wrong UI.
		 */
		drawHandlerStack.add(new DrawManager());
	}

	/**
	 * Ends the currently active session.
	 */
	public static void endSession() {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.pop();
	}

	/**
	 * Registers a {@link Drawable} for drawing on the normal layer.
	 * 
	 * <p>
	 * This layer should be used for most {@link Drawables} as the default.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to register
	 */
	public static void registerForDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().standardDrawItems.add(drawable);
	}

	/**
	 * Unregisters a {@link Drawable} from drawing on the normal layer.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to unregister
	 */
	public static void unRegisterForDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().standardDrawItems.remove(drawable);
	}

	/**
	 * Registers a {@link Drawable} for drawing on the overlay layer.
	 * 
	 * <p>
	 * This layer should be used for e.g. HUDs or sprites that should be on top
	 * of the player no matter what.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to register
	 */
	public static void registerForOverlayDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().overlayDrawItems.add(drawable);
	}

	/**
	 * Unregisters a {@link Drawable} from drawing on the overlay layer.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to unregister
	 */
	public static void unRegisterForOverlayDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().overlayDrawItems.remove(drawable);
	}

	/**
	 * Registers a {@link Drawable} for drawing on the topmost layer.
	 * 
	 * <p>
	 * This layer should only be (sparingly) used for elements overlaying e.g.
	 * the game UI. Examples include debug tools.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to register
	 */
	public static void registerForFrontDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().frontDrawItems.add(drawable);
	}

	/**
	 * Unregisters a {@link Drawable} from drawing on the topmost layer.
	 * 
	 * @param drawable
	 *            The {@link Drawable} to unregister
	 */
	public static void unRegisterForFrontDraw(Drawable drawable) {
		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawHandlerStack.peek().frontDrawItems.remove(drawable);
	}

	private static void drawItems(List<Drawable> drawList) {

		for (Drawable drawable : drawList) {

			AsciiSprite sprite = drawable.getAsciiSprite();
			Point spriteLocation = sprite.getSpriteOrigin();
			String[] stringRepresentation = sprite.getStringRepresentation();

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

	/**
	 * Draws a single frame.
	 * 
	 * <p>
	 * Note that frequent draw calls will induce flicker in the terminal.
	 */
	public static void drawFrame() {
		OutputManager.clear();

		if (drawHandlerStack.isEmpty())
			throw new NoActiveDrawSessionException();

		drawItems(drawHandlerStack.peek().standardDrawItems);
		drawItems(drawHandlerStack.peek().overlayDrawItems);
		drawItems(drawHandlerStack.peek().frontDrawItems);

	}
}
