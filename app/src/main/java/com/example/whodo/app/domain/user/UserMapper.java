package com.example.whodo.app.domain.user;


public class UserMapper {

      public static UserApiRestRequestDTO toApiRestRequestDTO(User pUser) {
        if (pUser == null) {
            return null;
        }

        UserApiRestRequestDTO mUserApiRestRequestDTO = new UserApiRestRequestDTO();
        mUserApiRestRequestDTO.setId(pUser.getId());
        mUserApiRestRequestDTO.setAuthId(pUser.getAuthId());
        mUserApiRestRequestDTO.setName(pUser.getName());
        if(pUser.getBirthday() !=null){
            mUserApiRestRequestDTO.setBirthday(new UserApiRestRequestDTO.UserDate(pUser.getBirthday()));
        }
        mUserApiRestRequestDTO.setEmail(pUser.getEmail());
        mUserApiRestRequestDTO.setAddress(pUser.getAddress());
        mUserApiRestRequestDTO.setType(pUser.getType());
        mUserApiRestRequestDTO.setPassword(pUser.getPassword());
        if(pUser.getCreateDate() !=null) {
            mUserApiRestRequestDTO.setCreateDate(new UserApiRestRequestDTO.UserDate(pUser.getCreateDate()));
        }
        if(pUser.getDeleteDate() !=null) {
            mUserApiRestRequestDTO.setDeleteDate(new UserApiRestRequestDTO.UserDate(pUser.getDeleteDate()));
        }
        mUserApiRestRequestDTO.setState(pUser.getState());
        mUserApiRestRequestDTO.setProfilePicture(pUser.getProfilePicture());
        mUserApiRestRequestDTO.setLanguages(pUser.getLanguages());
        mUserApiRestRequestDTO.setDescription(pUser.getDescription());
        mUserApiRestRequestDTO.setSpecialization(pUser.getSpecialization());

        // Manejo seguro de objetos anidados
        if(mUserApiRestRequestDTO.getLocation()!=null && pUser.getLocation()!=null){
            //esta validacion se incluye a pesar de que el constructor define el dato Location en su instanciacion
            mUserApiRestRequestDTO.getLocation().setLatitude(pUser.getLocation().getLatitude());
            mUserApiRestRequestDTO.getLocation().setLongitude(pUser.getLocation().getLongitude());
        }

        if(mUserApiRestRequestDTO.getPhone()!=null && pUser.getPhone()!=null){
            mUserApiRestRequestDTO.getPhone().setCcn(pUser.getPhone().getCcn());
            mUserApiRestRequestDTO.getPhone().setNumber(pUser.getPhone().getNumber());
        }

        if(mUserApiRestRequestDTO.getUserScore()!=null && pUser.getUserScore()!=null){
            mUserApiRestRequestDTO.getUserScore().setAppearanceScore(pUser.getUserScore().getAppearanceScore());
            mUserApiRestRequestDTO.getUserScore().setCleanlinessScore(pUser.getUserScore().getCleanlinessScore());
            mUserApiRestRequestDTO.getUserScore().setQualityScore(pUser.getUserScore().getQualityScore());
            mUserApiRestRequestDTO.getUserScore().setSpeedScore(pUser.getUserScore().getSpeedScore());
            mUserApiRestRequestDTO.getUserScore().setOverallScore(pUser.getUserScore().getOverallScore());
            mUserApiRestRequestDTO.getUserScore().setAvgCompletionTime(pUser.getUserScore().getAvgCompletionTime());
            mUserApiRestRequestDTO.getUserScore().setAvgTariff(pUser.getUserScore().getAvgTariff());
        }

        return mUserApiRestRequestDTO;
    }

}