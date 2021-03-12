package com.trueu.titigomedia.ui.home;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trueu.titigomedia.BaseDetailPager;
import com.trueu.titigomedia.R;
import com.trueu.titigomedia.encrypt.base.TextUtils;
import com.trueu.titigomedia.encrypt.symmetric.AESUtil;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Colin
 * on 2020/8/6
 * E-mail: hecanqi168@gmail.com
 */
public class HomePager extends BaseDetailPager {
    @BindView(R.id.input_tx)
    EditText inputTx;
    @BindView(R.id.encryption_btn)
    Button encryptionBtn;
    @BindView(R.id.decode_btn)
    Button decodeBtn;
    @BindView(R.id.result_tx)
    TextView resultTx;
    @BindView(R.id.copy_btn)
    TextView copyBtn;
    @BindView(R.id.paste_btn)
    TextView pasteBtn;
    @BindView(R.id.password_edit)
    EditText password;

    private ClipboardManager cm;
    private String mPasswordText, mInputText, mResultText;

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @OnClick({R.id.encryption_btn, R.id.decode_btn, R.id.copy_btn, R.id.paste_btn})
    public void onViewClicked(View view) {

        mPasswordText = password.getText().toString().trim();
        mInputText = inputTx.getText().toString().trim();
        mResultText = resultTx.getText().toString().trim();

        switch (view.getId()) {
            case R.id.encryption_btn:
                if (TextUtils.isEmpty(mPasswordText)) {
                    Toast.makeText(mActivity, "输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                resultTx.setText(AESUtil.aes(mInputText, mPasswordText, Cipher.ENCRYPT_MODE));
                break;
            case R.id.decode_btn:
                if (TextUtils.isEmpty(mPasswordText)) {
                    Toast.makeText(mActivity, "输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                resultTx.setText(AESUtil.aes(mInputText, mPasswordText, Cipher.DECRYPT_MODE));
                break;
            case R.id.paste_btn:
                inputTx.setText(cm.getText().toString().trim());
                break;
            case R.id.copy_btn:
                ClipData mClipData = ClipData.newPlainText("Label", mResultText);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
