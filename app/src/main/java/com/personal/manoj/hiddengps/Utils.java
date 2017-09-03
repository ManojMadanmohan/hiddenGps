package com.personal.manoj.hiddengps;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.UUID;
import java.util.regex.Pattern;

public class Utils
{
    public static String getUserPhone(Context context)
    {
        TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        return phoneNumber;
    }

    public static String getUserEmail(Context context)
    {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        String backupEmail = null;
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                if(possibleEmail.contains("google") || possibleEmail.contains("gmail"))
                {
                    return possibleEmail;
                } else
                {
                    backupEmail = possibleEmail;
                }
            }
        }
        return backupEmail;
    }

    public static String getUserPhoneInfo()
    {
        String device = android.os.Build.DEVICE;          // Device
        String model = android.os.Build.MODEL;       // Model
        String product = android.os.Build.PRODUCT;

        return device+"---"+model+"---"+product;
    }

    public static String getUserId(Context context)
    {
        String rand = UUID.randomUUID().toString().substring(0,4);
        return getFirstAvlValue("rand"+rand, getUserPhone(context), getUserEmail(context), getUserPhoneInfo());
    }

    private static String getFirstAvlValue(String defaultVal,, String ...possibleValues)
    {
        for(String s: possibleValues)
        {
            if(!TextUtils.isEmpty(s))
            {
                return s;
            }
        }
        return defaultVal;
    }
}
