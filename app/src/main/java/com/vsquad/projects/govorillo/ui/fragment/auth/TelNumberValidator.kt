package com.vsquad.projects.govorillo.ui.fragment.auth

class TelNumberValidator {
    private lateinit var valide_string: String

    fun validate(s: String): String {
        valide_string = s.replace("[^0-9]".toRegex(), "").reversed()
        if(valide_string.length >= 10){
            valide_string = (valide_string.substring(0, 10) + "7+").reversed()
        }else{
            valide_string = ""
        }

        return valide_string
    }
}
