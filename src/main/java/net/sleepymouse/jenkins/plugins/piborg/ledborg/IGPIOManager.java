/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import java.io.PrintStream;

import net.sleepymouse.jenkins.plugins.piborg.ledborg.Constants.Colour;

/**
 * 
 * GPIO control functions
 *
 */
public interface IGPIOManager
{
	/**
	 * Default the RGB LED states
	 */
	public void reset();

	/**
	 * Shutdown the GPIO connectivity
	 */
	public void shutdown();

	/**
	 * Set the display colour
	 * 
	 * @param colour
	 *            Colour to display
	 */
	public void setColour(Colour colour);

	/**
	 * Set the console logger
	 * 
	 * @param consoleLogger
	 *            Logger for console output
	 */
	public void setConsoleLogger(PrintStream consoleLogger);
}
