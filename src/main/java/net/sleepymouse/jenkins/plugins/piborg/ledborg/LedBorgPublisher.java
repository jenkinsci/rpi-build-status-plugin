/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import static net.sleepymouse.jenkins.plugins.piborg.ledborg.Messages.*;

import java.io.IOException;
import java.util.logging.*;

import org.kohsuke.stapler.*;

import hudson.*;
import hudson.model.*;
import hudson.tasks.*;
import jenkins.tasks.SimpleBuildStep;

/**
 * Sample {@link Builder}.
 *
 * <p>
 * When the user configures the project and enables this builder, {@link Descriptor#newInstance(StaplerRequest)} is
 * invoked and a new {@link LedBorgPublisher} is created. The created instance is persisted to the project configuration
 * XML by using XStream, so this allows you to use instance fields to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform} method will be invoked.
 *
 */
public class LedBorgPublisher extends Notifier implements SimpleBuildStep
{
	private final String		failureState;
	private final String		successState;
	private final String		unstableState;
	private final String		notBuiltState;
	private final String		abortedState;

	private final static Logger	fileLogger	= Logger.getLogger(LedBorgPublisher.class.getName());

	@DataBoundConstructor
	public LedBorgPublisher(String failureState, String successState, String unstableState, String notBuiltState,
			String abortedState)
	{
		this.failureState = failureState;
		this.successState = successState;
		this.unstableState = unstableState;
		this.notBuiltState = notBuiltState;
		this.abortedState = abortedState;
	}

	/**
	 * @return the failureState
	 */
	public String getFailureState()
	{
		return failureState;
	}

	/**
	 * @return the successState
	 */
	public String getSuccessState()
	{
		return successState;
	}

	/**
	 * @return the unstableState
	 */
	public String getUnstableState()
	{
		return unstableState;
	}

	/**
	 * @return the notBuiltState
	 */
	public String getNotBuiltState()
	{
		return notBuiltState;
	}

	/**
	 * @return the abortedState
	 */
	public String getAbortedState()
	{
		return abortedState;
	}

	/**
	 * Get the associated descriptor object
	 */
	@Override
	public DescriptorImpl getDescriptor()
	{
		return (DescriptorImpl) super.getDescriptor();
	}

	/**
	 * Get the build step monitor. This build step is only executed after the same step in the previous build is
	 * completed. Don't want multiple build trying to update the LED's concurrently
	 */
	@Override
	public BuildStepMonitor getRequiredMonitorService()
	{
		return BuildStepMonitor.STEP; // wait until any other step of this type has completed
	}

	/**
	 * Perform actions associated with the build step. In this instance, setting the appropriate LED colour
	 */
	@Override
	public void perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener)
			throws InterruptedException, IOException
	{
		Controller controller = Controller.getInstance(listener.getLogger(), fileLogger);
		Result result = build.getResult();
		if (null != result)
		{
			listener.getLogger().println(LOG_MSG() + " " + SET_LED_MSG() + " " + result.toString());

			if (result.isBetterOrEqualTo(Result.SUCCESS))
			{
				controller.setColour(successState);
			}
			else if (result.isBetterOrEqualTo(Result.UNSTABLE))
			{
				controller.setColour(unstableState);
			}
			else if (result.isBetterOrEqualTo(Result.FAILURE))
			{
				controller.setColour(failureState);
			}
			else if (result.isBetterOrEqualTo(Result.NOT_BUILT))
			{
				controller.setColour(notBuiltState);
			}
			else
			{
				controller.setColour(abortedState);
			}
		}
		else
		{
			listener.getLogger().println(LOG_MSG() + " " + ONGOING_ERR());
			fileLogger.log(Level.WARNING, ONGOING_ERR());
		}
	}

	/**
	 * Add the descriptor class extension point
	 */
	@Extension
	public static class DescriptorImpl extends Descriptor
	{
		// Done like this as I didn't want a huge source file
	}

}
