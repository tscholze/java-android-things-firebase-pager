package io.github.tscholze.firebasepager;

import android.util.Log;

import java.io.IOException;

import io.github.tscholze.firebasepager.interfaces.IRunningTextContext;


/**
 * Running text Runnable that prepares the given text to be
 * more segment display friendly.
 *
 * Original inspiration:
 * - Author: JS Koran (https://github.com/jdkoren)
 * - Gist: https://gist.github.com/jdkoren/a3c37883f839c0b3adcee43821128329
 */
public class RunningTextRunnable implements Runnable
{
    private static final String TAG = RunningTextRunnable.class.getSimpleName();
    private static final int MAX_CHARS_PER_CYCLE = 4;

    private IRunningTextContext context;
    private String text;
    private int beginIndex = 0;
    private boolean isFirstRun = true;

    RunningTextRunnable(IRunningTextContext context, String text)
    {
        this.context = context;

        StringBuilder stringBuilder = new StringBuilder();

        // Append leading spaces (text runs completely from right to left).
        for(int i = 0; i < MAX_CHARS_PER_CYCLE; i++)
        {
            stringBuilder.append(' ');
        }

        // Append trimmed text.
        stringBuilder.append(text.trim());


        // Append trailing spaces.
        int spacesCount = MAX_CHARS_PER_CYCLE - (text.length() % MAX_CHARS_PER_CYCLE);
        for(int i = 0; i < spacesCount; i++)
        {
            stringBuilder.append(' ');
        }

        this.text = stringBuilder.toString();

        Log.d(TAG, "Running text with trailed text: '"+this.text+"'");
    }

    @Override
    public void run()
    {
        // Try to display the current visible part of the text string.
        try
        {
            context.getAlphaNumericDisplay().display(text.substring(beginIndex));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Check if finished parameters are valid.
        if(!isFirstRun && beginIndex == 0)
        {
            context.onRunningTextFinished();
            return;
        }

        // Update the beginIndex for the next cycle.
        beginIndex = (beginIndex + 1) % text.length();
        isFirstRun = false;

        // Post next cycle execution with a delay.
        context.getRunningTextHandler().postDelayed(this, 750L);
    }
}
