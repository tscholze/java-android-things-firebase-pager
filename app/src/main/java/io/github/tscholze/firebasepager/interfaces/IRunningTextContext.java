package io.github.tscholze.firebasepager.interfaces;


import android.os.Handler;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;

/**
 * Describes the required information and listener of a RunningTextRunnable.
 */
public interface IRunningTextContext
{
    /**
     * Gets the display that will be used to show
     * information
     * @return An assigned AlphanumericDisplay.
     */
    AlphanumericDisplay getAlphaNumericDisplay();

    /**
     * Gets the activity handler to post runnables.
     * @return Assigned handler.
     */
    Handler getRunningTextHandler();

    /**
     * Raised if running text has been finished.
     */
    void onRunningTextFinished();
}
