package github.ntn.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
/*
* ![CDATA[Bằng việc ấn vào nút <HIGHLIGHT><COLOR=#00428E/><CLICKABLE=0/>\"Đăng ký\"</HIGHLIGHT> bên dưới, bạn đồng ý bắt đầu thiết lập mối quan hệ tín dụng với Shinhan Finance. Đồng thời bạn cam kết cho Shinhan Finance quản lý, lưu trữ và sử dụng toàn bộ hoặc một phần thông tin mà bạn đã cung cấp với thời gian không xác định nhằm mục đích thẩm định hồ sơ vay, tiếp thị và nghiên cứu thị trường mà không cần thêm bất kỳ sự chấp thuận nào khác.\n\nĐồng thời, bạn cam kết đã đọc, hiểu và đồng ý với <HIGHLIGHT><COLOR=#00428E/><CLICKABLE=1/>Chính sách bảo mật</HIGHLIGHT> của Shinhan Finance và <HIGHLIGHT><COLOR=#00428E/><CLICKABLE=1/>Điều khoản sử dụng</HIGHLIGHT> ứng dụng iShinhan]]
*/
// Set màu sắc trong chuỗi và có thể set click text bất kì
class StringUtil {

    open class SpanData {
        var startIndex: Int = 0
        var endIndex: Int = 0
    }

    class ForegroundColorSpanData : SpanData() {
        var color: Int = 0
        var clickable: Boolean = false
    }

    companion object {

        fun formatSpannableString(s: String?, defaultColor: Int, action:(view: View) -> Unit): SpannableString {
            if (s == null || s.isBlank()) return SpannableString("")

            var str: String = s
            val list = mutableListOf<ForegroundColorSpanData>()

            val highlightTag = "<HIGHLIGHT>"
            val highlightCloseTag = "</HIGHLIGHT>"
            val colorTag = "<COLOR="
            val colorCloseTag = "/>"
            val clickableTag = "<CLICKABLE="
            val clickableCloseTag = "/>"
            val colorLength = 7//eg. #ff0000
            var startIndex = str.indexOf(highlightTag)

            while (startIndex != -1) {
                str = str.substring(0, startIndex) + str.substring(startIndex + highlightTag.length)

                var clickable = false
                val clickableStartIndex = str.indexOf(clickableTag)
                if (clickableStartIndex != -1) {
                    clickable = "1" == str.substring(clickableStartIndex + clickableTag.length, clickableStartIndex + clickableTag.length + 1)
                    str = str.substring(0, clickableStartIndex) + str.substring(clickableStartIndex + clickableTag.length + 1
                            + clickableCloseTag.length)
                }

                //get color
                val colorIndex = str.indexOf(colorTag)
                val color: Int
                if (colorIndex != -1) {
                    color = try {
                        Color.parseColor(str.substring(colorIndex + colorTag.length, colorIndex + colorTag.length + colorLength))
                    } catch (ignore: java.lang.Exception) { defaultColor }
                    str = str.substring(0, colorIndex) + str.substring(colorIndex + colorTag.length + colorLength + colorCloseTag.length)
                } else {
                    color = defaultColor
                }
                val endIndex = str.indexOf(highlightCloseTag)


                if (endIndex != -1) {
                    val data = ForegroundColorSpanData()
                    data.startIndex = startIndex
                    data.endIndex = endIndex
                    data.color = color
                    data.clickable = clickable
                    list.add(data)
                    str = str.substring(0, endIndex) + str.substring(endIndex + highlightCloseTag.length)
                }

                startIndex = str.indexOf(highlightTag)
            }

            //str = str.replace(highlightTag, "").replace(highlightCloseTag, "")
            val spanBuilder = SpannableString(str)
            list.forEach {
                spanBuilder.setSpan(ForegroundColorSpan(it.color), it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spanBuilder.setSpan(StyleSpan(Typeface.BOLD), it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                if (it.clickable) {
                    spanBuilder.setSpan(object : ClickableSpan() {
                        override fun onClick(view: View) {
                            action(view)
                        }
                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            // Set the color and other settings of the TextPaint to match non-clickable text
                            ds.color = it.color
                            ds.isUnderlineText = false
                        }
                    }, it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    spanBuilder.setSpan(object : UnderlineSpan() {
                        override fun updateDrawState(tp: TextPaint) {
                            tp.isUnderlineText = false
                        }
                    }, it.startIndex, it.endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            return spanBuilder
        }

//        fun formatClickableSpan(spannableString: SpannableString, action:(view: View) -> Unit): SpannableString {
//            val clickableTag = "<CLICKABLE>"
//            val clickableCloseTag = "</CLICKABLE>"
//            var str = spannableString.toString()
//            val startIndex = str.indexOf(clickableTag)
//            var endIndex = -1
//
//            if (startIndex != -1) {
//                str = str.replace(clickableTag, "")
//                endIndex = str.indexOf(clickableCloseTag)
//                str = str.replace(clickableCloseTag, "")
//            }
//            return if (endIndex != -1) {
//                val span = SpannableString(str)
//                span.setSpan(object : ClickableSpan() {
//                    override fun onClick(view: View) { action(view) }
//                }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                span
//            } else spannableString
//        }
    }
}