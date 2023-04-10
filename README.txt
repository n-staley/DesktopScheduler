The Workplace Scheduler

The Workplace Scheduler is a desktop application that provides users with a simple and efficient way to manage schedules.
The user will be able to add new appointments, edit existing appointments, and delete appointments that are canceled.
Also, they will be able to add new customers, edit existing customer, and delete customer. The user will also be able
to see various reports and lists of customers and appointments.

Author: Nicholas Staley
Email: nstale3@wgu.edu
Version: 1.0
Date: 04/10/23

IDE: IntelliJ
JDK:
JavaFX:
MySQL Connector Driver:

Directions to run program:
    Launch program.
    Log in with username: test, password: test, or username: admin password: admin.
    This will bring you to the appointment dashboard with a notification of any appointments in the next 15 minutes.
    From here you can click on view week, view month, or view all to view the different appointments based on constraint.
    Clicking on add appointment with bring you to the add appointment form where you can add a new appointment.
        Appointment id is automatically generated.
        Must enter a contact, type, start date and time, end date and time, customer id, and user id.
            Start and end times must be between 0800 and 2200 est.
            Times must not overlap with another scheduled appointment.
        Title, description, and location are optional.
        To add the new appointment click save, if successful you will be returned to the appointment dashboard.
        If there is a problem an error will pop up telling you what is wrong.
        To cancel adding an appointment you can click the cancel button to return to the appointment dashboard.
    To edit an appointment you must select an appointment from the table view, and click the Edit Appointment button.
        Once in the edit appointment window the appointment will be populated into the fields.
        You are able to edit everything except the appointment id number.
        There must be a contact, type, start date and time, end date and time, customer id, and user id.
            Start and end times must be between 0800 and 2200 est.
            Times must not overlap with another scheduled appointment.
        To save the edited appointment click the save button and the appointment will be updated.
        If there are any problems with updating the appointment, an error message will tell you what is wrong.
        If you do not wish to edit the appointment you can click the cancel button to return to the appointment dashboard.
    To Delete an appointment you must select the appointment you wish to delete and then click the delete button.
        A confirmation will then pop up if you still wish to delete the appointment click OK, otherwise click Cancel.
        Once the appointment is deleted you will receive a confirmation of the deletion with the appointment id and type of appointment.
    From the appointment dashboard you can either go to customers or reports by clicking View Customers or View Reports.
    To view the customers click on the View Customer button, this will bring you to the Customers Dashboard.
    To add a new customer click on the Add Customer button, and this will bring you to the add customer form.
        The customer id will be automatically generated.
        You must enter a name, phone number, address, postal code, country, and a state/province.
            To view the available state/provinces, you must first select a country.
            Then you will be able to view the available states/provinces for the selected country.
        To save the new customer click on the save buttons and if there are any problems they will show up in an error message.
        If you do not wish to save the new customer you can return to the customer dashboard by clicking the Cancel button.
    To edit a customer you must select a customer from the table view and then click the Edit Customer button.
        You will be taken to the edit customer form where the selected customer's information will be populated in the fields.
        As with adding a customer, all fields must be filled in except the customer id number which can't be changed.
        To save the edited customer click the Save button, and if there were any problems you will get an error message.
        If you do not wish to edit the customer you can click the Cancel button to return to the customer dashboard.
    To delete a customer you must select a customer from the table view and then click the Delete Customer button.
        Deleting a customer will also delete all of their appointments.
        A conformation message will appear asking if you wish to continue deleting the customer, click OK to delete, or Cancel to cancel.
        A conformation will appear after deleting the customer is done confirming that the customer and their appointments were deleted.
    From the customer dashboard you can either go to the appointments by clicking View Appointments or to reports by
    clicking View Reports.
    To View Reports click the View Reports button.
        This brings you to the first report: Customer Appointments by type and month.
        You are able to view how many appointments of a particular type there are in a specific month.
        To change the appointment type click the combo box and select an appointment type.
        To change the month, click the month combo box and select the desired month.
        These will show the count in the third column for that type and month combination.
    To view the second report click the Contacts' Schedules tab.
        Here you are able to select a contact from the Contact's Name combo box and the table view will populate that
        contact's appointments.
    To view the third report click the Appointments By Customer tab.
        Here you are able to select a customer from the Customer Name combo box, and the table view will populate
        that customer's appointments.
    From the reports screen you are able to go to either appointments or customers by clicking View Appointments or Customers.
    To exit the program you can either click the Exit button on the Reports window, Customer Dashboard, Appointment
    Dashboard, or Login screen.
    Also, you can exit the program by clicking the X on the top right of the window from any screen.


The additional report that I included in the program is a list of appointments based on the selected customer.
This shows every appointment that each individual customer has scheduled.