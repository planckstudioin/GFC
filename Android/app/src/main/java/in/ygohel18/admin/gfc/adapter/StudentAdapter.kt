package `in`.ygohel18.gfcadmin.adapter

import `in`.ygohel18.gfcadmin.R
import `in`.ygohel18.gfcadmin.model.Student
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

@Suppress("NAME_SHADOWING")
class StudentAdapter(private val context: Context, private val studentModelArrayList: ArrayList<Student>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return studentModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return studentModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.student_list, null, true)

            holder.idEdit = convertView!!.findViewById(R.id.student_id) as TextView
            holder.nameEdit = convertView.findViewById(R.id.student_name) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.idEdit!!.text = studentModelArrayList[position].id.toString()
        holder.nameEdit!!.text = studentModelArrayList[position].fullName
        return convertView
    }

    private inner class ViewHolder {
        var idEdit: TextView? = null
        var nameEdit: TextView? = null
    }
}
