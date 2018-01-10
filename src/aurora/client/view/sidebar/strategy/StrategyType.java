package aurora.client.view.sidebar.strategy;

/**
 * Strategy types, without manual selection.
 * If you want manual selection, you need to send a different event.
 */
public enum StrategyType {
    CALLBYVALUE,
    CALLBYNAME,
    NORMALORDER,
    MANUALSELECTION
}