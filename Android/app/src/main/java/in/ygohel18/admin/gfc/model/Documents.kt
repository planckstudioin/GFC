package `in`.ygohel18.admin.gfc.model

class Documents {
    private var documentID = ""
    private var documentNO = ""
    private var documentName = ""
    private var documentExpiryDate = ""

    constructor()

    constructor(
        exp: String,
        id: String,
        no: String,
        name: String
    ) {
        this.documentID = id
        this.documentNO = no
        this.documentName = name
        this.documentExpiryDate = exp
    }

    fun setDocumentID(r: String) {
        this.documentID = r
    }

    fun setDocumentNO(r: String) {
        this.documentNO = r
    }

    fun setDocumentName(r: String) {
        this.documentName = r
    }

    fun setDocumentExpiryDate(r: String) {
        this.documentExpiryDate = r
    }

    fun getDocumentID(): String {
        return this.documentID
    }

    fun getDocumentNO(): String {
        return this.documentNO
    }

    fun getDocumentName(): String {
        return this.documentName
    }

    fun getDocumentExpiryDate(): String {
        return this.documentExpiryDate
    }

    fun divideDate(date: String): String {
        if (date == "0") return "EMPTY"
        if (date == "1") return "TU"
        val y: String = date.substring(0, 4)
        val m: String = date.substring(4, 6)
        val d: String = date.substring(6, 8)
        return "$d/$m/$y"
    }
}