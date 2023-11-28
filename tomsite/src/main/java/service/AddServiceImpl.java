package service;

import interfaces.AddService;
import org.springframework.security.core.parameters.P;

import javax.servlet.http.Part;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class AddServiceImpl implements AddService {

    public String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        return formatForDateNow.format(dateNow);
    }

    public String setFormat(String rawText) {
        byte[] text = rawText.getBytes(ISO_8859_1);
        return new String(text, UTF_8);
    }

    public String toHtmlFormat(String rawStr) {
        byte[] text = rawStr.getBytes(ISO_8859_1);
        String newText = new String(text, UTF_8);


        // pre-processing
        char[] chars = newText.toCharArray();
        List<Character> charList = new ArrayList<>(Arrays.asList(toCharacterArray(chars)));
        StringBuilder preBuilder = new StringBuilder();

        for (Character value : charList) {
            if ((int) value == 13) {
                preBuilder.append('/');
            } else if((int) value == 10) {
                continue;
            } else {
                preBuilder.append(value);
            }
        }
        String raw = preBuilder.toString();
        // end

        // final-processing
        char[] chars1 = raw.toCharArray();
        List<Character> charList1 = new ArrayList<>(Arrays.asList(toCharacterArray(chars1)));

        String before = "<p class=\"description\" style=\"margin-top: 35px;\">";
        String ins = "</p> <p class=\"description\">";
        String after = "</p>";

        StringBuilder finalBuilder = new StringBuilder(before);

        for (int i = 0; i < charList1.size(); i++) {
            if(charList1.get(i) == '/') {
                int k = i, j = 0;

                while(charList1.get(k) == '/')
                {
                    k++;
                    j++;
                }
                k--;

                if(j == 1) finalBuilder.append(' ');
                if (j >= 2) finalBuilder.append('*');

                i = k;
            } else { finalBuilder.append(charList1.get(i)); }
        }

        finalBuilder.append(after);
        String resultDesc = finalBuilder.toString().replace("*", ins);
        // end

        return resultDesc;
    }


    public Character[] toCharacterArray(char[] charArray) {
        Character[] characterArray = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            characterArray[i] = charArray[i];
        }
        return characterArray;
    }


    public boolean imgCheck(List<Part> imgs) {
        int lim = 10000000;
        int noAtch = 0;
        boolean result = true;

        for (Part img : imgs) {
            if (img.getSize() > lim || img.getSize() == 0) {
                noAtch++;
            }
        }
        if(noAtch > 2) return false;

        return result;
    }


    public boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean capsCheck(List<String> caps) {
        int noAtch = 0;
        for(String cap : caps) {
            if(cap.isEmpty()) noAtch++;
        }

        if(noAtch > 2) return false;

        return true;
    }

    public List<String> getCapsData(List<String> data) {
        List<String> newData = new ArrayList<>();
        for(String item : data) if(!item.isEmpty()) newData.add(item);
        return newData;
    }
    public List<Part> getImgsData(List<Part> data) {
        List<Part> newData = new ArrayList<>();
        for (Part img : data) if(!(img.getSize() == 0)) newData.add(img);
        return newData;
    }


    public boolean infoCheck(List<String> info) {
        for (String item : info) if (item.isEmpty()) return false;
        return true;
    }

}
