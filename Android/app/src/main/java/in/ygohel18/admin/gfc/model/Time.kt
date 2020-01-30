package `in`.ygohel18.admin.gfc.model

import `in`.ygohel18.admin.gfc.util.CommonFunction

@Suppress("UNUSED_CHANGED_VALUE")
class Time {
    private var total: String = "00:00"
    private var remain: String = "00:00"
    private var dual: String = "00:00"
    private var gftday: String = "00:00"
    private var gftnight: String = "00:00"
    private var vif: String = "00:00"
    private var vifremain: String = "00:00"
    private var ir: String = "00:00"
    private var night: String = "00:00"
    private var nightremain: String = "00:00"
    private var snight: String = "00:00"
    private var snightremain: String = "00:00"
    private var pic: String = "00:00"
    private var picremain: String = "00:00"
    private var sif: String = "00:00"
    private var sifremain: String = "00:00"
    private var solo: String = "00:00"
    private var soloremain: String = "00:00"
    private var sxcty: String = "00:00"
    private var sxctynight: String = "00:00"
    private var xcty: String = "00:00"
    private var xctyremain: String = "00:00"

    constructor()

    constructor(
        dual: String,
        gftday: String,
        gftnight: String,
        ir: String,
        night: String,
        pic: String,
        sif: String,
        snight: String,
        solo: String,
        sxcty: String,
        vif: String,
        xcty: String
    ) {
        this.dual = dual
        this.gftday = gftday
        this.gftnight = gftnight
        this.ir = ir
        this.night = night
        this.pic = pic
        this.sif = sif
        this.snight = snight
        this.solo = solo
        this.sxcty = sxcty
        this.vif = vif
        this.xcty = xcty
        this.sxctynight = getSxctyNight()
        this.total = getTotal()
        this.remain = getRemain()
        this.soloremain = getSoloRemain()
        this.vifremain = getIfRemain()
        this.nightremain = getNightRemain()
        this.xctyremain = getCtyRemain()
        this.sifremain = getSIfRemain()
        this.snightremain = getSNightRemain()
        this.picremain = getPicRemain()
    }

    fun calc() {

    }

    fun getDual(): String {
        return this.dual
    }

    fun setDual(t: String) {
        this.dual = t
    }

    fun getGftDay(): String {
        return this.gftday
    }

    fun setGftDay(t: String) {
        this.gftday = t
    }

    fun getGftNight(): String {
        return this.gftnight
    }

    fun setGftNight(t: String) {
        this.gftnight = t
    }

    fun getVif(): String {
        return this.vif
    }

    fun setVif(t: String) {
        this.vif = t
    }

    fun getIr(): String {
        return this.ir
    }

    fun setIr(t: String) {
        this.ir = t
    }

    fun getNight(): String {
        return this.night
    }

    fun setNight(t: String) {
        this.night = t
    }

    fun getPic(): String {
        return this.pic
    }

    fun setPic(t: String) {
        this.pic = t
    }

    fun getSif(): String {
        return this.sif
    }

    fun setSif(t: String) {
        this.sif = t
    }

    fun getSolo(): String {
        return this.solo
    }

    fun setSolo(t: String) {
        this.solo = t
    }

    fun getSnight(): String {
        return this.snight
    }

    fun setSnight(t: String) {
        this.snight = t
    }

    fun getXcty(): String {
        return this.xcty
    }

    fun setXcty(t: String) {
        this.xcty = t
    }

    fun getSxcty(): String {
        return this.sxcty
    }

    fun setSxcty(t: String) {
        this.sxcty = t
    }

    fun getSxctyNight(): String {
        return this.sxctynight
    }

    fun setSxctyNight(t: String) {
        this.sxctynight = t
    }

    fun getTotal(): String {
        this.total = setZero(totalTime(this.getSolo(), this.getDual()))
        return this.total
    }

    fun setTotal(t: String) {
        this.total = t
    }

    fun getRemain(): String {
        remain()
        return this.remain
    }

    fun remain() {
        this.remain = setZero(remainTime(this.total, 200))
    }

    fun setRemain(t: String) {
        this.remain = t
    }

    fun getSoloRemain(): String {
        remainSolo()
        return this.soloremain
    }

    fun remainSolo() {
        this.soloremain = setZero(remainTime(this.solo, 100))
    }

    fun setSoloRemain(t: String) {
        this.soloremain = t
    }

    fun getCtyRemain(): String {
        remainCty()
        return this.xctyremain
    }

    fun remainCty() {
        this.xctyremain = setZero(remainTime(this.xcty, 50))
    }

    fun setCtyRemain(t: String) {
        this.xctyremain = t
    }

    fun getSCtyNight(): String {
        return this.sxctynight
    }

