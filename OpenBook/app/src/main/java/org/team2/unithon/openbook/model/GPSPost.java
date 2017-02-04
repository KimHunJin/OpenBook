package org.team2.unithon.openbook.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HunJin on 2017-02-04.
 * <p>
 * json으로 받아올 항목을 입력하세요.
 * json형식의 경우 이름이 똑같아야 합니다.
 * 타입은 문자열로 통일해도 무관합니다.
 * <p>
 * ex)                                  json)
 * String l;                            l : "gdfg",
 * List<String> value;                  value {
 * String k;                                a : "abc",
 * b : "bcd"
 * },
 * k : "fsda"
 */

public class GPSPost {

    @SerializedName("success")
    int success;

    @SerializedName("message")
    String message;

    @SerializedName("result")
    List<Shops> result;

    public List<Shops> getResult() {
        return result;
    }

    public void setResult(List<Shops> result) {
        this.result = result;
    }

    /*    @SerializedName("result")
    List<Shops> result;

    public List<Shops> getResult() {
        return result;
    }

    public void setResult(List<Shops> result) {
        this.result = result;
    }*/

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
