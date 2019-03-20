package com.cxf.demo.Consumption;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cxf.demo.R;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.util.IntentUtil;
import com.cxf.moudule_common.util.ToastUtil;

public class MoneyAmountActivity extends BaseActivity {


    LinearLayout ll_moneyAcount;
    EditText et_moneyAcount;
    Button btn_next;





    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_money_amount);
    }

    @Override
    protected void setClickListening() {

        RxBindUtils.rxViewClicks(ll_moneyAcount, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_moneyAcount.requestFocus();
            }
        });

        RxBindUtils.rxViewClicks(btn_next, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String acount = getAcount(et_moneyAcount.getText().toString().trim());
                //确认金额后进行操作
                if(TextUtils.isEmpty(acount)){
                    return;
                }else{
                    IntentUtil.get().goActivityWithString(MoneyAmountActivity.this,DealManagerActivity.class,acount);
                }
            }
        });

    }

    @Override
    protected void bindViewById() {
        ll_moneyAcount = (LinearLayout)findViewById(R.id.ll_moneyAcount);
        et_moneyAcount = (EditText)findViewById(R.id.et_moneyAmount);
        btn_next = (Button)findViewById(R.id.btn_next);
    }

    @Override
    public void initData() {
        setTitle("消费");
    }


    private String getAcount(String acount){
        String[] s = acount.split("\\.");
        switch (s.length){
            case 1:
                if(8<s[0].length()){
                    ToastUtil.showShort(MoneyAmountActivity.this,"数目太大，请重新输入");
                    return "";
                }else{
                    return s[0];
                }
            case 2:
                if(2<s[1].length()){
                    ToastUtil.showShort(MoneyAmountActivity.this,"请保留小数点后两位");
                    return "";
                }else{
                    return acount;
                }
                default:
                    ToastUtil.showShort(MoneyAmountActivity.this,"金额错误，请重新输入");
                    return "";
        }
    }



}
