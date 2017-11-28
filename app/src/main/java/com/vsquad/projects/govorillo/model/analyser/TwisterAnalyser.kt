package com.vsquad.projects.govorillo.model.analyser

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.vsquad.projects.govorillo.model.entity.TwisterEntity

/**
 * Created by Vova on 24.11.2017.
 */

class TwisterSpeechResult(val twister: TwisterEntity, val recognitionRes: String, val speechTime: Int)


class TwisterAnalysisResult() : Parcelable{
    var speed: Int = 0
    var match: Double = 0.0

    constructor(parcel: Parcel) : this() {
        speed = parcel.readInt()
        match = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(speed)
        parcel.writeDouble(match)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TwisterAnalysisResult> {
        override fun createFromParcel(parcel: Parcel): TwisterAnalysisResult {
            return TwisterAnalysisResult(parcel)
        }

        override fun newArray(size: Int): Array<TwisterAnalysisResult?> {
            return arrayOfNulls(size)
        }
    }
}

class TwisterAnalyser {
    companion object {
        fun analyse(results: List<TwisterSpeechResult>): TwisterAnalysisResult{
            var res = TwisterAnalysisResult()
            var sumStr: String = ""
            var sumTime: Long = 0
            for(r in results){
                sumStr += r.twister.text.replace("[^A-Za-zа-яА-Я]".toRegex(), "")
                sumTime += r.speechTime
            }
            Log.d("TWISTER_ANALYSER", "STR: ${sumStr}")
            res.speed = (sumStr.length.toDouble() / sumTime * 1000 * 60).toInt()
            Log.d("TWISTER_ANALYSER", "SPEED: ${res.speed}")
            return res
        }
    }

}