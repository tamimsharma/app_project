import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AirlineTicketReservationSystem {

    // A map to store flight numbers and their available seats.
    private Map<String, Integer> flights; // flight number -> available seats

    // GUI components
    private JFrame frame;
    private JTextArea flightInfoArea;
    private JTextField nameField;
    private JTextField flightNumberField;
    private JTextField numSeatsField;

    // Constructor to initialize flight data and GUI components.
    public AirlineTicketReservationSystem() {
        flights = new HashMap<>();
        flights.put("FL101", 100);  // Initialize available seats for each flight
        flights.put("FL102", 150);
        flights.put("FL103", 200);

        // Set up the GUI
        setupGUI();
    }

    // Method to set up the GUI
    private void setupGUI() {
        frame = new JFrame("Airline Ticket Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Text area to display flight information
        flightInfoArea = new JTextArea();
        flightInfoArea.setEditable(false);
        updateFlightInfo();
        frame.add(new JScrollPane(flightInfoArea), BorderLayout.CENTER);

        // Panel for user input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Enter your name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Enter flight number:"));
        flightNumberField = new JTextField();
        inputPanel.add(flightNumberField);

        inputPanel.add(new JLabel("Enter number of seats:"));
        numSeatsField = new JTextField();
        inputPanel.add(numSeatsField);

        JButton bookButton = new JButton("Book Ticket");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
            }
        });
        inputPanel.add(bookButton);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Method to update the flight information displayed in the text area
    private void updateFlightInfo() {
        StringBuilder flightInfo = new StringBuilder("Available Flights:\n");
        for (Map.Entry<String, Integer> entry : flights.entrySet()) {
            flightInfo.append(entry.getKey()).append(" - ")
                    .append(entry.getValue()).append(" seats available\n");
        }
        flightInfoArea.setText(flightInfo.toString());
    }

    // Method to book a ticket for a specific flight.
    private void bookTicket() {
        String name = nameField.getText();  // Read the user's name.
        String flightNumber = flightNumberField.getText();  // Read the flight number.
        int numSeats;

        // Validate seat input
        try {
            numSeats = Integer.parseInt(numSeatsField.getText());  // Read number of seats.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number of seats.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate the flight number.
        if (!flights.containsKey(flightNumber)) {
            JOptionPane.showMessageDialog(frame, "Invalid flight number. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method if the flight number is invalid.
        }

        // Check if the requested number of seats is available.
        int availableSeats = flights.get(flightNumber);
        if (availableSeats >= numSeats) {
            flights.put(flightNumber, availableSeats - numSeats);  // Update seat count.
            JOptionPane.showMessageDialog(frame, "Ticket booked successfully!\n" +
                            "Name: " + name + "\n" +
                            "Flight Number: " + flightNumber + "\n" +
                            "Number of Seats: " + numSeats,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            updateFlightInfo();  // Update the flight information after booking.
        } else {
            JOptionPane.showMessageDialog(frame, "Not enough seats available for this flight.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the ticket reservation system.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AirlineTicketReservationSystem());
    }
}
