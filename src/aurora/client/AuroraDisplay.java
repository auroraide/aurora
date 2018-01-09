package aurora.client;

import aurora.backend.HighlightedLambdaExpression;

/**
 * Defines possible commands which can be used to modify the view.
 */
public interface AuroraDisplay {

    void displayWarning(String message);

    void setStepNumber(int stepNumber);
}
