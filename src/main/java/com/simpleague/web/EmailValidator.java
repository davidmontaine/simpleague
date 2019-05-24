package com.simpleague.web;

import com.simpleague.util.StringUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "EmailValidator", managed = true)
public class EmailValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!((UIInput)component).isValid()) {
            return;
        }        
        if(value == null || !StringUtil.validateEmail(value.toString())) {
            String msg = FacesUtil.getValidatorMessage(component, "emailValidatorMessage");
            throw new ValidatorException(new FacesMessage(msg));
	}
    }
}
