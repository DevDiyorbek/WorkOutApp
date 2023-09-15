package com.example.workoutapp

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val  exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.gif_jumping_jacks,
            false,
            false
        )
        val abdominalCrunch = ExerciseModel(
            2,
            "Rotating hands",
            R.drawable.gif_hands_rotation,
            false,
            false
        )
        val highKneesRunning = ExerciseModel(
            3,
            "Rotating Foods",
            R.drawable.gif_foods_rotation,
            false,
            false
        )
        val pushUpRotation = ExerciseModel(
            4,
            "Push up",
            R.drawable.gif_push_ups,
            false,
            false
        )
        val lunge = ExerciseModel(
            5,
            "Double Crunch",
            R.drawable.gif_double_crunch,
            false,
            false
        )
        val plank = ExerciseModel(
            6,
            "Jump squats",
            R.drawable.gif_sitting_and_jumping,
            false,
            false
        )
        val pushUp = ExerciseModel(
            7,
            "Hitting. Right and left",
            R.drawable.gif_right_left_hitting,
            false,
            false
        )
        val squat = ExerciseModel(
            8,
            "Stand up and push up",
            R.drawable.gif_push_up_stand,
            false,
            false
        )
        val sidePlank = ExerciseModel(
            9,
            "Squat with one food",
            R.drawable.gif_squat_with_one_food,
            false,
            false
        )
        val stepUpToChair = ExerciseModel(
            10,
            "Push up and rotation",
            R.drawable.gif_push_up_and_rotation,
            false,
            false
        )


        exerciseList.add(jumpingJacks)
        exerciseList.add(abdominalCrunch)
        exerciseList.add(highKneesRunning)
        exerciseList.add(pushUpRotation)
        exerciseList.add(lunge)
        exerciseList.add(plank)
        exerciseList.add(pushUp)
        exerciseList.add(squat)
        exerciseList.add(sidePlank)
        exerciseList.add(stepUpToChair)



        return  exerciseList
    }

}