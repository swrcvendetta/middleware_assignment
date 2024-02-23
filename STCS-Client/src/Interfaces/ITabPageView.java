/**
 * The ITabPageView interface defines a contract for views that represent a tab page in a GUI application.
 * It includes methods to retrieve information about the tab page, such as its name, panel, icon, and tooltip.
 */
package Interfaces;

import javax.swing.*;

/**
 * The ITabPageView interface provides a standard set of methods for views representing tab pages.
 */
public interface ITabPageView {

    /**
     * Gets the name of the tab page.
     *
     * @return The name of the tab page.
     */
    public String getTabPageName();

    /**
     * Gets the panel associated with the tab page.
     *
     * @return The JPanel representing the content of the tab page.
     */
    public JPanel getTabPagePanel();

    /**
     * Gets the icon associated with the tab page.
     *
     * @return The Icon representing the tab page.
     */
    public Icon getTabPageIcon();

    /**
     * Gets the tooltip text for the tab page.
     *
     * @return The tooltip text for the tab page.
     */
    public String getTabPageTip();
}
