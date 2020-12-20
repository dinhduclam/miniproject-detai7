import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class Truck extends JPanel implements ActionListener {
	private JTextField truckMake;
	private JTextField truckManufacturingYear;
	private JTextField truckPrice;
	private JTextField truckColor;
	private JTextField truckLoad;
	private JTable truckTable;
	private JPanel pnSouth, truckButtonList, truckMain, truckTitle, truckInfo, pnExport;
	private JScrollPane scrollPane;
	private JButton add, update, clear, delete, export;
	private JLabel status;
	private DefaultTableModel truckModel;
	private Object[] truckCol = {"ID", "Make", "Manufacturing Year", "Price", "Color", "Load" };
	private Object[] truckGetRs = { "class_id", "make", "manufacturing_year", "price", "color", "truck_load"};
	private Object[] truckRow = new Object[6];
	private Connection con;
	private int truckId = 0;

	Truck() {
		connect();
		createPnLeft(); // pnRight is table
		//create table
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600, 2));

		truckTable = new JTable();
		truckTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int i = truckTable.getSelectedRow();
				truckMake.setText(truckTable.getValueAt(i, 1).toString());
				truckManufacturingYear.setText(truckTable.getValueAt(i, 2).toString());
				truckPrice.setText(truckTable.getValueAt(i, 3).toString());
				truckColor.setText(truckTable.getValueAt(i, 4).toString());
				truckLoad.setText(truckTable.getValueAt(i, 5).toString());
			}
		});
		truckModel = new DefaultTableModel();

		truckModel.setColumnIdentifiers(truckCol);
		truckTable.setModel(truckModel);
		scrollPane.setViewportView(truckTable);
		try {
			readdb();
			//System.out.println(truckId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Read database fail!");
			e.printStackTrace();
		}
		
		
		add(scrollPane);
	}
	
	private void createPnLeft() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		truckInfo = new JPanel();
		truckInfo.setLayout(new BorderLayout(0, 0));

		truckTitle = new JPanel();
		truckTitle.setPreferredSize(new Dimension(10, 50));
		truckInfo.add(truckTitle, BorderLayout.NORTH);
		truckTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel title = new JLabel("TRUCK");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		truckTitle.add(title);

		truckMain = new JPanel();
		truckInfo.add(truckMain, BorderLayout.CENTER);
		truckMain.setLayout(new GridLayout(6, 0, 10, 10));

		truckMain.add(new JLabel("Make"));

		truckMake = new JTextField();
		truckMake.setBackground(UIManager.getColor("TextField.disabledBackground"));
		truckMake.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		truckMake.setColumns(10);
		truckMain.add(truckMake);

		truckMain.add(new JLabel("Manufacturing Year"));

		truckManufacturingYear = new JTextField();
		truckManufacturingYear.setBackground(UIManager.getColor("TextField.disabledBackground"));
		truckManufacturingYear.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		truckManufacturingYear.setColumns(10);
		truckMain.add(truckManufacturingYear);

		truckMain.add(new JLabel("Price"));

		truckPrice = new JTextField();
		truckPrice.setBackground(UIManager.getColor("TextField.disabledBackground"));
		truckPrice.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		truckPrice.setColumns(10);
		truckMain.add(truckPrice);

		truckMain.add(new JLabel("Color"));

		truckColor = new JTextField();
		truckColor.setBackground(UIManager.getColor("TextField.disabledBackground"));
		truckColor.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		truckColor.setColumns(10);
		truckMain.add(truckColor);

		truckMain.add(new JLabel("Load"));

		truckLoad = new JTextField();
		truckLoad.setBackground(UIManager.getColor("TextField.disabledBackground"));
		truckLoad.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		truckLoad.setColumns(10);
		truckMain.add(truckLoad);

		pnSouth = new JPanel();
		pnSouth.setLayout(new BorderLayout());
		pnSouth.setPreferredSize(new Dimension(0, 200));

		truckInfo.add(pnSouth, BorderLayout.SOUTH);

		status = new JLabel("");
		status.setPreferredSize(new Dimension(0, 70));
		pnSouth.add(status, BorderLayout.NORTH);
		
		//create button (add, update, clear, delete
		truckButtonList = new JPanel();
		truckButtonList.setLayout(new GridLayout(2, 2, 10, 10));
		pnSouth.add(truckButtonList);

		add = new JButton("Add");
		add.addActionListener(this);
		add.setMargin(new Insets(2, 25, 2, 25));
		add.setContentAreaFilled(false);
		truckButtonList.add(add);

		update = new JButton("Update");
		update.addActionListener(this);
		update.setMargin(new Insets(2, 20, 2, 20));
		update.setContentAreaFilled(false);
		truckButtonList.add(update);

		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setMargin(new Insets(2, 22, 2, 22));
		clear.setContentAreaFilled(false);
		truckButtonList.add(clear);

		delete = new JButton("Delete");
		delete.addActionListener(this);
		delete.setMargin(new Insets(2, 20, 2, 20));
		delete.setContentAreaFilled(false);
		truckButtonList.add(delete);
		
		pnExport = new JPanel();
		pnExport.setLayout(new BorderLayout());
		pnExport.add(new JPanel(), BorderLayout.NORTH);
		
		export = new JButton("Export (Excel)");
		export.addActionListener(this);
		export.setPreferredSize(new Dimension(0, 38));
		export.setContentAreaFilled(false);
		ImageIcon excel = new ImageIcon("src/icon/excel.png");
		export.setIcon(excel);
		pnExport.add(export);
		
		pnSouth.add(pnExport, BorderLayout.SOUTH);

		JPanel separator = new JPanel();
		separator.setPreferredSize(new Dimension(15, 0));

		add(truckInfo);
		add(separator);
	}

	//clear all text in the textfield (make, manufacturing year, price, color,...)
	private void clear() {
		truckMake.setText("");
		truckManufacturingYear.setText("");
		truckPrice.setText("");
		truckColor.setText("");
		truckLoad.setText("");
		truckMake.requestFocus();
	}

	//check the textfield that are not filled out
	private boolean check() {
		String noti = "Please fill all the form!";
		if (truckMake.getText().equals("")) {
			truckMake.requestFocus();
			status.setText(noti);
		} else if (truckManufacturingYear.getText().equals("")) {
			truckManufacturingYear.requestFocus();
			status.setText(noti);
		} else if (truckPrice.getText().equals("")) {
			truckPrice.requestFocus();
			status.setText(noti);
		} else if (truckColor.getText().equals("")) {
			truckColor.requestFocus();
			status.setText(noti);
		} else if (truckLoad.getText().equals("")) {
			truckLoad.requestFocus();
			status.setText(noti);
		} else return true;
		return false;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == add) {
			if (check()) {
				try {
					truckId++;
					add();
					truckRow[0] = truckId;
					truckRow[1] = truckMake.getText();
					truckRow[2] = truckManufacturingYear.getText();
					truckRow[3] = truckPrice.getText();
					truckRow[4] = truckColor.getText();
					truckRow[5] = truckLoad.getText();
					truckModel.addRow(truckRow);
					clear();
					status.setText("Add sucessful!");
				} catch (IllegalArgumentException e) {
					truckId--;
					status.setText("Wrong data type!");
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					truckId--;
					status.setText("Add fail!");
					e.printStackTrace();
				}	
			}
		} else if (arg0.getSource() == update) {
			int i = truckTable.getSelectedRow();
			if (i >= 0) {
				if (check()) {
					try {
						int class_id = (int) truckTable.getValueAt(i, 0);
						update(class_id);
						truckTable.setValueAt(truckMake.getText(), i, 1);
						truckTable.setValueAt(truckManufacturingYear.getText(), i, 2);
						truckTable.setValueAt(truckPrice.getText(), i, 3);
						truckTable.setValueAt(truckColor.getText(), i, 4);
						truckTable.setValueAt(truckLoad.getText(), i, 5);
						clear();
						truckTable.removeRowSelectionInterval(i, i);
						status.setText("Update sucessful!");
					} catch (IllegalArgumentException e) {
						status.setText("Wrong data type!");
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						status.setText("Update fail!");
						e.printStackTrace();
					}

				}
			} else
				JOptionPane.showMessageDialog(null, "Please Select A Row First");
		} else if (arg0.getSource() == clear) {
			clear();
			status.setText("");
		} else if (arg0.getSource() == delete) {
			int i = truckTable.getSelectedRow();
			if (i >= 0) {
				while (i >= 0) {
					int class_id = (int) truckTable.getValueAt(i, 0);
					try {
						delete(class_id);
						status.setText("Delete sucessful!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						status.setText("Delete fail!");
						e.printStackTrace();
					}
					truckModel.removeRow(i);
					i = truckTable.getSelectedRow();
				}
			} else
				JOptionPane.showMessageDialog(null, "Please Select A Row First");
		} else if (arg0.getSource() == export) {
			try {
				ResultSet rs = con.createStatement().executeQuery(
						"SELECT class_id, make, manufacturing_year, price, color, truck_load FROM vehicle.vehicle WHERE switch = 1 AND type = 'truck'");
				CreateExcel excel = new CreateExcel(rs, truckCol, truckGetRs, "Truck");
				status.setText("Created file: " + excel.getPath());
				rs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void connect() {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/vehicle";
		String username = "root";
		String password = "";

		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Connect to database fail");
			e.printStackTrace();
		}
	}
	
	private void readdb() throws SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM vehicle.vehicle");
		while (rs.next()) {
			String type = rs.getString("type");
			if ((type.compareTo("truck") == 0)) {
				truckId = rs.getInt("class_id");
				if (rs.getBoolean("switch")) {
					truckRow[0] = truckId;
					String make = rs.getString("make");
					truckRow[1] = make;
					int my = rs.getInt("manufacturing_year");
					truckRow[2] = my;
					double price = rs.getDouble("price");
					truckRow[3] = price;
					String color = rs.getString("color");
					truckRow[4] = color;
					double load = rs.getDouble("truck_load");
					truckRow[5] = load;
					truckModel.addRow(truckRow);
				}
			}		
		}
		rs.close();
		statement.close();
	}

	private void add() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"INSERT INTO vehicle.vehicle (class_id, type, make, manufacturing_year, price, color, truck_load) VALUES (?, 'truck', ?, ?, ?, ?, ?)");
		preparestatement.setInt(1, truckId);
		preparestatement.setString(2, truckMake.getText());
		preparestatement.setInt(3, Integer.parseInt(truckManufacturingYear.getText()));
		preparestatement.setDouble(4, Double.parseDouble(truckPrice.getText()));
		preparestatement.setString(5, truckColor.getText());
		preparestatement.setDouble(6, Double.parseDouble(truckLoad.getText()));
		preparestatement.executeUpdate();
		preparestatement.close();
	}

	private void update(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"UPDATE vehicle.vehicle SET make = ?, manufacturing_year = ?, price = ?, color = ?, truck_load = ? WHERE class_id = ? AND type = 'truck'");
		preparestatement.setString(1, truckMake.getText());
		preparestatement.setInt(2, Integer.parseInt(truckManufacturingYear.getText()));
		preparestatement.setDouble(3, Double.parseDouble(truckPrice.getText()));
		preparestatement.setString(4, truckColor.getText());
		preparestatement.setDouble(5, Double.parseDouble(truckLoad.getText()));
		preparestatement.setInt(6, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}
	
	private void delete(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"UPDATE vehicle.vehicle SET switch = ? WHERE class_id = ? AND type = 'truck'");
		preparestatement.setBoolean(1, false);
		preparestatement.setInt(2, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}

}
