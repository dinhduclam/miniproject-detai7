import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class Find extends JPanel implements ActionListener {

	JPanel pnSouth, pnNorth, pnCenter, pnGetInfo, pnClearAndFind;
	JButton clear, find;
	JTextField make, manufacturingYear, price, color, typeOfEngine, numberOfSeats, power, load;
	JLabel status;
	Connection con;

	public Find() {
		window();
	}

	private void window() {
		connect();
		setLayout(new BorderLayout(0, 0));

		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);

		pnSouth = new JPanel();
		pnSouth.setPreferredSize(new Dimension(0, 120));
		add(pnSouth, BorderLayout.SOUTH);
		pnSouth.setLayout(new BorderLayout());

		pnSouth.add(new JPanel(), BorderLayout.WEST);

		status = new JLabel();
		pnSouth.add(status);

		pnClearAndFind = new JPanel();
		pnClearAndFind.setPreferredSize(new Dimension(310, 0));
		pnClearAndFind.setLayout(null);
		pnSouth.add(pnClearAndFind, BorderLayout.EAST);

		find = new JButton("Find");
		find.setBounds(155, 55, 145, 38);
		find.setContentAreaFilled(false);
		pnClearAndFind.add(find);
		find.addActionListener(this);

		clear = new JButton("Clear");
		clear.setBounds(0, 55, 145, 38);
		clear.setContentAreaFilled(false);
		pnClearAndFind.add(clear);
		clear.addActionListener(this);

		pnNorth = new JPanel();
		pnNorth.setPreferredSize(new Dimension(0, 70));
		add(pnNorth, BorderLayout.NORTH);
		pnNorth.setLayout(new BorderLayout());

		JLabel title = new JLabel("FIND VEHICLE");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		pnNorth.add(title);

		pnCenter = new JPanel();
		add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new BorderLayout(0, 0));

		JLabel title2 = new JLabel("Fill out the form below to find vehicle:");
		title2.setPreferredSize(new Dimension(0, 80));
		pnCenter.add(title2, BorderLayout.NORTH);

		pnGetInfo = new JPanel();
		pnCenter.add(pnGetInfo, BorderLayout.CENTER);
		pnGetInfo.setLayout(new GridLayout(4, 4, 35, 35));

		pnGetInfo.add(new JLabel("Make"));

		make = new JTextField();
		make.setBackground(UIManager.getColor("TextField.disabledBackground"));
		make.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		make.setColumns(10);
		pnGetInfo.add(make);

		pnGetInfo.add(new JLabel("Manufacturing Year"));

		manufacturingYear = new JTextField();
		manufacturingYear.setBackground(UIManager.getColor("TextField.disabledBackground"));
		manufacturingYear.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		manufacturingYear.setColumns(10);
		pnGetInfo.add(manufacturingYear);

		pnGetInfo.add(new JLabel("Price"));

		price = new JTextField();
		price.setBackground(UIManager.getColor("TextField.disabledBackground"));
		price.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		price.setColumns(10);
		pnGetInfo.add(price);

		pnGetInfo.add(new JLabel("Color"));

		color = new JTextField();
		color.setBackground(UIManager.getColor("TextField.disabledBackground"));
		color.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		color.setColumns(10);
		pnGetInfo.add(color);

		pnGetInfo.add(new JLabel("Number Of Seats"));

		numberOfSeats = new JTextField();
		numberOfSeats.setBackground(UIManager.getColor("TextField.disabledBackground"));
		numberOfSeats.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		numberOfSeats.setColumns(10);
		pnGetInfo.add(numberOfSeats);
		numberOfSeats.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (numberOfSeats.getText().equals("") == false || typeOfEngine.getText().equals("") == false) {
					load.setEditable(false);
					power.setEditable(false);
				} else {
					load.setEditable(true);
					power.setEditable(true);
					power.setBackground(UIManager.getColor("TextField.disabledBackground"));
					load.setBackground(UIManager.getColor("TextField.disabledBackground"));
				}
			}
		});

		pnGetInfo.add(new JLabel("Type Of Engine"));

		typeOfEngine = new JTextField();
		typeOfEngine.setBackground(UIManager.getColor("TextField.disabledBackground"));
		typeOfEngine.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		typeOfEngine.setColumns(10);
		pnGetInfo.add(typeOfEngine);
		typeOfEngine.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (typeOfEngine.getText().equals("") == false || numberOfSeats.getText().equals("") == false) {
					load.setEditable(false);
					power.setEditable(false);
				} else {
					power.setEditable(true);
					load.setEditable(true);
					power.setBackground(UIManager.getColor("TextField.disabledBackground"));
					load.setBackground(UIManager.getColor("TextField.disabledBackground"));
				}
			}
		});

		pnGetInfo.add(new JLabel("Load"));

		load = new JTextField();
		load.setBackground(UIManager.getColor("TextField.disabledBackground"));
		load.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		load.setColumns(10);
		pnGetInfo.add(load);
		load.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (load.getText().equals("") == false) {
					numberOfSeats.setEditable(false);
					typeOfEngine.setEditable(false);
					power.setEditable(false);
				} else {
					numberOfSeats.setEditable(true);
					typeOfEngine.setEditable(true);
					power.setEditable(true);
					numberOfSeats.setBackground(UIManager.getColor("TextField.disabledBackground"));
					typeOfEngine.setBackground(UIManager.getColor("TextField.disabledBackground"));
					power.setBackground(UIManager.getColor("TextField.disabledBackground"));
				}
			}
		});

		pnGetInfo.add(new JLabel("Power"));

		power = new JTextField();
		power.setBackground(UIManager.getColor("TextField.disabledBackground"));
		power.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		power.setColumns(10);
		pnGetInfo.add(power);
		power.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (power.getText().equals("") == false) {
					numberOfSeats.setEditable(false);
					typeOfEngine.setEditable(false);
					load.setEditable(false);
				} else {
					numberOfSeats.setEditable(true);
					typeOfEngine.setEditable(true);
					load.setEditable(true);
					numberOfSeats.setBackground(UIManager.getColor("TextField.disabledBackground"));
					typeOfEngine.setBackground(UIManager.getColor("TextField.disabledBackground"));
					load.setBackground(UIManager.getColor("TextField.disabledBackground"));
				}
			}
		});
	}

	void connect() {
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

	private void clear() {
		make.setText("");
		manufacturingYear.setText("");
		price.setText("");
		color.setText("");
		numberOfSeats.setText("");
		typeOfEngine.setText("");
		load.setText("");
		power.setText("");
		make.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == clear) {
			clear();
			numberOfSeats.setEditable(true);
			typeOfEngine.setEditable(true);
			load.setEditable(true);
			power.setEditable(true);
			numberOfSeats.setBackground(UIManager.getColor("TextField.disabledBackground"));
			typeOfEngine.setBackground(UIManager.getColor("TextField.disabledBackground"));
			load.setBackground(UIManager.getColor("TextField.disabledBackground"));
			power.setBackground(UIManager.getColor("TextField.disabledBackground"));
			status.setText("");
		} else if (arg0.getSource() == find) {
			Statement st = null;
			ResultSet rs = null;
			String sql;
			try {
				boolean k = true;
				st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql = "SELECT class_id, type, make, manufacturing_year, price, color, number_of_seats, type_of_engine, power, truck_load FROM vehicle.vehicle WHERE switch = 1";
				// Make
				if (make.getText().equals("") == false)
					sql += " AND make = '" + make.getText() + "'";
				// Manufacturing Year
				if (manufacturingYear.getText().equals("") == false) {
					try {
						int y = Integer.parseInt(manufacturingYear.getText());
						sql += " AND manufacturing_year = " + manufacturingYear.getText();
					} catch (Exception e) {
						// TODO: handle exception
						status.setText("Wrong data type at Manufacturing Year!");
						k = false;
					}
				}
				// Color
				if (color.getText().equals("") == false)
					sql += " AND color = '" + color.getText() + "'";
				// Price
				if (price.getText().equals("") == false) {
					try {
						double p = Double.parseDouble(price.getText());
						sql += " AND price = " + price.getText();
					} catch (Exception e) {
						// TODO: handle exception
						status.setText("Wrong data type at Price!");
						k = false;
					}
				}
				//Number Of Seats
				if (numberOfSeats.getText().equals("") == false) {
					try {
						int x = Integer.parseInt(numberOfSeats.getText());
						sql += " AND number_of_seats = " + numberOfSeats.getText();
					} catch (Exception e) {
						// TODO: handle exception
						status.setText("Wrong data type at Number Of Seats!");
						k = false;
					}
				}
				//Type Of Engine
				if (typeOfEngine.getText().equals("") == false)
					sql += " AND type_of_engine = '" + typeOfEngine.getText() + "'";
				//Load
				if (load.getText().equals("") == false) {
					try {
						double x = Double.parseDouble(load.getText());
						sql += " AND truck_load = " + load.getText();
					} catch (Exception e) {
						// TODO: handle exception
						status.setText("Wrong data type at Load!");
						k = false;
					}
				}
				//Power
				if (power.getText().equals("") == false) {
					try {
						double x = Double.parseDouble(power.getText());
						sql += " AND power = " + power.getText();
					}
					catch (Exception e) {
						// TODO: handle exception
						status.setText("Wrong data type at Power!");
						k = false;
					}
				}
				
				if (k) {
					rs = st.executeQuery(sql);
					new ShowVehicleFound(rs);
				}
				else {
					try {
						rs.close();
						st.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					rs.close();
					st.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
