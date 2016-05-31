
/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.telestax.mayday_customer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.telestax.mayday_customer.R;
import com.telestax.mayday_customer.fragment.CustomerOfferFragment;
import com.telestax.mayday_customer.fragment.CustomerSupportFragment;
import com.telestax.mayday_customer.fragment.CustomerProductFragment;
import com.telestax.mayday_customer.utils.CustomerConstant;

import timer.com.maydaysdk.MayDayIconConfiguration;
import timer.com.maydaysdk.MayDayMessageChatFragment;
import timer.com.maydaysdk.MayDayRegister;
import timer.com.maydaysdk.MayDayVideoCallFragment;

public class CustomerMainActivity extends AppCompatActivity implements CustomerOfferFragment.CustomerOfferInterface,
        CustomerProductFragment.ProductInterface, MayDayVideoCallFragment.VideoCallInterface,
        CustomerSupportFragment.CustomerSupportInterface, MayDayMessageChatFragment.MessageChatInterface {

    private FragmentManager mFragmentManagerContent;
    private FragmentManager mFragmentManagerMayDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManagerContent = getSupportFragmentManager();
        mFragmentManagerMayDay = getSupportFragmentManager();
        maydaySharePref(this, CustomerConstant.NO);
        showProductFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void showProductFragment() {
        mFragmentManagerContent.beginTransaction()
                .replace(R.id.fragment_content, new CustomerProductFragment()).commit();
    }

    private void showCustomerSupportFragment() {
        mFragmentManagerContent.beginTransaction()
                .replace(R.id.fragment_content, new CustomerSupportFragment()).addToBackStack(null).commit();
    }

    private void showCustomerOfferFragment() {
        mFragmentManagerContent.beginTransaction()
                .replace(R.id.fragment_content, new CustomerOfferFragment()).addToBackStack(null).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        maydaySharePref(this, CustomerConstant.NO);
        // The activity is about to be destroyed restcomm connection.
        MayDayRegister.mayDayShutDown();

    }

    @Override
    public void onProductItemSelect() {
        showCustomerSupportFragment();
    }

    @Override
    public void onVideoCall() {

        maydaySharePref(this, CustomerConstant.YES);
        Bundle videoBundle = getAgentDetails();

        MayDayVideoCallFragment videoCallFragment = new MayDayVideoCallFragment();
        videoCallFragment.setArguments(videoBundle);

        mFragmentManagerMayDay.beginTransaction()
                .replace(R.id.fragment_mayday, videoCallFragment).commit();
    }

    @Override
    public void onChatMessage() {
        maydaySharePref(this, CustomerConstant.YES);
        Bundle chatBundle = getAgentDetails();

        MayDayMessageChatFragment messageChatFragment = new MayDayMessageChatFragment();
        messageChatFragment.setArguments(chatBundle);

        mFragmentManagerMayDay.beginTransaction()
                .replace(R.id.fragment_mayday, messageChatFragment).commit();
    }

    @Override
    public void onBackArrow() {
        mFragmentManagerContent.popBackStack();
    }

    private Bundle getAgentDetails() {

        // Set video and chat icons to MayDay SDK view.
        MayDayIconConfiguration maydayIcon = MayDayIconConfiguration.getInstance();
        maydayIcon.setCallAnswerIcon(R.drawable.endcall_green);
        maydayIcon.setCallHangIcon(R.drawable.call_icon);
        maydayIcon.setMaximiseIcon(R.drawable.maximize);
        maydayIcon.setMinimiseIcon(R.drawable.minimize);
        maydayIcon.setMicOnIcon(R.drawable.speaker_icon);
        maydayIcon.setMicOffIcon(R.drawable.speaker_mute);
        maydayIcon.setChatMaximiseIcon(R.drawable.chat_maximize);
        maydayIcon.setChatMinimiseIcon(R.drawable.chat_minimize);
        maydayIcon.setChatCloseIcon(R.drawable.chat_close);
        maydayIcon.setChatSendIcon(R.drawable.message_send);

        SharedPreferences prefShare = getSharedPreferences(CustomerConstant.MY_PREFS_NAME, MODE_PRIVATE);
        String domainAddress = prefShare.getString(CustomerConstant.DOMAIN, null);
        String agentName = prefShare.getString(CustomerConstant.AGENT_NAME, null);
        Bundle bundle = new Bundle();
        bundle.putString(CustomerConstant.AGENT_NAME, agentName);
        bundle.putString(CustomerConstant.DOMAIN_ADDRESS, domainAddress);
        bundle.putString(CustomerConstant.VIDEO_CALL, CustomerConstant.OUTGOING);

        return bundle;
    }

    @Override
    public void onSupportClick() {
        showCustomerOfferFragment();
    }

    @Override
    public void onMayDayClose() {
      LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CustomerConstant.BROADCAST_INTENT));
            maydaySharePref(this, CustomerConstant.NO);
            mFragmentManagerMayDay.beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragment_mayday)).commit();
    }

    private static void maydaySharePref(Context context, String data) {

        SharedPreferences.Editor editor = context.getSharedPreferences(CustomerConstant.MY_MAYDAY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(CustomerConstant.ACTION, data);
        editor.apply();
    }

    public static String getMaydaySharePref(Context context) {
        SharedPreferences prefShare = context.getSharedPreferences(CustomerConstant.MY_MAYDAY_PREFS_NAME, MODE_PRIVATE);
        String restoredValue = prefShare.getString(CustomerConstant.ACTION, null);
        return restoredValue;
    }
}
