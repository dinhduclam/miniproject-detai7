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

public class Bike extends JPanel implements ActionListener {
	private JTextField bikeMake;
	private JTextField bikeManufacturingYear;
	private JTextField bikePrice;
	private JTextField bikeColor;
	private JTextField bikePower;
	private JTable bikeTable;
	private JPanel pnSouth, bikeButtonList, bikeMain, bikeTitle, bikeInfo, pnExport;
	private JScrollPane scrollPane;
	private JButton add, update, clear, delete, export;
	private JLabel status;
	private DefaultTableModel bikeModel;
	private Object[] bikeCol = {"ID", "Make", "Manufacturing Year", "Price", "Color", "Power" };
	private Object[] bikeGetRs = { "class_id", "make", "manufacturing_year", "price", "color", "power"};
	private Object[] bikeRow = new Object[6];
	private Connection con;
	private int bikeId = 0;

	Bike() {
		connect();
		createPnLeft(); // pnRight is table
		//create table
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600, 2));

		bikeTable = new JTable();
		bikeTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int i = bikeTable.getSelectedRow();
				bikeMake.setText(bikeTable.getValueAt(i, 1).toString());
				bikeManufacturingYear.setText(bikeTable.getValueAt(i, 2).toString());
				bikePrice.setText(bikeTable.getValueAt(i, 3).toString());
				bikeColor.setText(bikeTable.getValueAt(i, 4).toString());
				bikePower.setText(bikeTable.getValueAt(i, 5).toString());
			}
		});
		bikeModel = new DefaultTableModel();

		bikeModel.setColumnIdentifiers(bikeCol);
		bikeTable.setModel(bikeModel);
		scrollPane.setViewportView(bikeTable);
		try {
			readdb();
			//System.out.println(bikeId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Read database fail!");
			e.printStackTrace();
		}
		
		
		add(scrollPane);
	}
	
	private void createPnLeft() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		bikeInfo = new JPanel();
		bikeInfo.setLayout(new BorderLayout(0, 0));

		bikeTitle = new JPanel();
		bikeTitle.setPreferredSize(new Dimension(10, 50));
		bikeInfo.add(bikeTitle, BorderLayout.NORTH);
		bikeTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel title = new JLabel("BIKE");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		bikeTitle.add(title);

		bikeMain = new JPanel();
		bikeInfo.add(bikeMain, BorderLayout.CENTER);
		bikeMain.setLayout(new GridLayout(6, 0, 10, 10));

		bikeMain.add(new JLabel("Make"));

		bikeMake = new JTextField();
		bikeMake.setBackground(UIManager.getColor("TextField.disabledBackground"));
		bikeMake.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bikeMake.setColumns(10);
		bikeMain.add(bikeMake);

		bikeMain.add(new JLabel("Manufacturing Year"));

		bikeManufacturingYear = new JTextField();
		bikeManufacturingYear.setBackground(UIManager.getColor("TextField.disabledBackground"));
		bikeManufacturingYear.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bikeManufacturingYear.setColumns(10);
		bikeMain.add(bikeManufacturingYear);

		bikeMain.add(new JLabel("Price"));

		bikePrice = new JTextField();
		bikePrice.setBackground(UIManager.getColor("TextField.disabledBackground"));
		bikePrice.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bikePrice.setColumns(10);
		bikeMain.add(bikePrice);

		bikeMain.add(new JLabel("Color"));

		bikeColor = new JTextField();
		bikeColor.setBackground(UIManager.getColor("TextField.disabledBackground"));
		bikeColor.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bikeColor.setColumns(10);
		bikeMain.add(bikeColor);

		bikeMain.add(new JLabel("Power"));

		bikePower = new JTextField();
		bikePower.setBackground(UIManager.getColor("TextField.disabledBackground"));
		bikePower.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bikePower.setColumns(10);
		bikeMain.add(bikePower);

		pnSouth = new JPanel();
		pnSouth.setLayout(new BorderLayout());
		pnSouth.setPreferredSize(new Dimension(0, 200));

		bikeInfo.add(pnSouth, BorderLayout.SOUTH);

		status = new JLabel("");
		status.setPreferredSize(new Dimension(0, 70));
		pnSouth.add(status, BorderLayout.NORTH);
		
		//create button (add, update, clear, delete
		bikeButtonList = new JPanel();
		bikeButtonList.setLayout(new GridLayout(2, 2, 10, 10));
		pnSouth.add(bikeButtonList);

		add = new JButton("Add");
		add.addActionListener(this);
		add.setMargin(new Insets(2, 25, 2, 25));
		add.setContentAreaFilled(false);
		bikeButtonList.add(add);

		update = new JButton("Update");
		update.addActionListener(this);
		update.setMargin(new Insets(2, 20, 2, 20));
		update.setContentAreaFilled(false);
		bikeButtonList.add(update);

		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setMargin(new Insets(2, 22, 2, 22));
		clear.setContentAreaFilled(false);
		bikeButtonList.add(clear);

		delete = new JButton("Delete");
		delete.addActionListener(this);
		delete.setMargin(new Insets(2, 20, 2, 20));
		delete.setContentAreaFilled(false);
		bikeButtonList.add(delete);

		JPanel separator = new JPanel();
		separator.setPreferredSize(new Dimension(15, 0));
		
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

		add(bikeInfo);
		add(separator);
	}

	//clear all text in the textfield (make, manufacturing year, price, color,...)
	private void clear() {
		bikeMake.setText("");
		bikeManufacturingYear.setText("");
		bikePrice.setText("");
		bikeColor.setText("");
		bikePower.setText("");
		bikeMake.requestFocus();
	}

	//check the textfield that are not filled out
	private boolean check() {
		String noti = "Please fill all the form!";
		if (bikeMake.getText().equals("")) {
			bikeMake.requestFocus();
			status.setText(noti);
		} else if (bikeManufacturingYear.getText().equals("")) {
			bikeManufacturingYear.requestFocus();
			status.setText(noti);
		} else if (bikePrice.getText().equals("")) {
			bikePrice.requestFocus();
			status.setText(noti);
		} else if (bikeColor.getText().equals("")) {
			bikeColor.requestFocus();
			status.setText(noti);
		} else if (bikePower.getText().equals("")) {
			bikePower.requestFocus();
			status.setText(noti);
		} else return true;
		return false;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == add) {
			if (check()) {
				try {
					bikeId++;
					add();
					bikeRow[0] = bikeId;
					bikeRow[1] = bikeMake.getText();
					bikeRow[2] = bikeManufacturingYear.getText();
					bikeRow[3] = bikePrice.getText();
					bikeRow[4] = bikeColor.getText();
					bikeRow[5] = bikePower.getText();
					bikeModel.addRow(bikeRow);
					clear();
					status.setText("Add sucessful!");
				} catch (IllegalArgumentException e) {
					bikeId--;
					status.setText("Wrong data type!");
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					bikeId--;
					status.setText("Add fail!");
					e.printStackTrace();
				}	
			}
		} else if (arg0.getSource() == update) {
			int i = bikeTable.getSelectedRow();
			if (i >= 0) {
				if (check()) {
					try {
						int class_id = (int) bikeTable.getValueAt(i, 0);
						update(class_id);
						bikeTable.setValueAt(bikeMake.getText(), i, 1);
						bikeTable.setValueAt(bikeManufacturingYear.getText(), i, 2);
						bikeTable.setValueAt(bikePrice.getText(), i, 3);
						bikeTable.setValueAt(bikeColor.getText(), i, 4);
						bikeTable.setValueAt(bikePower.getText(), i, 5);
						clear();
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
			int i = bikeTable.getSelectedRow();
			if (i >= 0) {
				while (i >= 0) {
					int class_id = (int) bikeTable.getValueAt(i, 0);
					try {
						delete(class_id);
						status.setText("Delete sucessful!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						status.setText("Delete fail!");
						e.printStackTrace();
					}
					bikeModel.removeRow(i);
					i = bikeTable.getSelectedRow();
				}
			} else
				JOptionPane.showMessageDialog(null, "Please Select A Row First");
		} else if (arg0.getSource() == export) {
			try {
				ResultSet rs = con.createStatement().executeQuery(
						"SELECT class_id, make, manufacturing_year, price, color, power FROM vehicle.vehicle WHERE switch = 1 AND type = 'bike'");
				CreateExcel excel = new CreateExcel(rs, bikeCol, bikeGetRs, "Bike");
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
			if ((type.compareTo("bike") == 0)) {
				bikeId = rs.getInt("class_id");
				if (rs.getBoolean("switch")) {
					bikeRow[0] = bikeId;
					String make = rs.getString("make");
					bikeRow[1] = make;
					int my = rs.getInt("manufacturing_year");
					bikeRow[2] = my;
					double price = rs.getDouble("price");
					bikeRow[3] = price;
					String color = rs.getString("color");
					bikeRow[4] = color;
					double power = rs.getDouble("power");
					bikeRow[5] = power;
					bikeModel.addRow(bikeRow);
				}
			}	
		}
		rs.close();
		statement.close();
	}

	private void add() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"INSERT INTO vehicle.vehicle (class_id, type, make, manufacturing_year, price, color, power) VALUES (?, 'bike', ?, ?, ?, ?, ?)");
		preparestatement.setInt(1, bikeId);
		preparestatement.setString(2, bikeMake.getText());
		preparestatement.setInt(3, Integer.parseInt(bikeManufacturingYear.getText()));
		preparestatement.setDouble(4, Double.parseDouble(bikePrice.getText()));
		preparestatement.setString(5, bikeColor.getText());
		preparestatement.setDouble(6, Double.parseDouble(bikePower.getText()));
		preparestatement.executeUpdate();
		preparestatement.close();
	}

	private void update(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"UPDATE vehicle.vehicle SET make = ?, manufacturing_year = ?, price = ?, color = ?, power = ? WHERE class_id = ? AND type = 'bike'");
		preparestatement.setString(1, bikeMake.getText());
		preparestatement.setInt(2, Integer.parseInt(bikeManufacturingYear.getText()));
		preparestatement.setDouble(3, Double.parseDouble(bikePrice.getText()));
		preparestatement.setString(4, bikeColor.getText());
		preparestatement.setDouble(5, Double.parseDouble(bikePower.getText()));
		preparestatement.setInt(6, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}
	
	private void delete(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"UPDATE vehicle.vehicle SET switch = ? WHERE class_id = ? AND type = 'bike'");
		preparestatement.setBoolean(1, false);
		preparestatement.setInt(2, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}

}
