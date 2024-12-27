package com.company.homeworkloans.view.client;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.view.main.MainView;
import com.company.homeworkloans.view.requestloan.Requestloan;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Route(value = "clients", layout = MainView.class)
@ViewController("Client.list")
@ViewDescriptor("client-list-view.xml")
@LookupComponent("clientsDataGrid")
@DialogMode(width = "64em")
public class ClientListView extends StandardListView<Client> {

    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private CollectionContainer<Client> clientsDc;


    @Subscribe("clientsDataGrid.request")
    public void onClientsDataGridRequest(final ActionPerformedEvent event) {
        DialogWindow<Requestloan> window =
                dialogWindows.view(this, Requestloan.class).build();
        window.open();
    }
}
