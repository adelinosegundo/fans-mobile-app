package co.tootz.fans.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class FansRoom implements Parcelable {

    private String id;
    private String matchId;
    private String name;
    private int numberOfFans;

    public FansRoom(String id, String matchId, String name, int numberOfFans) {
        this.id = id;
        this.matchId = matchId;
        this.name = name;
        this.numberOfFans = numberOfFans;
    }

    protected FansRoom(Parcel in) {
        id = in.readString();
        matchId = in.readString();
        name = in.readString();
        numberOfFans = in.readInt();
    }

    public static final Creator<FansRoom> CREATOR = new Creator<FansRoom>() {
        @Override
        public FansRoom createFromParcel(Parcel in) {
            return new FansRoom(in);
        }

        @Override
        public FansRoom[] newArray(int size) {
            return new FansRoom[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfFans() {
        return numberOfFans;
    }

    public void setNumberOfFans(int numberOfFans) {
        this.numberOfFans = numberOfFans;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(matchId);
        dest.writeString(name);
        dest.writeInt(numberOfFans);
    }
}
