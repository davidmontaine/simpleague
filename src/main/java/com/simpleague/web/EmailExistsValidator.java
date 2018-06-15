package com.simpleague.web;

import com.simpleague.user.User;
import com.simpleague.user.UserBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

@FacesValidator("EmailExistsValidator")
public class EmailExistsValidator implements Validator {
    @Inject
    private UserBean userBean;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!((UIInput)component).isValid()) {
            return;
        }
        if (value != null) {
            // true (default): if email exists then error (unless it's its own current value).
            // false: if email does not exists then error.
            String result = FacesUtil.getValidatorParameter(component, "emailExistsValidatorResult");
            
            if (!"true".equalsIgnoreCase(result) && !"false".equalsIgnoreCase(result)) {
                result = "true";
            }
            // Another component bound to this one that contains current value.
            String emailCurrent = null;
            UIInput uiInput = FacesUtil.getValidatorComponent(component, "emailExistsValidatorComponent");

            if (uiInput != null) {
                emailCurrent = (String)uiInput.getValue();
            }            
            User user = userBean.findByEmail((String)value);
            
            if (("false".equalsIgnoreCase(result) && user == null) || ("true".equalsIgnoreCase(result) &&
                    user != null) && !user.getCredentials().getEmail().equalsIgnoreCase(emailCurrent)) {            
                
                String msg = FacesUtil.getValidatorMessage(component, "emailExistsValidatorMessage");
                throw new ValidatorException(new FacesMessage(msg));
            }
        }        
    }
}
