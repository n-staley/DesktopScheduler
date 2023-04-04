package Controller;

import DAO.AppointmentDao;
import DAO.ContactsDao;
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
    public TableView r3TableView;
    public Button exitButton;
    public Button viewCustomersButton;
    public Button viewAppointmentsButton;
    public AnchorPane mainPane;
    public Label numberAppointments;
    public ComboBox<String> monthCombo;
    public ComboBox<String> appointmentTypeCombo;
    public ComboBox<String> contactNameCombo;

    public void exit(ActionEvent actionEvent) {
        exitProgram(mainPane);
    }

    public void toViewCustomers(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/CustomerDashboardForm.fxml", 1200, 600, "Customers DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toViewAppointments(ActionEvent actionEvent) {
        try {
            switchScene(actionEvent, "/view/AppointmentDashboardForm.fxml", 1200, 600, "Appointment DashBoard");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void appointmentTypeSelected(ActionEvent actionEvent) {
        numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));
    }

    public void monthSelected(ActionEvent actionEvent) {
        numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTypeCombo.setItems(AppointmentDao.getAppointmentTypeList());
        appointmentTypeCombo.setValue(AppointmentDao.getAppointmentTypeList().get(0));
        monthCombo.setItems(ReportsDao.MonthList);
        monthCombo.setValue(ReportsDao.MonthList.get(0));
        numberAppointments.setText(String.valueOf(ReportsDao.appointmentTypePerMonth(appointmentTypeCombo.getValue(), monthCombo.getValue())));

        contactNameCombo.setItems(ContactsDao.getContactsNameList());
        contactNameCombo.setValue(ContactsDao.getContactsNameList().get(0));

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
    }

    public void switchContactTableView(ActionEvent actionEvent) {
        ReportsDao.appointmentsByContact(ContactsDao.getContactIDNumber(contactNameCombo.getValue()));
        reportTwoTableView.setItems(ReportsDao.getAppointmentsByContactList());
    }
}
