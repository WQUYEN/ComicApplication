package quyenvvph20946.fpl.supercomicapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Comic implements Parcelable {
    private String _id;

    private String title;
    private String description;
    private String author;
    private int years;
    private String coverImage;
    private List<String> images;

    public Comic() {
    }

    public Comic(String _id, String title, String description, String author, int years, String coverImage, List<String> images) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.years = years;
        this.coverImage = coverImage;
        this.images = images;
    }

    protected Comic(Parcel in) {
        _id = in.readString();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        years = in.readInt();
        coverImage = in.readString();
        images = in.createStringArrayList();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeInt(years);
        dest.writeString(coverImage);
        dest.writeStringList(images);
    }
}