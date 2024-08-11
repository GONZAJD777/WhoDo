package com.example.whodo.app.domain.user;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserSpecRating {
    private String AppereanceScore;
    private String CleanlinessScore;
    private String SpeedScore;
    private String QualityScore;
    private String AvgTariff;
    private String AvgCompletionTime;
    private String OverallScore;



    public UserSpecRating () {
        AvgTariff = "0sat";
        AvgCompletionTime = "0hs";
        OverallScore = "0.0 (0)";
        AppereanceScore = "0.0";
        CleanlinessScore = "0.0";
        SpeedScore = "0.0";
        QualityScore = "0.0";
    }

    public String getAppereanceScore() {
        return AppereanceScore;
    }
    public void setAppereanceScore(String pAppereanceScore){ AppereanceScore=pAppereanceScore; }

    public String getCleanlinessScore() {
        return CleanlinessScore;
    }
    public void setCleanlinessScore(String pCleanlinessScore){ CleanlinessScore=pCleanlinessScore; }

    public String getSpeedScore() {
        return SpeedScore;
    }
    public void setSpeedScore(String pSpeedScore){ SpeedScore=pSpeedScore; }

    public String getQualityScore() {
        return QualityScore;
    }
    public void setQualityScore(String pQualityScore){ QualityScore=pQualityScore; }

    public String getAvgTariff() {
        return AvgTariff;
    }
    public void setAvgTariff(String pAvgTariff){ AvgTariff=pAvgTariff; }

    public String getAvgCompletionTime() {
        return AvgCompletionTime;
    }
    public void setAvgCompletionTime(String pAvgCompletionTime){ AvgCompletionTime=pAvgCompletionTime; }

    public String getOverallScore() {
        return OverallScore;
    }
    public void setOverallScore(String pOverallScore){ OverallScore=pOverallScore; }

    public UserSpecRating deepCopy() {
        UserSpecRating newUserSpecRating = new UserSpecRating();
        newUserSpecRating.setAppereanceScore(this.AppereanceScore);
        newUserSpecRating.setCleanlinessScore(this.CleanlinessScore);
        newUserSpecRating.setSpeedScore(this.SpeedScore);
        newUserSpecRating.setQualityScore(this.QualityScore);
        newUserSpecRating.setAvgTariff(this.AvgTariff);
        newUserSpecRating.setAvgCompletionTime(this.AvgCompletionTime);
        newUserSpecRating.setOverallScore(this.OverallScore);

        return newUserSpecRating;
    }
}
