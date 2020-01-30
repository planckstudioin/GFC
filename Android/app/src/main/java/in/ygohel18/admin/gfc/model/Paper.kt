package `in`.ygohel18.admin.gfc.model

class Paper {
    private var paperID = ""
    private var paperName = ""
    private var paperStatus = ""

    constructor()

    constructor(id: String, name: String, status: String) {
        this.paperID = id
        this.paperName = name
        this.paperStatus = status
    }

    fun setPaperID(r: String) {
        this.paperID = r
    }

    fun setPaperName(r: String) {
        this.paperName = r
    }

    fun setPaperStatus(r: String) {
        this.paperStatus = r
    }

    fun getPaperID(): String {
        return this.paperID
    }

    fun getPaperName(): String {
        return this.paperName
    }

    fun getPaperStatus(): String {
        return this.paperStatus
    }
}