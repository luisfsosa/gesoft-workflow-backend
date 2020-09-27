package cl.com.gesoft.workflow.model.hibernate;


import cl.com.gesoft.workflow.model.Address;

public class AddressDataUserType extends JsonDataUserType {
    public AddressDataUserType() {
        super(Address.class);
    }
}
