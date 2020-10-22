package com.example.cri

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.sql.Time
import java.util.*
private const val ARG_TIME = "time"
class TimePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val TimeListener = TimePickerDialog.OnTimeSetListener {
                _: TimePicker, hour: Int, muint: Int ->
            val resultTime : Time = GregorianCalendar(Calendar.HOUR_OF_DAY,hour, muint).time as Time
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onTimeSelected(resultTime)

            }
        }
        val date = arguments?.getSerializable(ARG_TIME) as Time


        var calendar = Calendar.getInstance()
        calendar.time = date


        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)


        return TimePickerDialog(requireContext(), TimeListener, hour, minute, true)
    }

    companion object {
        fun newInstance(time: Time): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, time)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks {

        fun onTimeSelected(time: Time)
        {

        }

    }

}