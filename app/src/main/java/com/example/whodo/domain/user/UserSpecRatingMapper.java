package com.example.whodo.domain.user;

 public class UserSpecRatingMapper {

        public static UserSpecRatingDTO toDTO(UserSpecRating userSpecRating) {
            if (userSpecRating == null) {
                return null;
            }
            UserSpecRatingDTO dto = new UserSpecRatingDTO();
            dto.setAvgTariff(userSpecRating.getAvgTariff());
            dto.setAvgCompletionTime(userSpecRating.getAvgCompletionTime());
            dto.setOverallScore(userSpecRating.getOverallScore());
            dto.setAppereanceScore(userSpecRating.getAppereanceScore());
            dto.setCleanlinessScore(userSpecRating.getCleanlinessScore());
            dto.setSpeedScore(userSpecRating.getSpeedScore());
            dto.setQualityScore(userSpecRating.getQualityScore());
            return dto;
        }

        public static UserSpecRating toEntity(UserSpecRatingDTO userSpecRatingDTO) {
            if (userSpecRatingDTO == null) {
                return null;
            }
            UserSpecRating userSpecRating = new UserSpecRating();
            userSpecRating.setAvgTariff(userSpecRatingDTO.getAvgTariff());
            userSpecRating.setAvgCompletionTime(userSpecRatingDTO.getAvgCompletionTime());
            userSpecRating.setOverallScore(userSpecRatingDTO.getOverallScore());
            userSpecRating.setAppereanceScore(userSpecRatingDTO.getAppereanceScore());
            userSpecRating.setCleanlinessScore(userSpecRatingDTO.getCleanlinessScore());
            userSpecRating.setSpeedScore(userSpecRatingDTO.getSpeedScore());
            userSpecRating.setQualityScore(userSpecRatingDTO.getQualityScore());
            return userSpecRating;
        }
    }


