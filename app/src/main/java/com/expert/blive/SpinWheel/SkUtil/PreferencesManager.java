package com.expert.blive.SpinWheel.SkUtil;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;



public class PreferencesManager extends MultiDexApplication {
    static SharedPreferences.Editor _prefEditor;
    static SharedPreferences _preferences;

    public void PreferencesManager() {
        super.onCreate();
    }

    public static void SetIsNew(Context context, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putInt("isNew", i);
        _prefEditor.commit();
    }

    public static int GetIsNew(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("isNew", 0);
    }

    public static void SetSound(Context context, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putInt("Sound", i);
        _prefEditor.commit();
    }

    public static int GetSound(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("Sound", 0);
    }

    public static void SetCoins(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("Coin", i);
            _prefEditor.commit();
            //updateScreen();
        }
    }

    public static int GetCoins(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("Coin", 0);
    }

    public static void SetHeart(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("Hart", i);
            _prefEditor.commit();
           // updateScreen();
        }
    }

    public static int GetHeart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("Hart", 0);
    }

    public static void SetDolller(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("Dolller", i);
            _prefEditor.commit();
            //updateScreen();
        }
    }

    public static int GetDolller(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("Dolller", 0);
    }

    public static void SetDolllerRequest(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("DolllerRequest", i);
            _prefEditor.commit();
           // updateScreen();
        }
    }

    public static int GetDolllerRequest(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("DolllerRequest", 0);
    }

    public static void SetAvtar(Context context, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putInt("Avtar", i);
        _prefEditor.commit();
      //  updateScreen();
    }

    public static int GetAvtar(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("Avtar", 0);
    }

    public static void SetUserName(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putString("UserName", str);
        _prefEditor.commit();
    }

    public static String GetUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getString("UserName", "");
    }

    public static void SetTodayDate(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putString("TodayDate", str);
        _prefEditor.commit();
    }

    public static String GetTodayDate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getString("TodayDate", "");
    }

    public static void SetDailyBonuse(Context context, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        _prefEditor = edit;
        edit.putInt("DailyBonuse", i);
        _prefEditor.commit();
    }

    public static int GetDailyBonuse(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("DailyBonuse", 0);
    }

    public static void SetTodaySpin(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("TodayFreeSpin", i);
            _prefEditor.commit();
            //updateScreen();
        }
    }

    public static int GetTodaySpin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("TodayFreeSpin", 0);
    }

    public static void SetTodayScratchCard(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("TodayScratchCard", i);
            _prefEditor.commit();
         //   updateScreen();
        }
    }

    public static int GetTodayScratchCard(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("TodayScratchCard", 0);
    }

    public static void SetTodayFlipCard(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("TodayFlipCard", i);
            _prefEditor.commit();
         //   updateScreen();
        }
    }

    public static int GetTodayFlipCard(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("TodayFlipCard", 0);
    }

    public static void SetTodaySlotMachine(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("TodaySlotMachine", i);
            _prefEditor.commit();
            //updateScreen();
        }
    }

    public static int GetTodaySlotMachine(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("TodaySlotMachine", 0);
    }

    public static void SetTodayUpDown(Context context, int i) {
        if (i >= 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
            _preferences = sharedPreferences;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            _prefEditor = edit;
            edit.putInt("TodayUpDown", i);
            _prefEditor.commit();
          //  updateScreen();
        }
    }

    public static int GetTodayUpDown(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PlayGameStore", 0);
        _preferences = sharedPreferences;
        return sharedPreferences.getInt("TodayUpDown", 0);
    }

