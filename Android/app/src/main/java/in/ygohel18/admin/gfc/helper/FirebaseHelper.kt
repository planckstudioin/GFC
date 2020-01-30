package `in`.ygohel18.admin.gfc.helper

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    private var path = ""
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = this.firebaseDatabase.reference
    private var uid = ""

    constructor()

    constructor(uid: String, path: String) {
        this.uid = uid
        this.path = path
    }

    fun setPath(r: String) {
        this.path = r
    }


    fun setUid(r: String) {
        this.uid = r
    }

    fun getPath(): String {
        return this.path
    }

    fun getUid(): String {
        return this.uid
    }

    fun getDatabaseReference(): DatabaseReference {
        return this.databaseReference
    }

    fun close() {
        this.firebaseDatabase.goOffline()
    }

    fun open() {
        this.firebaseDatabase.goOnline()
    }
}