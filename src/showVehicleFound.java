import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.CellType;

public class showVehicleFound extends JFrame implements ActionListener{
	private JScrollPane scrollPane;
	private JPanel pnSouth, pnCenter, pnExportAndClose;
	private JButton export, close;
	private JTable table;
	private JLabel status;
	private Object[] col = {"ID", "Type", "Make", "Manufacturing Year", "Price", "Color", "Number of seats", "Type of engine", "Power", "Load" };
	private Object[] getRs = {"class_id", "type", "make", "manufacturing_year", "price", "color", "number_of_seats", "type_of_engine", "power", "truck_load"}; 
	private Object[] row = new Object[10];
	private DefaultTableModel model;
	ResultSet rs;
	
	public showVehicleFound(ResultSet rs) {
		// TODO Auto-generated constructor stub
		this.rs = rs;
		setWindow();
		window();
	}
	
	private void setWindow() {
		setSize(1100, 600);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private void window() {
		pnSouth = new JPanel();
		pnSouth.setPreferredSize(new Dimension(10, 80));
		add(pnSouth, BorderLayout.SOUTH);
		pnSouth.setLayout(new BorderLayout(0, 0));
		
		pnSouth.add(new JPanel(), BorderLayout.WEST);
		status = new JLabel();
		pnSouth.add(status);
		
		pnExportAndClose = new JPanel();
		pnExportAndClose.setPreferredSize(new Dimension(500, 10));
		pnSouth.add(pnExportAndClose, BorderLayout.EAST);
		pnExportAndClose.setLayout(null);
		
		close = new JButton("Close");
		close.setContentAreaFilled(false);
		close.setBounds(315, 20, 175, 35);
		close.addActionListener(this);
		pnExportAndClose.add(close);
		
		export = new JButton("Export (Excel)");
		export.setContentAreaFilled(false);
		export.setBounds(130, 20, 175, 35);
		export.addActionListener(this);
		ImageIcon excel = new ImageIcon("src/icon/excel.png");
		export.setIcon(excel);
		pnExportAndClose.add(export);
		
		JLabel title = new JLabel("VEHICLE FOUND");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(0, 40));
		add(title, BorderLayout.NORTH);
		
		pnCenter = new JPanel();
		add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		pnCenter.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(col);
		table.setModel(model);
		scrollPane.setViewportView(table);
		try {
			readdb();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
	}
	
	private void readdb() throws SQLException {
		while (rs.next()) {	
			for (int i=0; i<col.length; i++) {
				String s = rs.getString(getRs[i].toString());
				row[i] = s;
			}
			model.addRow(row);		
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == close) {
			dispose();
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				rs.beforeFirst();
				CreateExcel excel = new CreateExcel(rs, col, getRs, "Vehicle Found");
				status.setText("Created file: " + excel.getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
