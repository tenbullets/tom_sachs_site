package interfaces;

import models.Exh;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface ExhRepository {
    Exh getExh(String tag);
    List<Exh> getExhList() throws IOException;
    void saveExh(String name, String tag, String place, String date, String eventDates, String sDesc, String desc, List<String> caps, List<Part> imgs) throws IOException;
    boolean findExh(String tag);
    void delExh(String tag, String imgsSource);
    String getImgsSource(String tag);
    List<String> getImgs(String tag, String imgsSource);
    List<Exh> getExhUpdate(List<Exh> exhList);
    Date getDate(String date);
    List<String> getCaps(String tag);
}