    fun setSCtyNight(t: String) {
        this.sxctynight = t
    }

    fun getIfRemain(): String {
        remainIf()
        return this.vifremain
    }

    fun remainIf() {
        this.vifremain = setZero(remainTime(this.vif, 20))
    }

    fun setIfRemain(t: String) {
        this.vifremain = t
    }

    fun getNightRemain(): String {
        remainNight()
        return this.nightremain
    }

    fun remainNight() {
        this.nightremain = setZero(remainTime(this.night, 10))
    }

    fun setNightRemain(t: String) {
        this.nightremain = t
    }

    fun getPicRemain(): String {
        remainPic()
        return this.picremain
    }

    fun remainPic() {
        this.picremain = setZero(remainTime(this.pic, 15))
    }

    fun setPicRemain(t: String) {
        this.picremain = t
    }

    fun getSNightRemain(): String {
        remainSNight()
        return this.snightremain
    }

    fun remainSNight() {
        this.snightremain = setZero(remainTime(this.snight, 5))
    }

    fun setSNightRemain(t: String) {
        this.snightremain = t
    }

    fun getSIfRemain(): String {
        remainSIf()
        return this.sifremain
    }

    fun remainSIf() {
        this.sifremain = setZero(remainTime(this.sif, 5))
    }

    fun setSIfRemain(t: String) {
        this.sifremain = t
    }

    fun totalTime(solo: String, dual: String): String {
        val s = solo.split(":")
        val d = dual.split(":")

        var m: Int = s[1].toInt() + d[1].toInt()
        var h: Int = s[0].toInt() + d[0].toInt()

        if (m > 59) {
            if (m != 60) m -= 60 else m = 0
            h++
        }
        return "$h:$m"
    }

    fun subTime(a: String, b: String): String {
        val t1: List<String> = a.split(":")
        val t2: List<String> = b.split(":")
        val h1: Int = t1[0].toInt()
        val h2: Int = t2[0].toInt()
        val m1: Int = t1[1].toInt()
        val m2: Int = t2[1].toInt()
        var h: Int
        var m: Int = 0

        if (h2 == h1) {
            m = m2 - m1
            h = 0
        } else {
            m = (60 - m1) + m2

            if (h2 == (h1 + 1)) h = 0
            else h = ((h2 - 1) - h1)
        }

        if (m > 59) {
            if (m != 60) m -= 60 else m = 0
            h++
        }
        return "$h:$m"
    }

    private fun remainTime(time: String, limit: Int): String {
        val t: List<String> = time.split(":")
        var h: Int = t[0].toInt()
        var m: Int = t[1].toInt()
        val d: String = "00:00"

        if (h >= limit) {
            h = 0
            m = 0
            return d
        }
        if (h == 0) {

            h = if (m > 0) (limit - 1) else limit

            m = if (m != 0) (60 - m) else 0

            return "$h:$m"
        }
        if (m >= 0) {
            if (h == 0) {
                h = 0
                m = 0
            }
            if (h != limit) {
                h = (limit - 1) - h
            } else {
                h = 0
                m = 0
            }
            m = 60 - m
            if (h == 0 && m == 0) {
                h = (limit + 1)
                m = 0
            }
        } else {
            m = 0
        }
        return addHour(h, m)
    }

    private fun addHour(hour: Int, minute: Int): String {
        var h: Int = hour
        var m: Int = minute

        if (m == 60) {
            h += 1
            m = 0
        }
        return "$h:$m"
    }

    fun setZero(value: String): String {
        val t: List<String> = value.split(":")
        var h = t[0]
        var m = t[1]
        val d: String = "00:00"

        if (h.toInt() < 10) {
            if (h.toInt() <= 0) {
                h = "00"
            } else {
                h = "0$h"
            }
        }

        if (m.toInt() < 10) {
            if (m.toInt() == 0) {
                m = "00"
            } else {
                m = "0$m"
            }
        }

        if (h.toInt() < 0) {
            return d
        }

        return "$h:$m"
    }

    fun checkHour(time: String, limit: Int): Boolean {
        val t: List<String> = time.split(":")
        val h = t[0].toInt()
        val flag = CommonFunction().compareNumber(h, limit)
        return flag == 0 || flag == 1
    }

    fun checkMinute(time: String, limit: String): Boolean {
        val t: List<String> = time.split(":")
        val l: List<String> = limit.split(":")
        val h = t[0].toInt()
        val m = t[1].toInt()
        val lh = l[0].toInt()
        val lm = l[1].toInt()
        var f = 0

        if (h == lh) {
            if (h == 0 && lh == 0) {
                f = CommonFunction().compareNumber(m, lm)
            }
        }

        return f != -1
    }
}