package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class UIInfo {
	public static final Rectangle GameArea = new Rectangle(0, 0, (int)(TerminalInfo.getTerminalCols() * .75f), (int)(TerminalInfo.getTerminalLines()));
	public static final Rectangle StatsArea = new Rectangle(GameArea.getRight(), 0, (int)(TerminalInfo.getTerminalCols() * .25f), GameArea.getHeight());
	
	public static final TerminalBackgroundColor arenaBackground = TerminalBackgroundColor.BLACK;
	public static final TerminalForegroundColor obstacleForeground = TerminalForegroundColor.BLACK;
	public static final TerminalBackgroundColor obstacleBackground = TerminalBackgroundColor.WHITE;
	
	
}
