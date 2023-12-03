package interfaces;

import javax.servlet.http.Part;
import java.util.List;

public interface AddService {
    String getDate();
    String setFormat(String rawText);
    String toHtmlFormat(String rawStr);
    Character[] toCharacterArray(char[] charArray);
    boolean imgCheck(List<Part> imgs);
    boolean isDigit(String s) throws NumberFormatException;
    boolean capsCheck(List<String> caps);
    boolean infoCheck(List<String> info);
    List<String> getCapsData(List<String> data);
    List<Part> getImgsData(List<Part> data);
}
