package com.cammoastay.zzon.payment.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaopayReqVo {
    private String cid;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private Integer quantity;
    private Integer total_amount;
    private Integer tax_free_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;

    public KakaopayReqVo(String cid, String receiveUrl, KakaopayReq kakaopayReq) {
        this.cid = cid;
        this.partner_order_id = kakaopayReq.getOrdId();
        this.partner_user_id = kakaopayReq.getUserId();
        this.item_name = kakaopayReq.getItemNm();
        this.quantity = kakaopayReq.getQuantity();
        this.total_amount = kakaopayReq.getItemAmt();
        this.tax_free_amount = kakaopayReq.getFreeAmt();
        this.approval_url = receiveUrl;
        this.cancel_url = receiveUrl;
        this.fail_url = receiveUrl;
    }



}
