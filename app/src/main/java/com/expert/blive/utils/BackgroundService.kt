package com.expert.blive.utils

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

import com.google.firebase.database.FirebaseDatabase

class BackgroundService : Service() {
    var count: CountDownTimer? = null

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val ref = firebaseDatabase.reference.child("userInfo")

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        count = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("onTick: ", "" + millisUntilFinished / 1000 + "")
                if (!App.getSingletone().isStop) {
                    count?.cancel()
                }
            }

            override fun onFinish() {
//                Log.i("onTick: ", App.getSingletone().isStop)
                if (App.getSingletone().isStop) {
                    App.getSingletone().isStop = true
                 //   setLiveStreamOffline()
                    Log.i("onTick: ", "finish")
                } else {

                }
            }
        }.start()
        return Service.START_STICKY;
    }


    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

//    private fun setLiveStreamOffline() {
//        val data = HashMap<String, Boolean>()
//        data["live"] = false
//        ref.child(getUserId()).child(liveType).child(getUserId()).child("hostLiveInfo")
//            .setValue(data)
//    }
}