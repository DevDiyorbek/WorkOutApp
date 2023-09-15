package com.example.workoutapp
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.DialogConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTime: Long = 1

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTime: Long = 3

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    private var videoView : VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarEx)

        tts = TextToSpeech(this, this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarEx?.setNavigationOnClickListener {
            this.onPause()
            customDialog()
        }
        exerciseList = Constants.defaultExerciseList()
        setUpRestTime()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        this.onPause()
        customDialog()
    }


    private fun customDialog(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpRestTime(){

        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.workoutapp/"+R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()

        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvProgressTitle?.visibility = View.VISIBLE
        binding?.layoutUpcomingExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.tvUpcomingExName?.text = exerciseList!![currentExercisePosition+1].getName()
        restTimer = object : CountDownTimer(restTime*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.ProgressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseTime()
            }
        }.start()
    }

    private fun setUpExerciseTime(){
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvProgressTitle?.visibility = View.INVISIBLE
        binding?.layoutUpcomingExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        if (exerciseList !=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.let { showGif(it,exerciseList!![currentExercisePosition].getImage()) }
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()


    }
    private fun showGif(imageViewID: ImageView, imageID: Int){
        Glide.with(this).load(imageID).into(imageViewID)
    }

    private fun setExerciseProgressBar(){
        speakOut(binding?.tvExerciseName?.text.toString())

        binding?.ExerciseProgressBar?.progress = exerciseProgress
        exerciseTimer = object  : CountDownTimer(exerciseTime*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.ExerciseProgressBar?.progress = 30 - exerciseProgress
                binding?.tvExerciseTimer?.text = (30 - exerciseProgress).toString()
            }
            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestTime()
                } else {
                    startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                }
            }
        }.start()
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_NOT_SUPPORTED ||
                    result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "INITIALIZING FAILED")
            }
        }
    }

    private fun speakOut(text : String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseList !=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null){
            player!!.stop()
        }

        binding = null
    }


}

//    private fun pauseTimer() {
//        if (restTimer != null) {
//            restTimer!!.cancel()
//        }
//    }

//    private fun resetTimer(){
//        if (restTimer != null){
//            restTimer!!.cancel()
//            binding?.tvTimer?.text = (workoutTime/1000).toString()
//            restTimer = null
//            restProgress = 0
//        }
//    }
