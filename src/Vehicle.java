import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Layout;

public class Vehicle extends JFrame implements ActionListener {
	private JButton btCar, btBike, btTruck, btFind;
	private JPanel pnChoose, pnCenter, cardVehicle, car, bike, truck, find;
	CardLayout card;
	Vehicle() {
		// TODO Auto-generated constructor stub
		window();
		car = new Car();
		bike = new Bike();
		truck = new Truck();
		find = new Find();
		
		pnCenter = new JPanel();
		cardVehicle = new JPanel();
		card = new CardLayout(15, 15);
		cardVehicle.setLayout(card);
		
		cardVehicle.add("car", car);
		cardVehicle.add("bike", bike);
		cardVehicle.add("truck", truck);
		cardVehicle.add("find", find);
		
		pnCenter.setLayout(new BorderLayout());
		pnCenter.add(cardVehicle, BorderLayout.CENTER);
		add(pnCenter);
		set();
	}
	
	private void window() {
		pnChoose = new JPanel();
		pnChoose.setPreferredSize(new Dimension(130, 10));
		pnChoose.setBackground(SystemColor.activeCaption);
		add(pnChoose, BorderLayout.WEST);
		pnChoose.setLayout(null);
		
		JLabel welcome = new JLabel("WELCOME!");
		welcome.setBounds(0, 2, 130, 64);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnChoose.add(welcome);
		
		btCar = new JButton("Car");
		btCar.setForeground(SystemColor.info);
		btCar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btCar.setBackground(SystemColor.activeCaptionBorder);
		btCar.setBounds(0, 89, 130, 47);
		btCar.setBorder(null);
		pnChoose.add(btCar);
		btCar.addActionListener(this);
		
		btBike = new JButton("Bike");
		btBike.setForeground(SystemColor.info);
		btBike.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btBike.setBackground(SystemColor.activeCaption);
		btBike.setBounds(0, 132, 130, 47);
		btBike.setBorder(null);
		pnChoose.add(btBike);
		btBike.addActionListener(this);
	
		btTruck = new JButton("Truck");
		btTruck.setForeground(SystemColor.info);
		btTruck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btTruck.setBackground(SystemColor.activeCaption);
		btTruck.setBounds(0, 175, 130, 47);
		btTruck.setBorder(null);
		pnChoose.add(btTruck);
		btTruck.addActionListener(this);
		
		btFind = new JButton("Find");
		btFind.setForeground(SystemColor.info);
		btFind.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btFind.setBackground(SystemColor.activeCaption);
		btFind.setBounds(0, 218, 130, 47);
		btFind.setBorder(null);
		pnChoose.add(btFind);
		btFind.addActionListener(this);
		
		JLabel lbTitle = new JLabel("VEHICLE MANAGEMENT");
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(SystemColor.activeCaption);
		pnTitle.add(lbTitle);
		add(pnTitle, BorderLayout.NORTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		btCar.setBackground(SystemColor.activeCaption);
		btBike.setBackground(SystemColor.activeCaption);
		btTruck.setBackground(SystemColor.activeCaption);
		btFind.setBackground(SystemColor.activeCaption);
		if (arg0.getSource() == btCar) {
			card.show(cardVehicle, "car");
			btCar.setBackground(SystemColor.activeCaptionBorder);
		}
		else if (arg0.getSource() == btBike) {
			card.show(cardVehicle, "bike");
			btBike.setBackground(SystemColor.activeCaptionBorder);
		}
		else if (arg0.getSource() == btTruck) {
			card.show(cardVehicle, "truck");
			btTruck.setBackground(SystemColor.activeCaptionBorder);
		}
		else if (arg0.getSource() == btFind) {
			card.show(cardVehicle, "find");
			btFind.setBackground(SystemColor.activeCaptionBorder);
		}
	}
	
	private void set() {
		setSize(1100, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setTitle("Vehicle Management");
	}
		
}
