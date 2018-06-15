package com.simpleague.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import nl.captcha.Captcha;

@FacesValidator("CaptchaValidator")
public class CaptchaValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!((UIInput)component).isValid()) {
            return;
        }
        String captchaText = (String)value;
        Captcha captcha = (Captcha)FacesUtil.getRequest().getSession().getAttribute(Captcha.NAME);

        if (captchaText == null || !captcha.isCorrect(captchaText)) {
            String msg = FacesUtil.getValidatorMessage(component, "captchaValidatorMessage");
            throw new ValidatorException(new FacesMessage(msg));
	}
    }
}
