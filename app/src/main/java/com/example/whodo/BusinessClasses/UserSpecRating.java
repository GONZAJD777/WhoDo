package com.example.whodo.BusinessClasses;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.JsonAdapter;
@IgnoreExtraProperties
public class UserSpecRating {
    private String RatingSpec;
    private String AppereanceScore;
    private String CleanlinessScore;
    private String SpeedScore;
    private String QualityScore;
    private String AvgTariff;
    private String AvgCompletionTime;
    private String OverallScore;




    public UserSpecRating () {
        RatingSpec = "";
        AppereanceScore = "";
        CleanlinessScore = "";
        SpeedScore = "";
        QualityScore = "";
        AvgTariff = "";
        AvgCompletionTime = "";
        OverallScore = "";
    }
    @PropertyName("ratingSpec")
    public String getRatingSpec() {
        return RatingSpec;
    }
    public void setRatingSpec(String pRatingSpec){ RatingSpec=pRatingSpec; }

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

}
