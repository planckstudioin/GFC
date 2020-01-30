package `in`.ygohel18.admin.gfc.model

class Student {
    private var StudentID: Int = 0
    private var StudentName: String = ""

    constructor()

    constructor(id: Int, name: String) {
        this.StudentID = id
        this.StudentName = name
    }

    fun setStudentID(id: Int) {
        this.StudentID = id
    }

    fun setStudentName(name: String) {
        this.StudentName = name
    }

    fun getStudentID(): Int {
        return this.StudentID
    }

    fun getStudentName(): String {
        return this.StudentName
    }
}