package com.cammoastay.zzon.camp;

public record CampDto(
    int campId,
    String campName,
    String campState,
    String campCity,
    String campAddress,
    String campTel,
    String campNotice,
    String campIntro,
    String campService,
    String campRating

)
{
    public static CampEntity to(CampDto campDto) {
        return new CampEntity(

        );
    }

    public static CampDto from(CampEntity campEntity) {
       return new CampDto (
               campEntity.getCampId(),
               campEntity.getCampName(),
               campEntity.getCampState(),
               campEntity.getCampCity(),
               campEntity.getCampAddress(),
               campEntity.getCampTel(),
               campEntity.getCampNotice(),
               campEntity.getCampIntro(),
               campEntity.getCampService(),
               campEntity.getCampRating()
       );
    }


}
