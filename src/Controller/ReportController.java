package Controller;

import DAO.AppointmentDao;
import DAO.ContactsDao;
import DAO.CustomersDao;
import DAO.ReportsDao;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the reports form, giving it its functionality.
 * @author Nicholas Staley
 */
public class ReportController extends ViewController implements Initializable {
    public TabPane reportHolder;
    public Tab reportOneTab;
    public Tab reportTwoTab;
    public TableView<Appointment> reportTwoTableView;
    public TableColumn<Appointment, Integer> r2Contact;
    public TableColumn<Appointment, String> r2Title;
    public TableColumn<Appointment, Integer> r2AppointmentID;
    public TableColumn<Appointment, String> r2Type;
    public TableColumn<Appointment, String> r2Description;
    public TableColumn<Appointment, String> r2StartDateTime;
    public TableColumn<Appointment, String> r2EndDateTime;
    public TableColumn<Appointment, Integer> r2CustomerID;
    public Tab report3Tab;
    public Button exitButton;
    public Button viewCustomersButton;
    public Button viewAppointmentsButton;
    public AnchorPane mainPane;
    public Label numberAppointments;
    public ComboBox<String> monthCombo;
    public ComboBox<String> appointmentTypeCombo;
    public ComboBox<String> contactNameCombo;
    public TableView<Appointment> appointmentsTableView;
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, Integer> contactColumn;
    public TableColumn<Appointment, String>typeColumn;
    public TableColumn<Appointment, String> startDateTimeColumn;
    public TableColumn<Appointment, String> endDateTimeColumn;
    public TableColumn<Appointment, Integer> customerIDColumn;
    public TableColumn<Appointment, Integer> userIDColumn;
    public ComboBox<String> customerNameCombo;

    /**
     * This method is used to exit the program.
     * @param actionEvent Exit button clicked
     */
    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
    }

    /**
     * This method is used to switch scenes to the customers' dashboard.
     * @param actionEvent View Customers button clicked
     */
    public void toViewCustomers(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers Dashboard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to switch scenes to the appointments' dashboard.
     * @param actionEvent View Appointments button clicked
     */
    public void toViewAppointments(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment Dashboard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method sets the appointments count to the number of appointments by type per month search.
     * @param actionEvent Selection made in the appointment type combo box
     */
    public void appointmentTypeSelected(ActionEvent actionEvent) {
        numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));
    }

    /**
     * This method sets the appointments count to the number of appointments by type per month search.
     * @param actionEvent Selection made in the month combo box
     */
    public void monthSelected(ActionEvent actionEvent) {
        numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));
    }

    /**
     * This method initializes the reports scene by setting the first report to have the first appointment type and the
     * first month selections. Then it sets the table view in report two with the appointments by contact list. Finally,
     * it sets the table view in the third report to appointments by customer list.
     * @param url The url
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Report one initialize
        monthCombo.setItems(ReportsDao.MonthList);
        monthCombo.setValue(ReportsDao.MonthList.get(0));
        if (AppointmentDao.getAppointmentTypeList().size() > 0) {
            appointmentTypeCombo.setItems(AppointmentDao.getAppointmentTypeList());
            appointmentTypeCombo.setValue(AppointmentDao.getAppointmentTypeList().get(0));
            numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));
        }
        else {
            numberAppointments.setText("Must enter an appointment.");
        }
        contactNameCombo.setItems(ContactsDao.getContactsNameList());
        contactNameCombo.setValue(ContactsDao.getContactsNameList().get(0));

        //Report two initialize
        ReportsDao.appointmentsByContact(ContactsDao.getContactIDNumber(contactNameCombo.getValue()));
        reportTwoTableView.setItems(ReportsDao.getAppointmentsByContactList());
        r2Contact.setCellValueFactory(new PropertyValueFactory<>("contactsID"));
        r2AppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        r2Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        r2Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        r2Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        r2StartDateTime.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        r2EndDateTime.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        r2CustomerID.setCellValueFactory(new PropertyValueFactory<>("custID"));

        if (CustomersDao.getCustomerNameList().size() > 0) {
            customerNameCombo.setItems(CustomersDao.getCustomerNameList());
            customerNameCombo.setValue(CustomersDao.getCustomerNameList().get(0));
            ReportsDao.appointmentsByCustomer(CustomersDao.getCustomerIDNumber(customerNameCombo.getValue()));
        }
        appointmentsTableView.setItems(ReportsDao.getAppointmentsByCustomersList());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("usersID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactsID"));


    }

    /**
     * This method changes the report two table view to the currently selected contact's appointments.
     * @param actionEvent Selected a contact from the contacts combo box.
     */
    public void switchContactTableView(ActionEvent actionEvent) {
        ReportsDao.appointmentsByContact(ContactsDao.getContactIDNumber(contactNameCombo.getValue()));
        reportTwoTableView.setItems(ReportsDao.getAppointmentsByContactList());
    }

    /**
     * This method changes the report three table view to the currently selected customer's appointments
     * @param actionEvent Selection made in the customer name combo box.
     */
    public void switchCustomer(ActionEvent actionEvent) {
        ReportsDao.appointmentsByCustomer(CustomersDao.getCustomerIDNumber(customerNameCombo.getValue()));
        appointmentsTableView.setItems(ReportsDao.getAppointmentsByCustomersList());
    }
}
