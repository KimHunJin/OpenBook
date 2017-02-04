package org.team2.unithon.openbook.model;

import com.google.gson.annotations.SerializedName;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by HunJin on 2017-02-04.
 */

public class Test {
    @SerializedName("result")
    List<TestClass> result;

    public List<TestClass> getResult() {
        return result;
    }

    public void setResult(List<TestClass> result) {
        this.result = result;
    }
}
