package com.example.whodo.app;



import androidx.lifecycle.LiveData;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.workOrder.WorkOrder;

import java.util.List;

public interface ViewModelInterface {
    /// ********************************************************** ///
    /// LoggedUser Methods ///
    void setLoggedUser(User pUser);
    LiveData<User> getLoggedUser();
    /// ********************************************************** ///
    /// Providers Methods ///
    void setProviders(List<User> pProvidersList);
    LiveData<List<User>> getProviders();
    /// ********************************************************** ///
    /// workOrder methods ///
    void createWorkOrder(WorkOrder pWorkOrder);
    void updateWorkOrder(WorkOrder pWorkOrder);
    void setWorkOrder(List<WorkOrder> pWorkOrdersList);
    LiveData<List<WorkOrder>> getWorkOrder();
}
