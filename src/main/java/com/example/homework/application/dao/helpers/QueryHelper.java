package com.example.homework.application.dao.helpers;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryHelper {

    public boolean isPhraseInString(String string, String phrase) {
        return string != null && string.toLowerCase().contains(phrase.toLowerCase());
    }

    public boolean isPhraseInListOfString(List<String> list, String phrase) {
        return list != null ?
                list.stream().map(s -> s.toLowerCase().contains(phrase.toLowerCase()))
                        .reduce((a, b) -> a || b).orElse(false) :
                false;
    }

}
