package com.example.whodo.app.domain.user;

public class UserSpecRatingDTO {

    private String AppereanceScore;
    private String CleanlinessScore;
    private String SpeedScore;
    private String QualityScore;
    private String AvgTariff;
    private String AvgCompletionTime;
    private String OverallScore;


    public UserSpecRatingDTO() { }

    public UserSpecRatingDTO(String avgTariff, String avgCompletionTime, String overallScore,
                             String appearanceScore, String cleanlinessScore,
                             String speedScore, String qualityScore) {
        this.AvgTariff = avgTariff;
        this.AvgCompletionTime = avgCompletionTime;
        this.OverallScore = overallScore;
        this.AppereanceScore = appearanceScore;
        this.CleanlinessScore = cleanlinessScore;
        this.SpeedScore = speedScore;
        this.QualityScore = qualityScore;
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
}
