package com.anatoliyvinokurov.quizanimegamekotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import java.util.*

class Level2 : AppCompatActivity() {

    var dialog: Dialog? = null
    var dialogEndTrue: Dialog? = null
    var dialogEndFalse: Dialog? = null
    var numLeft = 0
    var numRight = 0
    var powerLeft = 0
    var powerRight = 0
    val array = Array()
    var random = Random()
    var count = 0 // correct answer counter
    var levels = IntArray(30) // created an array containing answers (correct/incorrect/ and unanswered)
    var starsLevelTwo = 0

    private lateinit var sPref: SharedPreferences
    var str = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //assign a value to the variable count from the Internet using the key count
        count = intent.getIntExtra("count", 0) // if there is nothing on the count key, then i=0, otherwise i=value

        //loading the levels array from the save
        loadAnArrayWithLevels()

        //count the number of stars in the first level
        countNumberOfStarsInOneLevel()

        //Create a variable text_levels
        val text_levels = findViewById<TextView>(R.id.text_levels)
        text_levels.setText(R.string.level2)

        //set the background image for the first level
        val background = findViewById<ImageView>(R.id.background)
        background.setImageResource(R.drawable.level2)

        val img_left = findViewById<ImageView>(R.id.img_left)
        val img_right = findViewById<ImageView>(R.id.img_right)
        //code for rounding corners
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_left.clipToOutline = true
            img_right.clipToOutline = true
        }

        // Path to onscreen TextView
        val text_left = findViewById<TextView>(R.id.text_left)
        val text_right = findViewById<TextView>(R.id.text_right)

        //code for on-screen Back button"
        val buttonStart = findViewById<Button>(R.id.button_back)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@Level2, GameLevel2::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }

        //-1-//call the dialog box at the beginning of the game
        dialog = Dialog(this)//created a new dialog box
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
        dialog!!.setContentView(R.layout.previewdialog) //the path to the dialog box layout
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
        dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

        //put the image
        val previewimg = dialog!!.findViewById<ImageView>(R.id.previewimg)
        previewimg.setImageResource(R.drawable.preview_img_two)
        //put the text
        val textdescription = dialog!!.findViewById<View>(R.id.textdescription) as TextView
        textdescription.setText(R.string.leveltwo)
        //put the background image
        val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
        dialogfom.setBackgroundResource(R.drawable.preview_background_two)

        // button to close the dialog box
        val btnclose = dialog!!.findViewById<View>(R.id.btncloce) as TextView
        btnclose.setOnClickListener {
            try {
                val intent = Intent(this@Level2, GameLevel2::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
            dialog!!.dismiss() // closes the dialog box
        }
        // continue button
        val btncontinue = dialog!!.findViewById<View>(R.id.btncontinue) as Button
        btncontinue.setOnClickListener {
            try {
                dialog!!.dismiss() //closes the dialog box
            } catch (e:Exception){
            }
        }

        dialog!!.show()//show dialog box

        //-2-//call the dialog box at the end of the game if the user has completed the level correctly (true)
        dialogEndTrue = Dialog(this)//created a new dialog box
        dialogEndTrue!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
        dialogEndTrue!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
        dialogEndTrue!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
        dialogEndTrue!!.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT) //stretch the dialog box to the full screen
        dialogEndTrue!!.setCancelable(false) // disallow closing the dialog box with the back button

        //put the text
        val textdescriptionEndTrue = dialogEndTrue!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
        textdescriptionEndTrue.setText(R.string.leveltwofinishtrue)
        //put the background image
        val dialogfomEndTrue = dialogEndTrue!!.findViewById<LinearLayout>(R.id.dialogfom)
        dialogfomEndTrue.setBackgroundResource(R.drawable.preview_background_two)

        // button to close the dialog box
        val btncloseEndTrue = dialogEndTrue!!.findViewById<View>(R.id.btncloce) as TextView
        btncloseEndTrue.setOnClickListener {
            try {
                val intent = Intent(this@Level2, GameLevel2::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
            dialogEndTrue!!.dismiss() // closes the dialog box
        }
        // continue button
        val btncontinueEndTrue = dialogEndTrue!!.findViewById<View>(R.id.btncontinue) as Button
        btncontinueEndTrue.setOnClickListener {
            try {
                val intent = Intent(this@Level2, Level3::class.java)
                intent.putExtra("count", 0)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
            dialogEndTrue!!.dismiss() // closes the dialog box
        }

        //-3-//call a dialog box at the end of the game if the user has completed a level incorrectly (false)
        dialogEndFalse = Dialog(this)//created a new dialog box
        dialogEndFalse!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
        dialogEndFalse!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
        dialogEndFalse!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
        dialogEndFalse!!.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT) //stretch the dialog box to the full screen
        dialogEndFalse!!.setCancelable(false) // disallow closing the dialog box with the back button

        //put the text
        val textdescriptionEndFalse = dialogEndFalse!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
        textdescriptionEndFalse.setText(R.string.levelonefinishfalse)
        //put the background image
        val dialogfomEndFalse = dialogEndFalse!!.findViewById<LinearLayout>(R.id.dialogfom)
        dialogfomEndFalse.setBackgroundResource(R.drawable.preview_background_two)
        val btncontinueEndFalse = dialogEndFalse!!.findViewById<View>(R.id.btncontinue) as Button
        btncontinueEndFalse.setText(R.string.textstartover)


        // button to close the dialog box
        val btncloseEndFalse = dialogEndFalse!!.findViewById<View>(R.id.btncloce) as TextView
        btncloseEndFalse.setOnClickListener {
            try {
                val intent = Intent(this@Level2, GameLevel2::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
            dialogEndFalse!!.dismiss() // closes the dialog box
        }
        // continue button
        btncontinueEndFalse.setOnClickListener {
            count = 0
            dialogEndFalse!!.dismiss() // closes the dialog box
            //code to insert images from Array to screen
            assignedToThePicturesOfThePlace()//method for placing picture numbers
            img_left.setImageResource(array.images2[numLeft]) //Get from the array images 1 picture under the number generated randomly numLeft
            text_left.setText(array.texts2[numLeft]) //Get from array texts1 the inscription under the number generated randomly by numLeft
            powerLeft = array.power2[numLeft] ////Get from the array power 1 power value under the number generated randomly numLeft

            img_right.setImageResource(array.images2[numRight])
            text_right.setText(array.texts2[numRight])
            powerRight = array.power2[numRight]
        }

        //An array of elements to display the progress of the game
        val progress = intArrayOf(
            R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
            R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
            R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
            R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,
            R.id.point21, R.id.point22, R.id.point23, R.id.point24, R.id.point25,
            R.id.point26, R.id.point27, R.id.point28, R.id.point29, R.id.point30
        )

        val point14 = findViewById<View>(R.id.point14) as TextView
        val point15 = findViewById<View>(R.id.point15) as TextView
        val point16 = findViewById<View>(R.id.point16) as TextView
        val point17 = findViewById<View>(R.id.point17) as TextView
        val point18 = findViewById<View>(R.id.point18) as TextView
        val point19 = findViewById<View>(R.id.point19) as TextView
        val point20 = findViewById<View>(R.id.point20) as TextView
        val point21 = findViewById<View>(R.id.point21) as TextView
        val point22 = findViewById<View>(R.id.point22) as TextView
        val point23 = findViewById<View>(R.id.point23) as TextView
        val point24 = findViewById<View>(R.id.point24) as TextView
        val point25 = findViewById<View>(R.id.point25) as TextView
        val point26 = findViewById<View>(R.id.point26) as TextView
        val point27 = findViewById<View>(R.id.point27) as TextView
        val point28 = findViewById<View>(R.id.point28) as TextView
        val point29 = findViewById<View>(R.id.point29) as TextView
        val point30 = findViewById<View>(R.id.point30) as TextView

        point14.isInvisible = true
        point15.isInvisible = true
        point16.isInvisible = true
        point17.isInvisible = true
        point18.isInvisible = true
        point19.isInvisible = true
        point20.isInvisible = true
        point21.isInvisible = true
        point22.isInvisible = true
        point23.isInvisible = true
        point24.isInvisible = true
        point25.isInvisible = true
        point26.isInvisible = true
        point27.isInvisible = true
        point28.isInvisible = true
        point29.isInvisible = true
        point30.isInvisible = true

        //Animation
        val a = AnimationUtils.loadAnimation(this@Level2, R.anim.alpha)

        //code to insert images from Array to screen
        assignedToThePicturesOfThePlace()//method for placing picture numbers
        img_left.setImageResource(array.images2[numLeft]) //Get from the array images 1 picture under the number generated randomly numLeft
        text_left.setText(array.texts2[numLeft]) //Get from array texts1 the inscription under the number generated randomly by numLeft
        powerLeft = array.power2[numLeft] ////Get from the array power 1 power value under the number generated randomly numLeft

        img_right.setImageResource(array.images2[numRight])
        text_right.setText(array.texts2[numRight])
        powerRight = array.power2[numRight]

        //Paint the elements from the progress array
        for (i in 0..12) {
            when {
                levels[i] == 0 -> { //if the level I-th element in the array is zero ...
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points)//...then we paint the i-th element in gray
                }
                levels[i] == 1 -> {
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points_gold)//otherwise-in gold
                }
                levels[i] == -1 -> {
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points_grey)//otherwise - in grey
                }
            }
        }

        // process clicking on the left picture
        img_left.setOnTouchListener { v, event ->
            //if touched pictures
            if (event.action == MotionEvent.ACTION_DOWN) { //touch
                img_right.isEnabled = false //when touched to the left of the picture, need to lock the clicking the right
                //checking the correct answer
                if (powerLeft > powerRight) {
                    img_left.setImageResource(R.drawable.img_true)
                } else {
                    img_left.setImageResource(R.drawable.img_false)
                }
            } else if (event.action == MotionEvent.ACTION_UP) { //untouched
                // Check the answer for correctness
                if (powerLeft > powerRight) { //if the correct answer was given, then
                    // Paint an element from the progress array gold
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_gold)
                    levels[count] = 1 // write one to the level array
                } else { //if an incorrect answer was given, then
                    // Paint an element from the progress array in grey
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_grey)
                    levels[count] = -1 // write minus one to the level array
                }


                if (count == 12) { ////if this is the last level, then
                    // Output
                    countNumberOfStarsInOneLevel()
                    if (starsLevelTwo >= 6){
                        //call the dialog box at the end of the game if the user has completed the level correctly (true)
                        dialogEndTrue!!.show()
                    } else {
                        //call the dialog box at the end of the game if the user has completed the level incorrectly (false)
                        dialogEndFalse!!.show()
                    }

                } else {
                    count++ // increase points
                    // put pictures
                    assignedToThePicturesOfThePlace()
                    img_left.setImageResource(array.images2[numLeft])
                    img_left.startAnimation(a) //start animation
                    text_left.setText(array.texts2[numLeft])
                    powerLeft = array.power2[numLeft]
                    img_right.setImageResource(array.images2[numRight])
                    img_right.startAnimation(a) //start animation
                    text_right.setText(array.texts2[numRight])
                    powerRight = array.power2[numRight]
                }
                img_right.isEnabled = true //when unpressed with the finger of the left picture, need to unlock, press the right
            }
            true
        }

        // process clicking on the right picture
        img_right.setOnTouchListener { v, event ->
            //if touched pictures
            if (event.action == MotionEvent.ACTION_DOWN) { //touch
                img_left.isEnabled = false //when touched to the right of the picture, need to lock the clicking the left
                //checking the correct answer
                if (powerRight > powerLeft) {
                    img_right.setImageResource(R.drawable.img_true)
                } else {
                    img_right.setImageResource(R.drawable.img_false)
                }
            } else if (event.action == MotionEvent.ACTION_UP) { //untouched
                // Check the answer for correctness
                if (powerRight > powerLeft) { //if the correct answer was given, then
                    // Paint an element from the progress array gold
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_gold)
                    levels[count] = 1 // write one to the level array
                } else { //if an incorrect answer was given, then
                    // Paint an element from the progress array in grey
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_grey)
                    levels[count] = -1 // write minus one to the level array
                }


                if (count == 12) { ////if this is the last level, then
                    // Output
                    countNumberOfStarsInOneLevel()
                    if (starsLevelTwo >= 6){
                        //call the dialog box at the end of the game if the user has completed the level correctly (true)
                        dialogEndTrue!!.show()
                    } else {
                        //call the dialog box at the end of the game if the user has completed the level incorrectly (false)
                        dialogEndFalse!!.show()
                    }

                } else {
                    count++ // increase points
                    // put pictures
                    assignedToThePicturesOfThePlace()
                    img_left.setImageResource(array.images2[numLeft])
                    img_left.startAnimation(a) //start animation
                    text_left.setText(array.texts2[numLeft])
                    powerLeft = array.power2[numLeft]
                    img_right.setImageResource(array.images2[numRight])
                    img_right.startAnimation(a) //start animation
                    text_right.setText(array.texts2[numRight])
                    powerRight = array.power2[numRight]
                }
                img_left.isEnabled = true //when unpressed with the finger of the right picture, need to unlock, press the left
            }
            true
        }

    }

    ////method for placing picture numbers
    private fun assignedToThePicturesOfThePlace() {
        when (count) {
            0 -> {
                numLeft = 18
                numRight = 0
            }
            1 -> {
                numLeft = 1
                numRight = 14
            }
            2 -> {
                numLeft = 13
                numRight = 2
            }
            3 -> {
                numLeft = 3
                numRight = 20
            }
            4 -> {
                numLeft = 16
                numRight = 4
            }
            5 -> {
                numLeft = 5
                numRight = 25
            }
            6 -> {
                numLeft = 17
                numRight = 6
            }
            7 -> {
                numLeft = 22
                numRight = 7
            }
            8 -> {
                numLeft = 8
                numRight = 21
            }
            9 -> {
                numLeft = 9
                numRight = 23
            }
            10 -> {
                numLeft = 24
                numRight = 10
            }
            11 -> {
                numLeft = 19
                numRight = 11
            }
            12 -> {
                numLeft = 12
                numRight = 15
            }
            else -> {
                numLeft = random.nextInt(12)
                numRight = random.nextInt(12)
                while (numLeft == numRight) {
                    numRight = random.nextInt(12)
                }
            }
        }
    }


    //load the levels array from the save
    private fun loadAnArrayWithLevels() {
        try {
            sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
            var savedString: String = sPref.getString("level_two", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            val st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levels[i] = st.nextToken().toInt()
            }
        } catch (e:Exception) {
        }
    }
    //save the levels array to save
    private fun saveAnArrayWithLevels() {
        sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
        for (i in levels.indices) {
            str.append(levels[i]).append(",")
        }
        val ed = sPref.edit()
        ed.putString("level_two", str.toString())
        ed.commit()
    }

    //count the number of stars in the first level
    private fun countNumberOfStarsInOneLevel() {
        //counting the number of stars
        starsLevelTwo = 0
        for (i in 0..29) {
            if (levels[i] == 1) {
                starsLevelTwo++
            }
        }
        //save the value of the number of stars
        sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putInt("StarsInTwoLevel", starsLevelTwo)
        ed.commit()
    }


    // code for system back button
    override fun onBackPressed() {
        try {
            val intent = Intent(this@Level2, GameLevel2::class.java)
            startActivity(intent)
            finish()
        } catch (e:Exception) {
        }
    }

    override fun onResume() {
        super.onResume()
        countNumberOfStarsInOneLevel()
    }

    override fun onPause() {
        super.onPause()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }

    override fun onStop() {
        super.onStop()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }

    //code that is triggered when the form is destroyed/closed
    override fun onDestroy() {
        super.onDestroy()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }


}