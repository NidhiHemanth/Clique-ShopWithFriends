package com.bmsce.clique_shopwithfriends;

import android.text.TextUtils;
import androidx.annotation.NonNull;

public class OpenTokConfig {
    /*
    Fill the following variables using your own Project info from the OpenTok dashboard
    https://dashboard.tokbox.com/projects
    */

    // Replace with a API key
    public static final String API_KEY = "47434391";
        //14cda0eaf7b993b14a74fe26f7faf5203038c33c
    // Replace with a generated Session ID
    public static String SESSION_ID = "2_MX40NzQzNDM5MX5-MTY0MjgwMjY1MDQ5MX5QWnVMRm0vSzlMYkxaOWtmUmFqU3ZrYkV-fg";

    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    public static String TOKEN = "T1==cGFydG5lcl9pZD00NzQzNDM5MSZzaWc9MmEwMmMyYmU5YzQ2OWFjYWMyYzU2YzVkNzc3NTYzMmM4NWE4NmFkZTpzZXNzaW9uX2lkPTJfTVg0ME56UXpORE01TVg1LU1UWTBNamd3TWpZMU1EUTVNWDVRV25WTVJtMHZTemxNWWt4YU9XdG1VbUZxVTNacllrVi1mZyZjcmVhdGVfdGltZT0xNjQyODAyNjg5Jm5vbmNlPTAuMDYzODk1MzAxMjk5NzQ0OTcmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTY0MjgwNjI4OSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";

    public static boolean isValid() {
        if (TextUtils.isEmpty(OpenTokConfig.API_KEY)
                || TextUtils.isEmpty(OpenTokConfig.SESSION_ID)
                || TextUtils.isEmpty(OpenTokConfig.TOKEN)) {
            return false;
        }

        return true;
    }

    @NonNull
    public static String getDescription() {
        return "OpenTokConfig:" + "\n"
                + "API_KEY: " + OpenTokConfig.API_KEY + "\n"
                + "SESSION_ID: " + OpenTokConfig.SESSION_ID + "\n"
                + "TOKEN: " + OpenTokConfig.TOKEN + "\n";
    }
}

