package com.cmuhatia.android.keepup

import android.app.IntentService
import android.content.Intent
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.repository.KeepUpDatabase
import com.cmuhatia.android.keepup.repository.TimerGroupDao
import timber.log.Timber

class KeepUpTimersService : JobIntentService() {

    var activeTimers = 0

    override fun onDestroy() {
        Log.i(KeepUpTimersService::class.simpleName, "My work is done here bye :)")
        super.onDestroy()
    }

    override fun onHandleWork(intent: Intent) {
        Looper.prepare()
        val timerRepo = KeepUpDatabase.getInstance(applicationContext).timerGroupDao
        timerRepo.getTimers(true).forEach{timer ->
            CustomCountDownTimer(timer, timerRepo).start()
        }
        Looper.loop()

//        timerRepo.getTimers(true).onEach { timers ->
//            timers.onEach{timer ->
//                Toast.makeText(applicationContext, "KeepUp: Starting timers...", Toast.LENGTH_LONG).show()
//                startTimer(timer, timerRepo)
//            }
//        }
    }

}

class CustomCountDownTimer
    (private val timerEntity: TimerEntity, private val timerRepo: TimerGroupDao)
    : CountDownTimer(timerEntity.seconds * 1000, 1000) {

    init {
        Log.d(this.javaClass.simpleName, "Processing timer ${timerEntity.id}")
    }
    override fun onTick(millisUntilFinished: Long) {
        if(timerEntity.active) {
//            Log.d(this.javaClass.simpleName, "Updating timer ${timerEntity.id}. Seconds remaining ${millisUntilFinished}")
            timerEntity.seconds = millisUntilFinished / 1000
            timerRepo.updateTimers(timerEntity)
        } else {
            this.cancel()
        }
    }

    override fun onFinish() {
        timerEntity.active = false
        timerRepo.updateTimers(timerEntity)
    }

}