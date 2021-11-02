package telegram.free.roomlibrary;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @Ignore
    public Note(String content, String title) {
        this.content = content;
        this.title = title;
    }

    @PrimaryKey(autoGenerate = true)
    private int note_id;
    @ColumnInfo(name ="note")
    private String content;
    @ColumnInfo(name ="title")
    private String title;

    public Note(int note_id, String content, String title) {
        this.note_id = note_id;
        this.content = content;
        this.title = title;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
