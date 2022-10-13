import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class TableHandler extends JTable implements ActionListener {

    protected User curentUser;
    protected ResourceBundle bundle;

    public TableHandler() {
    }

    public TableHandler(User curentUser, ResourceBundle bundle) {
        setCurentUser(curentUser);
        setBundle(bundle);
        setTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public User getCurentUser() {
        return curentUser;
    }

    public void setCurentUser(User curentUser) {
        this.curentUser = curentUser;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public JTable setTable() {

        String column[] = bundle.getStringArray("userHistoryTablaHeader");
        String data[][] = new String[curentUser.getUserHistoriesList().size()][3];

        if (curentUser != null && curentUser.getUserHistoriesList().size() > 0) {

            int i = 0;
            for (UserHistory userHistoryser : curentUser.getUserHistoriesList()) {
                data[i][0] = userHistoryser.getGameResult();
                data[i][1] = userHistoryser.getGameLevel();
                data[i][2] = userHistoryser.datePlayed;
                i++;
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, column);
        this.setModel(model);
        this.setShowGrid(true);
        this.setShowVerticalLines(true);
        this.getScrollableTracksViewportHeight();
        this.setAutoscrolls(true);
        //this.setBounds(0, 0, 500, 300);
        //this.setSize(500, 300);
        this.repaint();
        return this;
    }

    DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();

    { // initializer block
        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public DefaultTableCellRenderer getCellRenderer(int arg0, int arg1) {
        return renderCenter;
    }
}
