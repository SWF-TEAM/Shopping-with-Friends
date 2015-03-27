package am.te.myapplication.Model;

import java.util.List;

/**
 * The administrator class represents an agent with administrative
 * authority.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 18
 */
public class Administrator extends Agent {

    public void banUser(User banned) {
        banned.setBanned(true);
    }

    public List<SalesReport> viewReports(User inspected) {
        return inspected.getSalesReports();
    }
}
