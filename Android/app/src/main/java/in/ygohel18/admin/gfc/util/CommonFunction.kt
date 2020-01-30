package `in`.ygohel18.admin.gfc.util

class CommonFunction {

    private var separator = ""
    private var dateFormat = ""
    private var year = ""
    private var month = ""
    private var day = ""
    private var hour = ""
    private var minute = ""
    private var second = ""

    constructor()

    fun setSeparator(r: String) {
        this.separator = r
    }

    fun setDateFormat(r: String) {
        this.dateFormat = r
    }

    fun setYear(r: String) {
        this.year = r
    }

    fun setMonth(r: String) {
        this.month = r
    }

    fun setDay(r: String) {
        this.day = r
    }

    fun setHour(r: String) {
        this.hour = r
    }

    fun setMinute(r: String) {
        this.minute = r
    }

    fun setSecond(r: String) {
        this.second = r
    }

    fun getSeparator(): String {
        return this.separator
    }

    fun getDateFormat(): String {
        return this.dateFormat
    }

    fun getYear(): String {
        return this.year
    }

    fun getMonth(): String {
        return this.month
    }

    fun getDay(): String {
        return this.day
    }

    fun getHour(): String {
        return this.hour
    }

    fun getMinute(): String {
        return this.minute
    }

    fun getSecond(): String {
        return this.second
    }

    fun getDate(): String {
        val y: Int = this.getYear().toInt()
        val m: Int = this.getMonth().toInt()
        val d: Int = this.getDay().toInt()
        val f: String = this.getDateFormat()
        var date = ""

        when (f) {
            "yyyymmdd" -> {
                date = "$y$m$d"
            }

            "yyyyddmm" -> {
                date = "$y$d$m"
            }

            "ddmmyyyy" -> {
                date = "$d$m$y"
            }
        }
        return date
    }

    fun setDate(date: String, format: String) {
        var y = ""
        var m = ""
        var d = ""

        when (format) {
            "yyyymmdd" -> {
                y = date.substring(range = 0..3)
                m = date.substring(range = 4..5)
                d = date.substring(6)
            }

            "yyyyddmm" -> {
                y = date.substring(0, 4)
                d = date.substring(4, 6)
                m = date.substring(6, 8)
            }

            "ddmmyyyy" -> {
                y = date.substring(0, 2)
                m = date.substring(2, 4)
                d = date.substring(4, 8)
            }
        }

        this.setYear(y)
        this.setMonth(m)
        this.setDay(d)
        this.setDateFormat(format)
    }

    fun compareNumber(a: Int, b: Int): Int {
        return when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }

    fun compareNumber(a: Float, b: Float): Int {
        return when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }

    fun compareNumber(a: Double, b: Double): Int {
        return when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }

    fun getFormatedDate(): String {
        val f = this.getDateFormat()
        val s = this.getSeparator()
        val y = this.getYear()
        val m = this.getMonth()
        val d = this.getDay()
        var string = ""

        when (f) {
            "yyyymmdd" -> string = "$y$s$m$s$d"
            "yyyyddmm" -> string = "$y$s$d$s$m"
            "ddmmyyyy" -> string = "$d$s$m$s$y"
        }
        return string
    }

    fun divide(a: Int, b: Int): Int {
        return a - b
    }

    fun divide(a: Float, b: Float): Float {
        return a - b
    }

    fun compareDate(a: String, b: String, format: String): Int {
        var flag = 2
        val f: String = format
        val one = CommonFunction()
        val two = CommonFunction()

        one.setDate(a, f)
        two.setDate(b, f)

        val oy = one.getYear().toInt()
        val om = one.getMonth().toInt()
        val od = one.getDay().toInt()

        val ty = two.getYear().toInt()
        val tm = two.getMonth().toInt()
        val td = two.getDay().toInt()

        val y = this.compareNumber(oy, ty)
        val m = this.compareNumber(om, tm)
        val d = this.compareNumber(od, td)

        when (y) {
            0 -> when (m) {
                0 -> when (d) {
                    0 -> flag = 0
                    1 -> flag = 1
                    -1 -> flag = -1
                }
                1 -> flag = 1
                -1 -> flag = -1
            }
            1 -> flag = 1
            -1 -> flag = -1
        }
        return flag
    }
}