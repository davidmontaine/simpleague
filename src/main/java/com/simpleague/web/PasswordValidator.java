package com.simpleague.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {
    @Inject
    private Login login;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!((UIInput) component).isValid()) {
            return;
        }
        String password = (String)value;
        // true: compare to current password.
        // false: do not compare to current password.
        String current = FacesUtil.getValidatorParameter(component, "passwordValidatorCurrent");
        String passwordCurrent = null;
        
        if ("true".equalsIgnoreCase(current)) {
            passwordCurrent = login.getCredentials().getPasswordText();
        } else {
            passwordCurrent = password;
        }
        // Password verify component bound to this one.
        String passwordVerify = null;
        UIInput uiInput = FacesUtil.getValidatorComponent(component, "passwordValidatorComponent");
        
        if (uiInput != null) {
            passwordVerify = (String)uiInput.getSubmittedValue();                    
        }
        if (passwordVerify == null) {
            passwordVerify = password;
        }
        if (password == null || password.length() < 8 || !password.equals(passwordCurrent) ||
                !password.equals(passwordVerify)) {
            
            String msg = FacesUtil.getValidatorMessage(component, "passwordValidatorMessage");
            throw new ValidatorException(new FacesMessage(msg));
	}
    }
}
