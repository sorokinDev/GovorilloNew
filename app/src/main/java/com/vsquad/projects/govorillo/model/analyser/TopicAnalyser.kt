package com.vsquad.projects.govorillo.model.analyser

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.vsquad.projects.govorillo.model.entity.TopicEntity

/**
 * Created by Vova on 18.11.2017.
 */
open class TextAnalysisResult() : Parcelable{
    lateinit var text: String
    var speed: Int = 0
    var SMOG_index: Double = 0.0
    var waterness: Double = 0.0
    var speechTimeDiff = 0.0
    var preparingTimeDiff = 0.0
    var isRandomTopic = false

    constructor(parcel: Parcel) : this() {
        text = parcel.readString()
        speed = parcel.readInt()
        SMOG_index = parcel.readDouble()
        waterness = parcel.readDouble()
        speechTimeDiff = parcel.readDouble()
        preparingTimeDiff = parcel.readDouble()
        isRandomTopic = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeInt(speed)
        parcel.writeDouble(SMOG_index)
        parcel.writeDouble(waterness)
        parcel.writeDouble(speechTimeDiff)
        parcel.writeDouble(preparingTimeDiff)
        parcel.writeByte(if (isRandomTopic) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TextAnalysisResult> {
        override fun createFromParcel(parcel: Parcel): TextAnalysisResult {
            return TextAnalysisResult(parcel)
        }

        override fun newArray(size: Int): Array<TextAnalysisResult?> {
            return arrayOfNulls(size)
        }
    }

}
open class BaseTextAnalyser {
    companion object {
        val VOWELS = "уеыаоэяиюё"
        //Number of syllabes for long words
        val COMPLEX_SYL_FACTOR: Int = 4
        //Consts
        /*
        //default
        val SMOG_X_GRADE: Double = 1.1
        val SMOG_Y_GRADE: Double = 64.6
        val SMOG_Z_GRADE: Double = 0.05
        */
        //my
        val SMOG_X_GRADE: Double = 1.1
        val SMOG_Y_GRADE: Double = 64.6
        val SMOG_Z_GRADE: Double = 0.05

        val WATER_PHRASES = setOf("короче", "конечно", "однако", "это", "типа", "типо", "как бы", "так сказать",
                "прежде всего", "хочется отметить", "стоит отметить" , "хотелось бы отметить")

        fun analyse(txt: String, timeOfSpeech: Int, prepTime: Int? = null, topic: TopicEntity? = null): TextAnalysisResult {
            val res: TextAnalysisResult = TextAnalysisResult()
            val txtNoSigns = txt.replace("[^A-Za-zА-Яа-я0-9\\s]".toRegex(), "")

            res.text = txt
            Log.d("ANALYSER", res.text)
            Log.d("ANALYSER", txtNoSigns)
            res.speed = (txtNoSigns.split(" ").count()*60.0/timeOfSpeech).toInt()
            res.SMOG_index = getSMOGIndex(txt, txtNoSigns)
            res.waterness = getWaterness(txt, txtNoSigns)
            if(prepTime != null && topic != null){
                res.isRandomTopic = true
                res.speechTimeDiff = (timeOfSpeech - topic.speechTime).toDouble() / topic.speechTime
                res.preparingTimeDiff = (prepTime - topic.preparingTime).toDouble() / topic.preparingTime
            }

            return res
        }

        //region SMOG
        fun getSMOGIndex(txt: String, txtNoSigns: String): Double{
            var n_complex_words = 0
            for (wrd in txtNoSigns.split(' ')){
                var n_syl = 0
                for(chr in wrd){
                    if(VOWELS.contains(chr.toLowerCase())){
                        ++n_syl
                    }
                }
                if(n_syl >= COMPLEX_SYL_FACTOR){
                    ++n_complex_words
                }
            }

            var sentences = txt.split('.', '!', '?')
            var n_sent = (txtNoSigns.split(' ').count()/10.3).toInt()+1
/*
            for(sent in sentences){
                sent.replace(", ", ",")
                var prevComma = 0
                for(i in sent.indices){
                    if(sent[i] == ','){
                        var n_words = sent.substring(prevComma, i).split(' ').count()

                        if(n_words > 13) n_sent += 3
                        else if(n_words > 7) n_sent += 2
                        else if(n_words > 3) ++n_sent
                    }
                }
            }
*/

            /*
            for (chr in txt){
                if(chr == '.' || chr == '?' || chr == '!' || chr == ','){
                    ++n_sent
                }
            }
            */
            return SMOG_X_GRADE * Math.sqrt((SMOG_Y_GRADE/n_sent) * n_complex_words) + SMOG_Z_GRADE
        }
        //endregion

        //region WATERNESS

        fun getWaterness(txt: String, txtNoSigns: String): Double{
            var waterWords = 0
            for(sent in txt.split('.', '!', '?')){
                for(wp in WATER_PHRASES){
                    if(sent.toLowerCase().contains(wp)){
                        ++waterWords
                    }
                }
            }
            return (waterWords.toDouble() / txtNoSigns.split(' ').count().toDouble())
        }
        //endregion
    }
}