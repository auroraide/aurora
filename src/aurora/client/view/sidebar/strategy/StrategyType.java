package aurora.client.view.sidebar.strategy;

/**
 * Lambda evaluation strategy types, including a special case to let the user select their own redex.
 * If you want manual selection, you need to send a different event.
 */
public enum StrategyType {
    CALLBYVALUE,
    CALLBYNAME,
    NORMALORDER,
    MANUALSELECTION
}
