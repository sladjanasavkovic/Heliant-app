package com.heliant.my_app.model.listeners;

import com.heliant.my_app.model.User;

public interface Auditable {

    void setCreatedBy(User user);

    void setModifiedBy(User user);

}
