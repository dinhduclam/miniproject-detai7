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

public class Car extends JPanel implements ActionListener {
	private JTextField carMake;
	private JTextField carManufacturingYear;
	private JTextField carPrice;
	private JTextField carColor;
	private JTextField carNumberOfSeats;
	private JTextField carTypeOfEngine;
	private JTable carTable;
	private JPanel pnSouth, carButtonList, carMain, carTitle, carInfo, pnExport;
	private JScrollPane scrollPane;
	private JButton add, update, clear, delete, export;
	private JLabel status;
	private DefaultTableModel carModel;
	private Object[] carCol = { "ID", "Make", "Manufacturing Year", "Price", "Color", "Number of seats",
			"Type of engine" };
	private Object[] carGetRs = { "class_id", "make", "manufacturing_year", "price", "color", "number_of_seats",
			"type_of_engine" };
	private Object[] carRow = new Object[7];
	private Connection con;
	private int carId = 0;

	Car() {
		connect();
		createPnLeft(); // pnRight is table
		// create table
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600, 2));

		carTable = new JTable();
		carTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int i = carTable.getSelectedRow();
				carMake.setText(carTable.getValueAt(i, 1).toString());
				carManufacturingYear.setText(carTable.getValueAt(i, 2).toString());
				carPrice.setText(carTable.getValueAt(i, 3).toString());
				carColor.setText(carTable.getValueAt(i, 4).toString());
				carNumberOfSeats.setText(carTable.getValueAt(i, 5).toString());
				carTypeOfEngine.setText(carTable.getValueAt(i, 6).toString());
			}
		});
		carModel = new DefaultTableModel();
		carModel.setColumnIdentifiers(carCol);
		carTable.setModel(carModel);
		scrollPane.setViewportView(carTable);
		try {
			readdb();
			// System.out.println(carId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Read database fail!");
			e.printStackTrace();
		}
		add(scrollPane);
	}

	private void createPnLeft() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		carInfo = new JPanel();
		carInfo.setLayout(new BorderLayout(0, 0));

		carTitle = new JPanel();
		carTitle.setPreferredSize(new Dimension(10, 50));
		carInfo.add(carTitle, BorderLayout.NORTH);
		carTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel title = new JLabel("CAR");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		carTitle.add(title);

		carMain = new JPanel();
		carInfo.add(carMain, BorderLayout.CENTER);
		carMain.setLayout(new GridLayout(6, 0, 10, 10));

		carMain.add(new JLabel("Make"));

		carMake = new JTextField();
		carMake.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carMake.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carMake.setColumns(10);
		carMain.add(carMake);

		carMain.add(new JLabel("Manufacturing Year"));

		carManufacturingYear = new JTextField();
		carManufacturingYear.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carManufacturingYear.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carManufacturingYear.setColumns(10);
		carMain.add(carManufacturingYear);

		carMain.add(new JLabel("Price"));

		carPrice = new JTextField();
		carPrice.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carPrice.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carPrice.setColumns(10);
		carMain.add(carPrice);

		carMain.add(new JLabel("Color"));

		carColor = new JTextField();
		carColor.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carColor.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carColor.setColumns(10);
		carMain.add(carColor);

		carMain.add(new JLabel("Number of seats"));

		carNumberOfSeats = new JTextField();
		carNumberOfSeats.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carNumberOfSeats.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carNumberOfSeats.setColumns(10);
		carMain.add(carNumberOfSeats);

		carMain.add(new JLabel("Type of engine"));

		carTypeOfEngine = new JTextField();
		carTypeOfEngine.setBackground(UIManager.getColor("TextField.disabledBackground"));
		carTypeOfEngine.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		carTypeOfEngine.setColumns(10);
		carMain.add(carTypeOfEngine);

		pnSouth = new JPanel();
		pnSouth.setLayout(new BorderLayout());
		pnSouth.setPreferredSize(new Dimension(0, 200));

		carInfo.add(pnSouth, BorderLayout.SOUTH);

		status = new JLabel("");
		status.setPreferredSize(new Dimension(0, 70));
		pnSouth.add(status, BorderLayout.NORTH);

		// create button (add, update, clear, delete)
		carButtonList = new JPanel();
		carButtonList.setLayout(new GridLayout(2, 2, 10, 10));
		pnSouth.add(carButtonList);

		add = new JButton("Add");
		add.addActionListener(this);
		add.setMargin(new Insets(2, 25, 2, 25));
		add.setContentAreaFilled(false);
		carButtonList.add(add);

		update = new JButton("Update");
		update.addActionListener(this);
		update.setMargin(new Insets(2, 20, 2, 20));
		update.setContentAreaFilled(false);
		carButtonList.add(update);

		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setMargin(new Insets(2, 22, 2, 22));
		clear.setContentAreaFilled(false);
		carButtonList.add(clear);

		delete = new JButton("Delete");
		delete.addActionListener(this);
		delete.setMargin(new Insets(2, 20, 2, 20));
		delete.setContentAreaFilled(false);
		carButtonList.add(delete);

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

		add(carInfo);
		add(separator);
	}

	// clear all text in the textfield (make, manufacturing year, price, color,...)
	private void clear() {
		carMake.setText("");
		carManufacturingYear.setText("");
		carPrice.setText("");
		carColor.setText("");
		carNumberOfSeats.setText("");
		carTypeOfEngine.setText("");
		carMake.requestFocus();
	}

	// check
	private boolean check() {
		String noti = "Please fill all the form!";
		if (carMake.getText().equals("")) {
			carMake.requestFocus();
			status.setText(noti);
		} else if (carManufacturingYear.getText().equals("")) {
			carManufacturingYear.requestFocus();
			status.setText(noti);
		} else if (carPrice.getText().equals("")) {
			carPrice.requestFocus();
			status.setText(noti);
		} else if (carColor.getText().equals("")) {
			carColor.requestFocus();
			status.setText(noti);
		} else if (carNumberOfSeats.getText().equals("")) {
			carNumberOfSeats.requestFocus();
			status.setText(noti);
		} else if (carTypeOfEngine.getText().equals("")) {
			carTypeOfEngine.requestFocus();
			status.setText(noti);
		} else
			return true;
		return false;
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == add) {
			if (check()) {
				try {
					carId++;
					// System.out.println(carId);
					add();
					carRow[0] = carId;
					carRow[1] = carMake.getText();
					carRow[2] = carManufacturingYear.getText();
					carRow[3] = carPrice.getText();
					carRow[4] = carColor.getText();
					carRow[5] = carNumberOfSeats.getText();
					carRow[6] = carTypeOfEngine.getText();
					carModel.addRow(carRow);
					clear();
					status.setText("Add sucessful!");
				} catch (IllegalArgumentException e) {
					carId--;
					// System.out.println(carId);
					status.setText("Wrong data type!");
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					carId--;
					// System.out.println(carId);
					status.setText("Add fail!");
					e.printStackTrace();
				}

			}
		} else if (arg0.getSource() == update) {
			int i = carTable.getSelectedRow();
			if (i >= 0) {
				if (check()) {
					try {
						int class_id = (int) carTable.getValueAt(i, 0);
						update(class_id);
						carTable.setValueAt(carMake.getText(), i, 1);
						carTable.setValueAt(carManufacturingYear.getText(), i, 2);
						carTable.setValueAt(carPrice.getText(), i, 3);
						carTable.setValueAt(carColor.getText(), i, 4);
						carTable.setValueAt(carNumberOfSeats.getText(), i, 5);
						carTable.setValueAt(carTypeOfEngine.getText(), i, 6);
						clear();
						carTable.removeRowSelectionInterval(i, i);
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
			int i = carTable.getSelectedRow();
			if (i >= 0) {
				while (i >= 0) {
					int class_id = (int) carTable.getValueAt(i, 0);
					try {
						delete(class_id);
						status.setText("Delete sucessful!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						status.setText("Delete fail!");
						e.printStackTrace();
					}
					carModel.removeRow(i);
					i = carTable.getSelectedRow();
				}
			} else
				JOptionPane.showMessageDialog(null, "Please Select A Row First");
		} else if (arg0.getSource() == export) {
			try {
				ResultSet rs = con.createStatement().executeQuery(
						"SELECT class_id, make, manufacturing_year, price, color, number_of_seats, type_of_engine FROM vehicle.vehicle WHERE switch = 1 AND type = 'car'");
				CreateExcel excel = new CreateExcel(rs, carCol, carGetRs, "Car");
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
			if ((type.compareTo("car") == 0)) {
				carId = rs.getInt("class_id");
				if (rs.getBoolean("switch")) {
					carRow[0] = carId;
					String make = rs.getString("make");
					carRow[1] = make;
					int my = rs.getInt("manufacturing_year");
					carRow[2] = my;
					double price = rs.getDouble("price");
					carRow[3] = price;
					String color = rs.getString("color");
					carRow[4] = color;
					int nos = rs.getInt("number_of_seats");
					carRow[5] = nos;
					String toe = rs.getString("type_of_engine");
					carRow[6] = toe;
					carModel.addRow(carRow);
				}
			}
		}
		statement.close();
		rs.close();
	}

	private void add() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"INSERT INTO vehicle.vehicle (class_id, type, make, manufacturing_year, price, color, number_of_seats, type_of_engine) VALUES (?, 'car', ?, ?, ?, ?, ?, ?)");
		preparestatement.setInt(1, carId);
		preparestatement.setString(2, carMake.getText());
		preparestatement.setInt(3, Integer.parseInt(carManufacturingYear.getText()));
		preparestatement.setDouble(4, Double.parseDouble(carPrice.getText()));
		preparestatement.setString(5, carColor.getText());
		preparestatement.setInt(6, Integer.parseInt(carNumberOfSeats.getText()));
		preparestatement.setString(7, carTypeOfEngine.getText());
		preparestatement.executeUpdate();
		preparestatement.close();
	}

	private void update(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con.prepareStatement(
				"UPDATE vehicle.vehicle SET make = ?, manufacturing_year = ?, price = ?, color = ?, number_of_seats = ?, type_of_engine = ? WHERE class_id = ? AND type = 'car'");
		preparestatement.setString(1, carMake.getText());
		preparestatement.setInt(2, Integer.parseInt(carManufacturingYear.getText()));
		preparestatement.setDouble(3, Double.parseDouble(carPrice.getText()));
		preparestatement.setString(4, carColor.getText());
		preparestatement.setInt(5, Integer.parseInt(carNumberOfSeats.getText()));
		preparestatement.setString(6, carTypeOfEngine.getText());
		preparestatement.setInt(7, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}

	private void delete(int class_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement preparestatement = con
				.prepareStatement("UPDATE vehicle.vehicle SET switch = ? WHERE class_id = ? AND type = 'car'");
		preparestatement.setBoolean(1, false);
		preparestatement.setInt(2, class_id);
		preparestatement.executeUpdate();
		preparestatement.close();
	}

}