//    private static void updateScreen() {
//        if (CoinStoreScreen.mHandler != null) {
//            Message message = new Message();
//            message.what = 100;
//            CoinStoreScreen.mHandler.sendMessage(message);
//        }
//        if (HomeScreen.mHandler != null) {
//            Message message2 = new Message();
//            message2.what = 100;
//            HomeScreen.mHandler.sendMessage(message2);
//        }
//        if (ProfileScreen.mHandler != null) {
//            Message message3 = new Message();
//            message3.what = 100;
//            ProfileScreen.mHandler.sendMessage(message3);
//        }
//        if (WalletScreen.mHandler != null) {
//            Message message4 = new Message();
//            message4.what = 100;
//            WalletScreen.mHandler.sendMessage(message4);
//        }
//        if (SpinWheelScreen.mHandler != null) {
//            Message message5 = new Message();
//            message5.what = 100;
//            SpinWheelScreen.mHandler.sendMessage(message5);
//        }
//        if (SpinWheel1.mHandler != null) {
//            Message message6 = new Message();
//            message6.what = 100;
//            SpinWheel1.mHandler.sendMessage(message6);
//        }
//        if (SpinWheel2.mHandler != null) {
//            Message message7 = new Message();
//            message7.what = 100;
//            SpinWheel2.mHandler.sendMessage(message7);
//        }
//        if (SpinWheel3.mHandler != null) {
//            Message message8 = new Message();
//            message8.what = 100;
//            SpinWheel3.mHandler.sendMessage(message8);
//        }
//        if (SpinWheel4.mHandler != null) {
//            Message message9 = new Message();
//            message9.what = 100;
//            SpinWheel4.mHandler.sendMessage(message9);
//        }
//        if (SpinWheel5.mHandler != null) {
//            Message message10 = new Message();
//            message10.what = 100;
//            SpinWheel5.mHandler.sendMessage(message10);
//        }
//        if (SpinWheel6.mHandler != null) {
//            Message message11 = new Message();
//            message11.what = 100;
//            SpinWheel6.mHandler.sendMessage(message11);
//        }
//        if (ScratchCardScreen.mHandler != null) {
//            Message message12 = new Message();
//            message12.what = 100;
//            ScratchCardScreen.mHandler.sendMessage(message12);
//        }
//        if (FreeScratchCardScreen.mHandler != null) {
//            Message message13 = new Message();
//            message13.what = 100;
//            FreeScratchCardScreen.mHandler.sendMessage(message13);
//        }
//        if (LuckyScratchCardScreen.mHandler != null) {
//            Message message14 = new Message();
//            message14.what = 100;
//            LuckyScratchCardScreen.mHandler.sendMessage(message14);
//        }
//        if (FlipCardScreen.mHandler != null) {
//            Message message15 = new Message();
//            message15.what = 100;
//            FlipCardScreen.mHandler.sendMessage(message15);
//        }
//        if (FreeFlipCardScreen.mHandler != null) {
//            Message message16 = new Message();
//            message16.what = 100;
//            FreeFlipCardScreen.mHandler.sendMessage(message16);
//        }
//        if (LuckyFlipCardScreen.mHandler != null) {
//            Message message17 = new Message();
//            message17.what = 100;
//            LuckyFlipCardScreen.mHandler.sendMessage(message17);
//        }
//        if (SlotMachineScreen.mHandler != null) {
//            Message message18 = new Message();
//            message18.what = 100;
//            SlotMachineScreen.mHandler.sendMessage(message18);
//        }
//        if (SlotMachine1.mHandler != null) {
//            Message message19 = new Message();
//            message19.what = 100;
//            SlotMachine1.mHandler.sendMessage(message19);
//        }
//        if (UpDownGameScreen.mHandler != null) {
//            Message message20 = new Message();
//            message20.what = 100;
//            UpDownGameScreen.mHandler.sendMessage(message20);
//        }
//        if (UpDownScreen1.mHandler != null) {
//            Message message21 = new Message();
//            message21.what = 100;
//            UpDownScreen1.mHandler.sendMessage(message21);
//        }
//        if (UpDownScreen2.mHandler != null) {
//            Message message22 = new Message();
//            message22.what = 100;
//            UpDownScreen2.mHandler.sendMessage(message22);
//        }
//        if (HeartStoreScreen.mHandler != null) {
//            Message message23 = new Message();
//            message23.what = 100;
//            HeartStoreScreen.mHandler.sendMessage(message23);
//        }
//    }
}
