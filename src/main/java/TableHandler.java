import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
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


    Toolkit toolkit = Toolkit.getDefaultToolkit();
    ImageIcon deleteIcon = new ImageIcon(toolkit.getImage(this.getClass().getClassLoader().getResource("delete.png")));
    ImageIcon editIcon = new ImageIcon(toolkit.getImage(this.getClass().getClassLoader().getResource("check.png")));

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

            Box box = Box.createHorizontalBox();
            JLabel deleteLabel = new JLabel();
            deleteLabel.setIcon(deleteIcon);
            JLabel editLabel = new JLabel();
            editLabel.setIcon(editIcon);
            box.add(deleteLabel, LEFT_ALIGNMENT);
            box.add(Box.createHorizontalGlue());
            box.add(editLabel, RIGHT_ALIGNMENT);

            return box;
        });
        this.getColumn("Action").setCellEditor(
                new DefaultCellEditor(new JCheckBox("checked")) {
                    public Component getTableCellEditorComponent(
                            JTable table, Object value, boolean isSelected, int row, int column) {

                        Box box = Box.createHorizontalBox();
                        JLabel deleteLabel = new JLabel();
                        deleteLabel.setIcon(deleteIcon);
                        JLabel editLabel = new JLabel();
                        editLabel.setIcon(editIcon);
                        box.add(deleteLabel, LEFT_ALIGNMENT);
                        box.add(Box.createHorizontalGlue());
                        box.add(editLabel, RIGHT_ALIGNMENT);

                        deleteLabel.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                System.out.println(curentUser.getUserHistoriesList().remove(row));
                                setTable();
                            }

                            public void mouseClicked(MouseEvent e) {
                            }

                            public void mousePressed(MouseEvent e) {
                            }

                            public void mouseEntered(MouseEvent e) {
                            }

                            public void mouseExited(MouseEvent e) {
                            }
                        });
                        editLabel.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                UserHistory userHistory = curentUser.getUserHistoriesList().get(row);
                                if (column == 3) {
                                    setTable();
                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }
                        });
                        return box;
                    }
                }
        );
        JTextField jTextFieldGameResult = new JTextField();
        this.getColumn(columns[0]).setCellEditor(new DefaultCellEditor(jTextFieldGameResult) {

            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int row, int column) {
                jTextFieldGameResult.setText((String) value);

                jTextFieldGameResult.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            UserHistory userHistory = curentUser.getUserHistoriesList().get(row);

                            userHistory.setGameResult(getCellEditorValue().toString());

                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

                return jTextFieldGameResult;
            }

            public Object getCellEditorValue() {
                return jTextFieldGameResult.getText();
            }

        });
        JTextField jTextFieldGameLevel = new JTextField();
        this.getColumn(columns[1]).setCellEditor(new DefaultCellEditor(jTextFieldGameLevel) {

            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int row, int column) {
                jTextFieldGameLevel.setText((String) value);

                jTextFieldGameLevel.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            UserHistory userHistory = curentUser.getUserHistoriesList().get(row);
                            userHistory.setGameLevel(getCellEditorValue().toString());
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

                return jTextFieldGameLevel;
            }

            public Object getCellEditorValue() {
                return jTextFieldGameLevel.getText();
            }

        });
        JTextField jTextFieldDatePlayed = new JTextField();
        this.getColumn(columns[2]).setCellEditor(new DefaultCellEditor(jTextFieldDatePlayed) {

            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int row, int column) {
                jTextFieldDatePlayed.setText((String) value);

                jTextFieldDatePlayed.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            UserHistory userHistory = curentUser.getUserHistoriesList().get(row);

                            userHistory.setDatePlayed(getCellEditorValue().toString());
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

                return jTextFieldDatePlayed;
            }

            public Object getCellEditorValue() {
                return jTextFieldDatePlayed.getText();
            }

        });


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
