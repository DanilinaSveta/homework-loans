package com.company.homeworkloans.view.loan;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import com.company.homeworkloans.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;




@Route(value = "loansApproval", layout = MainView.class)
@ViewController(id = "LoanApproval.list")
@ViewDescriptor(path = "loan-approval-view.xml")
@LookupComponent("loansDataGrid")
@DialogMode(width = "64em")
public class LoanApprovalView extends StandardListView<Loan> {
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private DataGrid<Loan> loansDataGrid;
    @ViewComponent
    private CollectionLoader<Loan> loansDl;
    @ViewComponent
    private CollectionContainer<Loan> loansDc;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit (InitEvent enet){
        // Устанавливаем параметр фильтрации
        loansDl.setParameter("status", LoanStatus.REQUESTED);
        // Загружаем данные
        loansDl.load();

        LocalDate localDate = LocalDate.now();
        loansDataGrid.addColumn(loan ->{
            LocalDate birthDate = loan.getClient().getBirthDate();
            if (birthDate != null){
                String str = String.valueOf(Period.between(birthDate, localDate).getYears());
                return str;
            }
            return "N/A";
        }).setHeader("Age");
    }

    @Subscribe(id = "approveBtn", subject = "clickListener")
    public void onApproveBtnClick(final ClickEvent<JmixButton> event) {
        Loan selectedLoan = loansDc.getItemOrNull();
        if (selectedLoan != null) {
            selectedLoan.setStatus(LoanStatus.APPROVED);
            dataManager.save(selectedLoan);
            loansDc.getMutableItems().remove(selectedLoan);
            notifications.create("Approved")
                    .withType(Notifications.Type.SUCCESS)
                    .withPosition(Notification.Position.MIDDLE)
                    .show();
        }
    }

    @Subscribe(id = "rejectBtn", subject = "clickListener")
    public void onRejectBtnClick(final ClickEvent<JmixButton> event) {
        Loan selectedLoan = loansDc.getItemOrNull();
        if (selectedLoan != null) {
            selectedLoan.setStatus(LoanStatus.REJECTED);
            dataManager.save(selectedLoan);
            loansDc.getMutableItems().remove(selectedLoan);
            notifications.create("Rejected")
                    .withType(Notifications.Type.SUCCESS)
                    .withPosition(Notification.Position.MIDDLE)
                    .show();
        }
    }
    


    
//    public List<Loan> loadBySatus(){
//        return dataManager.load(Loan.class)
//                .query("select e from Loan e where e.status = :status")
//                .parameter("status", String.valueOf(LoanStatus.REQUESTED))
//                .list();
//    }



}