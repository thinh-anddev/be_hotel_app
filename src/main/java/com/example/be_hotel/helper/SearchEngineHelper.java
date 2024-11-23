package com.example.be_hotel.helper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SearchEngineHelper {
    public static final Map<String, String> cityKeywordMap = new HashMap<>();

    static {
        cityKeywordMap.put("tphcm", "Hồ Chí Minh");
        cityKeywordMap.put("hcm", "Hồ Chí Minh");
        cityKeywordMap.put("hanoi", "Hà Nội");
        cityKeywordMap.put("danang", "Đà Nẵng");
        cityKeywordMap.put("quynhon", "Quy Nhơn");
        cityKeywordMap.put("hn", "Hà Nội");
        cityKeywordMap.put("haiphong", "Hải Phòng");
        cityKeywordMap.put("cantho", "Cần Thơ");
    }

    public static String processSearchQuery(String query) {
        query = query.trim().toLowerCase();

        if (cityKeywordMap.containsKey(query)) {
            return cityKeywordMap.get(query);
        }

        return query;
    }

    public static String getCityNameFromPartial(String query) {
        for (Map.Entry<String, String> entry : cityKeywordMap.entrySet()) {
            if (entry.getKey().contains(query.toLowerCase()) || entry.getValue().toLowerCase().contains(query.toLowerCase())) {
                return entry.getValue();
            }
        }
        return query;
    }

    public static String removeDiacritics(String input) {
        if (input == null) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
