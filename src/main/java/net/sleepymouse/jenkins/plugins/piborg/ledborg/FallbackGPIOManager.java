/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import static net.sleepymouse.jenkins.plugins.Messages.*;

import java.io.PrintStream;
import java.util.logging.*;

import net.sleepymouse.jenkins.plugins.piborg.ledborg.Constants.Colour;

/**
 *
 *
 */
public class FallbackGPIOManager implements IGPIOManager
{
	private PrintStream	consoleLogger;
	private Logger		fileLogger;

	public FallbackGPIOManager(PrintStream consoleLogger, Logger fileLogger)
	{
		this.consoleLogger = consoleLogger;
		this.fileLogger = fileLogger;
	}

	/**
	 * Default the RGB LED states
	 */
	public void reset()
	{
		fileLogger.log(Level.INFO, GPIO_RESET_MSG());
	}

	/**
	 * Shutdown the GPIO connectivity
	 */
	public void shutdown()
	{
		fileLogger.log(Level.INFO, GPIO_SHUTDOWN_MSG());
	}

	/**
	 * Set the display colour
	 * 
	 * @param colour
	 *            Colour to display
	 */
	public void setColour(Colour colour)
	{
		String msg = SET_LED_MSG() + " " + colour;
		fileLogger.log(Level.INFO, msg);
		consoleLogger.println(msg);
	}

	/**
	 * Set the console logger
	 * 
	 * @param consoleLogger
	 *            Logger for console output
	 */
	public void setConsoleLogger(PrintStream consoleLogger)
	{
		this.consoleLogger = consoleLogger;
	}
}
