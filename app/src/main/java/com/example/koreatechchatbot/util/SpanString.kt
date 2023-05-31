package com.example.koreatechchatbot.util

import android.os.Build
import android.text.SpannableString
import android.text.style.URLSpan
import android.text.util.Linkify

class SpanString(source: CharSequence) : SpannableString(source) {
    companion object {
        fun getLinkInfoList(text: String): List<LinkInfo> {
            val spanString = SpanString(text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Linkify.addLinks(spanString, Linkify.ALL) { str: String -> URLSpan(str) }
            } else {
                Linkify.addLinks(spanString, Linkify.ALL)
            }
            return spanString.linkInfos
        }
    }
    private inner class Data(
        val what: Any?,
        val start: Int,
        val end: Int
    )

    private val spanList = mutableListOf<Data>()

    private val linkInfos: List<LinkInfo>
        get() = spanList.filter { it.what is URLSpan }.map {
            LinkInfo(
                (it.what as URLSpan).url,
                it.start,
                it.end
            )
        }

    override fun removeSpan(what: Any?) {
        super.removeSpan(what)
        spanList.removeAll { it.what == what }
    }

    override fun setSpan(what: Any?, start: Int, end: Int, flags: Int) {
        super.setSpan(what, start, end, flags)
        spanList.add(Data(what, start, end))
    }
}

data class LinkInfo(
    val url: String,
    val start: Int,
    val end: Int
)