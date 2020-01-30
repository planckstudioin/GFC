package `in`.ygohel18.admin.gfc.model

class Logbook {
    private var LogbookID: String = ""
    private var Date: String = ""
    private var Eng: String = ""
    private var Type: String = ""
    private var Regn: String = ""
    private var P1: String = ""
    private var P2: String = ""
    private var Arr: String = ""
    private var Dep: String = ""
    private var From: String = ""
    private var To: String = ""

    constructor()

    constructor(
        date: String,
        eng: String,
        type: String,
        regn: String,
        p1: String,
        p2: String,
        arr: String,
        dep: String,
        from: String,
        to: String
    ) {
        this.Date = date
        this.Eng = eng
        this.Type = type
        this.Regn = regn
        this.P1 = p1
        this.P2 = p2
        this.Arr = arr
        this.Dep = dep
        this.From = from
        this.To = to
    }

    fun setLogbookID(r: String) {
        this.LogbookID = r
    }

    fun setDate(r: String) {
        this.Date = r
    }

    fun setEng(r: String) {
        this.Eng = r
    }

    fun setType(r: String) {
        this.Type = r
    }

    fun setRegn(r: String) {
        this.Regn = r
    }

    fun setP1(r: String) {
        this.P1 = r
    }

    fun setP2(r: String) {
        this.P2 = r
    }

    fun setArr(r: String) {
        this.Arr = r
    }

    fun setDep(r: String) {
        this.Dep = r
    }

    fun setFrom(r: String) {
        this.From = r
    }

    fun setTo(r: String) {
        this.To = r
    }

    fun getLogbookID(): String {
        return this.LogbookID
    }

    fun getDate(): String {
        return this.Date
    }

    fun getType(): String {
        return this.Type
    }

    fun getEng(): String {
        return this.Eng
    }

    fun getRegn(): String {
        return this.Regn
    }

    fun getP1(): String {
        return this.P1
    }

    fun getP2(): String {
        return this.P2
    }

    fun getFrom(): String {
        return this.From
    }

    fun getTo(): String {
        return this.To
    }

    fun getArr(): String {
        return this.Arr
    }

    fun getDep(): String {
        return this.Dep
    }
}