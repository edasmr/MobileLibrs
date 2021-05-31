package com.example.mobilelibrs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilelibrs.databinding.LayoutLmSelectDateAndTimeSlotBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Activity_LM_Seelect_Date_and_Time_Slot : AppCompatActivity() {

    var libname:TextView? =null
    var lmID: TextView? = null
    var datePicker: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding to access layout
        val binding = LayoutLmSelectDateAndTimeSlotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Calendar for Date Picker
        val today = Calendar.getInstance()
        binding.datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            datePicker = "$day/$month/$year"
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Get database reference
        var database = FirebaseDatabase.getInstance().reference

        //Take the library name in l1 and print the textview
        libname = binding.tvLibraryName as TextView
        var libraryName2 = intent.getStringExtra("ln1")
        libname!!.setText("Library Name: "+ libraryName2)

        //Show lmID in textview
        lmID = binding.tvLmIDSelectDateTimeSlot  as TextView
        var lmID2 = intent.getStringExtra("userId2")
        lmID!!.setText("User"+ lmID2)


        //Click button to go Choose Table page with new entries
        binding.btnSearchTable.setOnClickListener {
            //Take date and time slot to choose table page
            var date2 = datePicker
            var timeslot2 = binding.spinnerTimeslot.selectedItem.toString()

            val newIntent = Intent(this, Activity_LM_Choose_Table::class.java)
            //Send these data to Choose Table page
            newIntent.putExtra("ln2", libraryName2)
            newIntent.putExtra("d2", date2)
            newIntent.putExtra("ts2", timeslot2)
            newIntent.putExtra("userId3", lmID2)
            startActivity(newIntent)
            finish()
        }
    }
}