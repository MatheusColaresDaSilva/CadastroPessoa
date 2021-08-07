package com.elotech.business;

import com.elotech.exception.EmailInvalidoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEmailValidator implements RegexValidator{
    @Override
    public boolean validator(String field) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@[\\w-\\.]+.com$");
        Matcher matcher = pattern.matcher(field);
        if(!matcher.find()) {
            throw new EmailInvalidoException();
        }

        return true;
    }
}
