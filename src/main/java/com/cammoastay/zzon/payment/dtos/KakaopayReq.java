package com.cammoastay.zzon.payment.dtos;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor

public class KakaopayReq{


    private String ordId;
    private String userId;
    private String itemNm;
    private int quantity;
    private int itemAmt;
    private int freeAmt;

    @Builder
    public KakaopayReq(String ordId, String userId, String itemNm, int quantity, int itemAmt, int freeAmt) {
        this.ordId = ordId;
        this.userId = userId;
        this.itemNm = itemNm;
        this.quantity = quantity;
        this.itemAmt = itemAmt;
        this.freeAmt = freeAmt;
    }


}


