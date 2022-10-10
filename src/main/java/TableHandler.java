import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class TableHandler extends JTable implements ActionListener {

    protected User curentUser;
    protected ResourceBundle bundle;

    public TableHandler(User curentUser, ResourceBundle bundle) {
        this.curentUser = curentUser;
        bundle = bundle;
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

    public JTable getTable(User curentUser, ResourceBundle bundle) {
        this.setBundle(bundle);
        this.setCurentUser(curentUser);


        if (curentUser != null && curentUser.getUserHistoriesList().size() > 0) {
            String column[] = {getBundle().getString("userLastPlayLabel")+"", getBundle().getString("userGameLevel")+"", getBundle().getString("userLastPlayDate")+""};
            String data[][] = new String[curentUser.getUserHistoriesList().size()][3];
            int i = 0;
            for (UserHistory userHistoryser : curentUser.getUserHistoriesList()) {
                System.out.println(column[0]+column[1]+column[2]);
                //System.out.println(userHistoryser.getGameResult() + userHistoryser.getGameLevel() + userHistoryser.datePlayed);
                data[i][0] = userHistoryser.getGameResult();
                data[i][1] = userHistoryser.getGameLevel();
                data[i][2] = userHistoryser.datePlayed;
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(data, column);
             this.setModel(model);
            //this.getColumn(column[0]).setMaxWidth(40);
            //this.getColumn(column[1]).setMaxWidth(30);

        }

        this.setShowGrid(true);
        this.setShowVerticalLines(true);
        this.getScrollableTracksViewportHeight();
        this.getAutoscrolls();
        this.setBounds(0, 0, 500, 300);
        this.setSize(500, 300);
        this.repaint();
        return this;
    }
}
