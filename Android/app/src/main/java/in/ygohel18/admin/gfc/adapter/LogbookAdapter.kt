package `in`.ygohel18.admin.gfc.adapter

import `in`.ygohel18.admin.gfc.R
import `in`.ygohel18.admin.gfc.model.Logbook
import `in`.ygohel18.admin.gfc.model.Time
import `in`.ygohel18.admin.gfc.util.CommonFunction
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

@Suppress("NAME_SHADOWING")
class LogbookAdapter(
    private val context: Context,
    private val logbookModelArrayList: ArrayList<Logbook>
) :
    BaseAdapter() {

    override fun getCount(): Int {
        return logbookModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return logbookModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_logbook, null, true)

            holder.from = convertView!!.findViewById(R.id.flight_takeoff_place) as TextView
            holder.to = convertView.findViewById(R.id.flight_landing_place) as TextView
            holder.dep = convertView.findViewById(R.id.flight_departure_time) as TextView
            holder.arr = convertView.findViewById(R.id.flight_arrival_time) as TextView
            holder.time = convertView.findViewById(R.id.flight_flying_time) as TextView
            holder.date = convertView.findViewById(R.id.flight_flying_date) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        val a = logbookModelArrayList[position].getArr()
        val d = logbookModelArrayList[position].getDep()
        val flyingTime = Time().subTime(d, a)
        val cf = CommonFunction()
        cf.setDate(logbookModelArrayList[position].getDate(), "yyyymmdd")
        cf.setDateFormat("ddmmyyyy")
        cf.setSeparator("-")
        val flyingDate = cf.getFormatedDate()

        holder.from!!.text = logbookModelArrayList[position].getFrom()
        holder.to!!.text = logbookModelArrayList[position].getTo()
        holder.dep!!.text = logbookModelArrayList[position].getDep()
        holder.arr!!.text = logbookModelArrayList[position].getArr()
        holder.time!!.text = flyingTime
        holder.date!!.text = flyingDate
        return convertView
    }

    private inner class ViewHolder {
        var from: TextView? = null
        var to: TextView? = null
        var dep: TextView? = null
        var arr: TextView? = null
        var time: TextView? = null
        var date: TextView? = null
    }
}
