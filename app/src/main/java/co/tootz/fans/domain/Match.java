package co.tootz.fans.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {

    private String id;
    private String firstTeamName;
    private String firstTeamScore;
    private String firstTeamAvatarUrl;
    private String firstTeamAvatarString;
    private String secondTeamName;
    private String secondTeamScore;
    private String secondTeamAvatarUrl;
    private String secondTeamAvatarString;

    public Match(String id, String firstTeamName, String firstTeamScore, String firstTeamAvatarUrl, String firstTeamAvatarString, String secondTeamName, String secondTeamScore, String secondTeamAvatarUrl, String secondTeamAvatarString) {
        this.id = id;
        this.firstTeamName = firstTeamName;
        this.firstTeamScore = firstTeamScore;
        this.firstTeamAvatarUrl = firstTeamAvatarUrl;
        this.firstTeamAvatarString = firstTeamAvatarString;
        this.secondTeamName = secondTeamName;
        this.secondTeamScore = secondTeamScore;
        this.secondTeamAvatarUrl = secondTeamAvatarUrl;
        this.secondTeamAvatarString = secondTeamAvatarString;
    }

    protected Match(Parcel in) {
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName = firstTeamName;
    }

    public String getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(String firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public String getFirstTeamAvatarUrl() {
        return firstTeamAvatarUrl;
    }

    public void setFirstTeamAvatarUrl(String firstTeamAvatarUrl) {
        this.firstTeamAvatarUrl = firstTeamAvatarUrl;
    }

    public String getFirstTeamAvatarString() {
        return firstTeamAvatarString;
    }

    public void setFirstTeamAvatarString(String firstTeamAvatarString) {
        this.firstTeamAvatarString = firstTeamAvatarString;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName = secondTeamName;
    }

    public String getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(String secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    public String getSecondTeamAvatarUrl() {
        return secondTeamAvatarUrl;
    }

    public void setSecondTeamAvatarUrl(String secondTeamAvatarUrl) {
        this.secondTeamAvatarUrl = secondTeamAvatarUrl;
    }

    public String getSecondTeamAvatarString() {
        return secondTeamAvatarString;
    }

    public void setSecondTeamAvatarString(String secondTeamAvatarString) {
        this.secondTeamAvatarString = secondTeamAvatarString;
    }
}
