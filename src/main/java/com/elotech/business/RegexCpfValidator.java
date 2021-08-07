package com.elotech.business;

import com.elotech.exception.CpfInvalidoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCpfValidator implements RegexValidator{
    @Override
    public boolean validator(String field) {
        Pattern pattern = Pattern.compile("^\\d{11}$");
        Matcher matcher = pattern.matcher(field);
        if(!matcher.find()) {
            throw new CpfInvalidoException();
        }

        return true;
    }
}
