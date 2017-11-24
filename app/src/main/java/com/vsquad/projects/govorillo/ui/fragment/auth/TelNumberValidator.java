package com.vsquad.projects.govorillo.ui.fragment.auth;

public class TelNumberValidator {
    private StringBuilder valide_string;

    public String validate(final String s){
        for (int i = s.length()-1; i > s.length()-11; i--) {
            valide_string.append(s.split("")[i]);
        }
        valide_string.append("7+");
        valide_string.reverse();
        return valide_string.toString();
    }
}
