import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        String columns[] = bundle.getStringArray("userHistoryTablaHeader");
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        model.addColumn("Action");
        this.setModel(model);
        DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();
        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);

        this.getColumnModel().getColumn(0).setCellRenderer(renderCenter);
        this.getColumnModel().getColumn(1).setCellRenderer(renderCenter);
        this.getColumnModel().getColumn(2).setCellRenderer(renderCenter);
        this.getColumnModel().getColumn(3).setCellRenderer(renderCenter);

        this.getColumn("Action").setMaxWidth(40);

        this.getColumn("Action").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {

            JLabel label = new JLabel("X");
            label.setAlignmentX(CENTER_ALIGNMENT);
            return label;
        });
        this.getColumn("Action").setCellEditor(
                new DefaultCellEditor(new JCheckBox("checked")) {
                    public Component getTableCellEditorComponent(
                            JTable table, Object value, boolean isSelected, int row, int column) {
                        JLabel icone = new JLabel("XX");
                        icone.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                System.out.println(curentUser.getUserHistoriesList().remove(row));
                                setTable();
                            }

                            public void mouseClicked(MouseEvent e) { }
                            public void mousePressed(MouseEvent e) { }
                            public void mouseEntered(MouseEvent e) {}
                            public void mouseExited(MouseEvent e) {}
                        });
                        return icone;
                    }
                }
        );
        
        if (curentUser != null) {
            for (UserHistory userHistory : curentUser.getUserHistoriesList()) {
                model.addRow(userHistory.getRowTable());
            }
        }


        model.fireTableDataChanged();
        this.setShowGrid(true);
        this.setShowVerticalLines(true);
        this.getScrollableTracksViewportHeight();
        this.setAutoscrolls(true);
        return this;
    }

  /*  DefaultTableCellRenderer renderCenter = new DefaultTableCellRenderer();


    { // initializer block
        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public DefaultTableCellRenderer getCellRenderer(int arg0, int arg1) {
        return renderCenter;
    }*/
}
