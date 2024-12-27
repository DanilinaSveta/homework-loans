package com.company.homeworkloans.view.requestloan;


import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import com.company.homeworkloans.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.JmixBigDecimalField;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Route(value = "RequestLoan", layout = MainView.class)
@ViewController(id = "Requestloan")
@ViewDescriptor(path = "RequestLoan.xml")
public class Requestloan extends StandardView {
    @ViewComponent
    private JmixBigDecimalField amount;
    @ViewComponent
    private EntityComboBox<Client> clientsComboBox;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private VerticalLayout requestVBox;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Notifications notifications;


    @Subscribe("requestAction")
    public void onRequestAction(final ActionPerformedEvent event) {

        LocalDate localDate = LocalDate.now();

        Client client = clientsComboBox.getValue();
        BigDecimal amountBD = amount.getValue();

        Loan loan = dataManager.create(Loan.class);
        loan.setClient(client);
        loan.setAmount(amountBD);
        loan.setStatus(LoanStatus.REQUESTED);
        loan.setRequestDate(localDate);

        dataManager.save(loan);
        closeWithDefaultAction();

    }

}